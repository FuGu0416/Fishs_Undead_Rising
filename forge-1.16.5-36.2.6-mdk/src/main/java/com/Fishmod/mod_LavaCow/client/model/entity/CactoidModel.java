package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.CactoidEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports

@OnlyIn(Dist.CLIENT)
public class CactoidModel<T extends CactoidEntity> extends FURBaseModel<T> implements IHasHead {
	private final ModelRenderer body_base;
	private final ModelRenderer root_r;
	private final ModelRenderer root_l;
	/* Skin-0 */
	private final ModelRenderer spike3_r1;
	private final ModelRenderer spike2_r1;
	private final ModelRenderer spike1_r1;
	private final ModelRenderer spike0_r1;
	private final ModelRenderer crest_0;
	private final ModelRenderer flower0_0;
	private final ModelRenderer flower1_0;
	private final ModelRenderer fruit_0;
	/* Skin-1 */
	private final ModelRenderer leaf0_1;
	private final ModelRenderer leaf1_1;
	private final ModelRenderer flower0_1;
	private final ModelRenderer flower1_1;
	private final ModelRenderer fruit_1;
	/* Skin-2 */
	private final ModelRenderer leaf0_2;
	private final ModelRenderer leaf0_claw_2;
	private final ModelRenderer leaf1_2;
	private final ModelRenderer leaf0_claw2_2;
	private final ModelRenderer flower0_2;
	private final ModelRenderer flower1_2;
	private final ModelRenderer fruit_2;
	/* Skin-3 */
	private final ModelRenderer stem0_3;
	private final ModelRenderer stem1_3;
	private final ModelRenderer flower0_3;
	private final ModelRenderer flower1_3;
	private final ModelRenderer fruit_3;
	private final ModelRenderer stem2_3;
	private final ModelRenderer flower2_3;
	private final ModelRenderer flower3_3;
	private final ModelRenderer fruit2_3;
	private final ModelRenderer stem3_3;
	
	public CactoidModel() {
		this.texWidth = 32;
		this.texHeight = 32;

		this.body_base = new ModelRenderer(this);
		this.body_base.setPos(0.0F, 22.0F, 0.0F);
		this.body_base.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		this.root_r = new ModelRenderer(this);
		this.root_r.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.root_r);
		this.root_r.texOffs(0, 0).addBox(-3.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, true);

