package com.Fishmod.mod_LavaCow.compat.somanyenchantments;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityCactusThorn;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityDeathCoil;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityEnchantableFireBall;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityPiranhaLauncher;
import com.Fishmod.mod_LavaCow.item.ItemPiranhaLauncher;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.properties.ArrowPropertiesProvider;
import com.shultrea.rin.properties.IArrowProperties;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.IEntityDamageSourceMixin;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

// Based on https://github.com/fonnymunkey/SoManyEnchantments/blob/master/src/main/java/com/shultrea/rin/properties/ArrowPropertiesHandler.java#L38
public class LauncherAmmoHandler {

    // EntityCactusThorn extends EntityArrow
    // EntityDeathCoil extends EntityWitherSkull extends EntityFireball
    // EntityPiranhaLauncher extends EntityEnchantableFireBall extends EntityFireball
    // EntityEnchantableFireBall extends EntityFireball
    // Safe to assume EntityCactusThorn, EntityDeathCoil, and EntityEnchantableFireBall are only ever Fish Mod
    // Power tiers handled in ItemPiranhaLauncher to be the least headache

    private static final ResourceLocation ARROWPROPERTIES_CAP_RESOURCE = new ResourceLocation(SoManyEnchantments.MODID + ":arrow_capabilities");

    private static final Random RANDOM = new Random();

    // Covers all the Launcher's Ammo Types
    public boolean isLauncherAmmo(Object object) {
        return (object instanceof EntityCactusThorn || object instanceof EntityDeathCoil || object instanceof EntityEnchantableFireBall);
    }

