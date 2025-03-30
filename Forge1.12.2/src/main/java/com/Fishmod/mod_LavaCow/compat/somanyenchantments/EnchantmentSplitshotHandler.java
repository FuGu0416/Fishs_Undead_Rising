package com.Fishmod.mod_LavaCow.compat.somanyenchantments;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.projectiles.*;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.item.ItemPiranhaLauncher;
import com.shultrea.rin.mixin.vanilla.IItemBowMixin;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentSplitshotHandler {

    @SubscribeEvent
    public void onLauncherStart(PlayerInteractEvent.RightClickItem event) {
        if (!(Modconfig.SME_Compat_Special)) return;
        if (event.getWorld().isRemote) return;
        EntityPlayer player = event.getEntityPlayer();
        if (event.getEntityPlayer() == null) return;

        if (!(event.getItemStack().getItem() instanceof ItemPiranhaLauncher)) return;
        ItemStack stack = event.getItemStack();

        /*
         * Mostly a copy of original except for some rng calcs to spread out projectiles
         */

        int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
        int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
        int flameLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack);

        powerLevel -= SoManyEnchantmentsCompat.getPowerlessLevel(stack);
        powerLevel += 5 * SoManyEnchantmentsCompat.getAdvancedPowerLevel(stack) / 3;

        int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.splitShot, stack);
        if (level > 0) {
            // Infinite ammo check
            boolean flag = stack.getItem() == FishItems.FORSAKEN_STAFF || player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack ammo = ((IItemBowMixin) stack.getItem()).invokeFindAmmo(player);
            if (ammo.isEmpty() && !flag) return;
            for (int x = 0; x < level; ++x) {
                // EntityArrow
                if (stack.getItem() == FishItems.THORN_SHOOTER) {
                    EntityCactusThorn cactusThorn = new EntityCactusThorn(player.world, player);
                    cactusThorn.shootingEntity = player;
                    cactusThorn.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2.0F, 6.0F + player.getRNG().nextFloat() * 12.0F);

                    if (powerLevel > 0)
                        cactusThorn.setDamage(cactusThorn.getDamage() + (double) powerLevel * 0.1D + 0.1D);
                    if (punchLevel > 0) cactusThorn.setKnockbackStrength(punchLevel);
                    if (flameLevel > 0) cactusThorn.setFire(100);
                    stack.damageItem(1, player);
                    cactusThorn.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;

                    player.world.spawnEntity(cactusThorn);
                } else { // EntityFireball
                    Vec3d vector = player.getLookVec();
                    vector = vector.rotatePitch((float) Math.toRadians(10D * player.getRNG().nextGaussian()) * (-1 * player.getRNG().nextInt(2)));
                    vector = vector.rotateYaw((float) Math.toRadians(10D * player.getRNG().nextGaussian()) * (-1 * player.getRNG().nextInt(2)));

                    double xMod = vector.x;
                    double yMod = vector.y;
                    double zMod = vector.z;

                    // EntityDeathCoil
                    if (stack.getItem() == FishItems.FORSAKEN_STAFF) {
                        EntityDeathCoil deathCoil = new EntityDeathCoil(player.world, player, 0D, 0D, 0D);
                        deathCoil.accelerationX = deathCoil.accelerationY = deathCoil.accelerationZ = 0;

                        deathCoil.shootingEntity = player;
                        deathCoil.addVelocity(xMod, yMod, zMod);
                        deathCoil.setPosition(player.posX + vector.x, player.posY + (double) (player.height), player.posZ + vector.z);

                        if (powerLevel > 0)
                            deathCoil.setDamage(deathCoil.getDamage() * (1.0F + (powerLevel + 1) * 0.25F));
                        if (punchLevel > 0) deathCoil.setKnockbackStrength(punchLevel);
                        if (flameLevel > 0) deathCoil.setFlame(true);
                        stack.damageItem(1, player);

                        player.world.spawnEntity(deathCoil);
                    } else { // EntityEnchantableFireBall
                        EntityEnchantableFireBall enchantableFireBall;
                        double ammoMod = 2.5D;
                        if (stack.getItem() == FishItems.PIRANHALAUNCHER) {
                            enchantableFireBall = new EntityPiranhaLauncher(player.world, player, 0D, 0D, 0D);
                            enchantableFireBall.accelerationX = enchantableFireBall.accelerationY = enchantableFireBall.accelerationZ = 0;
                            ammoMod = 2.0D;
                        } else {
                            enchantableFireBall = new EntityWarSmallFireball(player.world, player, 0D, 0D, 0D);
                            enchantableFireBall.accelerationX = enchantableFireBall.accelerationY = enchantableFireBall.accelerationZ = 0;
                        }
                        enchantableFireBall.shootingEntity = player;
                        enchantableFireBall.addVelocity(xMod * ammoMod, yMod * ammoMod, zMod * ammoMod);
                        enchantableFireBall.setPosition(player.posX + vector.x, player.posY + (double) (player.height), player.posZ + vector.z);

                        if (powerLevel > 0)
                            enchantableFireBall.setDamage(enchantableFireBall.getDamage() * (1.0F + (powerLevel + 1) * 0.25F));
                        if (punchLevel > 0) enchantableFireBall.setKnockbackStrength(punchLevel);
                        if (flameLevel > 0) enchantableFireBall.setFlame(true);
                        stack.damageItem(1, player);

                        player.world.spawnEntity(enchantableFireBall);
                    }
                }
            }
        }
    }
}
