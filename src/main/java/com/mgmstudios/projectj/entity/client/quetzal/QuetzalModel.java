// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class quetzal<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "quetzal"), "main");
	private final ModelPart bb_main;

	public quetzal(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -14.0F, -6.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(63, 28).addBox(0.5F, 0.0F, -4.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(63, 29).addBox(-2.5F, 0.0F, -4.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(48, 55).addBox(0.5F, -3.0F, -2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 56).addBox(-1.5F, -3.0F, -2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 56).addBox(0.0F, -16.0F, -5.9F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 32).addBox(-1.0F, 2.0F, -5.0F, 3.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.7F, -3.9F, 5.3F, -0.2694F, -0.2359F, 0.0644F));

		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 2.0F, -5.0F, 3.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.7F, -3.9F, 5.3F, -0.2694F, 0.2359F, -0.0644F));

		PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 45).addBox(-3.0F, 2.0F, -5.0F, 4.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -4.3F, 3.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(48, 59).addBox(-0.3912F, -0.9163F, -4.3578F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(30, 55).addBox(-0.1088F, -3.0837F, -3.6422F, 0.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.9141F, -7.1783F, -1.6803F, -0.9101F, -0.0576F, 0.106F));

		PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 56).addBox(-0.6088F, -0.9163F, -4.3578F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(52, 45).addBox(0.1088F, -3.0837F, -3.6422F, 0.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9141F, -7.1783F, -1.6803F, -0.9101F, 0.0576F, -0.106F));

		PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(64, 16).addBox(-2.0F, 0.0F, -5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.3F, 2.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r7 = bb_main.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(64, 24).addBox(0.0F, -1.5F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -10.5F, -6.3F, 0.2182F, 0.0F, 0.0F));

		PartDefinition cube_r8 = bb_main.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(64, 8).addBox(-3.0F, -2.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.3F, -2.0F, -1.2217F, 0.0F, 0.0F));

		PartDefinition cube_r9 = bb_main.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(30, 45).addBox(-4.0F, -3.0F, -1.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -9.3F, -4.1F, -1.4399F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}