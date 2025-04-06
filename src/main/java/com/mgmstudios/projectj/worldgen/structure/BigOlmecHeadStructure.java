package com.mgmstudios.projectj.worldgen.structure;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.worldgen.ModStructureTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class BigOlmecHeadStructure extends Structure {

    static final ResourceLocation STRUCTURE_LOCATION_BIG_OLMEC_HEAD = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "big_olmec_head");
    static final ResourceLocation STRUCTURE_LOCATION_DEFAULT = ResourceLocation.withDefaultNamespace("big_olmec_head");

    public BigOlmecHeadStructure(StructureSettings settings) {
        super(settings);
    }

    public static final MapCodec<BigOlmecHeadStructure> CODEC = simpleCodec(BigOlmecHeadStructure::new);

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, structurePiecesBuilder -> this.generatePieces(structurePiecesBuilder, context));
    }

    private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
        ChunkPos chunkpos = context.chunkPos();
        WorldgenRandom worldgenrandom = context.random();
        BlockPos blockpos = new BlockPos(chunkpos.getMinBlockX(), 90, chunkpos.getMinBlockZ());
        Rotation rotation = Rotation.getRandom(worldgenrandom);
        BigOlmecHeadPiece.addPieces(context.structureTemplateManager(), blockpos, rotation, builder, STRUCTURE_LOCATION_DEFAULT);
    }

    @Override
    public StructureType<?> type() {
        return ModStructureTypes.BIG_OLMEC_HEAD.get();
    }
}
