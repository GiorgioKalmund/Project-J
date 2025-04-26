package com.mgmstudios.projectj.entity.client.canoe;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.client.model.AbstractBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class CanoeModel extends AbstractBoatModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "mock_canoe"), "main");
	private final ModelPart Body;
	private final ModelPart Seats;

	public CanoeModel(ModelPart root) {
        super(root);
        this.Body = root.getChild("Body");
		this.Seats = root.getChild("Seats");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-33.0F, 0.5F, -7.0F, 48.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(3.0F, -3.5F, -7.0F, 12.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, 21.5F, 0.0F));

		PartDefinition Seats = partdefinition.addOrReplaceChild("Seats", CubeListBuilder.create().texOffs(0, 34).addBox(-19.0F, 2.0F, -1.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(24, 34).addBox(-34.0F, 2.0F, -1.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 42).addBox(-5.0F, -2.0F, -1.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(20.0F, 18.0F, -2.0F));

		partdefinition.addOrReplaceChild("left_paddle", CubeListBuilder.create().texOffs(62, 0).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(3.0F, -5.0F, 9.0F, 0.0F, 0.0F, 0.19634955F));
		partdefinition.addOrReplaceChild("right_paddle", CubeListBuilder.create().texOffs(62, 20).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(3.0F, -5.0F, -9.0F, 0.0F, (float)Math.PI, 0.19634955F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}