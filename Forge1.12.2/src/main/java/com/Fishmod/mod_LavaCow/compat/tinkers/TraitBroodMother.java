package com.Fishmod.mod_LavaCow.compat.tinkers;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntityParasite;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitBroodMother extends AbstractTrait {
    private static float chance = 0.01f;
    private static float chance_fight = 0.40f;

    public TraitBroodMother() {
        super("mod_lavacow.broodmother", 0X85E214);
    }

    @Override
    public String getLocalizedName() {
        return Util.translate(String.format(LOC_Name, "mod_lavacow.broodmother"));
    }

    @Override
    public String getLocalizedDesc() {
        return Util.translate(String.format(LOC_Desc, "mod_lavacow.broodmother"));
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if (wasEffective && !world.isRemote && random.nextFloat() < chance && Modconfig.pSpawnRate_Parasite > 0) {
            spawnParasite(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, world, null);
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.isEntityAlive() && !target.getEntityWorld().isRemote && random.nextFloat() < chance_fight && Modconfig.pSpawnRate_Parasite > 0) {
            spawnParasite(target.posX, target.posY, target.posZ, target.getEntityWorld(), target);
        }
    }

    protected void spawnParasite(double x, double y, double z, World world, @Nullable EntityLivingBase target) {
        EntityParasite entity = new EntityParasite(world);
        entity.setPosition(x, y, z);
        world.spawnEntity(entity);
        entity.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(x, y, z)), null);
        entity.setSkin(2);
        if (target != null) {
            entity.setAttackTarget(target);
        }
        entity.playLivingSound();
    }
}
