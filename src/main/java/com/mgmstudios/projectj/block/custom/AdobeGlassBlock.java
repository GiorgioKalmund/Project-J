package com.mgmstudios.projectj.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.minecraft.world.phys.shapes.Shapes.empty;

public class AdobeGlassBlock extends TransparentBlock {

    public AdobeGlassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityContext) {
            Entity collidingEntity = entityContext.getEntity();
            if ((collidingEntity instanceof Player player && !player.isCrouching()) || collidingEntity instanceof ItemEntity) {
                return empty();
            }
        }
        return super.getCollisionShape(state, world, pos, context);
    }
}
