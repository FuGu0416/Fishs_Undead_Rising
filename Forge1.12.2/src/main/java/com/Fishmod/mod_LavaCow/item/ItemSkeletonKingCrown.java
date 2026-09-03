package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.model.armor.ModelCrown;
import com.Fishmod.mod_LavaCow.entities.EntityBoneWorm;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIFollowEntity;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIGuardingEntity;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import baubles.api.IBauble;

/**
 * Governance of nearby skeletons is a live aura, not a one-time conversion: it is driven entirely
 * from the skeleton's own tick (see ModEventHandler#onSkeletonGuardUpkeep), which every 20 ticks
 * checks whether the skeleton's current owner (read back off its {@link EntityAIFollowEntity})
 * still wears this crown, and releases it the moment that stops being true. There is no persisted
 * "already tamed" marker: the AI task list itself is the single source of truth ({@link #getOwnerId}
 * returns null when ungoverned), so a skeleton that unloads and reloads simply comes back with
 * vanilla's own default AI (nothing to desync) and gets re-governed the next time it is next to a
 * wearer. Unlike the old implementation, this only strips the skeleton's actual attack-target
 * task(s) (stashed for exact restoration on release) instead of clearing the whole task/targetTask
 * lists -- the original always-hostile AI (sun avoidance, wolf avoidance, wandering, melee/bow
 * attack, ...) is left completely untouched. Ported from the 1.20.1/1.16.5 redesign.
 */
