package com.Fishmod.mod_LavaCow.compat.tinkers;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarab;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.message.PacketParticle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitAmberPharaoh extends AbstractTrait {
    private static float chance = 0.075F;
    private static float chance_fight = 0.15F;

    public TraitAmberPharaoh() {
        super("mod_lavacow.amber_pharaoh", 0xBA7232);
    }

    @Override
    public String getLocalizedName() {
        return Util.translate(String.format(LOC_Name, "mod_lavacow.amber_pharaoh"));
    }

    @Override
    public String getLocalizedDesc() {
        return Util.translate(String.format(LOC_Desc, "mod_lavacow.amber_pharaoh"));
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if (wasEffective && !world.isRemote && random.nextFloat() < chance) {
            spawnAmberScarab(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, world, null, player);

            if (player instanceof EntityPlayer) {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 5 * 20, 1));
            }
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.isEntityAlive() && !target.getEntityWorld().isRemote && random.nextFloat() < chance_fight) {
            spawnAmberScarab(target.posX, target.posY, target.posZ, target.getEntityWorld(), target, player);

            if (player instanceof EntityPlayer) {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 5 * 20, 1));
            }
        }
    }

    protected void spawnAmberScarab(double x, double y, double z, World world, @Nullable EntityLivingBase target, EntityLivingBase player) {
        EntityScarab entity = new EntityScarab(world);
        entity.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(x, y, z)), null);
        entity.setPosition(x, y, z);
        entity.setOwnerId(player.getUniqueID());
        entity.setTamed(true);
        entity.setLimitedLife(60 * 20);
        entity.playSound(FishItems.ENTITY_AVATON_SPELL, 1.0F, 1.25F);
        world.spawnEntity(entity);

        if (player.world instanceof World) {
            for (int j = 0; j < 24; ++j) {
                double d0 = entity.posX + (double) (entity.world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width;
                double d1 = entity.posY + (double) (entity.world.rand.nextFloat() * entity.height);
                double d2 = entity.posZ + (double) (entity.world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width;
                mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2));
            }
        }
    }
}
