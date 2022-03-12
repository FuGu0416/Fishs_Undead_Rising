package com.Fishmod.mod_LavaCow.entities.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIPickupMeat<T extends ItemEntity> extends TargetGoal {
    protected final Class<T> targetClass;
    private final int targetChance;
    /** Instance of EntityAINearestAttackableTargetSorter. */
    protected final Sorter sorter;
    protected final Predicate <? super ItemEntity > targetEntitySelector;
    protected ItemEntity targetEntity;

    public EntityAIPickupMeat(CreatureEntity creature, Class<T> classTarget, boolean checkSight)
    {
        this(creature, classTarget, checkSight, false);
    }

    public EntityAIPickupMeat(CreatureEntity creature, Class<T> classTarget, boolean checkSight, boolean onlyNearby)
    {
        this(creature, classTarget, 10, checkSight, onlyNearby, (Predicate<? super ItemEntity>)null);
    }

    public EntityAIPickupMeat(CreatureEntity creature, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable final Predicate <? super T > targetSelector)
    {
        super(creature, checkSight, onlyNearby);
        this.targetClass = classTarget;
        this.targetChance = chance;
        this.sorter = new Sorter(creature);
        this.targetEntitySelector = new Predicate<ItemEntity>()
        {
            @Override
        	public boolean apply(@Nullable ItemEntity item)
            {
            	return item instanceof ItemEntity && !item.getItem().isEmpty() && item.getItem().isEdible() && item.getItem().getItem().getFoodProperties().isMeat();
            }
        };
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse()
    {
        if (this.targetChance > 0 && this.mob.getRandom().nextInt(this.targetChance) != 0)
        {
            return false;
        }
        else
        {
            List<ItemEntity> list = this.mob.level.getEntitiesOfClass(ItemEntity.class, this.getTargetableArea(this.getFollowDistance()));
            
            if (list.isEmpty())
            {
                return false;
            }
            else
            {
                Collections.sort(list, this.sorter);
                this.targetEntity = list.get(0);
                return true;
            }
        }

    }

    protected AxisAlignedBB getTargetableArea(double targetDistance)
    {
        return this.mob.getBoundingBox().expandTowards(targetDistance, 4.0D, targetDistance);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start()
    {
    	this.mob.getNavigation().moveTo(this.targetEntity.getX(), this.targetEntity.getY(), this.targetEntity.getZ(), 1);
        super.start();
    }

	@Override
	public void tick() {
		super.tick();
		if (this.targetEntity == null || this.targetEntity != null && !this.targetEntity.isAlive()) {
			this.stop();
		}
		if (this.targetEntity != null && this.targetEntity.isAlive() && this.mob.distanceToSqr(this.targetEntity) < 1.5D) {
			this.targetEntity.getItem().shrink(1);
			this.mob.playSound(SoundEvents.GENERIC_EAT, 1, 1);
			this.mob.heal(this.mob.getMaxHealth() * 0.1F);

			this.stop();
		}
	}

	@Override
	public boolean canContinueToUse() {
		return !this.mob.getNavigation().isDone();
	}    
    
    public static class Sorter implements Comparator<Entity>
        {
            private final Entity entity;

            public Sorter(Entity entityIn)
            {
                this.entity = entityIn;
            }

            public int compare(Entity p_compare_1_, Entity p_compare_2_)
            {
                double d0 = this.entity.distanceToSqr(p_compare_1_);
                double d1 = this.entity.distanceToSqr(p_compare_2_);

                if (d0 < d1)
                {
                    return -1;
                }
                else
                {
                    return d0 > d1 ? 1 : 0;
                }
            }
        }
}
