package com.Fishmod.fur.entities;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.init.FUREffectRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ImpEntity extends FogletEntity {	
	public ImpEntity(EntityType<? extends ImpEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);
    }
	
	@Override
    protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new ImpEntity.AISelfImmolation());     
    }
        
    public static AttributeSupplier.Builder createAttributesImp() {    	
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.25D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D)
        		.add(Attributes.MAX_HEALTH, 16.0D/*FURConfig.Imp_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 2.0D/*FURConfig.Imp_Attack.get()*/);
    }
    
    public static boolean checkImpSpawnRules(EntityType<? extends ImpEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) {
        return Monster.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
		switch(id) {
			case 6:
	        	this.level().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY() + this.getBbHeight(), this.getZ(), 0.0D, 0.0D, 0.0D);
	    		SpawnUtil.LavaBurst(this.level(), this.getX(), this.getY(), this.getZ(), 3.0D, ParticleTypes.FLAME);
	    		SpawnUtil.LavaBurst(this.level(), this.getX(), this.getY(), this.getZ(), 1.5D, ParticleTypes.CAMPFIRE_COSY_SMOKE);
				break;		
			default:
				super.handleEntityEvent(id);
				break;
		}
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag p_213386_5_) {
        //this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Imp_Health.get());
        //this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Imp_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	this.setSkin(2);
    	
 	   	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
 	}
       
    public class AISelfImmolation extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean canUse() {
            if (ImpEntity.this.getTarget() == null || ImpEntity.this.getSkin() != 2) {
                return false;
            } else if (ImpEntity.this.isSpellcasting()) {
                return false;
            } else {
            	return ImpEntity.this.tickCount >= this.spellCooldown && ImpEntity.this.distanceTo(ImpEntity.this.getTarget()) < 3.0F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            return ImpEntity.this.getTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void start() {
            this.spellWarmup = this.getCastWarmupTime();
            ImpEntity.this.spellTicks = this.getCastingTime();
            ImpEntity.this.level().broadcastEntityEvent(ImpEntity.this, (byte)10);
            this.spellCooldown = ImpEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                ImpEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                ImpEntity.this.addEffect(new MobEffectInstance(FUREffectRegistry.IMMOLATION.get(), 8 * 20));
            }
        }
        
        protected void castSpell() {
        	double radius = 3.0D;
        	List<Entity> list = ImpEntity.this.level().getEntities(ImpEntity.this, ImpEntity.this.getBoundingBox().inflate(radius));
        	
        	ImpEntity.this.level().broadcastEntityEvent(ImpEntity.this, (byte)6);
        	ImpEntity.this.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F);
        	
			if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(ImpEntity.this.level(), ImpEntity.this)) {
				BlockPos blockpos = ImpEntity.this.blockPosition();
				for(int i = -3 ; i < 3 ; i++) {
					for(int j = -3 ; j < 3 ; j++) {
						for(int k = -3 ; k < 3 ; k++) {					
				            if (ImpEntity.this.random.nextFloat() < 0.1F && ImpEntity.this.level().isEmptyBlock(blockpos.offset(i, j, k))) {
				            	ImpEntity.this.level().setBlockAndUpdate(blockpos.offset(i, j, k), BaseFireBlock.getState(ImpEntity.this.level(), blockpos.offset(i, j, k)));
				            }
						}
					}
				}
			}
			
        	for (Entity entity1 : list) {
        		if (entity1 instanceof LivingEntity livingentity) {                 
        			if (!livingentity.fireImmune()) {        				
        				if (livingentity.hurt(ImpEntity.this.damageSources().mobAttack(ImpEntity.this), (float) ImpEntity.this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.0F)) {
        					livingentity.setRemainingFireTicks(4);
        				}       							
        			}
        		}
        	}
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingTime() {
            return 100;
        }

        protected int getCastingInterval() {
            return 200;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.FURNACE_FIRE_CRACKLE;
        }
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
	}
}
