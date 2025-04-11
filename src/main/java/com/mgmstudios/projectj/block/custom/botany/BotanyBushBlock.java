package com.mgmstudios.projectj.block.custom.botany;

import com.mgmstudios.projectj.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class BotanyBushBlock extends SweetBerryBushBlock {

    public BotanyBushBlock(Properties properties) {
        super(properties);
    }

    protected Item getItem(){
        return Items.AIR;
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader p_304655_, BlockPos p_57257_, BlockState p_57258_, boolean p_388022_) {
        return new ItemStack(getItem());
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState p_316134_, Level p_316429_, BlockPos p_316748_, Player p_316431_, BlockHitResult p_316474_) {
        int i = p_316134_.getValue(AGE);
        boolean flag = i == 3;
        if (i > 1) {
            int j = 1 + p_316429_.random.nextInt(2);
            popResource(p_316429_, p_316748_, new ItemStack(getItem(), j + (flag ? 1 : 0)));
            p_316429_.playSound(
                    null, p_316748_, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + p_316429_.random.nextFloat() * 0.4F
            );
            BlockState blockstate = p_316134_.setValue(AGE, Integer.valueOf(1));
            p_316429_.setBlock(p_316748_, blockstate, 2);
            p_316429_.gameEvent(GameEvent.BLOCK_CHANGE, p_316748_, GameEvent.Context.of(p_316431_, blockstate));
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(p_316134_, p_316429_, p_316748_, p_316431_, p_316474_);
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.BOTANY_POT.get()) || state.is(BlockTags.DIRT) || state.getBlock() instanceof FarmBlock;
    }
}
