package com.mgmstudios.projectj.block.entity.custom;

import com.google.common.annotations.VisibleForTesting;
import com.mgmstudios.projectj.block.custom.AncientAltarBlock;
import com.mgmstudios.projectj.block.entity.ModBlockEntities;
import net.minecraft.Optionull;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.core.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SculkCatalystBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import static com.mgmstudios.projectj.block.custom.AncientAltarBlock.PRODUCT_INSIDE;

public class AncientAltarBlockEntity extends BlockEntity  implements GameEventListener.Provider<AncientAltarBlockEntity.AncientAltarListener>{
    public final ItemStackHandler inventory = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
            super.onContentsChanged(slot);
        }

        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }
    };

    public void insertNewItemStack(ItemStack stack){
        ItemStack returned = inventory.insertItem(itemsInside, stack.copy(), false);
        //System.out.println("Inserted " + stack + " and got back " + returned);
        itemsInside++;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public boolean canInsert(){
        //System.out.println("Items: " + itemsInside + " Inv: " + inventory.getSlots());
        return itemsInside < inventory.getSlots();
    }

    public void startCrafting(){
        crafting = true;
    }

    public void endCrafting(){
        crafting = false;
    }

    public ItemStack extractLatestItem(){
        if (itemsInside <= 0){
            itemsInside = 0;
            return ItemStack.EMPTY;
        }
        ItemStack extracted = inventory.extractItem(itemsInside - 1, 1, false);
        inventory.setStackInSlot(itemsInside - 1 , ItemStack.EMPTY);
        itemsInside--;
        //System.out.println("extracted: " + extracted + " inside: " + itemsInside);
        return extracted;
    }


    public int itemsInside;
    private float rotation;
    private boolean crafting;
    private final AncientAltarListener deathListener;
    public AncientAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ANCIENT_ALTAR_BE.get(), pos, blockState);
        itemsInside = 0;
        crafting = false;

        this.deathListener= new AncientAltarListener(blockState, new BlockPositionSource(pos));
    }

    public void clearAllContents(){
        for (int slot = 0; slot < inventory.getSlots(); slot++){
            inventory.setStackInSlot(slot, ItemStack.EMPTY);
        }
        itemsInside = 0;
    }

    public boolean isEmpty(){
        return itemsInside == 0;
    }

    public float getRenderingRotation(){
        rotation += 0.5F;
        rotation %= 360;
        return rotation;
    }

    public void drops(){
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int slot = 0; slot < inventory.getSlots(); slot++){
            inv.setItem(slot, this.inventory.getStackInSlot(slot));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("itemsInside", itemsInside);
        tag.putBoolean("crafting", crafting);
    }


    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        itemsInside = tag.getInt("itemsInside");
        crafting = tag.getBoolean("crafting");
    }

    @Override
    public AncientAltarListener getListener() {
        return deathListener;
    }


    public static class AncientAltarListener implements GameEventListener {
        private final BlockState blockState;
        private final PositionSource positionSource;

        public AncientAltarListener(BlockState blockState, PositionSource positionSource) {
            this.blockState = blockState;
            this.positionSource = positionSource;
        }

        @Override
        public PositionSource getListenerSource() {
            return this.positionSource;
        }

        @Override
        public int getListenerRadius() {
            return 8;
        }

        @Override
        public GameEventListener.DeliveryMode getDeliveryMode() {
            return GameEventListener.DeliveryMode.BY_DISTANCE;
        }

        @Override
        public boolean handleGameEvent(ServerLevel serverLevel, Holder<GameEvent> gameEvent, GameEvent.Context context, Vec3 pos) {
            if (gameEvent.is(GameEvent.ENTITY_DIE) && context.sourceEntity() instanceof LivingEntity livingentity) {
                if (livingentity instanceof Animal){
                    this.positionSource
                            .getPosition(serverLevel)
                            .ifPresent(vec3 -> fillAltar(serverLevel, BlockPos.containing(vec3), blockState));
                    livingentity.skipDropExperience();
                }
                return true;
            } else {
                return false;
            }
        }

        public void fillAltar(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState){
            serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(PRODUCT_INSIDE, true));
            serverLevel.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS);
        }
    }
}