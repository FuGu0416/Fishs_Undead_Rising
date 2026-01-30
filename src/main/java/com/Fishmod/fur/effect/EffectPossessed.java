package com.Fishmod.fur.effect;

import com.Fishmod.fur.entities.floating.WraithEntity;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class EffectPossessed extends MobEffect {

	public EffectPossessed() {
        super(MobEffectCategory.BENEFICIAL, 0x6FE2E3);
	}
	
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
    
    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);

        if (entity.level().isClientSide()) return;
        if (!entity.isAlive()) return;

        EntityType<WraithEntity> type = FUREntityRegistry.WRAITH.get();
        WraithEntity wraith = type.create(entity.level());
        if (wraith == null) return;

        wraith.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());

        entity.level().addFreshEntity(wraith);
    }
}
