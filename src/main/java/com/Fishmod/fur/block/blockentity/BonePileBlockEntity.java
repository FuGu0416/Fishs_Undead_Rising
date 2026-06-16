package com.Fishmod.fur.block.blockentity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.tameable.ScarabEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURBlockEntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Beehive-style storage for {@link com.Fishmod.fur.block.BonePileBlock}. Starts empty and replenishes
 * one Scarab every 1–2 days up to {@link #MAX_SCARABS}. During the day the stored Scarabs roam out;
 * at night they return. Breaking the pile (without Silk Touch) releases the stored count plus 2–3
 * extra, angry at the breaker (see {@code BonePileBlock#playerWillDestroy}).
 */
public class BonePileBlockEntity extends BlockEntity {
    public static final int MAX_SCARABS = 3;
    /** 1 day (minimum) plus up to 1 more day of jitter → replenish every 1–2 days. */
    private static final int REPLENISH_MIN = 24000;
    private static final int REPLENISH_VAR = 24001;
    /** A roaming scarab is absorbed once within ~2.5 blocks (slightly past the return goal's arrive radius). */
    private static final double ABSORB_DIST_SQR = 6.25D;
    /** After this many ticks of night, force-absorb any stragglers that couldn't path home. */
    private static final int RECALL_GRACE = 2400;

    private int scarabs = 0;                 // owned count (0..MAX), authoritative
    private int replenishCooldown;           // ticks until the next +1
    private boolean released = false;        // are the owned scarabs currently out roaming?
    private boolean initialized = false;     // first-tick cooldown seeding done?
    private int recallTicks = 0;             // how long we've been trying to recall this night
    private final List<UUID> roamingScarabs = new ArrayList<>();

    public BonePileBlockEntity(BlockPos pos, BlockState state) {
        super(FURBlockEntityRegistry.BONE_PILE.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BonePileBlockEntity be) {
        if (!(level instanceof ServerLevel server)) {
            return;
        }

        if (!be.initialized) {
            be.replenishCooldown = REPLENISH_MIN + server.random.nextInt(REPLENISH_VAR);
            be.initialized = true;
            be.setChanged();
        }

        // Replenish over time, up to the cap.
        if (be.scarabs < MAX_SCARABS) {
            if (be.replenishCooldown > 0) {
                be.replenishCooldown--;
            }
            if (be.replenishCooldown <= 0) {
                be.scarabs++;
                be.replenishCooldown = REPLENISH_MIN + server.random.nextInt(REPLENISH_VAR);
                be.setChanged();
            }
        }

        // Day → roam out; night → walk home and get absorbed on arrival.
        if (server.isDay()) {
            be.recallTicks = 0;
            if (!be.released && be.scarabs > 0) {
                be.releaseScarabs(server, pos);
            }
        } else if (be.released) {
            be.recallTicks++;
            be.recallScarabs(server, be.recallTicks >= RECALL_GRACE);
        }
    }

    private void releaseScarabs(ServerLevel level, BlockPos pos) {
        roamingScarabs.clear();
        for (int i = 0; i < scarabs; i++) {
            ScarabEntity scarab = spawnScarab(level, pos, true, null);
            if (scarab != null) {
                scarab.setHomePos(this.worldPosition);   // so it can walk back here at night
                roamingScarabs.add(scarab.getUUID());
            }
        }
        released = true;
        setChanged();
    }

    /**
     * Absorb roaming scarabs that have reached the pile (their {@link ScarabReturnHomeGoal} walks them
     * here). Dead/missing ones reduce the owned count; the rest keep walking. {@code force} pulls in any
     * stragglers regardless of distance once the night grace period elapses.
     */
    private void recallScarabs(ServerLevel level, boolean force) {
        int lost = 0;
        boolean changed = false;
        List<UUID> remaining = new ArrayList<>();
        for (UUID id : roamingScarabs) {
            Entity entity = level.getEntity(id);
            if (entity instanceof ScarabEntity scarab && scarab.isAlive()) {
                double distSqr = scarab.distanceToSqr(
                        worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D);
                if (force || distSqr <= ABSORB_DIST_SQR) {
                    scarab.discard();   // arrived home — absorbed back into the pile
                    changed = true;
                } else {
                    remaining.add(id);  // still walking home
                }
            } else {
                lost++;   // died or vanished while roaming
            }
        }
        if (lost > 0) {
            scarabs = Mth.clamp(scarabs - lost, 0, MAX_SCARABS);
            changed = true;
        }
        roamingScarabs.clear();
        roamingScarabs.addAll(remaining);
        if (roamingScarabs.isEmpty()) {
            released = false;
            recallTicks = 0;
            changed = true;
        }
        if (changed) {
            setChanged();
        }
    }

    /** Player broke the pile without Silk Touch: vent the stored count plus 2–3 extra angry Scarabs. */
    public void onAngryBreak(ServerLevel level, BlockPos pos, @Nullable LivingEntity attacker) {
        discardRoaming(level);   // pull the roaming ones back in so the total isn't double-counted
        int count = scarabs + 2 + level.random.nextInt(2);   // current + 2~3
        for (int i = 0; i < count; i++) {
            spawnScarab(level, pos, false, attacker);
        }
        scarabs = 0;
        released = false;
        setChanged();
    }

    /** Remove any currently-roaming scarabs (block being removed). */
    public void discardRoaming(ServerLevel level) {
        for (UUID id : roamingScarabs) {
            Entity entity = level.getEntity(id);
            if (entity instanceof ScarabEntity scarab) {
                scarab.discard();
            }
        }
        roamingScarabs.clear();
    }

    @Nullable
    private ScarabEntity spawnScarab(ServerLevel level, BlockPos pos, boolean persistent, @Nullable LivingEntity target) {
        ScarabEntity scarab = SpawnUtil.trySpawnEntity(FUREntityRegistry.SCARAB.get(), level, pos);
        if (scarab == null) {
            return null;
        }
        if (persistent) {
            // Roaming pile-scarabs belong to the pile (no player owner), so keep them from despawning.
            scarab.setPersistenceRequired();
        }
        if (target != null) {
            scarab.setTarget(target);
        }
        return scarab;
    }

    public int getScarabs() {
        return this.scarabs;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Scarabs", this.scarabs);
        tag.putInt("ReplenishCooldown", this.replenishCooldown);
        tag.putBoolean("Released", this.released);
        tag.putBoolean("Initialized", this.initialized);
        tag.putInt("RecallTicks", this.recallTicks);
        ListTag list = new ListTag();
        for (UUID id : this.roamingScarabs) {
            list.add(NbtUtils.createUUID(id));
        }
        tag.put("Roaming", list);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.scarabs = Mth.clamp(tag.getInt("Scarabs"), 0, MAX_SCARABS);
        this.replenishCooldown = tag.getInt("ReplenishCooldown");
        this.released = tag.getBoolean("Released");
        this.initialized = tag.getBoolean("Initialized");
        this.recallTicks = tag.getInt("RecallTicks");
        this.roamingScarabs.clear();
        ListTag list = tag.getList("Roaming", Tag.TAG_INT_ARRAY);
        for (Tag t : list) {
            this.roamingScarabs.add(NbtUtils.loadUUID(t));
        }
    }
}
