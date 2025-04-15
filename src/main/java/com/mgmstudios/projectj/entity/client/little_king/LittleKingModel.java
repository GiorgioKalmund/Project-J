package com.mgmstudios.projectj.entity.client.little_king;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.entity.client.little_man.LittleManAnimations;
import com.mgmstudios.projectj.entity.custom.LittleKingEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class LittleKingModel extends EntityModel<LittleKingRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "little_king"), "main");
	private final ModelPart leftleg;
	private final ModelPart leftarm;
	private final ModelPart lowerbody;
	private final ModelPart head;
	private final ModelPart rightarm;
	private final ModelPart rightleg;
	private final ModelPart backpack;

	public LittleKingModel(ModelPart root) {
        super(root);
        this.leftleg = root.getChild("leftleg");
		this.leftarm = root.getChild("leftarm");
		this.lowerbody = root.getChild("lowerbody");
		this.head = root.getChild("head");
		this.rightarm = root.getChild("rightarm");
		this.rightleg = root.getChild("rightleg");
		this.backpack = root.getChild("backpack");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition leftleg = partdefinition.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(28, 9).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 21.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition leftarm = partdefinition.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(20, 17).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 14.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition lowerbody = partdefinition.addOrReplaceChild("lowerbody", CubeListBuilder.create().texOffs(0, 19).addBox(-3.0F, -6.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 8).addBox(-3.0F, -7.125F, -1.375F, 6.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-6.0F, -11.125F, -0.125F, 12.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-1.0F, -3.125F, 2.625F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 8).addBox(-5.0F, -12.125F, -0.125F, 10.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.125F, 0.625F, 0.0F, 3.1416F, 0.0F));

		PartDefinition rightarm = partdefinition.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(20, 9).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 14.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition rightleg = partdefinition.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(28, 14).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 21.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition backpack = partdefinition.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 19).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(20, 25).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(LittleKingRenderState renderState) {
		super.setupAnim(renderState);

		animate(renderState.idle, LittleKingAnimations.IDLE, renderState.ageInTicks);
		animateWalk(LittleKingAnimations.WALKING, renderState.walkAnimationPos * 10, renderState.walkAnimationSpeed, 1, 1);
	}
}