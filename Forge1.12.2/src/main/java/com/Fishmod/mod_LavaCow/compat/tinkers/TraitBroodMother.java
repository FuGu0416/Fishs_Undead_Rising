package com.Fishmod.mod_LavaCow.compat.tinkers;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.misc.EntityVespaBrood;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.message.PacketParticle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitBroodMother extends AbstractTrait {
    private static float chance = 0.15F;
    private static float chance_fight = 0.3F;

    public TraitBroodMother() {
        super("mod_lavacow.broodmother", 0x85E214);
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
            spawnVespaBrood(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, world, null, player);
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.isEntityAlive() && !target.getEntityWorld().isRemote && random.nextFloat() < chance_fight && Modconfig.pSpawnRate_Parasite > 0) {
            spawnVespaBrood(target.posX, target.posY, target.posZ, target.getEntityWorld(), target, player);
        }
    }

    protected void spawnVespaBrood(double x, double y, double z, World world, @Nullable EntityLivingBase target, EntityLivingBase player) {
        EntityVespaBrood entity = new EntityVespaBrood(world);
        entity.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(x, y, z)), null);
        entity.setPosition(x, y, z);
        entity.setOwnerId(player.getUniqueID());
        entity.setTamed(true);
        entity.setLimitedLife(60 * 20);
        entity.playSound(FishItems.ENTITY_PARASITE_WEAVE, 1.0F, 1.25F);
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
