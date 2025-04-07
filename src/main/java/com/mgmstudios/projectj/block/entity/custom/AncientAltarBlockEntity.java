package com.mgmstudios.projectj.block.entity.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.ModBlockEntities;
import com.mgmstudios.projectj.fluid.ModFluids;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import static com.mgmstudios.projectj.block.custom.AncientAltarBlock.*;

public class AncientAltarBlockEntity extends BlockEntity  implements
        GameEventListener.Provider<AncientAltarBlockEntity.AncientAltarListener>,
        IFluidHandler,
        ICapabilityProvider<BlockCapability<IFluidHandler, Direction>,  IFluidHandler, IFluidHandler>
{

    private final FluidTank fluidTank = new FluidTank(1000, fs -> fs.getFluid() == ModFluids.FLOWING_PYRITE.get()){
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()){
                if (isEmpty()){
                    level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(PYRITE_INSIDE, false));
                } else {
                    level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(PYRITE_INSIDE, true));
                }
            }
            super.onContentsChanged();
        }
    };

    public static final int ANCIENT_ALTAR_INVENTORY_SIZE = 9;

    public final ItemStackHandler inventory = new ItemStackHandler(ANCIENT_ALTAR_INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                itemsInside = getItemsInside();
            }
            super.onContentsChanged(slot);
        }

        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }
    };

    public final ItemStackHandler bowlInventory = new ItemStackHandler(1) {
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

    public int getItemsInside(){
        int result = 0;
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()){
                result++;
            }
        }
        return result;
    }

    public void insertNewItemStack(ItemStack stack){
        ItemStack returned = inventory.insertItem(itemsInside, stack.copy(), false);
        //System.out.println("Inserted " + stack + " and got back " + returned);
    }

    public void insertBlood(ItemStack stack){
        ItemStack returned = bowlInventory.insertItem(0, stack.copy(), false);
        //System.out.println("Inserted " + stack + " and got back " + returned);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public ItemStackHandler getBowlInventory() {
        return bowlInventory;
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
            return ItemStack.EMPTY;
        }
        ItemStack extracted = inventory.extractItem(itemsInside - 1, 1, false);
        // Move one index down, as we now have one less item inside and the value is updated in the onChanged() method of the inventory
        inventory.setStackInSlot(itemsInside , ItemStack.EMPTY);
        //System.out.println("extracted: " + extracted + " inside: " + itemsInside);
        return extracted;
    }

    public ItemStack extractBlood(){
        ItemStack extracted = bowlInventory.extractItem(0, 1, false);
        bowlInventory.setStackInSlot(0, ItemStack.EMPTY);
        //System.out.println("BOWL: extracted: " + extracted + " inside: " + itemsInside);
        return extracted;
    }


    public int itemsInside;
    private float rotation;
    private float rotationSpeed;
    private float height;
    private float radius;
    private boolean crafting;
    private final AncientAltarListener deathListener;
    private ItemStack craftingResultItem;
    public AncientAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ANCIENT_ALTAR_BE.get(), pos, blockState);
        itemsInside = 0;
        crafting = false;
        height = 1.2F;
        radius = 0.75F;
        rotationSpeed = 0.5F;
        craftingResultItem = ItemStack.EMPTY;

        this.deathListener= new AncientAltarListener(blockState, new BlockPositionSource(pos));
    }

    public ItemStack getCraftingResult() {
        return craftingResultItem.copy();
    }

    public void setCraftingResult(ItemStack craftingResultItem) {
        this.craftingResultItem = craftingResultItem;
    }

    public void clearAllContents(){
        for (int slot = 0; slot < inventory.getSlots(); slot++){
            inventory.setStackInSlot(slot, ItemStack.EMPTY);
        }
        craftingResultItem = ItemStack.EMPTY;
        itemsInside = 0;
    }

    public boolean isEmpty(){
        return itemsInside == 0;
    }

    public float getRenderingRotation(){
        assert level != null;
        if (level.getBlockState(getBlockPos()).is(ModBlocks.ANCIENT_ALTAR) && getBlockState().getValue(CRAFTING)){
            rotation += getRotationSpeed();
        } else {
            rotation += 0.5F;
        }
        rotation %= 360;
        return rotation;
    }

    public float getRotationSpeed(){
        assert level != null;
        if (level.getBlockState(getBlockPos()).is(ModBlocks.ANCIENT_ALTAR) && getBlockState().getValue(CRAFTING)){
            rotationSpeed = Math.min(rotationSpeed + 0.1F, 3F);
        }
        return this.rotationSpeed;
    }

    public float getRenderingHeight(){
        assert level != null;
        if (level.getBlockState(getBlockPos()).is(ModBlocks.ANCIENT_ALTAR) && getBlockState().getValue(CRAFTING)){
            height = Math.min(height + 0.003F, 1.7F);
        }
        return this.height;
    }

    public float getRenderingRadius(){
        assert level != null;
        if (level.getBlockState(getBlockPos()).is(ModBlocks.ANCIENT_ALTAR) && getBlockState().getValue(CRAFTING)){
            radius = Math.max(radius - 0.005F, 0.1F);
        }
        return this.radius;
    }

    public void resetRenderingAttributes(){
        this.radius = 0.75F;
        this.height = 1.2F;
    }

    public void drops(){
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int slot = 0; slot < inventory.getSlots(); slot++){
            inv.setItem(slot, this.inventory.getStackInSlot(slot));
        }

        SimpleContainer bowlInv = new SimpleContainer(inventory.getSlots());
        bowlInv.setItem(0, this.inventory.getStackInSlot(0));

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inv);
        Containers.dropContents(this.level, this.worldPosition, bowlInv);
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
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.put("bowlInventory", bowlInventory.serializeNBT(registries));
        tag.putInt("itemsInside", itemsInside);
        tag.putBoolean("crafting", crafting);
        if (!craftingResultItem.isEmpty())
            tag.put("craftingResult", craftingResultItem.save(registries));
        fluidTank.writeToNBT(registries, tag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        bowlInventory.deserializeNBT(registries, tag.getCompound("bowlInventory"));
        itemsInside = tag.getInt("itemsInside");
        crafting = tag.getBoolean("crafting");
        if (tag.contains("craftingResult")) {
            craftingResultItem = ItemStack.parse(registries, tag.getCompound("craftingResult")).orElse(ItemStack.EMPTY);
        }
        fluidTank.readFromNBT(registries, tag);
    }

    @Override
    public AncientAltarListener getListener() {
        return deathListener;
    }

    @Override
    public int getTanks() {
        return fluidTank.getTanks();
    }

    @Override
    public FluidStack getFluidInTank(int i) {
        return fluidTank.getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        return fluidTank.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack fluidStack) {
        return fluidTank.isFluidValid(tank, fluidStack);
    }

    @Override
    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
        return fluidTank.fill(fluidStack, fluidAction);
    }

    @Override
    public FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        return fluidTank.drain(fluidStack, fluidAction);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction fluidAction) {
        return fluidTank.drain(maxDrain, fluidAction);
    }

    @Override
    public @Nullable IFluidHandler getCapability(BlockCapability<IFluidHandler, Direction> cap, IFluidHandler fluidHandler) {
        if (cap.equals(Capabilities.FluidHandler.BLOCK)) {
            return fluidHandler;
        }
        return null;
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
                            .ifPresent(vec3 -> fillAltar(serverLevel, BlockPos.containing(vec3)));
                    livingentity.skipDropExperience();
                }
                return true;
            } else {
                return false;
            }
        }

        public void fillAltar(ServerLevel serverLevel, BlockPos blockPos){
            serverLevel.setBlockAndUpdate(blockPos, serverLevel.getBlockState(blockPos).setValue(BLOOD_INSIDE, true));
            if ((serverLevel.getBlockEntity(blockPos) instanceof AncientAltarBlockEntity altarEntity)) {
                altarEntity.insertBlood(new ItemStack(ModItems.FILLED_CRUDE_SACRIFICE_BOWL.get()));
            }
            serverLevel.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS);
        }
    }
}