package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityLilSludge;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityUnburied;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModEnchantments;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFishCustomWeapon extends ItemSword{

	private Item repair_material;
	private float Damage;
	private float AttackSpeed;
	private EnumRarity Rarity;
	protected float efficiency;
	private String Tooltip = null;
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER);
	
	public ItemFishCustomWeapon(String registryName, ToolMaterial materialIn, float damageIn, float attackspeedIn, Item repair, EnumRarity rarity) {
		super(materialIn);
        setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
        setCreativeTab(mod_LavaCow.TAB_ITEMS);
        this.Damage = 3.0F + damageIn;
        this.AttackSpeed = attackspeedIn;
        this.repair_material = repair;
        this.Rarity = rarity;
        this.efficiency = materialIn.getEfficiency();
        this.Tooltip = "tootip." + mod_LavaCow.MODID + "." + registryName;
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(stack.getItem() == FishItems.MOLTENHAMMER || stack.getItem() == FishItems.MOLTENPAN)stack.addEnchantment(Enchantments.FIRE_ASPECT, 2);
		else if(Modconfig.Enchantment_Enable && stack.getItem() == FishItems.REAPERS_SCYTHE)stack.addEnchantment(ModEnchantments.LIFESTEAL, 3);
		else if(Modconfig.Enchantment_Enable && stack.getItem() == FishItems.VESPA_DAGGER)stack.addEnchantment(ModEnchantments.POISONOUS, 2);
	}
	
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        for (String type : getToolClasses(stack))
        {
            if (state.getBlock().isToolEffective(type, state))
                return efficiency;
        }
        return stack.getItem().equals(FishItems.UNDERTAKER_SHOVEL) && EFFECTIVE_ON.contains(state.getBlock()) ? this.efficiency : 1.0F;
    }
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!stack.isItemEnchanted())
		{
			if(stack.getItem() == FishItems.MOLTENHAMMER || stack.getItem() == FishItems.MOLTENPAN)stack.addEnchantment(Enchantments.FIRE_ASPECT, 2);
			else if(Modconfig.Enchantment_Enable && stack.getItem() == FishItems.REAPERS_SCYTHE)stack.addEnchantment(ModEnchantments.LIFESTEAL, 3);
			else if(Modconfig.Enchantment_Enable && stack.getItem() == FishItems.VESPA_DAGGER)stack.addEnchantment(ModEnchantments.POISONOUS, 2);
		}
		
		if(entityIn instanceof EntityPlayer && stack.getItem() == FishItems.FAMINE && isSelected) {
			((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 7 * 20, 4));
		}
		
		if(entityIn instanceof EntityLivingBase && stack.getItem() == FishItems.FROZEN_DAGGER && entityIn.isWet() && worldIn.rand.nextInt(50) < 2)
			stack.setItemDamage(java.lang.Math.max(stack.getItemDamage() - 1, 0));
	}
	
	/**
     * Returns the amount of damage this item will deal. One heart of damage is equal to 2 damage points.
     */
	@Override
    public float getAttackDamage()
    {
        return this.Damage;
    }
	
    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack)
    {
        return this.Rarity;
    }
    
	@Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return stack.getItem() == FishItems.MOLTENPAN && stack.getItemDamage() < stack.getMaxDamage();
    }
    
	@Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
		if(this.hasContainerItem(itemStack)) {
			ItemStack result = itemStack.copy();
			result.setItemDamage(itemStack.getItemDamage() + 8);
			return result;
		}
		else return ItemStack.EMPTY;
    }
	
	/**
	* Called when this item is used when targetting a Block
	*/
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		/*Biome biome = worldIn.getBiomeForCoordsBody(pos);
		for(SpawnListEntry E: biome.getSpawnableList(EnumCreatureType.MONSTER)) {
			System.out.println(biome.getBiomeName() + ": " + E.entityClass.getName() + " " + E.itemWeight);
		}
		for(SpawnListEntry E: biome.getSpawnableList(EnumCreatureType.CREATURE)) {
			System.out.println(biome.getBiomeName() + ": " + E.entityClass.getName() + " " + E.itemWeight);
		}
		for(SpawnListEntry E: biome.getSpawnableList(EnumCreatureType.WATER_CREATURE)) {
			System.out.println(biome.getBiomeName() + ": " + E.entityClass.getName() + " " + E.itemWeight);
		}*/
		
		return super.onItemUse(player, worldIn, pos, hand, facing, hitZ, hitZ, hitZ);
	}
	
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		float f = (float) attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		
		if(attacker instanceof EntityPlayer && stack.getItem() == FishItems.REAPERS_SCYTHE)
		{
            float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(attacker) * f;

            for (EntityLivingBase entitylivingbase : attacker.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(2.0D, 0.25D, 2.0D)))
            {
                if (entitylivingbase != attacker && entitylivingbase != target && !attacker.isOnSameTeam(entitylivingbase) && attacker.getDistanceSq(entitylivingbase) < 16.0D)
                {
                    entitylivingbase.knockBack(attacker, 0.4F, (double)MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(attacker.rotationYaw * 0.017453292F)));
                    entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), f3);
                }
            }

            attacker.world.playSound((EntityPlayer)null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, attacker.getSoundCategory(), 1.0F, 1.0F);
            ((EntityPlayer) attacker).spawnSweepParticles();
		}
		else if(attacker instanceof EntityPlayer && stack.getItem() == FishItems.FAMINE && target.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
			((EntityPlayer)attacker).getFoodStats().addStats(1, 0.0F);
		}
		else if(stack.getItem() == FishItems.MOLTENPAN)
		{
			target.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
		}
		else if(stack.getItem() == FishItems.SKELETONKING_MACE)
		{
        	target.addPotionEffect(new PotionEffect(ModMobEffects.FRAGILE, 200, 4));
		}
		
		stack.damageItem(1, attacker);
        return true;
    }
	
	public static void LavaBurst(World worldIn, double x, double y, double z, double radius, EnumParticleTypes particleIn)
	{		
		double NumberofParticles = radius * 8.0D;
		
		for(double i = 0.0D; i < NumberofParticles; i++)
		{
			double d0 = x + radius * MathHelper.sin((float) (i / NumberofParticles * 360.0f));
            double d1 = (double)(y + 1);
            double d2 = z + radius * MathHelper.cos((float) (i / NumberofParticles * 360.0f));
            
            worldIn.spawnParticle(particleIn, d0, d1, d2, 0.0D, 0.0D, 0.0D); 
		}
	}
	
    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		int fire_aspect = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, playerIn.getHeldItem(handIn));
		int sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, playerIn.getHeldItem(handIn));
		int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, playerIn.getHeldItem(handIn));
		int bane_of_arthropods = EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, playerIn.getHeldItem(handIn));
		int smite = EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, playerIn.getHeldItem(handIn));
		int lifesteal = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.LIFESTEAL, playerIn.getHeldItem(handIn));
		int poisonous = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.POISONOUS, playerIn.getHeldItem(handIn));
		int corrosive = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.CORROSIVE, playerIn.getHeldItem(handIn));
		int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, playerIn.getHeldItem(handIn));
		
    	if(playerIn.getHeldItem(handIn).getItem() == FishItems.SLUDGE_WAND) {
        	
        	EntityLilSludge entity = new EntityLilSludge(worldIn);
        	NBTTagCompound nbttagcompound = new NBTTagCompound();
        	
        	entity.setOwnerId(playerIn.getUniqueID());
        	entity.setTamed(true);
        	entity.setLocationAndAngles(playerIn.posX + playerIn.getLookVec().x, playerIn.posY + 0.2F, playerIn.posZ + playerIn.getLookVec().z, 0.0F, 0.0F);       	
        	nbttagcompound.setInteger("fire_aspect", fire_aspect);
        	nbttagcompound.setInteger("sharpness", sharpness);
        	nbttagcompound.setInteger("knockback", knockback);
        	nbttagcompound.setInteger("bane_of_arthropods", bane_of_arthropods);
        	nbttagcompound.setInteger("smite", smite);
        	nbttagcompound.setInteger("unbreaking", unbreaking);
        	nbttagcompound.setInteger("lifesteal", lifesteal);
        	nbttagcompound.setInteger("poisonous", poisonous);
        	nbttagcompound.setInteger("corrosive", corrosive);
        	entity.readEntityFromNBT(nbttagcompound);
        	entity.setLimitedLife(Modconfig.LilSludge_Lifespan * 20);
        	entity.setSkin((fire_aspect > 0) ? 1 : 0);
        	
        	if(!worldIn.isRemote) {
	            worldIn.spawnEntity(entity);
        	}
        		
            LavaBurst(worldIn, entity.posX, entity.posY, entity.posZ, 1.0D, fire_aspect > 0 ? EnumParticleTypes.FLAME : EnumParticleTypes.WATER_BUBBLE);
                        
            playerIn.getHeldItem(handIn).damageItem(8, playerIn);
			playerIn.getCooldownTracker().setCooldown(this, Modconfig.SludgeWand_Cooldown * 20);
			playerIn.getHeldItem(handIn).setAnimationsToGo(5);
            
        	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        
        if(playerIn.getHeldItem(handIn).getItem() == FishItems.MOLTENHAMMER)
		{
			double radius = 4.0D;

			List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerIn.getEntityBoundingBox().grow(radius, radius, radius));
			for(Entity entity1 : list)
			{
				if ((entity1 instanceof EntityLiving && !(entity1 instanceof EntityTameable)) || (entity1 instanceof EntityTameable && !((EntityTameable)entity1).isOwner(playerIn)) || (entity1 instanceof EntityPlayer && Modconfig.MoltenHammer_PVP)/*&& entity1.height <= 1.0F && entity1.width <= 1.0F*/)
				{
					entity1.setFire(2 * fire_aspect);
					entity1.attackEntityFrom(DamageSource.causeMobDamage(playerIn) , 8.0F + (float)sharpness
							+ (((EntityLivingBase) entity1).getCreatureAttribute().equals(EnumCreatureAttribute.ARTHROPOD) ? (float)bane_of_arthropods : 0)
							+ (((EntityLivingBase) entity1).getCreatureAttribute().equals(EnumCreatureAttribute.UNDEAD) ? (float)smite : 0));
					
					((EntityLivingBase)entity1).knockBack(playerIn, (float)knockback * 0.5F, (playerIn.posX - entity1.posX)/playerIn.getDistance(entity1), (playerIn.posZ - entity1.posZ)/playerIn.getDistance(entity1));
					
		            if (bane_of_arthropods > 0 && (((EntityLivingBase) entity1).getCreatureAttribute().equals(EnumCreatureAttribute.ARTHROPOD)))
		            {
		                int i = 20 + worldIn.rand.nextInt(10 * bane_of_arthropods);
		                ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, i, 3));
		            }
		            
		            if(poisonous > 0)
		    			((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(MobEffects.POISON, 8*20, poisonous - 1));
		            
		            if(corrosive > 0)
		            	((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 4*20, corrosive - 1));
				}
			}
			LavaBurst(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, radius, EnumParticleTypes.FLAME);
			playerIn.getHeldItem(handIn).damageItem(16, playerIn);
			playerIn.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
			playerIn.getCooldownTracker().setCooldown(this, 80);
			playerIn.getHeldItem(handIn).setAnimationsToGo(5);
			
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
        
        if(playerIn.getHeldItem(handIn).getItem() == FishItems.BEAST_CLAW && playerIn.onGround)
		{
        	Vec3d lookVec = playerIn.getLookVec();
        	
        	if(playerIn.getHeldItemOffhand().getItem() == FishItems.BEAST_CLAW && playerIn.getHeldItemMainhand().getItem() == FishItems.BEAST_CLAW) {
        		playerIn.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 3 * 20, 0));
        		playerIn.addPotionEffect(new PotionEffect(MobEffects.HASTE, 3 * 20, 0));
        	}
        	
        	playerIn.addVelocity(lookVec.x * 1.5D, lookVec.y * 0.15D + 0.4D, lookVec.z * 1.5D);
        	playerIn.getHeldItem(handIn).damageItem(8, playerIn);
			playerIn.getCooldownTracker().setCooldown(this, 120);
			playerIn.getHeldItem(handIn).setAnimationsToGo(5);
			
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
        
        if(playerIn.getHeldItem(handIn).getItem() == FishItems.UNDERTAKER_SHOVEL)
		{
            for (int i = 0; i < 4; ++i)
            {
                BlockPos blockpos = (new BlockPos(playerIn)).add(-6 + Item.itemRand.nextInt(12), 0, -6 + Item.itemRand.nextInt(12));
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                EntityUnburied entity = new EntityUnburied(worldIn);
                
                entity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                entity.onInitialSpawn(worldIn.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                entity.setOwnerId(playerIn.getUniqueID());
                entity.setTamed(true);
            	nbttagcompound.setInteger("fire_aspect", fire_aspect);
            	nbttagcompound.setInteger("sharpness", sharpness);
            	nbttagcompound.setInteger("knockback", knockback);
            	nbttagcompound.setInteger("bane_of_arthropods", bane_of_arthropods);
            	nbttagcompound.setInteger("smite", smite);
            	nbttagcompound.setInteger("unbreaking", unbreaking);
            	nbttagcompound.setInteger("lifesteal", lifesteal);
            	nbttagcompound.setInteger("poisonous", poisonous);
            	nbttagcompound.setInteger("corrosive", corrosive);
            	entity.readEntityFromNBT(nbttagcompound);
                entity.setLimitedLife(Modconfig.Unburied_Lifespan * 20);
                
                if(!worldIn.isRemote) {
    	            worldIn.spawnEntity(entity);
            	}
            }
            playerIn.getHeldItem(handIn).damageItem(63, playerIn);
            playerIn.getCooldownTracker().setCooldown(this, Modconfig.Undertaker_Shovel_Cooldown * 20);
			playerIn.getHeldItem(handIn).setAnimationsToGo(5);
			
        	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}

    	return super.onItemRightClick(worldIn, playerIn, handIn);
    }
	
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return par2ItemStack.getItem() == this.repair_material ? true : false;
	}
	
	public ItemFishCustomWeapon setNoDescription() {
		this.Tooltip = null;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		if (stack.getItem().equals(FishItems.BONESWORD)) {
			list.add(TextFormatting.YELLOW + I18n.format(this.Tooltip, Modconfig.BoneSword_Damage, Modconfig.BoneSword_DamageCap));
		} else if (stack.getItem().equals(FishItems.BEAST_CLAW)) {
			list.add(TextFormatting.YELLOW + I18n.format(this.Tooltip+".desc0"));
			list.add(TextFormatting.YELLOW + I18n.format(this.Tooltip+".desc1"));
		} else if(this.Tooltip != null)
			list.add(TextFormatting.YELLOW + I18n.format(this.Tooltip));
	}
	
    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
	@Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.Damage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.AttackSpeed, 0));
        }

        return multimap;
    }
}
