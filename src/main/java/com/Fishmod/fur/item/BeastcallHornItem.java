package com.Fishmod.fur.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.entities.tameable.FURTameableEntity;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Beastcall Horn — a binding/recall tool for a single pet.
 *
 * <ul>
 *   <li>Right-click a pet: bind that pet.</li>
 *   <li>Right-click the air (unbound): auto-bind the nearest owned pet.</li>
 *   <li>Right-click the air (bound): summon the bound pet to a clear spot at your side and sit it down.</li>
 *   <li>Sneak + right-click a pet: override the current binding with that pet.</li>
 *   <li>Sneak + right-click the air: clear the binding.</li>
 * </ul>
 *
 * The bound pet is stored on the stack by UUID, so a single horn tracks one pet at a time.
 */
public class BeastcallHornItem extends Item {

    private static final String UUID_TAG = "BeastcallBound";
    private static final String NAME_TAG = "BeastcallName";
    /** Search radius for the air-click auto-bind of the nearest pet. */
    private static final double BIND_RADIUS = 16.0D;

    public BeastcallHornItem(Properties properties) {
        super(properties);
    }

    // ── Bind on clicking a pet directly ──────────────────────────────────────

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof TamableAnimal pet) || !pet.isOwnedBy(player)) {
            return InteractionResult.PASS;
        }

        if (player.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }

        UUID current = getBound(stack);
        // Normal click binds when nothing is bound (or re-affirms the same pet); a
        // different pet only overrides while sneaking, so a tap can't silently swap pets.
        if (current == null || player.isShiftKeyDown() || current.equals(pet.getUUID())) {
            setBound(stack, pet);
            player.displayClientMessage(Component.translatable("message.fur.beastcall_horn.bound", pet.getDisplayName()), true);
            playHorn(player, SoundEvents.AMETHYST_BLOCK_CHIME, 1.2F);
        } else {
            player.displayClientMessage(Component.translatable("message.fur.beastcall_horn.override", pet.getDisplayName()), true);
        }

        return InteractionResult.SUCCESS;
    }

    // ── Air click: bind nearest / summon bound / clear ───────────────────────

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        }

        // Sneak + air → clear the binding
        if (player.isShiftKeyDown()) {
            if (getBound(stack) != null) {
                clearBound(stack);
                player.displayClientMessage(Component.translatable("message.fur.beastcall_horn.cleared"), true);
                playHorn(player, SoundEvents.AMETHYST_BLOCK_HIT, 0.8F);
            }
            return InteractionResultHolder.success(stack);
        }

        UUID bound = getBound(stack);

        // Bound → summon it to the player's side and make it follow
        if (bound != null) {
            Entity entity = ((ServerLevel) level).getEntity(bound);
            if (entity instanceof TamableAnimal pet && pet.isAlive() && pet.isOwnedBy(player)) {
                summonToSide(pet, player, level);
                player.displayClientMessage(Component.translatable("message.fur.beastcall_horn.summon", pet.getDisplayName()), true);
                playHorn(player, SoundEvents.RAID_HORN.value(), 1.0F);
                player.getCooldowns().addCooldown(this, 40);
            } else {
                player.displayClientMessage(Component.translatable("message.fur.beastcall_horn.notfound"), true);
            }
            return InteractionResultHolder.success(stack);
        }

        // Unbound → auto-bind the nearest owned pet
        TamableAnimal nearest = findNearestPet(level, player);
        if (nearest != null) {
            setBound(stack, nearest);
            player.displayClientMessage(Component.translatable("message.fur.beastcall_horn.bound", nearest.getDisplayName()), true);
            playHorn(player, SoundEvents.AMETHYST_BLOCK_CHIME, 1.2F);
        } else {
            player.displayClientMessage(Component.translatable("message.fur.beastcall_horn.none"), true);
        }
        return InteractionResultHolder.success(stack);
    }

    // ── Behaviour helpers ────────────────────────────────────────────────────

    /** Teleports the pet to a clear spot next to the player and sits it down. */
    private static void summonToSide(TamableAnimal pet, Player player, Level level) {
        Vec3 spot = findSafeSpot(level, player, pet);
        pet.moveTo(spot.x, spot.y, spot.z, player.getYRot(), 0.0F);
        pet.getNavigation().stop();
        pet.setOrderedToSit(true);
        if (pet instanceof FURTameableEntity furPet) {
            furPet.doSitCommand(player);
        }
    }

    /**
     * Picks a spot beside the player where the pet's whole body fits without overlapping
     * blocks, so it can't be teleported into a wall and suffocate. Tries a ring of spots
     * around the player and falls back to the player's own position (always clear, since
     * the player is standing there).
     */
    private static Vec3 findSafeSpot(Level level, Player player, TamableAnimal pet) {
        double w = pet.getBbWidth();
        double h = pet.getBbHeight();
        double y = player.getY();
        for (int i = 0; i < 8; i++) {
            double angle = (Math.PI * 2.0D / 8.0D) * i;
            double x = player.getX() + Math.cos(angle) * 1.5D;
            double z = player.getZ() + Math.sin(angle) * 1.5D;
            AABB box = new AABB(x - w / 2.0D, y, z - w / 2.0D, x + w / 2.0D, y + h, z + w / 2.0D);
            if (level.noCollision(pet, box)) {
                return new Vec3(x, y, z);
            }
        }
        return player.position();
    }

    @Nullable
    private static TamableAnimal findNearestPet(Level level, Player player) {
        List<TamableAnimal> pets = level.getEntitiesOfClass(TamableAnimal.class,
                player.getBoundingBox().inflate(BIND_RADIUS),
                e -> e.isAlive() && e.isOwnedBy(player));

        TamableAnimal nearest = null;
        double best = Double.MAX_VALUE;
        for (TamableAnimal pet : pets) {
            double d = pet.distanceToSqr(player);
            if (d < best) {
                best = d;
                nearest = pet;
            }
        }
        return nearest;
    }

    private static void playHorn(Player player, SoundEvent sound, float pitch) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                sound, SoundSource.PLAYERS, 1.0F, pitch);
    }

    // ── NBT binding storage ──────────────────────────────────────────────────

    @Nullable
    private static UUID getBound(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return (tag != null && tag.hasUUID(UUID_TAG)) ? tag.getUUID(UUID_TAG) : null;
    }

    private static void setBound(ItemStack stack, TamableAnimal pet) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putUUID(UUID_TAG, pet.getUUID());
        tag.putString(NAME_TAG, pet.getDisplayName().getString());
    }

    private static void clearBound(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            tag.remove(UUID_TAG);
            tag.remove(NAME_TAG);
        }
    }

    // ── Visuals ──────────────────────────────────────────────────────────────

    /** A horn with a bound pet shows the enchantment glint, so a charged horn reads at a glance. */
    @Override
    public boolean isFoil(ItemStack stack) {
        return getBound(stack) != null || super.isFoil(stack);
    }

    // ── Tooltip ──────────────────────────────────────────────────────────────

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.hasUUID(UUID_TAG)) {
            tooltip.add(Component.translatable("item.fur.beastcall_horn.bound", tag.getString(NAME_TAG)).withStyle(ChatFormatting.GREEN));
        } else {
            tooltip.add(Component.translatable("item.fur.beastcall_horn.unbound").withStyle(ChatFormatting.GRAY));
        }
        tooltip.add(Component.translatable("item.fur.beastcall_horn.desc").withStyle(ChatFormatting.YELLOW));
    }
}
