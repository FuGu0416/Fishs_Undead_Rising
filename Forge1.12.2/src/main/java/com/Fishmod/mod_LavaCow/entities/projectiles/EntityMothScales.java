package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.mod_LavaCow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMothScales extends EntityFireball {
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.createKey(EntityMothScales.class, DataSerializers.VARINT);
    private static final float[][] SCALECOLOR = {{0.42F, 0.4F, 1.00F}, {1.00F, 0.59F, 0.06F}, {0.99F, 0.0F, 0.32F}};
    private float damage = 6.0F;

    public EntityMothScales(World worldIn) {
        super(worldIn);
        this.setSize(0.6250F, 0.6250F);
    }

    public EntityMothScales(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
    }

    public EntityMothScales(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
    }

    public static void registerFixesSmallFireball(DataFixer fixer) {
        EntityFireball.registerFixesFireball(fixer, "MothScales");
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, 0);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.collidedHorizontally && !this.collidedVertically) this.accelerationY -= 0.006D;

        if (this.world.isRemote) for (int i = 0; i < 2 + this.rand.nextInt(2); i++) {
            mod_LavaCow.PROXY.spawnCustomParticle("spore", world, this.posX + this.rand.nextDouble() * 0.5D, this.posY + 0.5D + this.rand.nextDouble() * 0.5D, this.posZ + this.rand.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D, Math.max(0.0F, Math.min(1.0F, SCALECOLOR[this.getScaleType()][0] + (this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F)), Math.max(0.0F, Math.min(1.0F, SCALECOLOR[this.getScaleType()][1] + (this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F)), Math.max(0.0F, Math.min(1.0F, SCALECOLOR[this.getScaleType()][2] + (this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            if (this.rand.nextFloat() < 0.15F) {
                this.world.spawnParticle(this.getAdditionalParticle(this.getScaleType()), this.posX + this.rand.nextDouble() * 0.5D, this.posY + 0.5D + this.rand.nextDouble() * 0.5D, this.posZ + this.rand.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    @Override
    protected void onImpact(RayTraceResult result) {
        Entity entity = result.entityHit;
        EntityLivingBase owner = this.shootingEntity;

        if (!this.world.isRemote && result != null && result.typeOfHit != RayTraceResult.Type.MISS) {
            if (entity != this.shootingEntity && owner != null) {
                switch (this.getScaleType()) {
                    default:
                    case 1:
                        if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
                            if (entity != null && entity != owner && !entity.isOnSameTeam(owner)) {
                                // Currently causes issues with velocity
	                			/*\if (!entity.isImmuneToFire() && entity.attackEntityFrom(DamageSource.causeFireballDamage(this, owner), 5.0F)) {
	                            	this.applyEnchantments(owner, entity);
	                            	entity.setFire(5);
	                            	break;
	                        	}*/
                                break;
                            }
                        } else if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                            boolean flag = true;

                            if (this.shootingEntity instanceof EntityLiving) {
                                flag = ForgeEventFactory.getMobGriefingEvent(this.world, this.shootingEntity);
                            }

                            if (flag) {
                                BlockPos blockpos = result.getBlockPos().offset(result.sideHit);
                                if (this.world.isAirBlock(blockpos)) {
                                    this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
                                    break;
                                }
                            }
                        }
                        break;
                    case 0:
                    case 2:
                        if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);

                            entityareaeffectcloud.setOwner(owner);
                            entityareaeffectcloud.setRadius(2.0F);
                            entityareaeffectcloud.setRadiusOnUse(-0.5F);
                            entityareaeffectcloud.setWaitTime(10);
                            entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
                            entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float) entityareaeffectcloud.getDuration());

                            if (this.getScaleType() == 0) {
                                entityareaeffectcloud.addEffect(new PotionEffect(ModMobEffects.VOID_DUST, 4 * 20, 9));
                            } else {
                                entityareaeffectcloud.addEffect(new PotionEffect(ModMobEffects.CORRODED, 20 * 20, 2));
                            }
                            
                            this.world.spawnEntity(entityareaeffectcloud);
                            break;
                        }
                }
            }
        }
    }

    /**
     * Return the motion factor for this projectile. The factor is multiplied by the original motion.
     */
    @Override
    protected float getMotionFactor() {
        return 0.66F;
    }

    public float getDamage() {
        return this.damage;
    }

    public void setDamage(float damageIn) {
        this.damage = damageIn;
    }

    private EnumParticleTypes getAdditionalParticle(int Skin) {
        switch (Skin) {
            default:
            case 0:
                return EnumParticleTypes.PORTAL;
            case 1:
                return EnumParticleTypes.FLAME;
            case 2:
                return EnumParticleTypes.SMOKE_NORMAL;
        }
    }

    @Override
    protected boolean isFireballFiery() {
        return false;
    }

    @Override
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.TOWN_AURA;
    }

    public int getScaleType() {
        return this.dataManager.get(SKIN_TYPE);
    }

    public void setScaleType(int type) {
        this.world.setEntityState(this, (byte) (type + 6));
        this.dataManager.set(SKIN_TYPE, type);
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 6) {
            this.setScaleType(0);
        } else if (id == 7) {
            this.setScaleType(1);
        } else if (id == 8) {
            this.setScaleType(2);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        setScaleType(compound.getInteger("ScaleType"));
        this.world.setEntityState(this, (byte) (this.getScaleType() + 6));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("ScaleType", 99)) {
            this.setScaleType(compound.getInteger("ScaleType"));
        }
    }
}