		this.root_l = new ModelRenderer(this);
		this.root_l.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.root_l);
		this.root_l.texOffs(0, 0).addBox(1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		/* Skin-0 */
		spike3_r1 = new ModelRenderer(this);
		spike3_r1.setPos(-4.0F, -5.0F, 4.0F);
		body_base.addChild(spike3_r1);
		this.setRotateAngle(spike3_r1, 0.0F, 0.7854F, 0.0F);
		spike3_r1.texOffs(0, 24).addBox(-1.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, 0.0F, false);

		spike2_r1 = new ModelRenderer(this);
		spike2_r1.setPos(4.0F, -5.0F, 4.0F);
		body_base.addChild(spike2_r1);
		this.setRotateAngle(spike2_r1, 0.0F, -0.7854F, 0.0F);
		spike2_r1.texOffs(0, 24).addBox(0.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, 0.0F, true);

		spike1_r1 = new ModelRenderer(this);
		spike1_r1.setPos(4.0F, -5.0F, -4.0F);
		body_base.addChild(spike1_r1);
		this.setRotateAngle(spike1_r1, 0.0F, 0.7854F, 0.0F);
		spike1_r1.texOffs(0, 24).addBox(0.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, 0.0F, true);

		spike0_r1 = new ModelRenderer(this);
		spike0_r1.setPos(-4.0F, -5.0F, -4.0F);
		body_base.addChild(spike0_r1);
		this.setRotateAngle(spike0_r1, 0.0F, -0.7854F, 0.0F);
		spike0_r1.texOffs(0, 24).addBox(-1.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, 0.0F, false);
		
		this.crest_0 = new ModelRenderer(this);
		this.crest_0.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.crest_0);
		this.crest_0.texOffs(0, 16).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
		
		this.flower0_0 = new ModelRenderer(this);
		this.flower0_0.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.flower0_0);
		this.setRotateAngle(this.flower0_0, 0.0F, -0.7854F, 0.0F);
		this.flower0_0.texOffs(16, 17).addBox(-4.0F, -14.0F, 0.0F, 8.0F, 6.0F, 0.0F, 0.0F, false);

		this.flower1_0 = new ModelRenderer(this);
		this.flower1_0.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.flower1_0);
		this.setRotateAngle(this.flower1_0, 0.0F, 0.7854F, 0.0F);
		this.flower1_0.texOffs(16, 17).addBox(-4.0F, -14.0F, 0.0F, 8.0F, 6.0F, 0.0F, 0.0F, true);

		this.fruit_0 = new ModelRenderer(this);
		this.fruit_0.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.fruit_0);
		this.fruit_0.texOffs(24, 0).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		
		/* Skin-1 */
		this.leaf0_1 = new ModelRenderer(this);
		this.leaf0_1.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.leaf0_1);
		this.setRotateAngle(this.leaf0_1, 0.0F, -1.5708F, 0.0F);
		this.leaf0_1.texOffs(0, 16).addBox(-4.0F, -16.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F, false);

		this.leaf1_1 = new ModelRenderer(this);
		this.leaf1_1.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.leaf1_1);
		this.leaf1_1.texOffs(0, 16).addBox(-4.0F, -16.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F, true);

		this.flower0_1 = new ModelRenderer(this);
		this.flower0_1.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.flower0_1);
		this.flower0_1.texOffs(20, 19).addBox(-3.0F, -20.0F, 0.0F, 6.0F, 4.0F, 0.0F, 0.0F, false);

		this.flower1_1 = new ModelRenderer(this);
		this.flower1_1.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.flower1_1);
		this.setRotateAngle(this.flower1_1, 0.0F, -1.5708F, 0.0F);
		this.flower1_1.texOffs(20, 19).addBox(-3.0F, -20.0F, 0.0F, 6.0F, 4.0F, 0.0F, 0.0F, true);

		this.fruit_1 = new ModelRenderer(this);
		this.fruit_1.setPos(0.0F, -8.0F, 0.0F);
		this.body_base.addChild(this.fruit_1);
		this.fruit_1.texOffs(24, 0).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		
		/* Skin-2 */
		this.leaf0_2 = new ModelRenderer(this);
		this.leaf0_2.setPos(3.0F, -7.0F, 0.0F);
		this.body_base.addChild(this.leaf0_2);
		this.setRotateAngle(this.leaf0_2, 0.0F, -1.5708F, -0.9599F);
		this.leaf0_2.texOffs(0, 18).addBox(-2.5F, -2.0F, -8.0F, 5.0F, 2.0F, 8.0F, 0.0F, false);

		this.leaf0_claw_2 = new ModelRenderer(this);
		this.leaf0_claw_2.setPos(-0.5F, -4.5F, -7.5F);
		this.leaf0_2.addChild(this.leaf0_claw_2);
		this.leaf0_claw_2.texOffs(0, 16).addBox(-2.0F, 1.5F, -0.5F, 5.0F, 1.0F, 1.0F, 0.0F, false);

		this.leaf1_2 = new ModelRenderer(this);
		this.leaf1_2.setPos(-3.0F, -7.0F, 0.0F);
		this.body_base.addChild(this.leaf1_2);
		this.setRotateAngle(this.leaf1_2, 0.0F, 1.5708F, 0.9599F);
		this.leaf1_2.texOffs(0, 18).addBox(-2.5F, -2.0F, -8.0F, 5.0F, 2.0F, 8.0F, 0.0F, true);

		this.leaf0_claw2_2 = new ModelRenderer(this);
		this.leaf0_claw2_2.setPos(0.5F, -4.5F, -7.5F);
		this.leaf1_2.addChild(this.leaf0_claw2_2);
		this.leaf0_claw2_2.texOffs(0, 16).addBox(-3.0F, 1.5F, -0.5F, 5.0F, 1.0F, 1.0F, 0.0F, true);

		this.flower0_2 = new ModelRenderer(this);
		this.flower0_2.setPos(0.0F, -8.0F, 0.0F);
		this.body_base.addChild(this.flower0_2);
		this.flower0_2.texOffs(20, 16).addBox(-3.0F, -10.0F, 0.0F, 6.0F, 10.0F, 0.0F, 0.0F, false);

		this.flower1_2 = new ModelRenderer(this);
		this.flower1_2.setPos(0.0F, -8.0F, 0.0F);
		this.body_base.addChild(this.flower1_2);
		this.setRotateAngle(this.flower1_2, 0.0F, -1.5708F, 0.0F);
		this.flower1_2.texOffs(20, 16).addBox(-3.0F, -10.0F, 0.0F, 6.0F, 10.0F, 0.0F, 0.0F, true);

		this.fruit_2 = new ModelRenderer(this);
		this.fruit_2.setPos(0.0F, -8.0F, 0.0F);
		this.body_base.addChild(this.fruit_2);
		this.fruit_2.texOffs(24, 0).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		
		/* Skin-3 */
		this.stem0_3 = new ModelRenderer(this);
		this.stem0_3.setPos(-2.0F, -8.0F, -2.0F);
		this.body_base.addChild(this.stem0_3);
		this.setRotateAngle(this.stem0_3, 0.2132F, 0.0469F, -0.2132F);
		this.stem0_3.texOffs(0, 16).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		this.stem1_3 = new ModelRenderer(this);
		this.stem1_3.setPos(2.0F, -8.0F, -2.0F);
		this.body_base.addChild(this.stem1_3);
		this.setRotateAngle(this.stem1_3, 0.1745F, 0.0F, 0.1745F);
		this.stem1_3.texOffs(16, 16).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		this.flower0_3 = new ModelRenderer(this);
		this.flower0_3.setPos(0.0F, -4.0F, 0.0F);
		this.stem1_3.addChild(this.flower0_3);
		this.flower0_3.texOffs(20, 28).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F, 0.0F, false);

		this.flower1_3 = new ModelRenderer(this);
		this.flower1_3.setPos(-4.0F, -10.0F, 4.0F);
		this.stem1_3.addChild(this.flower1_3);
		this.setRotateAngle(this.flower1_3, 0.0F, -1.5708F, 0.0F);
		this.flower1_3.texOffs(20, 28).addBox(-7.0F, 2.0F, -4.0F, 6.0F, 4.0F, 0.0F, 0.0F, false);

		this.fruit_3 = new ModelRenderer(this);
		this.fruit_3.setPos(0.0F, -4.0F, 0.0F);
		this.stem1_3.addChild(this.fruit_3);
		this.fruit_3.texOffs(24, 0).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		this.stem2_3 = new ModelRenderer(this);
		this.stem2_3.setPos(-2.0F, -8.0F, 2.0F);
		this.body_base.addChild(this.stem2_3);
		this.setRotateAngle(this.stem2_3, -0.1309F, 0.0F, -0.1745F);
		this.stem2_3.texOffs(0, 16).addBox(-2.0F, -10.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, true);

		this.flower2_3 = new ModelRenderer(this);
		this.flower2_3.setPos(0.0F, -10.0F, 0.0F);
		this.stem2_3.addChild(this.flower2_3);
		this.flower2_3.texOffs(20, 28).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F, 0.0F, true);

		this.flower3_3 = new ModelRenderer(this);
		this.flower3_3.setPos(0.0F, -10.0F, 0.0F);
		this.stem2_3.addChild(this.flower3_3);
		this.setRotateAngle(this.flower3_3, 0.0F, -1.5708F, 0.0F);
		this.flower3_3.texOffs(20, 28).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F, 0.0F, false);

		this.fruit2_3 = new ModelRenderer(this);
		this.fruit2_3.setPos(0.0F, -10.0F, 0.0F);
		this.stem2_3.addChild(this.fruit2_3);
		this.fruit2_3.texOffs(24, 0).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		this.stem3_3 = new ModelRenderer(this);
		this.stem3_3.setPos(2.0F, -8.0F, 2.0F);
		this.body_base.addChild(this.stem3_3);
		this.setRotateAngle(this.stem3_3, -0.2132F, 0.0469F, 0.2132F);
		this.stem3_3.texOffs(16, 16).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, true);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		switch(entity.getSkin()) {
	    	case 1:
	    		this.spike3_r1.visible = false;
	    		this.spike2_r1.visible = false;
	    		this.spike1_r1.visible = false;
	    		this.spike0_r1.visible = false;
	    		this.crest_0.visible = false;
		    	this.flower0_0.visible = false;
		    	this.flower1_0.visible = false;
		    	this.fruit_0.visible = false;
		    	this.leaf0_1.visible = true;
		    	this.leaf1_1.visible = true;
		    	this.flower0_1.visible = true;
		    	this.flower1_1.visible = true;
		    	this.fruit_1.visible = true;
		    	this.leaf0_2.visible = false;
		    	this.leaf0_claw_2.visible = false;
		    	this.leaf1_2.visible = false;
		    	this.leaf0_claw2_2.visible = false;
		    	this.flower0_2.visible = false;
		    	this.flower1_2.visible = false;
		    	this.fruit_2.visible = false;
		    	this.stem0_3.visible = false;
		    	this.stem1_3.visible = false;
		    	this.flower0_3.visible = false;
		    	this.flower1_3.visible = false;
		    	this.fruit_3.visible = false;
		    	this.stem2_3.visible = false;
		    	this.flower2_3.visible = false;
		    	this.flower3_3.visible = false;
		    	this.fruit2_3.visible = false;
		    	this.stem3_3.visible = false;
		    	switch(entity.getGrowingStage()) {
			    	case 0:
				    	this.flower0_0.visible = false;
				    	this.flower1_0.visible = false;
				    	this.fruit_0.visible = false;
				    	this.flower0_1.visible = false;
				    	this.flower1_1.visible = false;
				    	this.fruit_1.visible = false;
				    	this.flower0_2.visible = false;
				    	this.flower1_2.visible = false;
				    	this.fruit_2.visible = false;
				    	this.flower0_3.visible = false;
				    	this.flower1_3.visible = false;
				    	this.fruit_3.visible = false;
				    	this.flower2_3.visible = false;
				    	this.flower3_3.visible = false;
				    	this.fruit2_3.visible = false;
			    		break;
			    	case 1:
				    	this.flower0_0.visible = false;
				    	this.flower1_0.visible = false;
				    	this.fruit_0.visible = false;
				    	this.flower0_1.visible = true;
				    	this.flower1_1.visible = true;
				    	this.fruit_1.visible = false;
				    	this.flower0_2.visible = false;
				    	this.flower1_2.visible = false;
				    	this.fruit_2.visible = false;
				    	this.flower0_3.visible = false;
				    	this.flower1_3.visible = false;
				    	this.fruit_3.visible = false;
				    	this.flower2_3.visible = false;
				    	this.flower3_3.visible = false;
				    	this.fruit2_3.visible = false;
			    		break;
			    	case 2:
					default:
				    	this.flower0_0.visible = false;
				    	this.flower1_0.visible = false;
				    	this.fruit_0.visible = false;
				    	this.flower0_1.visible = false;
				    	this.flower1_1.visible = false;
				    	this.fruit_1.visible = true;
				    	this.flower0_2.visible = false;
				    	this.flower1_2.visible = false;
				    	this.fruit_2.visible = false;
				    	this.flower0_3.visible = false;
				    	this.flower1_3.visible = false;
				    	this.fruit_3.visible = false;
				    	this.flower2_3.visible = false;
				    	this.flower3_3.visible = false;
				    	this.fruit2_3.visible = false;
						break;
				}
	    		break;
	    	case 2:
	    		this.spike3_r1.visible = false;
	    		this.spike2_r1.visible = false;
	    		this.spike1_r1.visible = false;
	    		this.spike0_r1.visible = false;
	    		this.crest_0.visible = false;
		    	this.flower0_0.visible = false;
		    	this.flower1_0.visible = false;
		    	this.fruit_0.visible = false;
		    	this.leaf0_1.visible = false;
		    	this.leaf1_1.visible = false;
		    	this.flower0_1.visible = false;
		    	this.flower1_1.visible = false;
		    	this.fruit_1.visible = false;
		    	this.leaf0_2.visible = true;
		    	this.leaf0_claw_2.visible = true;
		    	this.leaf1_2.visible = true;
		    	this.leaf0_claw2_2.visible = true;
		    	this.flower0_2.visible = true;
		    	this.flower1_2.visible = true;
		    	this.fruit_2.visible = true;
		    	this.stem0_3.visible = false;
		    	this.stem1_3.visible = false;
		    	this.flower0_3.visible = false;
		    	this.flower1_3.visible = false;
		    	this.fruit_3.visible = false;
		    	this.stem2_3.visible = false;
		    	this.flower2_3.visible = false;
		    	this.flower3_3.visible = false;
		    	this.fruit2_3.visible = false;
		    	this.stem3_3.visible = false;
		    	switch(entity.getGrowingStage()) {
			    	case 0:
				    	this.flower0_0.visible = false;
				    	this.flower1_0.visible = false;
				    	this.fruit_0.visible = false;
				    	this.flower0_1.visible = false;
				    	this.flower1_1.visible = false;
				    	this.fruit_1.visible = false;
				    	this.flower0_2.visible = false;
				    	this.flower1_2.visible = false;
				    	this.fruit_2.visible = false;
				    	this.flower0_3.visible = false;
				    	this.flower1_3.visible = false;
				    	this.fruit_3.visible = false;
				    	this.flower2_3.visible = false;
				    	this.flower3_3.visible = false;
				    	this.fruit2_3.visible = false;
			    		break;
			    	case 1:
				    	this.flower0_0.visible = false;
				    	this.flower1_0.visible = false;
				    	this.fruit_0.visible = false;
				    	this.flower0_1.visible = false;
				    	this.flower1_1.visible = false;
				    	this.fruit_1.visible = false;
				    	this.flower0_2.visible = true;
				    	this.flower1_2.visible = true;
				    	this.fruit_2.visible = false;
				    	this.flower0_3.visible = false;
				    	this.flower1_3.visible = false;
				    	this.fruit_3.visible = false;
				    	this.flower2_3.visible = false;
				    	this.flower3_3.visible = false;
				    	this.fruit2_3.visible = false;
			    		break;
			    	case 2:
					default:
				    	this.flower0_0.visible = false;
				    	this.flower1_0.visible = false;
				    	this.fruit_0.visible = false;
				    	this.flower0_1.visible = false;
				    	this.flower1_1.visible = false;
				    	this.fruit_1.visible = false;
				    	this.flower0_2.visible = false;
				    	this.flower1_2.visible = false;
				    	this.fruit_2.visible = true;
				    	this.flower0_3.visible = false;
				    	this.flower1_3.visible = false;
				    	this.fruit_3.visible = false;
				    	this.flower2_3.visible = false;
				    	this.flower3_3.visible = false;
				    	this.fruit2_3.visible = false;
						break;
				}
	    		break;
	    	case 3:
	    		this.spike3_r1.visible = false;
	    		this.spike2_r1.visible = false;
	    		this.spike1_r1.visible = false;
	    		this.spike0_r1.visible = false;
	    		this.crest_0.visible = false;
		    	this.flower0_0.visible = false;
		    	this.flower1_0.visible = false;
		    	this.fruit_0.visible = false;
		    	this.leaf0_1.visible = false;
		    	this.leaf1_1.visible = false;
		    	this.flower0_1.visible = false;
		    	this.flower1_1.visible = false;
		    	this.fruit_1.visible = false;
		    	this.leaf0_2.visible = false;
		    	this.leaf0_claw_2.visible = false;
		    	this.leaf1_2.visible = false;
		    	this.leaf0_claw2_2.visible = false;
		    	this.flower0_2.visible = false;
		    	this.flower1_2.visible = false;
		    	this.fruit_2.visible = false;
		    	this.stem0_3.visible = true;
		    	this.stem1_3.visible = true;
		    	this.flower0_3.visible = true;
		    	this.flower1_3.visible = true;
		    	this.fruit_3.visible = true;
		    	this.stem2_3.visible = true;
		    	this.flower2_3.visible = true;
		    	this.flower3_3.visible = true;
		    	this.fruit2_3.visible = true;
		    	this.stem3_3.visible = true;
		    	switch(entity.getGrowingStage()) {
			    	case 0:
				    	this.flower0_0.visible = false;
				    	this.flower1_0.visible = false;
				    	this.fruit_0.visible = false;
				    	this.flower0_1.visible = false;
				    	this.flower1_1.visible = false;
				    	this.fruit_1.visible = false;
				    	this.flower0_2.visible = false;
				    	this.flower1_2.visible = false;
				    	this.fruit_2.visible = false;
				    	this.flower0_3.visible = false;
				    	this.flower1_3.visible = false;
				    	this.fruit_3.visible = false;
				    	this.flower2_3.visible = false;
				    	this.flower3_3.visible = false;
				    	this.fruit2_3.visible = false;
			    		break;
			    	case 1:
				    	this.flower0_0.visible = false;
				    	this.flower1_0.visible = false;
				    	this.fruit_0.visible = false;
				    	this.flower0_1.visible = false;
				    	this.flower1_1.visible = false;
				    	this.fruit_1.visible = false;
				    	this.flower0_2.visible = false;
				    	this.flower1_2.visible = false;
				    	this.fruit_2.visible = false;
				    	this.flower0_3.visible = true;
				    	this.flower1_3.visible = true;
				    	this.fruit_3.visible = false;
				    	this.flower2_3.visible = true;
				    	this.flower3_3.visible = true;
				    	this.fruit2_3.visible = false;
			    		break;
			    	case 2:
					default:
				    	this.flower0_0.visible = false;
				    	this.flower1_0.visible = false;
				    	this.fruit_0.visible = false;
				    	this.flower0_1.visible = false;
				    	this.flower1_1.visible = false;
				    	this.fruit_1.visible = false;
				    	this.flower0_2.visible = false;
				    	this.flower1_2.visible = false;
				    	this.fruit_2.visible = false;
				    	this.flower0_3.visible = false;
				    	this.flower1_3.visible = false;
				    	this.fruit_3.visible = true;
				    	this.flower2_3.visible = false;
				    	this.flower3_3.visible = false;
				    	this.fruit2_3.visible = true;
						break;
				}
	    		break;
			default:
	    		this.spike3_r1.visible = true;
	    		this.spike2_r1.visible = true;
	    		this.spike1_r1.visible = true;
	    		this.spike0_r1.visible = true;
				this.crest_0.visible = true;
		    	this.flower0_0.visible = true;
		    	this.flower1_0.visible = true;
		    	this.fruit_0.visible = true;
		    	this.leaf0_1.visible = false;
		    	this.leaf1_1.visible = false;
		    	this.flower0_1.visible = false;
		    	this.flower1_1.visible = false;
		    	this.fruit_1.visible = false;
		    	this.leaf0_2.visible = false;
		    	this.leaf0_claw_2.visible = false;
		    	this.leaf1_2.visible = false;
		    	this.leaf0_claw2_2.visible = false;
		    	this.flower0_2.visible = false;
		    	this.flower1_2.visible = false;
		    	this.fruit_2.visible = false;
		    	this.stem0_3.visible = false;
		    	this.stem1_3.visible = false;
		    	this.flower0_3.visible = false;
		    	this.flower1_3.visible = false;
		    	this.fruit_3.visible = false;
		    	this.stem2_3.visible = false;
		    	this.flower2_3.visible = false;
		    	this.flower3_3.visible = false;
		    	this.fruit2_3.visible = false;
		    	this.stem3_3.visible = false;
		    	switch(entity.getGrowingStage()) {
			    	case 0:
				    	this.flower0_0.visible = false;
				    	this.flower1_0.visible = false;
				    	this.fruit_0.visible = false;
				    	this.flower0_1.visible = false;
				    	this.flower1_1.visible = false;
				    	this.fruit_1.visible = false;
				    	this.flower0_2.visible = false;
				    	this.flower1_2.visible = false;
				    	this.fruit_2.visible = false;
				    	this.flower0_3.visible = false;
				    	this.flower1_3.visible = false;
				    	this.fruit_3.visible = false;
				    	this.flower2_3.visible = false;
				    	this.flower3_3.visible = false;
				    	this.fruit2_3.visible = false;
			    		break;
			    	case 1:
				    	this.flower0_0.visible = true;
				    	this.flower1_0.visible = true;
				    	this.fruit_0.visible = false;
				    	this.flower0_1.visible = false;
				    	this.flower1_1.visible = false;
				    	this.fruit_1.visible = false;
				    	this.flower0_2.visible = false;
				    	this.flower1_2.visible = false;
				    	this.fruit_2.visible = false;
				    	this.flower0_3.visible = false;
				    	this.flower1_3.visible = false;
				    	this.fruit_3.visible = false;
				    	this.flower2_3.visible = false;
				    	this.flower3_3.visible = false;
				    	this.fruit2_3.visible = false;
			    		break;
			    	case 2:
					default:
				    	this.flower0_0.visible = false;
				    	this.flower1_0.visible = false;
				    	this.fruit_0.visible = true;
				    	this.flower0_1.visible = false;
				    	this.flower1_1.visible = false;
				    	this.fruit_1.visible = false;
				    	this.flower0_2.visible = false;
				    	this.flower1_2.visible = false;
				    	this.fruit_2.visible = false;
				    	this.flower0_3.visible = false;
				    	this.flower1_3.visible = false;
				    	this.fruit_3.visible = false;
				    	this.flower2_3.visible = false;
				    	this.flower3_3.visible = false;
				    	this.fruit2_3.visible = false;
						break;
				}
				break;
		}
		  	
		if (entity.isSilent()) {  
			this.body_base.y = 25.0F;
			this.root_r.visible = false;
			this.root_l.visible = false;
		} else {
			this.body_base.y = 22.0F + MathHelper.cos(limbSwing) * 0.2F * limbSwingAmount;
			this.root_r.visible = true;
			this.root_l.visible = true;
	        this.root_r.z = MathHelper.cos(limbSwing) * 2.5F * limbSwingAmount;
	        this.root_l.z = MathHelper.cos(limbSwing + (float)Math.PI) * 2.5F * limbSwingAmount;			
		}
	}

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.body_base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, 0.85F);
        });
    }

	@Override
	public ModelRenderer getHead() {
		return this.body_base;
	}
}