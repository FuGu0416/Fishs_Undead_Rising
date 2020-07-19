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
import net.minecraft.entity.EnumCreatureType;
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
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
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
        return ItemFishCustomWeapon.EFFECTIVE_ON.contains(state.getBlock()) ? this.efficiency : 1.0F;
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
		
		Biome biome = worldIn.getBiomeForCoordsBody(pos);
		for(SpawnListEntry E: biome.getSpawnableList(EnumCreatureType.MONSTER)) {
			System.out.println(biome.getBiomeName() + ": " + E.entityClass.getName() + " " + E.itemWeight);
		}
		for(SpawnListEntry E: biome.getSpawnableList(EnumCreatureType.CREATURE)) {
			System.out.println(biome.getBiomeName() + ": " + E.entityClass.getName() + " " + E.itemWeight);
		}
		for(SpawnListEntry E: biome.getSpawnableList(EnumCreatureType.WATER_CREATURE)) {
			System.out.println(biome.getBiomeName() + ": " + E.entityClass.getName() + " " + E.itemWeight);
		}
		/*if(player.getHeldItem(hand).getItem() == FishItems.MOLTENHAMMER)
		{
			double radius = 4.0D;
			
			//p_195939_1_.getWorld().playSound(p_195939_1_.getHitX(), p_195939_1_.getHitY(), p_195939_1_.getHitZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(radius, radius, radius));
			for(Entity entity1 : list)
			{
				if (entity1 instanceof EntityLiving || (entity1 instanceof EntityPlayer && Modconfig.MoltenHammer_PVP)/*&& entity1.height <= 1.0F && entity1.width <= 1.0F)
				{
					entity1.attackEntityFrom(DamageSource.causeMobDamage(player) , 8.0F);
					entity1.setFire(4);
				}
				//System.out.println("SMMMMMMASH");
			}
			LavaBurst(worldIn, player.posX, player.posY, player.posZ, radius);
			player.getHeldItem(hand).damageItem(16, player);
			player.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
			player.getCooldownTracker().setCooldown(this, 80);
			player.getHeldItem(hand).setAnimationsToGo(5);
			return EnumActionResult.SUCCESS;
		}
		else*/ return super.onItemUse(player, worldIn, pos, hand, facing, hitZ, hitZ, hitZ);
	}
	
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		float f = (float) attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		
		/*if(stack.getItem() == FishItems.BONESWORD)
		{
			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), ((float)Modconfig.BoneSword_Damage * 0.01F) * target.getMaxHealth() + f);
			//return false;
		}
		else */if(attacker instanceof EntityPlayer && stack.getItem() == FishItems.REAPERS_SCYTHE)
		{
			//target.attackEntityFrom(DamageSource.causeMobDamage(attacker).setDamageBypassesArmor() , this.getAttackDamage());
			//((EntityLiving)target).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, Integer.MAX_VALUE, 0));
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
			
			//return false;
		}
		else if(attacker instanceof EntityPlayer && stack.getItem() == FishItems.FAMINE && target.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
			((EntityPlayer)attacker).getFoodStats().addStats(1, 0.0F);
		}
		else if(stack.getItem() == FishItems.MOLTENPAN)
		{
			//if(attacker.getEntityWorld().isRemote)ClientProxy.playSound(SoundEvents.BLOCK_ANVIL_HIT, attacker);
			target.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
		}
		stack.damageItem(1, attacker);
        return true;
    }
	
	public static void LavaBurst(World worldIn, double x, double y, double z, double radius, EnumParticleTypes particleIn)
	{		
		double NumberofParticles = radius * 8.0D;
		
		for(double i = 0.0D; i < NumberofParticles; i++)
		{
            double d0 = /*radius * Math.sin((360.0D/NumberofParticles) * i);*/x + (Item.itemRand.nextDouble() - 0.5D) * radius;
            double d1 = (double)(y + 1);
            double d2 = /*radius * Math.cos((360.0D/NumberofParticles) * i);*/z + (Item.itemRand.nextDouble() - 0.5D) * radius;

            worldIn.spawnParticle(particleIn, d0, d1, d2, 0.0D, 0.0D, 0.0D); 

		}
	}
	
    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if(playerIn.getHeldItem(handIn).getItem() == FishItems.SLUDGE_WAND) {
        	
        	EntityLilSludge entitywolf = new EntityLilSludge(worldIn);
        	
        	entitywolf.setOwnerId(playerIn.getUniqueID());
        	entitywolf.setTamed(true);
        	entitywolf.setLocationAndAngles(playerIn.posX + playerIn.getLookVec().x, playerIn.posY + 0.2F, playerIn.posZ + playerIn.getLookVec().z, 0.0F, 0.0F);
        	entitywolf.setLimitedLife(Modconfig.LilSludge_Lifespan * 20);
        	
        	if(!worldIn.isRemote) {
	            worldIn.spawnEntity(entitywolf);
        	}
        	
            LavaBurst(worldIn, entitywolf.posX, entitywolf.posY, entitywolf.posZ, 1.0D, EnumParticleTypes.WATER_BUBBLE);
            
            playerIn.getHeldItem(handIn).damageItem(8, playerIn);
			playerIn.getCooldownTracker().setCooldown(this, Modconfig.SludgeWand_Cooldown * 20);
			playerIn.getHeldItem(handIn).setAnimationsToGo(5);
            
        	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        
        if(playerIn.getHeldItem(handIn).getItem() == FishItems.MOLTENHAMMER)
		{
			double radius = 4.0D;
			int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, playerIn.getHeldItem(handIn));
			int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, playerIn.getHeldItem(handIn));
			int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, playerIn.getHeldItem(handIn));
			int l = EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, playerIn.getHeldItem(handIn));
			int m = EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, playerIn.getHeldItem(handIn));
			//p_195939_1_.getWorld().playSound(p_195939_1_.getHitX(), p_195939_1_.getHitY(), p_195939_1_.getHitZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerIn.getEntityBoundingBox().grow(radius, radius, radius));
			for(Entity entity1 : list)
			{
				if ((entity1 instanceof EntityLiving && !(entity1 instanceof EntityTameable)) || (entity1 instanceof EntityTameable && !((EntityTameable)entity1).isOwner(playerIn)) || (entity1 instanceof EntityPlayer && Modconfig.MoltenHammer_PVP)/*&& entity1.height <= 1.0F && entity1.width <= 1.0F*/)
				{
					entity1.setFire(2 * i);
					entity1.attackEntityFrom(DamageSource.causeMobDamage(playerIn) , 8.0F + (float)j
							+ (((EntityLivingBase) entity1).getCreatureAttribute().equals(EnumCreatureAttribute.ARTHROPOD) ? (float)l : 0)
							+ (((EntityLivingBase) entity1).getCreatureAttribute().equals(EnumCreatureAttribute.UNDEAD) ? (float)m : 0));
					
					((EntityLivingBase)entity1).knockBack(playerIn, (float)k * 0.5F, (playerIn.posX - entity1.posX)/playerIn.getDistance(entity1), (playerIn.posZ - entity1.posZ)/playerIn.getDistance(entity1));
				}
				//System.out.println("SMMMMMMASH");
			}
			LavaBurst(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, radius, EnumParticleTypes.FLAME);
			playerIn.getHeldItem(handIn).damageItem(16, playerIn);
			playerIn.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
			playerIn.getCooldownTracker().setCooldown(this, 80);
			playerIn.getHeldItem(handIn).setAnimationsToGo(5);
			
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
        
        if(playerIn.getHeldItem(handIn).getItem() == FishItems.UNDERTAKER_SHOVEL)
		{
            for (int i = 0; i < 4; ++i)
            {
                BlockPos blockpos = (new BlockPos(playerIn)).add(-6 + Item.itemRand.nextInt(12), 0, -6 + Item.itemRand.nextInt(12));
                EntityUnburied entityvex = new EntityUnburied(worldIn);
                entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                entityvex.onInitialSpawn(worldIn.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                entityvex.setOwnerId(playerIn.getUniqueID());
                entityvex.setTamed(true);
                entityvex.setLimitedLife(Modconfig.Unburied_Lifespan * 20);
                
                if(!worldIn.isRemote) {
    	            worldIn.spawnEntity(entityvex);
            	}
            }
            playerIn.getHeldItem(handIn).damageItem(63, playerIn);
            playerIn.getCooldownTracker().setCooldown(this, Modconfig.Undertaker_Shovel_Cooldown * 20);
			playerIn.getHeldItem(handIn).setAnimationsToGo(5);
        	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
        playerIn.getHeldItem(handIn).damageItem(16, playerIn);
    	return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
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
		if(stack.getItem().equals(FishItems.BONESWORD))
			list.add(TextFormatting.YELLOW + I18n.format(this.Tooltip, Modconfig.BoneSword_Damage, Modconfig.BoneSword_DamageCap));
		else if(this.Tooltip != null)
			list.add(TextFormatting.YELLOW/* + "" + TextFormatting.ITALIC*/ + I18n.format(this.Tooltip));
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
