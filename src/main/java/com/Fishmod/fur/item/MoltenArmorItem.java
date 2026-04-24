package com.Fishmod.fur.item;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class MoltenArmorItem extends ArmorItem {

    // ── Set piece thresholds ─────────────────────────────────────────────────
    /** Minimum pieces worn to trigger the retaliation burn effect. */
    private static final int BURN_THRESHOLD    = 2;
    /** Pieces required for the full-set bonus. */
    private static final int FULLSET_THRESHOLD = 4;
    /** Fire damage reduction for full set (0.5 = 50%). */
    private static final float FIRE_REDUCTION  = 0.5F;

    public MoltenArmorItem(ArmorMaterials material, ArmorItem.Type slot, Item.Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
        return material.getItem() == FURItemRegistry.MOLTEN_ALLOY.get();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) mod_LavaCow.PROXY.getArmorProperties());
    }
    
	private static boolean isSoulforged(ItemStack stack) {
		return stack.getItem().getDescriptionId().contains("soulforged");
	}

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
    	if (isSoulforged(stack)) {
	        if (slot == EquipmentSlot.LEGS) {
	            return mod_LavaCow.MODID + ":textures/armors/soulforged/soulforged_layer_2.png";
	        } else {
	            return mod_LavaCow.MODID + ":textures/armors/soulforged/soulforged_layer_1.png";
	        }    		
    	} else {
	        if (slot == EquipmentSlot.LEGS) {
	            return mod_LavaCow.MODID + ":textures/armors/molten/molten_layer_2.png";
	        } else {
	            return mod_LavaCow.MODID + ":textures/armors/molten/molten_layer_1.png";
	        }
    	}
    }

    // Lava walking is handled in FURServerEvents#onELiving via applyLavaWalking()
    // because setPos() requires the entity's movement to be processed through the
    // normal LivingEntity tick pipeline to sync correctly to the client.

    // ── Static helpers called from FURServerEvents ───────────────────────────

    /**
     * Returns the number of Molten Armor pieces the given entity is wearing.
     * Counts both molten and soulforged pieces (both extend MoltenArmorItem).
     */
    public static int countMoltenPieces(LivingEntity entity) {
        int count = 0;
        for (ItemStack stack : entity.getArmorSlots()) {
            if (stack.getItem() instanceof MoltenArmorItem)
                count++;
        }
        return count;
    }

    /**
     * Returns the number of Soulforged Armor pieces the given entity is wearing.
     * Soulforged pieces are identified by their item ID containing "soulforged".
     */
    public static int countSoulforgedPieces(LivingEntity entity) {
        int count = 0;
        for (ItemStack stack : entity.getArmorSlots()) {
            if (stack.getItem() instanceof MoltenArmorItem && isSoulforged(stack))
                count++;
        }
        return count;
    }

    /**
     * Returns true if the entity is wearing a full set of Soulforged Armor.
     */
    public static boolean isWearingFullSoulforged(LivingEntity entity) {
        return countSoulforgedPieces(entity) >= FULLSET_THRESHOLD;
    }

    /**
     * Called from {@code FURServerEvents#onEDamage} when the wearer is attacked.
     *
     * Molten (>= 2 pieces): sets attacker on fire for 3 seconds.
     * Soulforged full set: sets attacker on fire for 5 seconds instead.
     *
     * @param attacked  The entity wearing the armor (the one being hurt).
     * @param attacker  The entity that dealt the hit (may be null for environmental damage).
     */
    public static void applyRetaliationBurn(LivingEntity attacked, Entity attacker) {
        if (attacker == null)
            return;

        if (countMoltenPieces(attacked) >= BURN_THRESHOLD) {
            if (isWearingFullSoulforged(attacked)) {
                // Soulforged full set: extended burn
                attacker.setSecondsOnFire(5);
            } else {
                // Molten 2-piece: standard burn
                attacker.setSecondsOnFire(3);
            }
        }
    }

    /**
     * Called from {@code FURServerEvents#onEDamage} when the wearer takes fire damage.
     *
     * Molten full set: reduces fire damage by 50%.
     * Soulforged full set: complete fire immunity (cancel the damage entirely).
     *
     * @param attacked       The entity wearing the armor.
     * @param originalAmount The original fire damage amount.
     * @return Modified damage amount (0 for soulforged full set, reduced for molten full set).
     */
    public static float applyFireReduction(LivingEntity attacked, float originalAmount) {
        if (attacked.fireImmune())
            return originalAmount;

        if (countMoltenPieces(attacked) >= FULLSET_THRESHOLD) {
            if (isWearingFullSoulforged(attacked)) {
                // Soulforged full set: complete immunity
                return 0.0F;
            } else {
                // Molten full set: 50% reduction
                return originalAmount * (1.0F - FIRE_REDUCTION);
            }
        }

        return originalAmount;
    }

    /**
     * Called from {@code FURServerEvents#onELiving} every tick for players.
     * Full-set effect: lava walking.
     *
     * Behaviour:
     *  - Player is kept on top of the lava surface.
     *  - Holding sneak lets the player sink normally.
     *  - Fire is suppressed while on the surface (damage reduction handled in applyFireReduction).
     *
     * Runs through LivingEntity tick pipeline so position changes sync correctly to client.
     */
    public static void applyLavaWalking(Player player) {
        if (player.isShiftKeyDown())
            return;

        if (countMoltenPieces(player) < FULLSET_THRESHOLD)
            return;

        Level level = player.level();
        BlockPos feet = player.blockPosition();

        boolean nearLava = player.isInLava()
                || level.getFluidState(feet).is(FluidTags.LAVA)
                || level.getFluidState(feet.below()).is(FluidTags.LAVA);

        if (!nearLava)
            return;

        // Descend to the bottom of the lava column
        BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos(feet.getX(), feet.getY(), feet.getZ());
        while (level.getFluidState(check.below()).is(FluidTags.LAVA)) {
            check.move(0, -1, 0);
        }

        // Ascend to find the top surface of the lava
        while (level.getFluidState(check).is(FluidTags.LAVA)) {
            check.move(0, 1, 0);
        }

        // check is now the first non-lava block — this is where the player should stand
        double targetY = check.getY();
        Vec3 motion = player.getDeltaMovement();

        if (player.getY() > targetY + 0.15D) {
            // Gently settle down to the surface
            player.setDeltaMovement(motion.x, Math.min(motion.y, -0.05D), motion.z);
        } else {
            // At or below surface: override Y motion entirely and position player on surface
            player.setDeltaMovement(motion.x, 0.0D, motion.z);
            player.setPosRaw(player.getX(), targetY, player.getZ());
            player.resetFallDistance();
            player.hasImpulse = true; // Flag to force a position packet to the client this tick
        }

        // Extinguish fire on surface (damage handled in applyFireReduction)
        if (player.isOnFire())
            player.clearFire();
    }

    /**
     * Called from {@code FURServerEvents#playerTick} (Phase.END) for players
     * wearing the full Soulforged Armor set.
     *
     * Soul Speed effect: applies Speed II while standing on Soul Sand or Soul Soil,
     * matching the behaviour of the vanilla Soul Speed III enchantment.
     * The effect duration is kept at 3 ticks so it continuously refreshes
     * without showing a countdown in the status bar.
     */
    public static void tickSoulSpeed(Player player) {   	
        Level level = player.level();       
        boolean onSoulBlock = level.getBlockState(player.blockPosition()).is(BlockTags.SOUL_SPEED_BLOCKS);
        
        if (onSoulBlock) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3, 3, false, false, false));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    	if (isSoulforged(stack)) {
            tooltip.add(Component.translatable("item.fur.soulforged_armor.desc0").withStyle(ChatFormatting.YELLOW));
            tooltip.add(Component.translatable("item.fur.soulforged_armor.desc1").withStyle(ChatFormatting.YELLOW));   		
    	} else {
            tooltip.add(Component.translatable("item.fur.molten_armor.desc0").withStyle(ChatFormatting.YELLOW));
            tooltip.add(Component.translatable("item.fur.molten_armor.desc1").withStyle(ChatFormatting.YELLOW));   		
    	}
    }
}
