package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemNetherStew extends ItemFishCustomFood {
    public static List<Triple<Potion, Integer, Integer>> Effect_nethersoup = Lists.newArrayList(
            new ImmutableTriple<Potion, Integer, Integer>(MobEffects.STRENGTH, 60 * 20, 1),
            new ImmutableTriple<Potion, Integer, Integer>(MobEffects.RESISTANCE, 60 * 20, 1),
            new ImmutableTriple<Potion, Integer, Integer>(MobEffects.SPEED, 60 * 20, 1),
            new ImmutableTriple<Potion, Integer, Integer>(MobEffects.ABSORPTION, 60 * 20, 1)
    );

    public static List<Triple<Potion, Integer, Integer>> Effect_bonestew = Lists.newArrayList(
            new ImmutableTriple<Potion, Integer, Integer>(MobEffects.ABSORPTION, 60 * 20, 1),
            new ImmutableTriple<Potion, Integer, Integer>(MobEffects.RESISTANCE, 60 * 20, 0)
    );

    public ItemNetherStew(String registryName, int amount, float saturation) {
        super(registryName, amount, saturation, false, 32, true);
        this.setAlwaysEdible();
        this.setMaxStackSize(Modconfig.Bowls_Stack ? 64 : 1);
    }

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        if (stack.getItem().equals(FishItems.NETHERSTEW) && target instanceof EntityPig && !target.isChild()) {
            if (!player.isCreative()) {
                stack.shrink(1);
            }

            player.playSound(SoundEvents.ENTITY_GENERIC_BURN, 1.0F, 1.0F);
            for (int i = 0; i < 5; ++i) {
                double d0 = new Random().nextGaussian() * 0.02D;
                double d1 = new Random().nextGaussian() * 0.02D;
                double d2 = new Random().nextGaussian() * 0.02D;
                target.world.spawnParticle(EnumParticleTypes.HEART, target.posX + (double) (new Random().nextFloat() * target.width * 2.0F) - (double) target.width, target.posY + 1.0D + (double) (new Random().nextFloat() * target.height), target.posZ + (double) (new Random().nextFloat() * target.width * 2.0F) - (double) target.width, d0, d1, d2);
            }

            if (!player.world.isRemote) {
                EntityPigZombie entitypigzombie = new EntityPigZombie(target.world);
                entitypigzombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
                entitypigzombie.setLocationAndAngles(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
                entitypigzombie.setChild(true);
                target.world.spawnEntity(entitypigzombie);
            }
            return true;
        } else {
            return super.itemInteractionForEntity(stack, player, target, hand);
        }
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
        super.onItemUseFinish(stack, world, entity);

        if (this.equals(FishItems.GHOSTJELLY)) {
            if (world.isRemote) {
                entity.setVelocity(0.0D, 2.0D, 0.0D);
            }

            entity.addPotionEffect(new PotionEffect(ModMobEffects.RAVENS_GRACE, 6 * 20, 0));
            entity.playSound(SoundEvents.ENTITY_FIREWORK_LAUNCH, 1.0F, 1.0F);
        }

        if (!world.isRemote && (Modconfig.Bowls_Stack || stack.getCount() > stack.getMaxStackSize()) && entity instanceof EntityPlayer && !((EntityPlayer) entity).isCreative())
            ((EntityPlayer) entity).inventory.addItemStackToInventory(new ItemStack(Items.BOWL));

        return (Modconfig.Bowls_Stack || stack.getCount() > stack.getMaxStackSize()) ? stack : new ItemStack(Items.BOWL);
    }
}
