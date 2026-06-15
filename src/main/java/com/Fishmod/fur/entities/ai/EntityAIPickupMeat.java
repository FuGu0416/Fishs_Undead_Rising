package com.Fishmod.fur.entities.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;

public class EntityAIPickupMeat<T extends ItemEntity> extends TargetGoal {
    protected final Class<T> targetType;
    private final int targetChance;
    /** Instance of EntityAINearestAttackableTargetSorter. */
    protected final Sorter sorter;
    protected final Predicate <? super ItemEntity > targetConditions;
    protected ItemEntity target;

    public EntityAIPickupMeat(Mob creature, Class<T> classTarget, boolean checkSight) {
        this(creature, classTarget, checkSight, false);
    }

    public EntityAIPickupMeat(Mob creature, Class<T> classTarget, boolean checkSight, boolean onlyNearby) {
        this(creature, classTarget, 10, checkSight, onlyNearby, (Predicate<? super ItemEntity>)null);
    }

    public EntityAIPickupMeat(Mob creature, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable final Predicate <? super T > targetSelector) {
        super(creature, checkSight, onlyNearby);
        this.targetType = classTarget;
        this.targetChance = chance;
        this.sorter = new Sorter(creature);
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        this.targetConditions = new Predicate<ItemEntity>() {
            @Override
        	public boolean apply(@Nullable ItemEntity item) {
            	return item instanceof ItemEntity && !item.getItem().isEmpty() && item.getItem().isEdible() && item.getItem().getItem().getFoodProperties().isMeat();
            }
        };
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
        if (this.targetChance > 0 && this.mob.getRandom().nextInt(this.targetChance) != 0) {
            return false;
        } else {
            List<ItemEntity> list = this.mob.level().getEntitiesOfClass(ItemEntity.class, this.getTargetSearchArea(this.getFollowDistance()), this.targetConditions);
            
            if (list.isEmpty()) {
                return false;
            } else {
                Collections.sort(list, this.sorter);
                this.target = list.get(0);
                return true;
            }
        }
    }

    protected AABB getTargetSearchArea(double targetDistance) {
        return this.mob.getBoundingBox().expandTowards(targetDistance, 4.0D, targetDistance);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
    	this.mob.getNavigation().moveTo(this.target.getX(), this.target.getY(), this.target.getZ(), 1);
        super.start();
    }

	@Override
	public void tick() {
		super.tick();
		if (this.target == null || this.target != null && !this.target.isAlive()) {
			this.stop();
		}
		
		if (this.target != null && this.target.isAlive() && this.mob.distanceToSqr(this.target) < 1.5D) {
			this.target.getItem().shrink(1);
			this.mob.playSound(SoundEvents.GENERIC_EAT, 1, 1);
			this.mob.heal(this.mob.getMaxHealth() * 0.1F);

			this.stop();
		}
	}

	@Override
	public boolean canContinueToUse() {
		return !this.mob.getNavigation().isDone();
	}    

    public static class Sorter implements Comparator<Entity> {

        private final Entity theEntity;

        public Sorter(Entity theEntityIn) {
            this.theEntity = theEntityIn;
        }

        @Override
        public int compare(Entity entity1, Entity entity2) {
            final double d0 = this.theEntity.distanceToSqr(entity1);
            final double d1 = this.theEntity.distanceToSqr(entity2);
            return Double.compare(d0, d1);
        }
    }
}