@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class ItemSkeletonKingCrown extends ItemArmor implements IBauble {
    /**
     * Vanilla target tasks stripped from a governed skeleton, keyed by the skeleton itself so they
     * can be restored verbatim (same instances, same priorities) on release. WeakHashMap so entries
     * for skeletons that die/unload while governed are reclaimed by the GC instead of leaking.
     */
    private static final Map<EntityCreature, List<EntityAITasks.EntityAITaskEntry>> SUPPRESSED_TARGET_TASKS = new WeakHashMap<>();

    private ModelCrown modelCrown;

    public ItemSkeletonKingCrown(String registryName, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(FishItems.ARMOR_CROWN, renderIndexIn, equipmentSlotIn);
        setTranslationKey(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
    }

    /**
     * Returns true if this item has an enchantment glint. By default, this returns
     * <code>stack.isItemEnchanted()</code>, but other items can override it (for instance, written books always return
     * true).
     * <p>
     * Note that if you override this method, you generally want to also call the super version (on {@link Item}) to get
     * the glint for enchanted items. Of course, that is unnecessary if the overwritten version always returns true.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    /**
     * Return an item rarity from EnumRarity
     */
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public boolean getIsRepairable(ItemStack armour, ItemStack material) {
        return material.getItem() == FishItems.HATRED_SHARD;
    }

    /**
     * Whether this mob is the kind of thing the crown can govern at all. 1.12.2 has no data-driven
     * entity type tags (that's a 1.13+ feature), so unlike 1.20.1/1.16.5 this is a plain Java
     * instanceof union instead of a tag lookup -- it lists the same "skeleton family" set as the
     * {@code minecraft:skeletons} tag extension on the other two versions (vanilla AbstractSkeleton
     * + Bone Worm; Forsaken is already covered since it extends AbstractSkeleton). Adding a future
     * skeleton-family mob here means adding it to this instanceof chain, since there is no tag to
     * extend instead. EntitySkeletonKing (the boss) extends EntityMob directly, so it is naturally
     * excluded without needing an explicit check.
     */
    public static boolean isEligible(EntityCreature mob) {
        return (mob instanceof AbstractSkeleton) || (mob instanceof EntityBoneWorm);
    }

    /**
     * The UUID of the player currently commanding this skeleton, or null if it isn't governed. This
     * is the only place "governed" is decided; nothing else tracks it.
     */
    @Nullable
    public static UUID getOwnerId(EntityCreature skeleton) {
        for (EntityAITasks.EntityAITaskEntry entry : skeleton.tasks.taskEntries) {
            if (entry.action instanceof EntityAIFollowEntity) {
                return ((EntityAIFollowEntity) entry.action).getOwnerId();
            }
        }
        return null;
    }

    /**
     * Whether the given entity currently wears this crown -- the vanilla HEAD armor slot, or (for
     * players, with Baubles installed) the Baubles HEAD bauble slot. Same dual-slot idiom
     * ModEventHandler#onESetTarget already uses for the Illager Nose disguise check.
     */
    public static boolean isWearingCrown(EntityLivingBase entity) {
        if (entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(FishItems.SKELETONKING_CROWN)) {
            return true;
        }
        if (entity instanceof EntityPlayer && Loader.isModLoaded("baubles")) {
            return baubles.api.BaublesApi.isBaubleEquipped((EntityPlayer) entity, FishItems.SKELETONKING_CROWN) != -1;
        }
        return false;
    }

    /**
     * Puts a still-ungoverned skeleton under the given player's command: strips its vanilla
     * attack-target task(s) (stashed for later restoration) and wires in the follow / defend-owner
     * tasks. Caller is expected to have already checked {@link #getOwnerId} is null and the
     * navigation type.
     */
    public static void govern(EntityCreature skeleton, EntityPlayer owner) {
        List<EntityAITasks.EntityAITaskEntry> stripped = skeleton.targetTasks.taskEntries.stream()
                .filter(entry -> entry.action instanceof EntityAINearestAttackableTarget)
                .collect(Collectors.toList());
        stripped.forEach(entry -> skeleton.targetTasks.removeTask(entry.action));
        SUPPRESSED_TARGET_TASKS.put(skeleton, stripped);

        skeleton.playSound(SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, 1.0F, 1.0F);
        // Broadcast via the WorldServer overload -- govern() only runs server side, where the
        // per-particle World.spawnParticle dispatches into ServerWorldEventHandler's empty body.
        if (skeleton.world instanceof WorldServer) {
            ((WorldServer) skeleton.world).spawnParticle(EnumParticleTypes.SPELL_MOB, skeleton.posX, skeleton.posY + (double) (skeleton.height * 0.5F), skeleton.posZ, 16, (double) (skeleton.width * 0.5F), (double) (skeleton.height * 0.5F), (double) (skeleton.width * 0.5F), 0.02D);
        }

        skeleton.tasks.addTask(6, new EntityAIFollowEntity(skeleton, owner.getUniqueID(), 1.0D, 10.0F, 2.0F));
        skeleton.targetTasks.addTask(1, new EntityAIGuardingEntity(skeleton, owner.getUniqueID()));
    }

    /**
     * Releases a governed skeleton: removes the follow / defend-owner tasks and restores whatever
     * vanilla attack-target task(s) were stripped in {@link #govern}, at their original priorities.
     */
    public static void release(EntityCreature skeleton) {
        List<EntityAIBase> ownTasks = skeleton.tasks.taskEntries.stream()
                .map(entry -> entry.action)
                .filter(action -> action instanceof EntityAIFollowEntity)
                .collect(Collectors.toList());
        ownTasks.forEach(skeleton.tasks::removeTask);

        List<EntityAIBase> ownTargetTasks = skeleton.targetTasks.taskEntries.stream()
                .map(entry -> entry.action)
                .filter(action -> action instanceof EntityAIGuardingEntity)
                .collect(Collectors.toList());
        ownTargetTasks.forEach(skeleton.targetTasks::removeTask);

        List<EntityAITasks.EntityAITaskEntry> restored = SUPPRESSED_TARGET_TASKS.remove(skeleton);
        if (restored != null) {
            for (EntityAITasks.EntityAITaskEntry entry : restored) {
                skeleton.targetTasks.addTask(entry.priority, entry.action);
            }
        }
    }

    /**
     * True if the skeleton's navigator is one {@link EntityAIFollowEntity} actually supports --
     * mirrors the check its constructor itself makes, so we never construct one just to have it
     * throw.
     */
    public static boolean canGovern(EntityCreature skeleton) {
        return (skeleton.getNavigator() instanceof PathNavigateGround) || (skeleton.getNavigator() instanceof PathNavigateFlying);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
        return "mod_lavacow:textures/armors/kings_crown/kings_crown.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase player, ItemStack stack, EntityEquipmentSlot armorSlot, ModelBiped modelBiped) {
        if (this.modelCrown == null) {
            this.modelCrown = new ModelCrown(1.0F);
        }

        return this.modelCrown;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
        list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.skeletonking_crown"));
    }

    /**
     * Baubles support: the crown can be worn in the Baubles HEAD slot (in addition to the vanilla
     * head armor slot), same as the Illager Nose. {@link #isWearingCrown} already checks both slots,
     * so governance works identically regardless of which one the wearer uses.
     */
    @Override
    @Optional.Method(modid = "baubles")
    public baubles.api.BaubleType getBaubleType(ItemStack stack) {
        return baubles.api.BaubleType.HEAD;
    }

    @Override
    @Optional.Method(modid = "baubles")
    public boolean canEquip(ItemStack stack, EntityLivingBase entity) {
        return true;
    }

    @Override
    @Optional.Method(modid = "baubles")
    public boolean canUnequip(ItemStack stack, EntityLivingBase entity) {
        return true;
    }

    @Override
    @Optional.Method(modid = "baubles")
    public void onWornTick(ItemStack stack, EntityLivingBase entity) {
    }

}
