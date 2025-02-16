package com.Fishmod.mod_LavaCow.item;

import java.util.Random;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntityLavaCow;
import com.Fishmod.mod_LavaCow.entities.EntityVespaCocoon;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityEnigmothLarva;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityParasite;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityRaven;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarecrow;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFissionPotion extends ItemFishCustom {

    private SoundEvent using_sound = null;
    private EnumParticleTypes using_particle = EnumParticleTypes.SMOKE_NORMAL;
    private EnumRarity Rarity;

    public ItemFissionPotion(String registryName, SoundEvent soundIn, EnumParticleTypes particleIn, EnumRarity rarity, boolean hasTooltip) {
        super(registryName, Items.GLASS_BOTTLE, mod_LavaCow.TAB_ITEMS, true);
        this.setMaxStackSize(1);
        this.using_sound = soundIn;
        this.using_particle = particleIn;
        this.Rarity = rarity;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return stack.getItem() == FishItems.POTION_OF_MOOTEN_LAVA;
    }

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack) {
        return this.Rarity;
    }

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        double dx = target.posX;
        double dy = target.posY;
        double dz = target.posZ;
        boolean flag = false;

        // Potion of Fission
        if (((!Modconfig.Fission_ModEntity && LootTableHandler.FISSION_WHITELIST.contains(EntityList.getKey(target))) || Modconfig.Fission_ModEntity && target instanceof EntityLiving) && !target.isChild()) {
            if (!playerIn.world.isRemote) {
                if (stack.getItem().equals(FishItems.FISSIONPOTION)) {

                    // Because EntityRaven and EntityScarecrow both extend EntityFishTameable and do not have baby models or a way to age, it will result in strange behaviors so let's prevent that
                    if (target instanceof EntityAgeable && !(target instanceof EntityRaven) && !(target instanceof EntityScarecrow)) {
                        EntityAgeable parent = (EntityAgeable) target;
                        EntityAgeable entity = ((EntityAgeable) parent).createChild((EntityAgeable) parent) != null ? ((EntityAgeable) parent).createChild((EntityAgeable) parent) : (EntityAgeable) EntityRegistry.getEntry(target.getClass()).newInstance(parent.world);
                        NBTTagCompound compoundnbt = new NBTTagCompound();

                        parent.writeEntityToNBT(compoundnbt);

                        // There's probably a better way to do this?
                        if (compoundnbt.getBoolean("Saddled")) compoundnbt.setBoolean("Saddled", false);

                        entity.readEntityFromNBT(compoundnbt);
                        entity.setGrowingAge(-24000);
                        entity.setLocationAndAngles(target.posX, target.posY + 0.2F, target.posZ, target.rotationYaw, target.rotationPitch);
                        parent.world.spawnEntity(entity);
                        if (entity.getClass() == parent.getClass()) parent.setGrowingAge(-24000);

                        if (parent instanceof EntityTameable && ((EntityTameable) parent).isTamed() && entity instanceof EntityTameable && !((EntityTameable) entity).isTamed()) {
                            ((EntityTameable) entity).setTamedBy(playerIn);
                        }

                        entity.playLivingSound();

                        flag = true;
                    } else {
                        EntityLiving parent = (EntityLiving) target;
                        EntityLiving entity = (EntityLiving) EntityRegistry.getEntry(target.getClass()).newInstance(parent.world);
                        NBTTagCompound compoundnbt = new NBTTagCompound();

                        parent.writeEntityToNBT(compoundnbt);
                        entity.readEntityFromNBT(compoundnbt);
                        entity.setLocationAndAngles(target.posX, target.posY + 0.2F, target.posZ, target.rotationYaw, target.rotationPitch);
                        parent.world.spawnEntity(entity);

                        if (parent instanceof EntityTameable && ((EntityTameable) parent).isTamed() && entity instanceof EntityTameable && !((EntityTameable) entity).isTamed()) {
                            ((EntityTameable) entity).setTamedBy(playerIn);
                        }

                        entity.playLivingSound();

                        flag = true;
                    }
                }
            }
        }

        // Potion of MOOten Lava
        if (!playerIn.world.isRemote) {
            if (stack.getItem().equals(FishItems.POTION_OF_MOOTEN_LAVA) && !(target instanceof EntityLavaCow) && target instanceof EntityCow) {
                if (target.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null && target.getActivePotionEffect(ModMobEffects.IMMOLATION) == null) {
                    target.setFire(12);
                } else {
                    for (int i = 0; i < 2; i++) {
                        EntityAgeable entityageable = new EntityLavaCow(playerIn.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        playerIn.world.spawnEntity(entityageable);
                    }
                    target.setDead();
                }

                flag = true;
            }
        }

        // Charming Catalyst
        if (!playerIn.world.isRemote) {
            if (stack.getItem().equals(FishItems.CHARMING_CATALYST) && target instanceof EntityParasite) {
                EntityVespaCocoon pupa = null;

                switch (((EntityParasite) target).getSkin()) {
                    // TODO: Beelzebub
                    case 3:
                        //pupa = FUREntityRegistry.BEELZEBUBPUPA.create(playerIn.world);
                        break;
                    // Vespa
                    case 2:
                        pupa = new EntityVespaCocoon(playerIn.world);
                        pupa.setSkin(0);
                        break;
                    default:
                        break;
                }

                if (pupa != null) {
                    target.playSound(FishItems.ENTITY_PARASITE_WEAVE, 1.0F, 1.0F);

                    pupa.setLocationAndAngles(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
                    pupa.setTamedBy(playerIn);
                    playerIn.world.spawnEntity(pupa);
                    target.setDead();

                    flag = true;
                }
            } else if (stack.getItem().equals(FishItems.CHARMING_CATALYST) && target instanceof EntityEnigmothLarva) {
                EntityVespaCocoon pupa = new EntityVespaCocoon(playerIn.world);

                if (pupa != null) {
                    target.playSound(FishItems.ENTITY_PARASITE_WEAVE, 1.0F, 1.0F);

                    pupa.setLocationAndAngles(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
                    pupa.setTamedBy(playerIn);
                    pupa.setSkin(1);
                    playerIn.world.spawnEntity(pupa);
                    target.setDead();

                    flag = true;
                }
            }

            if (!playerIn.isCreative() && flag) {
                stack.shrink(1);
                playerIn.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            }

            if (playerIn.world instanceof WorldServer && flag) {
                playerIn.world.playSound(null, playerIn.getPosition(), this.using_sound, SoundCategory.PLAYERS, 1.0F, 1.0F);

                for (int i = 0; i < 5; ++i) {
                    double d0 = new Random().nextGaussian() * 0.02D;
                    double d1 = new Random().nextGaussian() * 0.02D;
                    double d2 = new Random().nextGaussian() * 0.02D;
                    playerIn.world.spawnParticle(this.using_particle, dx + (double) (new Random().nextFloat() * playerIn.width * 2.0F) - (double) playerIn.width, dy + 1.0D + (double) (new Random().nextFloat() * playerIn.height), dz + (double) (new Random().nextFloat() * playerIn.width * 2.0F) - (double) playerIn.width, d0, d1, d2);
                }
            }
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    /**
     * Called when the equipped item is right clicked.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    private ItemStack returnItem(ItemStack stack, World world, EntityLivingBase entityLiving) {
        EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer) entityLiving : null;

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
            stack.shrink(1);
        }

        if (entityplayer instanceof EntityPlayerMP) {
            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) entityplayer, stack);
        }
        if (entityplayer != null) {
            entityplayer.addStat(StatList.getObjectUseStats(this));
        }

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (entityplayer != null) {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return stack;
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
        if (!world.isRemote) {
            if (stack.getItem().equals(FishItems.FISSIONPOTION)) {
                entityLiving.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 6 * 20, 2));
            } else if (stack.getItem().equals(FishItems.POTION_OF_MOOTEN_LAVA) && entityLiving.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null && entityLiving.getActivePotionEffect(ModMobEffects.IMMOLATION) == null) {
                entityLiving.setFire(12);
            } else if (stack.getItem().equals(FishItems.CHARMING_CATALYST)) {
                entityLiving.addPotionEffect(new PotionEffect(ModMobEffects.CHARMING_PHEROMONE, 45 * 20, 0));
            }
        }

        return returnItem(stack, world, entityLiving);
    }
}
