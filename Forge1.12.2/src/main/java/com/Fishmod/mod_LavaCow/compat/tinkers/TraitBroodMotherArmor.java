package com.Fishmod.mod_LavaCow.compat.tinkers;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.misc.EntityVespaBrood;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.message.PacketParticle;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TraitBroodMotherArmor extends AbstractArmorTrait {
    private static final float CHANCE = 0.1F;

    public TraitBroodMotherArmor() {
        super("mod_lavacow.broodmother", 0x85E214);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        Entity entity = source.getTrueSource();

        if (entity instanceof EntityLivingBase && entity.isEntityAlive() && !entity.getEntityWorld().isRemote && random.nextFloat() < CHANCE) {
            spawnVespaBrood(entity.posX, entity.posY, entity.posZ, entity.getEntityWorld(), (EntityLivingBase) entity, player);
        }

        return super.onHurt(armor, player, source, damage, newDamage, evt);
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
