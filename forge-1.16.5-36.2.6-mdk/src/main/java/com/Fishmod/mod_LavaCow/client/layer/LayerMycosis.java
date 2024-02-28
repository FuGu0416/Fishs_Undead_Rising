package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.block.FURShroomBlock;
import com.Fishmod.mod_LavaCow.client.model.entity.UnburiedModel;
import com.Fishmod.mod_LavaCow.entities.tameable.unburied.MycosisEntity;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerMycosis<T extends MycosisEntity> extends LayerRenderer<T, UnburiedModel<T>>{
   
    public LayerMycosis(IEntityRenderer<T, UnburiedModel<T>> p_i50931_1_) {
        super(p_i50931_1_);
    }

	@Override
	public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T entitylivingbaseIn, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!entitylivingbaseIn.isBaby() && !entitylivingbaseIn.isInvisible()) {
	         BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
	         BlockState blockstate = entitylivingbaseIn.getSkin() == 1 ? FURBlockRegistry.GLOWSHROOM.defaultBlockState().setValue(FURShroomBlock.AGE, Integer.valueOf(1)) : FURBlockRegistry.CORDY_SHROOM.defaultBlockState().setValue(FURShroomBlock.AGE, Integer.valueOf(1));
	         int i = LivingRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F);
	         p_225628_1_.pushPose();
	         this.getParentModel().Body_base.translateAndRotate(p_225628_1_);
	         this.getParentModel().Body_waist.translateAndRotate(p_225628_1_);
	         this.getParentModel().Body_chest.translateAndRotate(p_225628_1_);
	         this.getParentModel().Neck0.translateAndRotate(p_225628_1_);
	         this.getParentModel().Neck1.translateAndRotate(p_225628_1_);
	         this.getParentModel().Head.translateAndRotate(p_225628_1_);
	         p_225628_1_.translate(0.0D, (double)-0.675F, (double)-0.25F);
	         p_225628_1_.mulPose(Vector3f.YP.rotationDegrees(-78.0F));
	         p_225628_1_.scale(-1.0F, -1.0F, 1.0F);
	         p_225628_1_.translate(-0.5D, -0.5D, -0.5D);
	         blockrendererdispatcher.renderBlock(blockstate, p_225628_1_, p_225628_2_, p_225628_3_, i, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
	         p_225628_1_.popPose();
	         p_225628_1_.pushPose();
	         this.getParentModel().Body_base.translateAndRotate(p_225628_1_);
	         this.getParentModel().Body_waist.translateAndRotate(p_225628_1_);
	         this.getParentModel().Body_chest.translateAndRotate(p_225628_1_);
	         p_225628_1_.translate((double)0.0F, (double)-0.1F, 0.65D);
	         p_225628_1_.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
	         p_225628_1_.scale(-1.0F, -1.0F, 1.0F);
	         p_225628_1_.translate(-0.5D, -0.5D, -0.5D);
	         blockrendererdispatcher.renderBlock(blockstate, p_225628_1_, p_225628_2_, p_225628_3_, i, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
	         p_225628_1_.popPose();
      	}
	} 
}
