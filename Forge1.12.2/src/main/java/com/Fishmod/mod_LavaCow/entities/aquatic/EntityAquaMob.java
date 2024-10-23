package com.Fishmod.mod_LavaCow.entities.aquatic;

import com.Fishmod.mod_LavaCow.core.SpawnUtil;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAquaMob extends EntityMob implements IMob {

    protected int BeachedLife = 300;

    public EntityAquaMob(World worldIn) {
        super(worldIn);
        //this.setPathPriority(PathNodeType.WATER, 16.0F);
        //this.setPathPriority(PathNodeType.WALKABLE, -8.0F);
        //this.setPathPriority(PathNodeType.BLOCKED, -8.0F);
        this.moveHelper = new EntityAquaMob.MoveHelper(this);
        this.experienceValue = 1;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.65F;
    }

    @Override
    public boolean getCanSpawnHere() {
        return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected float getWaterSlowDown() {
        return 1.0F;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    /*public void fall(float distance, float damageMultiplier)
    {
    	if(!this.isInWater())super.fall(distance, damageMultiplier);
    }*/

    /**
     * Returns new PathNavigateGround instance
     */
    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateSwimmer(this, worldIn);
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.01F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double) 0.9F;
            this.motionY *= (double) 0.5F;
            this.motionZ *= (double) 0.9F;
            if (this.getAttackTarget() == null) {
                this.motionY -= 0.005D;
            }
        } else {
            super.travel(strafe, vertical, forward);
        }

    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        if (!this.isInWater() && this.onGround && this.collidedVertically) {
            this.motionY += (double) 0.4F;
            this.motionX += (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F);
            this.motionZ += (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F);
            this.onGround = false;
            this.isAirBorne = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
        }

        if (this.isInWater()) {
            this.setAIMoveSpeed(1.8F);
            //this.motionX *= 1.5F;
            //this.motionZ *= 1.5F;
        }

        if (this.rand.nextFloat() < 0.05F && this.isInWater()) {
            Vec3d vec3d = this.getLook(0.0F);

            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width - vec3d.x * 1.5D, this.posY + this.rand.nextDouble() * (double) this.height - vec3d.y * 1.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width - vec3d.z * 1.5D, 0.0D, 0.0D, 0.0D);
            }
        }

        super.onLivingUpdate();
    }

    /**
     * Gets called every tick from main Entity class
     */
    @Override
    public void onEntityUpdate() {
        int i = this.getAir();
        super.onEntityUpdate();
        if (BeachedLife > 0) {
            if (this.isEntityAlive() && !this.isInWater()) {
                --i;
                this.setAir(i);

                if (this.getAir() == -20) {
                    this.setAir(0);
                    this.attackEntityFrom(DamageSource.DROWN, 2.0F);
                }
            } else
                this.setAir(BeachedLife);
        }
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_ELDER_GUARDIAN_FLOP;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_HOSTILE_SWIM;
    }

    @Override
    public boolean isNotColliding() {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty();
    }

    static class MoveHelper extends EntityMoveHelper {
        private final EntityAquaMob fish;

        MoveHelper(EntityAquaMob p_i48857_1_) {
            super(p_i48857_1_);
            this.fish = p_i48857_1_;
        }

        public void onUpdateMoveHelper() {

            if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.fish.getNavigator().noPath()) {
                double d0 = this.posX - this.fish.posX;
                double d1 = this.posY - this.fish.posY;
                double d2 = this.posZ - this.fish.posZ;
                double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.fish.rotationYaw = this.limitAngle(this.fish.rotationYaw, f, 90.0F);
                this.fish.renderYawOffset = this.fish.rotationYaw;
                float f1 = (float) (this.speed * this.fish.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue());
                this.fish.setAIMoveSpeed(this.fish.getAIMoveSpeed() + (f1 - this.fish.getAIMoveSpeed()) * 0.125F);
                this.fish.motionY += (double) this.fish.getAIMoveSpeed() * d1 * 0.1D;
            } else {
                this.fish.setAIMoveSpeed(0.0F);
            }
        }
    }

}
