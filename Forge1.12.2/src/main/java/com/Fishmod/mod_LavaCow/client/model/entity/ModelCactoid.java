package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.client.model.FishModelBase;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityCactoid;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCactoid extends FishModelBase {
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
	
	public ModelCactoid() {
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.body_base = new ModelRenderer(this);
		this.body_base.setRotationPoint(0.0F, 22.0F, 0.0F);
		this.body_base.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, false);

		this.root_r = new ModelRenderer(this);
		this.root_r.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.root_r);
		this.root_r.setTextureOffset(0, 0).addBox(-3.0F, -1.0F, -1.0F, 2, 3, 2, true);

		this.root_l = new ModelRenderer(this);
		this.root_l.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.root_l);
		this.root_l.setTextureOffset(0, 0).addBox(1.0F, -1.0F, -1.0F, 2, 3, 2, false);

		/* Skin-0 */
		spike3_r1 = new ModelRenderer(this);
		spike3_r1.setRotationPoint(-4.0F, -5.0F, 4.0F);
		body_base.addChild(spike3_r1);
		this.setRotateAngle(spike3_r1, 0.0F, 0.7854F, 0.0F);
		spike3_r1.setTextureOffset(0, 24).addBox(-1.0F, -3.0F, 0.0F, 1, 6, 0, false);

		spike2_r1 = new ModelRenderer(this);
		spike2_r1.setRotationPoint(4.0F, -5.0F, 4.0F);
		body_base.addChild(spike2_r1);
		this.setRotateAngle(spike2_r1, 0.0F, -0.7854F, 0.0F);
		spike2_r1.setTextureOffset(0, 24).addBox(0.0F, -3.0F, 0.0F, 1, 6, 0, true);

		spike1_r1 = new ModelRenderer(this);
		spike1_r1.setRotationPoint(4.0F, -5.0F, -4.0F);
		body_base.addChild(spike1_r1);
		this.setRotateAngle(spike1_r1, 0.0F, 0.7854F, 0.0F);
		spike1_r1.setTextureOffset(0, 24).addBox(0.0F, -3.0F, 0.0F, 1, 6, 0, true);

		spike0_r1 = new ModelRenderer(this);
		spike0_r1.setRotationPoint(-4.0F, -5.0F, -4.0F);
		body_base.addChild(spike0_r1);
		this.setRotateAngle(spike0_r1, 0.0F, -0.7854F, 0.0F);
		spike0_r1.setTextureOffset(0, 24).addBox(-1.0F, -3.0F, 0.0F, 1, 6, 0, false);
		
		this.crest_0 = new ModelRenderer(this);
		this.crest_0.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.crest_0);
		this.crest_0.setTextureOffset(0, 16).addBox(-2.0F, -11.0F, -2.0F, 4, 3, 4, false);
		
		this.flower0_0 = new ModelRenderer(this);
		this.flower0_0.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.flower0_0);
		this.setRotateAngle(this.flower0_0, 0.0F, -0.7854F, 0.0F);
		this.flower0_0.setTextureOffset(16, 17).addBox(-4.0F, -14.0F, 0.0F, 8, 6, 0, false);

		this.flower1_0 = new ModelRenderer(this);
		this.flower1_0.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.flower1_0);
		this.setRotateAngle(this.flower1_0, 0.0F, 0.7854F, 0.0F);
		this.flower1_0.setTextureOffset(16, 17).addBox(-4.0F, -14.0F, 0.0F, 8, 6, 0, true);

		this.fruit_0 = new ModelRenderer(this);
		this.fruit_0.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.fruit_0);
		this.fruit_0.setTextureOffset(24, 0).addBox(-1.0F, -12.0F, -1.0F, 2, 4, 2, false);
		
		/* Skin-1 */
		this.leaf0_1 = new ModelRenderer(this);
		this.leaf0_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.leaf0_1);
		this.setRotateAngle(this.leaf0_1, 0.0F, -1.5708F, 0.0F);
		this.leaf0_1.setTextureOffset(0, 16).addBox(-4.0F, -16.0F, 0.0F, 8, 8, 0, false);

		this.leaf1_1 = new ModelRenderer(this);
		this.leaf1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.leaf1_1);
		this.leaf1_1.setTextureOffset(0, 16).addBox(-4.0F, -16.0F, 0.0F, 8, 8, 0, true);

		this.flower0_1 = new ModelRenderer(this);
		this.flower0_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.flower0_1);
		this.flower0_1.setTextureOffset(20, 19).addBox(-3.0F, -20.0F, 0.0F, 6, 4, 0, false);

		this.flower1_1 = new ModelRenderer(this);
		this.flower1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.flower1_1);
		this.setRotateAngle(this.flower1_1, 0.0F, -1.5708F, 0.0F);
		this.flower1_1.setTextureOffset(20, 19).addBox(-3.0F, -20.0F, 0.0F, 6, 4, 0, true);

		this.fruit_1 = new ModelRenderer(this);
		this.fruit_1.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.body_base.addChild(this.fruit_1);
		this.fruit_1.setTextureOffset(24, 0).addBox(-1.0F, -12.0F, -1.0F, 2, 4, 2, false);
		
		/* Skin-2 */
		this.leaf0_2 = new ModelRenderer(this);
		this.leaf0_2.setRotationPoint(3.0F, -7.0F, 0.0F);
		this.body_base.addChild(this.leaf0_2);
		this.setRotateAngle(this.leaf0_2, 0.0F, -1.5708F, -0.9599F);
		this.leaf0_2.setTextureOffset(0, 18).addBox(-2.5F, -2.0F, -8.0F, 5, 2, 8, false);

		this.leaf0_claw_2 = new ModelRenderer(this);
		this.leaf0_claw_2.setRotationPoint(-0.5F, -4.5F, -7.5F);
		this.leaf0_2.addChild(this.leaf0_claw_2);
		this.leaf0_claw_2.setTextureOffset(0, 16).addBox(-2.0F, 1.5F, -0.5F, 5, 1, 1, false);

		this.leaf1_2 = new ModelRenderer(this);
		this.leaf1_2.setRotationPoint(-3.0F, -7.0F, 0.0F);
		this.body_base.addChild(this.leaf1_2);
		this.setRotateAngle(this.leaf1_2, 0.0F, 1.5708F, 0.9599F);
		this.leaf1_2.setTextureOffset(0, 18).addBox(-2.5F, -2.0F, -8.0F, 5, 2, 8, true);

		this.leaf0_claw2_2 = new ModelRenderer(this);
		this.leaf0_claw2_2.setRotationPoint(0.5F, -4.5F, -7.5F);
		this.leaf1_2.addChild(this.leaf0_claw2_2);
		this.leaf0_claw2_2.setTextureOffset(0, 16).addBox(-3.0F, 1.5F, -0.5F, 5, 1, 1, true);

		this.flower0_2 = new ModelRenderer(this);
		this.flower0_2.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.body_base.addChild(this.flower0_2);
		this.flower0_2.setTextureOffset(20, 16).addBox(-3.0F, -10.0F, 0.0F, 6, 10, 0, false);

		this.flower1_2 = new ModelRenderer(this);
		this.flower1_2.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.body_base.addChild(this.flower1_2);
		this.setRotateAngle(this.flower1_2, 0.0F, -1.5708F, 0.0F);
		this.flower1_2.setTextureOffset(20, 16).addBox(-3.0F, -10.0F, 0.0F, 6, 10, 0, true);

		this.fruit_2 = new ModelRenderer(this);
		this.fruit_2.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.body_base.addChild(this.fruit_2);
		this.fruit_2.setTextureOffset(24, 0).addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, false);
		
		/* Skin-3 */
		this.stem0_3 = new ModelRenderer(this);
		this.stem0_3.setRotationPoint(-2.0F, -8.0F, -2.0F);
		this.body_base.addChild(this.stem0_3);
		this.setRotateAngle(this.stem0_3, 0.2132F, 0.0469F, -0.2132F);
		this.stem0_3.setTextureOffset(0, 16).addBox(-2.0F, -6.0F, -2.0F, 4, 6, 4, false);

		this.stem1_3 = new ModelRenderer(this);
		this.stem1_3.setRotationPoint(2.0F, -8.0F, -2.0F);
		this.body_base.addChild(this.stem1_3);
		this.setRotateAngle(this.stem1_3, 0.1745F, 0.0F, 0.1745F);
		this.stem1_3.setTextureOffset(16, 16).addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, false);

		this.flower0_3 = new ModelRenderer(this);
		this.flower0_3.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.stem1_3.addChild(this.flower0_3);
		this.flower0_3.setTextureOffset(20, 28).addBox(-3.0F, -4.0F, 0.0F, 6, 4, 0, false);

		this.flower1_3 = new ModelRenderer(this);
		this.flower1_3.setRotationPoint(-4.0F, -10.0F, 4.0F);
		this.stem1_3.addChild(this.flower1_3);
		this.setRotateAngle(this.flower1_3, 0.0F, -1.5708F, 0.0F);
		this.flower1_3.setTextureOffset(20, 28).addBox(-7.0F, 2.0F, -4.0F, 6, 4, 0, false);

		this.fruit_3 = new ModelRenderer(this);
		this.fruit_3.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.stem1_3.addChild(this.fruit_3);
		this.fruit_3.setTextureOffset(24, 0).addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, false);

		this.stem2_3 = new ModelRenderer(this);
		this.stem2_3.setRotationPoint(-2.0F, -8.0F, 2.0F);
		this.body_base.addChild(this.stem2_3);
		this.setRotateAngle(this.stem2_3, -0.1309F, 0.0F, -0.1745F);
		this.stem2_3.setTextureOffset(0, 16).addBox(-2.0F, -10.0F, -2.0F, 4, 10, 4, true);

		this.flower2_3 = new ModelRenderer(this);
		this.flower2_3.setRotationPoint(0.0F, -10.0F, 0.0F);
		this.stem2_3.addChild(this.flower2_3);
		this.flower2_3.setTextureOffset(20, 28).addBox(-3.0F, -4.0F, 0.0F, 6, 4, 0, true);

		this.flower3_3 = new ModelRenderer(this);
		this.flower3_3.setRotationPoint(0.0F, -10.0F, 0.0F);
		this.stem2_3.addChild(this.flower3_3);
		this.setRotateAngle(this.flower3_3, 0.0F, -1.5708F, 0.0F);
		this.flower3_3.setTextureOffset(20, 28).addBox(-3.0F, -4.0F, 0.0F, 6, 4, 0, false);

		this.fruit2_3 = new ModelRenderer(this);
		this.fruit2_3.setRotationPoint(0.0F, -10.0F, 0.0F);
		this.stem2_3.addChild(this.fruit2_3);
		this.fruit2_3.setTextureOffset(24, 0).addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, false);

		this.stem3_3 = new ModelRenderer(this);
		this.stem3_3.setRotationPoint(2.0F, -8.0F, 2.0F);
		this.body_base.addChild(this.stem3_3);
		this.setRotateAngle(this.stem3_3, -0.2132F, 0.0469F, 0.2132F);
		this.stem3_3.setTextureOffset(16, 16).addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, true);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity){
		switch(((EntityCactoid) entity).getSkin()) {
	    	case 1:
	    		this.spike3_r1.isHidden = true;
	    		this.spike2_r1.isHidden = true;
	    		this.spike1_r1.isHidden = true;
	    		this.spike0_r1.isHidden = true;
	    		this.crest_0.isHidden = true;
		    	this.flower0_0.isHidden = true;
		    	this.flower1_0.isHidden = true;
		    	this.fruit_0.isHidden = true;
		    	this.leaf0_1.isHidden = false;
		    	this.leaf1_1.isHidden = false;
		    	this.flower0_1.isHidden = false;
		    	this.flower1_1.isHidden = false;
		    	this.fruit_1.isHidden = false;
		    	this.leaf0_2.isHidden = true;
		    	this.leaf0_claw_2.isHidden = true;
		    	this.leaf1_2.isHidden = true;
		    	this.leaf0_claw2_2.isHidden = true;
		    	this.flower0_2.isHidden = true;
		    	this.flower1_2.isHidden = true;
		    	this.fruit_2.isHidden = true;
		    	this.stem0_3.isHidden = true;
		    	this.stem1_3.isHidden = true;
		    	this.flower0_3.isHidden = true;
		    	this.flower1_3.isHidden = true;
		    	this.fruit_3.isHidden = true;
		    	this.stem2_3.isHidden = true;
		    	this.flower2_3.isHidden = true;
		    	this.flower3_3.isHidden = true;
		    	this.fruit2_3.isHidden = true;
		    	this.stem3_3.isHidden = true;
		    	switch(((EntityCactoid) entity).getGrowingStage()) {
			    	case 0:
				    	this.flower0_0.isHidden = true;
				    	this.flower1_0.isHidden = true;
				    	this.fruit_0.isHidden = true;
				    	this.flower0_1.isHidden = true;
				    	this.flower1_1.isHidden = true;
				    	this.fruit_1.isHidden = true;
				    	this.flower0_2.isHidden = true;
				    	this.flower1_2.isHidden = true;
				    	this.fruit_2.isHidden = true;
				    	this.flower0_3.isHidden = true;
				    	this.flower1_3.isHidden = true;
				    	this.fruit_3.isHidden = true;
				    	this.flower2_3.isHidden = true;
				    	this.flower3_3.isHidden = true;
				    	this.fruit2_3.isHidden = true;
			    		break;
			    	case 1:
				    	this.flower0_0.isHidden = true;
				    	this.flower1_0.isHidden = true;
				    	this.fruit_0.isHidden = true;
				    	this.flower0_1.isHidden = false;
				    	this.flower1_1.isHidden = false;
				    	this.fruit_1.isHidden = true;
				    	this.flower0_2.isHidden = true;
				    	this.flower1_2.isHidden = true;
				    	this.fruit_2.isHidden = true;
				    	this.flower0_3.isHidden = true;
				    	this.flower1_3.isHidden = true;
				    	this.fruit_3.isHidden = true;
				    	this.flower2_3.isHidden = true;
				    	this.flower3_3.isHidden = true;
				    	this.fruit2_3.isHidden = true;
			    		break;
			    	case 2:
					default:
				    	this.flower0_0.isHidden = true;
				    	this.flower1_0.isHidden = true;
				    	this.fruit_0.isHidden = true;
				    	this.flower0_1.isHidden = true;
				    	this.flower1_1.isHidden = true;
				    	this.fruit_1.isHidden = false;
				    	this.flower0_2.isHidden = true;
				    	this.flower1_2.isHidden = true;
				    	this.fruit_2.isHidden = true;
				    	this.flower0_3.isHidden = true;
				    	this.flower1_3.isHidden = true;
				    	this.fruit_3.isHidden = true;
				    	this.flower2_3.isHidden = true;
				    	this.flower3_3.isHidden = true;
				    	this.fruit2_3.isHidden = true;
						break;
				}
	    		break;
	    	case 2:
	    		this.spike3_r1.isHidden = true;
	    		this.spike2_r1.isHidden = true;
	    		this.spike1_r1.isHidden = true;
	    		this.spike0_r1.isHidden = true;
	    		this.crest_0.isHidden = true;
		    	this.flower0_0.isHidden = true;
		    	this.flower1_0.isHidden = true;
		    	this.fruit_0.isHidden = true;
		    	this.leaf0_1.isHidden = true;
		    	this.leaf1_1.isHidden = true;
		    	this.flower0_1.isHidden = true;
		    	this.flower1_1.isHidden = true;
		    	this.fruit_1.isHidden = true;
		    	this.leaf0_2.isHidden = false;
		    	this.leaf0_claw_2.isHidden = false;
		    	this.leaf1_2.isHidden = false;
		    	this.leaf0_claw2_2.isHidden = false;
		    	this.flower0_2.isHidden = false;
		    	this.flower1_2.isHidden = false;
		    	this.fruit_2.isHidden = false;
		    	this.stem0_3.isHidden = true;
		    	this.stem1_3.isHidden = true;
		    	this.flower0_3.isHidden = true;
		    	this.flower1_3.isHidden = true;
		    	this.fruit_3.isHidden = true;
		    	this.stem2_3.isHidden = true;
		    	this.flower2_3.isHidden = true;
		    	this.flower3_3.isHidden = true;
		    	this.fruit2_3.isHidden = true;
		    	this.stem3_3.isHidden = true;
		    	switch(((EntityCactoid) entity).getGrowingStage()) {
			    	case 0:
				    	this.flower0_0.isHidden = true;
				    	this.flower1_0.isHidden = true;
				    	this.fruit_0.isHidden = true;
				    	this.flower0_1.isHidden = true;
				    	this.flower1_1.isHidden = true;
				    	this.fruit_1.isHidden = true;
				    	this.flower0_2.isHidden = true;
				    	this.flower1_2.isHidden = true;
				    	this.fruit_2.isHidden = true;
				    	this.flower0_3.isHidden = true;
				    	this.flower1_3.isHidden = true;
				    	this.fruit_3.isHidden = true;
				    	this.flower2_3.isHidden = true;
				    	this.flower3_3.isHidden = true;
				    	this.fruit2_3.isHidden = true;
			    		break;
			    	case 1:
				    	this.flower0_0.isHidden = true;
				    	this.flower1_0.isHidden = true;
				    	this.fruit_0.isHidden = true;
				    	this.flower0_1.isHidden = true;
				    	this.flower1_1.isHidden = true;
				    	this.fruit_1.isHidden = true;
				    	this.flower0_2.isHidden = false;
				    	this.flower1_2.isHidden = false;
				    	this.fruit_2.isHidden = true;
				    	this.flower0_3.isHidden = true;
				    	this.flower1_3.isHidden = true;
				    	this.fruit_3.isHidden = true;
				    	this.flower2_3.isHidden = true;
				    	this.flower3_3.isHidden = true;
				    	this.fruit2_3.isHidden = true;
			    		break;
			    	case 2:
					default:
				    	this.flower0_0.isHidden = true;
				    	this.flower1_0.isHidden = true;
				    	this.fruit_0.isHidden = true;
				    	this.flower0_1.isHidden = true;
				    	this.flower1_1.isHidden = true;
				    	this.fruit_1.isHidden = true;
				    	this.flower0_2.isHidden = true;
				    	this.flower1_2.isHidden = true;
				    	this.fruit_2.isHidden = false;
				    	this.flower0_3.isHidden = true;
				    	this.flower1_3.isHidden = true;
				    	this.fruit_3.isHidden = true;
				    	this.flower2_3.isHidden = true;
				    	this.flower3_3.isHidden = true;
				    	this.fruit2_3.isHidden = true;
						break;
				}
	    		break;
	    	case 3:
	    		this.spike3_r1.isHidden = true;
	    		this.spike2_r1.isHidden = true;
	    		this.spike1_r1.isHidden = true;
	    		this.spike0_r1.isHidden = true;
	    		this.crest_0.isHidden = true;
		    	this.flower0_0.isHidden = true;
		    	this.flower1_0.isHidden = true;
		    	this.fruit_0.isHidden = true;
		    	this.leaf0_1.isHidden = true;
		    	this.leaf1_1.isHidden = true;
		    	this.flower0_1.isHidden = true;
		    	this.flower1_1.isHidden = true;
		    	this.fruit_1.isHidden = true;
		    	this.leaf0_2.isHidden = true;
		    	this.leaf0_claw_2.isHidden = true;
		    	this.leaf1_2.isHidden = true;
		    	this.leaf0_claw2_2.isHidden = true;
		    	this.flower0_2.isHidden = true;
		    	this.flower1_2.isHidden = true;
		    	this.fruit_2.isHidden = true;
		    	this.stem0_3.isHidden = false;
		    	this.stem1_3.isHidden = false;
		    	this.flower0_3.isHidden = false;
		    	this.flower1_3.isHidden = false;
		    	this.fruit_3.isHidden = false;
		    	this.stem2_3.isHidden = false;
		    	this.flower2_3.isHidden = false;
		    	this.flower3_3.isHidden = false;
		    	this.fruit2_3.isHidden = false;
		    	this.stem3_3.isHidden = false;
		    	switch(((EntityCactoid) entity).getGrowingStage()) {
			    	case 0:
				    	this.flower0_0.isHidden = true;
				    	this.flower1_0.isHidden = true;
				    	this.fruit_0.isHidden = true;
				    	this.flower0_1.isHidden = true;
				    	this.flower1_1.isHidden = true;
				    	this.fruit_1.isHidden = true;
				    	this.flower0_2.isHidden = true;
				    	this.flower1_2.isHidden = true;
				    	this.fruit_2.isHidden = true;
				    	this.flower0_3.isHidden = true;
				    	this.flower1_3.isHidden = true;
				    	this.fruit_3.isHidden = true;
				    	this.flower2_3.isHidden = true;
				    	this.flower3_3.isHidden = true;
				    	this.fruit2_3.isHidden = true;
			    		break;
			    	case 1:
				    	this.flower0_0.isHidden = true;
				    	this.flower1_0.isHidden = true;
				    	this.fruit_0.isHidden = true;
				    	this.flower0_1.isHidden = true;
				    	this.flower1_1.isHidden = true;
				    	this.fruit_1.isHidden = true;
				    	this.flower0_2.isHidden = true;
				    	this.flower1_2.isHidden = true;
				    	this.fruit_2.isHidden = true;
				    	this.flower0_3.isHidden = false;
				    	this.flower1_3.isHidden = false;
				    	this.fruit_3.isHidden = true;
				    	this.flower2_3.isHidden = false;
				    	this.flower3_3.isHidden = false;
				    	this.fruit2_3.isHidden = true;
			    		break;
			    	case 2:
					default:
				    	this.flower0_0.isHidden = true;
				    	this.flower1_0.isHidden = true;
				    	this.fruit_0.isHidden = true;
				    	this.flower0_1.isHidden = true;
				    	this.flower1_1.isHidden = true;
				    	this.fruit_1.isHidden = true;
				    	this.flower0_2.isHidden = true;
				    	this.flower1_2.isHidden = true;
				    	this.fruit_2.isHidden = true;
				    	this.flower0_3.isHidden = true;
				    	this.flower1_3.isHidden = true;
				    	this.fruit_3.isHidden = false;
				    	this.flower2_3.isHidden = true;
				    	this.flower3_3.isHidden = true;
				    	this.fruit2_3.isHidden = false;
						break;
				}
	    		break;
			default:
	    		this.spike3_r1.isHidden = false;
	    		this.spike2_r1.isHidden = false;
	    		this.spike1_r1.isHidden = false;
	    		this.spike0_r1.isHidden = false;
				this.crest_0.isHidden = false;
		    	this.flower0_0.isHidden = false;
		    	this.flower1_0.isHidden = false;
		    	this.fruit_0.isHidden = false;
		    	this.leaf0_1.isHidden = true;
		    	this.leaf1_1.isHidden = true;
		    	this.flower0_1.isHidden = true;
		    	this.flower1_1.isHidden = true;
		    	this.fruit_1.isHidden = true;
		    	this.leaf0_2.isHidden = true;
		    	this.leaf0_claw_2.isHidden = true;
		    	this.leaf1_2.isHidden = true;
		    	this.leaf0_claw2_2.isHidden = true;
		    	this.flower0_2.isHidden = true;
		    	this.flower1_2.isHidden = true;
		    	this.fruit_2.isHidden = true;
		    	this.stem0_3.isHidden = true;
		    	this.stem1_3.isHidden = true;
		    	this.flower0_3.isHidden = true;
		    	this.flower1_3.isHidden = true;
		    	this.fruit_3.isHidden = true;
		    	this.stem2_3.isHidden = true;
		    	this.flower2_3.isHidden = true;
		    	this.flower3_3.isHidden = true;
		    	this.fruit2_3.isHidden = true;
		    	this.stem3_3.isHidden = true;
		    	switch(((EntityCactoid) entity).getGrowingStage()) {
			    	case 0:
				    	this.flower0_0.isHidden = true;
				    	this.flower1_0.isHidden = true;
				    	this.fruit_0.isHidden = true;
				    	this.flower0_1.isHidden = true;
				    	this.flower1_1.isHidden = true;
				    	this.fruit_1.isHidden = true;
				    	this.flower0_2.isHidden = true;
				    	this.flower1_2.isHidden = true;
				    	this.fruit_2.isHidden = true;
				    	this.flower0_3.isHidden = true;
				    	this.flower1_3.isHidden = true;
				    	this.fruit_3.isHidden = true;
				    	this.flower2_3.isHidden = true;
				    	this.flower3_3.isHidden = true;
				    	this.fruit2_3.isHidden = true;
			    		break;
			    	case 1:
				    	this.flower0_0.isHidden = false;
				    	this.flower1_0.isHidden = false;
				    	this.fruit_0.isHidden = true;
				    	this.flower0_1.isHidden = true;
				    	this.flower1_1.isHidden = true;
				    	this.fruit_1.isHidden = true;
				    	this.flower0_2.isHidden = true;
				    	this.flower1_2.isHidden = true;
				    	this.fruit_2.isHidden = true;
				    	this.flower0_3.isHidden = true;
				    	this.flower1_3.isHidden = true;
				    	this.fruit_3.isHidden = true;
				    	this.flower2_3.isHidden = true;
				    	this.flower3_3.isHidden = true;
				    	this.fruit2_3.isHidden = true;
			    		break;
			    	case 2:
					default:
				    	this.flower0_0.isHidden = true;
				    	this.flower1_0.isHidden = true;
				    	this.fruit_0.isHidden = false;
				    	this.flower0_1.isHidden = true;
				    	this.flower1_1.isHidden = true;
				    	this.fruit_1.isHidden = true;
				    	this.flower0_2.isHidden = true;
				    	this.flower1_2.isHidden = true;
				    	this.fruit_2.isHidden = true;
				    	this.flower0_3.isHidden = true;
				    	this.flower1_3.isHidden = true;
				    	this.fruit_3.isHidden = true;
				    	this.flower2_3.isHidden = true;
				    	this.flower3_3.isHidden = true;
				    	this.fruit2_3.isHidden = true;
						break;
				}
				break;
		}
		  	
		if (entity.isSilent()) {  
			this.body_base.rotationPointY = 25.0F;
			this.root_r.isHidden = true;
			this.root_l.isHidden = true;
		} else {
			this.body_base.rotationPointY = 22.0F + MathHelper.cos(limbSwing) * 0.2F * limbSwingAmount;
			this.root_r.isHidden = false;
			this.root_l.isHidden = false;
	        this.root_r.rotationPointZ = MathHelper.cos(limbSwing) * 2.5F * limbSwingAmount;
	        this.root_l.rotationPointZ = MathHelper.cos(limbSwing + (float)Math.PI) * 2.5F * limbSwingAmount;			
		}
	}
    
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.body_base.render(f5);
    }
}