    // Attach caps to Ammo Types except instances of Arrows
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityArrow) &&
                isLauncherAmmo(event.getObject()) &&
                !event.getObject().hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) {
            event.addCapability(ARROWPROPERTIES_CAP_RESOURCE, new ArrowPropertiesProvider());
        }
    }

    // Set Props before SME so Cactus Thorn isn't OP, SME checks if props already set
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getWorld().isRemote) return;
        if (!(isLauncherAmmo(event.getEntity()))) return;

        EntityLivingBase shooter = null;
        if (event.getEntity() instanceof EntityCactusThorn) {
            EntityCactusThorn entityArrow = (EntityCactusThorn) event.getEntity();
            if (!(entityArrow.shootingEntity instanceof EntityLivingBase)) return;
            shooter = (EntityLivingBase) entityArrow.shootingEntity;
        } else if (event.getEntity() instanceof EntityDeathCoil) {
            EntityDeathCoil entityArrow = (EntityDeathCoil) event.getEntity();
            if (!(entityArrow.shootingEntity instanceof EntityLivingBase)) return;
            shooter = entityArrow.shootingEntity;
        } else if (event.getEntity() instanceof EntityEnchantableFireBall) {
            EntityEnchantableFireBall entityArrow = (EntityEnchantableFireBall) event.getEntity();
            if (!(entityArrow.shootingEntity instanceof EntityLivingBase)) return;
            shooter = entityArrow.shootingEntity;
        }

        ItemStack bow = shooter.getHeldItemMainhand();
        if (!(bow.getItem() instanceof ItemPiranhaLauncher)) {
            bow = shooter.getHeldItemOffhand();
        }
        if (!(bow.getItem() instanceof ItemPiranhaLauncher)) {
            return;
        }

        IArrowProperties properties = event.getEntity().getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
        if (properties == null) return;

        if (properties.getPropertiesHandled()) return;
        if (event.getEntity() instanceof EntityCactusThorn)
            setArrowEnchantmentsFromStack(bow, event.getEntity(), properties);
        else if (event.getEntity() instanceof EntityDeathCoil)
            setArrowEnchantmentsFromStack(bow, event.getEntity(), properties);
        else if (event.getEntity() instanceof EntityEnchantableFireBall)
            setArrowEnchantmentsFromStack(bow, event.getEntity(), properties);
        properties.setPropertiesHandled(true);
    }

    // Cactus Thorn is an EntityArrow, handled by SME
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLauncherHitHurt(LivingHurtEvent event) {
        if (!(event.getSource().getImmediateSource() instanceof EntityEnchantableFireBall) &&
                !(event.getSource().getImmediateSource() instanceof EntityDeathCoil)) return;

        Entity arrow = event.getSource().getImmediateSource();

        IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
        if (properties == null) return;

        if (!(Modconfig.SME_Compat_Special)) return;
        if (EnchantmentRegistry.runeArrowPiercing.isEnabled()) {
            int pierceLevel = properties.getArmorPiercingLevel();
            if (pierceLevel > 0) {
                if (event.getSource() instanceof EntityDamageSource) {
                    float currPercent = ((IEntityDamageSourceMixin) event.getSource()).soManyEnchantments$getPiercingPercent();
                    float percent = Math.min(currPercent + 0.25F * (float) pierceLevel, 1.0F);
                    ((IEntityDamageSourceMixin) event.getSource()).soManyEnchantments$setPiercingPercent(percent);
                }
            }
        }
    }

    // Cactus Thorn is an EntityArrow, handled by SME
    @SubscribeEvent
    public void onLauncherHitDamage(LivingDamageEvent event) {
        if (!(event.getSource().getImmediateSource() instanceof EntityEnchantableFireBall) &&
                !(event.getSource().getImmediateSource() instanceof EntityDeathCoil)) return;

        Entity arrow = event.getSource().getImmediateSource();
        EntityLivingBase victim = event.getEntityLiving();

        IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
        if (properties == null) return;

        if (EnchantmentRegistry.lesserFlame.isEnabled() || EnchantmentRegistry.advancedFlame.isEnabled() || EnchantmentRegistry.supremeFlame.isEnabled()) {
            int seconds = getFireSeconds(properties.getFlameLevel());
            if (seconds > 0) victim.setFire(seconds);
        }

        if (EnchantmentRegistry.extinguish.isEnabled()) {
            int flameLevel = properties.getFlameLevel();
            if (flameLevel == -1) victim.extinguish();
        }

        if (EnchantmentRegistry.dragging.isEnabled()) {
            float draggingPower = properties.getDraggingPower();
            if (draggingPower > 0) {
                double velocityMultiplier = -0.6F * draggingPower / MathHelper.sqrt(arrow.motionX * arrow.motionX + arrow.motionZ * arrow.motionZ);
                victim.addVelocity(arrow.motionX * velocityMultiplier, 0.1, arrow.motionZ * velocityMultiplier);
                victim.velocityChanged = true;
            }
        }

        if (!(Modconfig.SME_Compat_Special)) return;
        if (EnchantmentRegistry.strafe.isEnabled()) {
            if (properties.getArrowResetsIFrames()) {
                victim.hurtResistantTime = 0;
            }
        }
    }

    private static int getFireSeconds(int tier) {
        switch (tier) {
            case 1:
                return 2;
            case 2:
                return 15;
            case 3:
                return 30;
        }
        return 0;
    }

    // This is ridiculous
    public static void setArrowEnchantmentsFromStack(ItemStack bow, Entity arrow, IArrowProperties properties) {
        boolean isDeathCoil = arrow instanceof EntityDeathCoil;
        boolean ispiranha = arrow instanceof EntityPiranhaLauncher;
        boolean isFireBall = arrow instanceof EntityEnchantableFireBall;
        boolean isCactusThorn = arrow instanceof EntityCactusThorn;

        if (!isDeathCoil && !isFireBall && !isCactusThorn) return;

        if (EnchantmentRegistry.powerless.isEnabled()) {
            int powerlessLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.powerless, bow);
            if (powerlessLevel > 0) {
                if (isCactusThorn)
                    if (powerlessLevel > 2 || RANDOM.nextFloat() < powerlessLevel * 0.4F) {
                        ((EntityCactusThorn) arrow).setIsCritical(false);
                    }
            }
        }

        if (EnchantmentRegistry.advancedPower.isEnabled()) {
            int advPowerLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedPower, bow);
            if (advPowerLevel > 0) {
                if (isCactusThorn)
                    if (advPowerLevel >= 4 || RANDOM.nextFloat() < advPowerLevel * 0.25F) {
                        ((EntityCactusThorn) arrow).setIsCritical(true);
                    }
            }
        }

        if (EnchantmentRegistry.advancedPunch.isEnabled()) {
            int advPunchLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedPunch, bow);
            if (advPunchLevel > 0) {
                if (isDeathCoil) ((EntityDeathCoil) arrow).setKnockbackStrength(1 + advPunchLevel * 2);
                else if (isFireBall) ((EntityEnchantableFireBall) arrow).setKnockbackStrength(1 + advPunchLevel * 2);
                else {
                    ((EntityCactusThorn) arrow).setKnockbackStrength(1 + advPunchLevel * 2);
                }
            }
        }

        if (EnchantmentRegistry.runeArrowPiercing.isEnabled()) {
            int runeArrowPiercingLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeArrowPiercing, bow);
            if (runeArrowPiercingLevel > 0) {
                properties.setArmorPiercingLevel(runeArrowPiercingLevel);
            }
        }

        if (EnchantmentRegistry.dragging.isEnabled()) {
            int draggingLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.dragging, bow);
            if (draggingLevel > 0) {
                properties.setDraggingPower(1.25F + draggingLevel * 1.75F);
            }
        }

        if (EnchantmentRegistry.strafe.isEnabled()) {
            int levelStrafe = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.strafe, bow);
            if (RANDOM.nextFloat() < 0.125F * levelStrafe) {
                properties.setArrowResetsIFrames(true);
            }
        }

        if (EnchantmentRegistry.lesserFlame.isEnabled()) {
            int levelLessFlame = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.lesserFlame, bow);
            if (levelLessFlame > 0) {
                arrow.setFire(50);
                properties.setFlameLevel(1);
            }
        }

        if (EnchantmentRegistry.advancedFlame.isEnabled()) {
            int levelAdvFlame = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedFlame, bow);
            if (levelAdvFlame > 0) {
                arrow.setFire(200);
                properties.setFlameLevel(2);
            }
        }

        if (EnchantmentRegistry.supremeFlame.isEnabled()) {
            int levelSupFlame = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.supremeFlame, bow);
            if (levelSupFlame > 0) {
                arrow.setFire(400);
                properties.setFlameLevel(3);
            }
        }

        if (EnchantmentRegistry.extinguish.isEnabled()) {
            int levelExtinguish = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.extinguish, bow);
            if (levelExtinguish > 0) {
                arrow.extinguish();
                properties.setFlameLevel(-1);
            }
        }
    }
}