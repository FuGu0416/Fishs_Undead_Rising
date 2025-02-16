package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAITargetItem;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityMimic;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.ArrayList;

public class EntityMimic extends EntityFishTameable implements IAggressive {
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityMimic.class, DataSerializers.VARINT);
    private static final DataParameter<String> CHEST_TEXTURE = EntityDataManager.<String>createKey(EntityMimic.class, DataSerializers.STRING);
    public static ArrayList<String> TEXTURE_POOL = new ArrayList<String>(Arrays.asList(
            "textures/entity/chest/normal.png"
    ));

    private boolean isAggressive = false;
    private int AttackTimer, AggressiveTimer = 40;
    public float rotationAngle = 0.0F;
    public int IdleTimer, SitTimer;
    public boolean canDropItems;

    public NonNullList<ItemStack> inventory;
    private EntityAITargetItem<EntityItem> AITargetItem;

    public EntityMimic(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
        this.inventory = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
        this.canDropItems = true;
        this.setCanPickUpLoot(true);
        this.setTamed(false);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.enablePersistence();
        this.tasks.addTask(1, this.aiSit);
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        this.AITargetItem = new EntityAITargetItem<>(this, EntityItem.class, true);
        this.targetTasks.addTask(4, this.AITargetItem);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, (4 + this.rand.nextInt(5)) % 6);
        this.getDataManager().register(CHEST_TEXTURE, EntityMimic.TEXTURE_POOL.get(this.rand.nextInt(EntityMimic.TEXTURE_POOL.size())));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);

        if (this.isTamed()) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Mimic_Health * 2.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Mimic_Attack * 0.5D);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Mimic_Health);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Mimic_Attack);
        }

    }

    public boolean canDropItems() {
        return canDropItems;
    }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired() || super.canDespawn();
    }

    @Override
    public boolean getCanSpawnHere() {
        BlockPos NearbyChest = SpawnUtil.isNearBlock(this.world, Blocks.CHEST, new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ), 2);

        if (NearbyChest != null) {
            this.rotationAngle = this.getEntityWorld().getBlockState(NearbyChest).getValue(BlockChest.FACING).getIndex() - 2;
        } else {
            return false;
        }

        return super.getCanSpawnHere();
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);

        if (tamed) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
            this.setSilent(false);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        }
    }

    private boolean canPickupItems() {
        for (ItemStack stack : this.inventory) {
            if (stack.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private void hasSpace(ItemStack itemstackIn) {
        if (!getEntityWorld().isRemote) {
            for (int i = 0; i < this.inventory.size(); i++)
                if (this.inventory.get(i).getItem().equals(itemstackIn.getItem()) && this.inventory.get(i).isStackable() && this.inventory.get(i).getCount() + itemstackIn.getCount() < this.inventory.get(i).getMaxStackSize()) {
                    this.inventory.get(i).setCount(this.inventory.get(i).getCount() + itemstackIn.getCount());
                    itemstackIn.shrink(itemstackIn.getCount());
                }
        }
    }

    public int containsItem(Item itemIn) {
        if (!getEntityWorld().isRemote) {
            for (int i = 0; i < this.inventory.size(); i++)
                if (this.inventory.get(i).getItem().equals(itemIn)) {
                    return i;
                }
        }

        return -1;
    }

    private void EmergencyFood() {
        if (!getEntityWorld().isRemote)
            for (int i = 0; i < this.inventory.size(); i++)
                if (this.isBreedingItem(this.inventory.get(i))) {
                    ItemFood itemfood = (ItemFood) this.inventory.get(i).getItem();
                    this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.4F, 1.0F);
                    this.heal((float) itemfood.getHealAmount(this.inventory.get(i)));
                    this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 8 * 20, 0));
                    this.inventory.set(i, new ItemStack(this.inventory.get(i).getItem(), this.inventory.get(i).getCount() - 1));
                }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.AggressiveTimer > 0)
            this.AggressiveTimer--;

        if (this.AttackTimer > 0)
            this.AttackTimer--;

        if (!getEntityWorld().isRemote) {
            if (!isAggressive && !this.isTamed()) {
                if (!this.isSilent()) {
                    this.setSitting(true);
                    this.world.setEntityState(this, (byte) (41 + this.rand.nextInt(4)));
                }

                this.posX = MathHelper.floor(posX) + 0.5;
                this.posY = MathHelper.floor(posY);
                this.posZ = MathHelper.floor(posZ) + 0.55;
                this.rotationYaw = prevRotationYaw = this.rotationAngle;
                this.renderYawOffset = prevRenderYawOffset = 0F;

                if (getEntityWorld().getBlockState(getPosition().down()) instanceof BlockAir)
                    posY -= 1;

                this.setSilent(true);
                this.setAIMoveSpeed(0.0F);
            } else if (this.getAttackingEntity() != null) {
                this.AggressiveTimer = 200;
                this.setSilent(false);
                this.setAIMoveSpeed(0.19F);
            }
        }

        if (!this.isTamed() && this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) > this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue()) {
            this.setAttackTarget((EntityLivingBase) null);
        }

        for (ItemStack S : this.getHeldEquipment())
            if (!S.isEmpty()) this.hasSpace(S);

        if (this.isTamed() && this.getHealth() <= this.getMaxHealth() * 0.5F) this.EmergencyFood();

        if (this.getSkin() == getVoidSkin() && this.ticksExisted % 100 == 0) {
            for (int i = 0; i < 8; ++i) {
                int j = rand.nextInt(2) * 2 - 1;
                int k = rand.nextInt(2) * 2 - 1;
                double d0 = (double) this.posX + 0.5D + 0.25D * (double) j;
                double d1 = (double) ((float) this.posY + rand.nextFloat());
                double d2 = (double) this.posZ + 0.5D + 0.25D * (double) k;
                double d3 = (double) (rand.nextFloat() * (float) j);
                double d4 = ((double) rand.nextFloat() - 0.5D) * 0.125D;
                double d5 = (double) (rand.nextFloat() * (float) k);
                this.world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
            }
        }

        if (this.getEntityWorld().isRemote) {
            if (this.isSitting() && this.SitTimer > 0) {
                this.SitTimer--;
            }

            if (!this.isSitting() && this.SitTimer < 20) {
                this.SitTimer++;
            }
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.IdleTimer > 0) {
            this.IdleTimer--;
        }

        if (!this.isAggressive && !this.isTamed() && this.ticksExisted % 100 == 0 && rand.nextInt(5) == 0)
            this.IdleTimer = 30 + rand.nextInt(30);
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if ((!isAggressive && !this.isTamed()) || (this.SitTimer > 0 && this.SitTimer < 20)) {
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
        } else {
            super.travel(strafe, vertical, forward);
        }
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        Entity entity = source.getTrueSource();
        this.setSitting(false);
        this.AggressiveTimer = 200;
        this.setSilent(false);
        this.setAIMoveSpeed(0.19F);
        this.playSound(SoundEvents.ENTITY_SHULKER_HURT_CLOSED, 1.0F, 1.0F);

        if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
            amount = (amount + 1.0F) / 2.0F;
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag) {
            this.playSound(FishItems.ENTITY_ZOMBIEPIRANHA_ATTACK, 1.0F, 1.0F);
            this.applyEnchantments(this, entityIn);
            this.AttackTimer = 5;
            this.world.setEntityState(this, (byte) 40);

            if (this.getSkin() == 6) {
                entityIn.setFire(5);
            }
        }

        return flag;
    }

    @Override
    protected void doSitCommand(EntityPlayer playerIn) {
        super.doSitCommand(playerIn);
        this.world.setEntityState(this, (byte) (41 + this.rand.nextInt(4)));
        this.setSitting(true);
    }

    @Override
    protected void doFollowCommand(EntityPlayer playerIn) {
        super.doFollowCommand(playerIn);

        if (this.getSkin() == this.getVoidSkin()) {
            this.setCanPickUpLoot(false);

            for (ItemStack is : this.inventory) {
                if (!is.isEmpty()) {
                    this.entityDropItem(is.copy(), 0.2F);
                    is.shrink(is.getCount());
                }
            }
        }

        this.setSitting(false);
    }

    public void doMimicChest(EnumFacing facing) {
        this.world.setEntityState(this, (byte) (41 + facing.getIndex() - 2));
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();

        if (itemstack.getItem() == Items.SPAWN_EGG) {
            return super.processInteract(player, hand);
        } else if (this.isTamed() && this.getOwner().equals(player) && this.getOwner() != null) {
            if (player.isSneaking()) {
                if (!this.world.isRemote) {
                    if (this.getSkin() == getVoidSkin()) {
                        player.displayGUIChest(player.getInventoryEnderChest());
                        this.playSound(SoundEvents.BLOCK_ENDERCHEST_OPEN, 1.0F, 1.0F);
                    } else {
                        player.displayGUIChest(new TileEntityMimic(this));
                    }
                    return true;
                }
            }

            if (!itemstack.isEmpty()) {
                if (item instanceof ItemFood) {
                    ItemFood itemfood = (ItemFood) item;
                    if (itemfood.isWolfsFavoriteMeat() && this.dataManager.get(DATA_HEALTH) < this.getMaxHealth()) {
                        if (!player.capabilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }

                        this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.4F, 1.0F);
                        this.heal((float) itemfood.getHealAmount(itemstack));
                        return true;
                    }
                } else if (this.isOwner(player) && this.getSkin() != getVoidSkin() && item == Items.ENDER_EYE) {
                    if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    this.setSkin(getVoidSkin());
                    this.setCanPickUpLoot(false);

                    for (ItemStack is : this.inventory) {
                        if (!is.isEmpty()) {
                            this.entityDropItem(is.copy(), 0.2F);
                            is.shrink(is.getCount());
                        }
                    }

                    this.playSound(SoundEvents.ENTITY_ENDEREYE_DEATH, 1.0F, 1.0F);
                    for (int i = 0; i < 16; ++i) {
                        double d0 = new Random().nextGaussian() * 0.02D;
                        double d1 = new Random().nextGaussian() * 0.02D;
                        double d2 = new Random().nextGaussian() * 0.02D;
                        this.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, this.posX + (double) (new Random().nextFloat() * this.width) - (double) this.width, this.posY + (double) (new Random().nextFloat() * this.height), this.posZ + (double) (new Random().nextFloat() * this.width) - (double) this.width, d0, d1, d2);
                    }

                    return true;
                } else if (this.isOwner(player) && this.getSkin() != getVoidSkin() && item == FishItems.MOOTENHEART) {
                    if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    this.setSkin(6);
                    this.isImmuneToFire = true;
                    this.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1.0F, 1.0F);
                    for (int i = 0; i < 16; ++i) {
                        double d0 = new Random().nextGaussian() * 0.02D;
                        double d1 = new Random().nextGaussian() * 0.02D;
                        double d2 = new Random().nextGaussian() * 0.02D;
                        this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (double) (new Random().nextFloat() * this.width) - (double) this.width, this.posY + (double) (new Random().nextFloat() * this.height), this.posZ + (double) (new Random().nextFloat() * this.width) - (double) this.width, d0, d1, d2);
                    }

                    return true;
                }
            }

            return super.processInteract(player, hand);
        }

        if (!this.isTamed() && this.getDistanceSq(player) < 2.0D) {
            this.playSound(SoundEvents.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
            this.playSound(FishItems.ENTITY_MIMIC_AMBIENT, 0.4F, 1.0F);
            this.setAttackTarget(player);
            this.setSitting(false);
        }

        return super.processInteract(player, hand);
    }

    @Override
    public void onEntityUpdate() {
        // Proper check to make sure that they're always immune to fire
        if (this.getSkin() == 6) {
            this.isImmuneToFire = true;
        }

        super.onEntityUpdate();
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
        if (BiomeDictionary.hasType(this.getEntityWorld().getBiome(this.getPosition()), Type.NETHER)) {
            this.setSkin(6);
            this.isImmuneToFire = true;
        }

        return entityLivingData;
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem().equals(FishItems.CANEROTTENMEAT) || stack.getItem().equals(FishItems.PTERA_WING) || stack.getItem().equals(FishItems.PTERA_WING_COOKED);
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    @Override
    public boolean canMateWith(EntityAnimal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(otherAnimal instanceof EntityMimic)) {
            return false;
        } else {
            EntityMimic entitywolf = (EntityMimic) otherAnimal;
            if (!entitywolf.isTamed()) {
                return false;
            } else if (entitywolf.isSitting()) {
                return false;
            } else {
                return this.isInLove() && entitywolf.isInLove();
            }
        }
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        this.dataManager.set(DATA_HEALTH, Float.valueOf(this.getHealth()));

        if (this.getAttackTarget() != null || this.AggressiveTimer > 0 || this.recentlyHit > 58) {
            isAggressive = true;
            this.world.setEntityState(this, (byte) 11);
        } else if (!this.isSitting() && this.AITargetItem.shouldExecute() && this.canPickupItems()) {
            if (!isAggressive && this.rand.nextInt(1000) < 10) {
                isAggressive = true;
                this.world.setEntityState(this, (byte) 11);
            }
        } else if (this.rand.nextInt(1000) < 100) {
            isAggressive = false;
            this.world.setEntityState(this, (byte) 34);
        }
    }

    public String getChestTexture() {
        return this.dataManager.get(CHEST_TEXTURE);
    }

    public void setChestTexture(String chestTexture) {
        this.dataManager.set(CHEST_TEXTURE, chestTexture);
    }

    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, skinType);
    }

    public int getVoidSkin() {
        return 3;
    }

    @Override
    public boolean isAggressive() {
        return isAggressive;
    }

    public int getSitTimer() {
        return this.SitTimer;
    }

    @Override
    public int getAttackTimer() {
        return this.AttackTimer;
    }

    @Override
    public void setAttackTimer(int i) {
        this.AttackTimer = i;
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 11) {
            this.isAggressive = true;
        } else if (id == 34) {
            this.isAggressive = false;
        } else if (id == 40) {
            this.AttackTimer = 5;
        } else if (id == 41) {
            this.rotationAngle = 180.0F * ((float) Math.PI / 180.0F);
        } else if (id == 42) {
            this.rotationAngle = 270.0F * ((float) Math.PI / 180.0F);
        } else if (id == 43) {
            this.rotationAngle = 0.0F * ((float) Math.PI / 180.0F);
        } else if (id == 44) {
            this.rotationAngle = 90.0F * ((float) Math.PI / 180.0F);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public float getEyeHeight() {
        return 0.7F;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        inventory = NonNullList.<ItemStack>withSize(this.inventory.size(), ItemStack.EMPTY);
        if (compound.hasKey("Items"))
            ItemStackHelper.loadAllItems(compound, inventory);
        this.setSkin(compound.getInteger("Variant"));
        this.setChestTexture(compound.getString("Chest"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        ItemStackHelper.saveAllItems(compound, inventory, false);
        compound.setInteger("Variant", getSkin());
        compound.setString("Chest", getChestTexture());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_MIMIC_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_MIMIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_MIMIC_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SPIDER_STEP;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 2.0F);
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean canBePushed() {
        return !this.isSitting();
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public boolean canBeRidden(Entity entity) {
        return false;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.MIMIC;
    }
    
    /**
     * This is called when Entity's growing age timer reaches 0 (negative values are considered as a child, positive as
     * an adult)
     */
    @Override
    protected void onGrowingAdult() {
    	this.dropItem(FishItems.CHITIN, this.rand.nextInt(2) + 1);
    }

    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
        return !this.isTamed() && this.canDropItems;
    }

    @Override
    public EntityMimic createChild(EntityAgeable ageable) {
        EntityMimic entity = new EntityMimic(this.world);
        UUID uuid = this.getOwnerId();
        if (uuid != null) {
            entity.setOwnerId(uuid);
            entity.setTamed(true);
            entity.setHealth(this.getMaxHealth());
            entity.setSkin(this.rand.nextBoolean() ? this.getSkin() : ((EntityMimic) ageable).getSkin());
            if (entity.getSkin() == 3) entity.setSkin(0);
        }

        return entity;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);

        if (!getEntityWorld().isRemote) {
            for (ItemStack is : this.inventory)
                if (!is.isEmpty()) {
                    this.entityDropItem(is, 0.2F);
                }
        }

        this.inventory.clear();
    }
}
