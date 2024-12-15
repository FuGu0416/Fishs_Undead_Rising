package com.Fishmod.mod_LavaCow.entities.floating;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.message.PacketParticle;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGraveRobberGhost extends EntityFloatingMob implements IAggressive {
    public EntityGraveRobberGhost(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
        this.daytimeBurning = false;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(3, new EntityFloatingMob.AIChargeAttack());
        this.tasks.addTask(3, new EntityGraveRobberGhost.AIUseSpell());
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Grave_Robber_Ghost_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Grave_Robber_Ghost_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }

    /**
     * Gets how bright this entity is.
     */
    @Override
    public float getBrightness() {
        return 1.0F;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Grave_Robber_Ghost_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Grave_Robber_Ghost_Attack);
        this.setHealth(this.getMaxHealth());

        return super.onInitialSpawn(difficulty, livingdata);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.spellTicks = compound.getInteger("SpellTicks");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("SpellTicks", this.spellTicks);
    }

    /**
     * Returns whether this Entity is on the same team as the given Entity.
     */
    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getCreatureAttribute() == EnumCreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        switch (id) {
            case 10:
                this.spellTicks = 30;
                break;
            default:
                super.handleStatusUpdate(id);
                break;
        }
    }

    public class AIUseSpell extends EntityAIBase {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (EntityGraveRobberGhost.this.isSpellcasting()) {
                return false;
            } else {
                int i = EntityGraveRobberGhost.this.world.getEntitiesWithinAABB(EntityVex.class, EntityGraveRobberGhost.this.getEntityBoundingBox().grow(16.0D)).size();
                return EntityGraveRobberGhost.this.ticksExisted >= this.spellCooldown && ((EntityGraveRobberGhost.this.getAttackTarget() != null && Math.abs(EntityGraveRobberGhost.this.posY - EntityGraveRobberGhost.this.getAttackTarget().posY) < 4.0D)) && i < Modconfig.Grave_Robber_Ghost_Ability_Max;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntityGraveRobberGhost.this.spellTicks = this.getCastingTime();
            EntityGraveRobberGhost.this.world.setEntityState(EntityGraveRobberGhost.this, (byte) 10);
            this.spellCooldown = EntityGraveRobberGhost.this.ticksExisted + this.getCastingInterval();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 5) {
                this.castSpell();
                EntityGraveRobberGhost.this.playSound(EntityGraveRobberGhost.this.getSpellSound(), 4.0F, 1.2F);

            }
        }

        protected void castSpell() {
            for (int i = 0; i < Modconfig.Grave_Robber_Ghost_Ability_Num; ++i) {
                BlockPos blockpos = (new BlockPos(EntityGraveRobberGhost.this)).add(-2 + EntityGraveRobberGhost.this.rand.nextInt(3), 1, -2 + EntityGraveRobberGhost.this.rand.nextInt(3));
                EntityVex entity = new EntityVex(EntityGraveRobberGhost.this.world);
                entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
                entity.setHealth(entity.getMaxHealth());
                entity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                entity.onInitialSpawn(EntityGraveRobberGhost.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData) null);
                entity.setLimitedLife(Modconfig.Grave_Robber_Ghost_Minion_Lifespan * 20);
                entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.IRON_SHOVEL));

                if (!EntityGraveRobberGhost.this.world.isRemote)
                    EntityGraveRobberGhost.this.world.spawnEntity(entity);

                entity.setAttackTarget(EntityGraveRobberGhost.this.getAttackTarget());

                if (EntityGraveRobberGhost.this.world instanceof World) {
                    for (int j = 0; j < 24; ++j) {
                        double d0 = entity.posX + (double) (entity.world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width;
                        double d1 = entity.posY + (double) (entity.world.rand.nextFloat() * entity.height);
                        double d2 = entity.posZ + (double) (entity.world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width;
                        mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2));
                    }
                }
            }
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingTime() {
            return 0;
        }

        protected int getCastingInterval() {
            return Modconfig.Grave_Robber_Ghost_Ability_Cooldown * 20;
        }
    }

    // Immune to Poison and Infested
    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return effect.getPotion() != MobEffects.POISON && effect.getPotion() != ModMobEffects.INFESTED && super.isPotionApplicable(effect);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_GRAVE_ROBBER_GHOST_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_GRAVE_ROBBER_GHOST_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_GRAVE_ROBBER_GHOST_DEATH;
    }

    protected SoundEvent getSpellSound() {
        return SoundEvents.AMBIENT_CAVE;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        // Always an illager, even at death...
        return EnumCreatureAttribute.ILLAGER;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.GRAVE_ROBBER_GHOST;
    }
}
