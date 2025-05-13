package com.mgmstudios.projectj.util;

import com.mgmstudios.projectj.component.ModDataComponents;
import com.mgmstudios.projectj.item.custom.socket.SocketHolder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.mgmstudios.projectj.component.ModDataComponents.Sockets.SOCKETS;
import static com.mgmstudios.projectj.component.ModDataComponents.Sockets.ZOMBIE_PACIFYING;
import static net.minecraft.core.component.DataComponents.EQUIPPABLE;
import static net.minecraft.core.component.DataComponents.GLIDER;

public final class Socket implements Comparable<Socket>{
    private final DataComponentType<?> dataComponentType;
    private int count;
    private final int maxCount;


    public static Socket GLIDER_SOCKET = new Socket(GLIDER, 1, 1);
    public static Socket PACIFYING_SOCKET = new Socket(ZOMBIE_PACIFYING.get(), 1, 1);

    public Socket(DataComponentType<?> dataComponentType, int count, int maxCount){
        this.dataComponentType = dataComponentType;
        this.count = count;
        this.maxCount = maxCount;
    }

    public Socket(DataComponentType<?> dataComponentType, int count){
        this(dataComponentType, count, Integer.MAX_VALUE);
    }

    public Socket(DataComponentType<?> dataComponentType){
        this(dataComponentType, 1);
    }

    public int getCount() {
        return count;
    }

    public boolean isEmpty(){
        return dataComponentType.equals(ModDataComponents.Sockets.EMPTY.get()) || count == 0;
    }

    public boolean is(DataComponentType<?> dataComponentType){
        return this.dataComponentType.equals(dataComponentType);
    }

    public <T> boolean is(DeferredHolder<DataComponentType<?>, DataComponentType<T>> dataComponentType){
        return is(dataComponentType.get());
    }

    public static Socket empty(){
        return new Socket(ModDataComponents.Sockets.EMPTY.get(), 1);
    }

    public static Socket[] emptySockets(int count){
        List<Socket> emptySockets = new ArrayList<>();
        for (int index = 0; index < count; index++){
            emptySockets.add(empty());
        }
        return emptySockets.toArray(new Socket[count]);
    }

    public Item.Properties apply(Item.Properties properties){
        if (dataComponentType.equals(DataComponents.GLIDER)){
            baseApply(properties, DataComponents.GLIDER, Unit.INSTANCE);
            properties.equippable(EquipmentSlot.CHEST);
        }
        return properties;
    }
    public void apply(ItemStack itemStack){
        if (dataComponentType.equals(DataComponents.GLIDER)){
            baseApply(itemStack, DataComponents.GLIDER, Unit.INSTANCE);
            itemStack.set(EQUIPPABLE, Equippable.builder(EquipmentSlot.CHEST).build());
        }
    }

    public <T> void baseApply(ItemStack stack, DataComponentType<T> type, T componentValue){
        stack.set(type, componentValue);
    }

    public <T> void baseApply(Item.Properties properties, DataComponentType<T> type, T componentValue){
        properties.component(type, componentValue);
    }

    public static ItemStack addSocket(ItemStack itemStack, Socket socketToApply) {
        DataComponentType<?> typeToApply = socketToApply.getDataComponentType();

        if (!(itemStack.getItem() instanceof SocketHolder socketHolder)) {
            return ItemStack.EMPTY;
        }

        if (socketHolder.getExcludedTypes().contains(typeToApply)
                || (!socketHolder.getAllowedTypes().isEmpty()
                && !socketHolder.getAllowedTypes().contains(typeToApply))) {
            return ItemStack.EMPTY;
        }

        List<Socket> sockets = itemStack.getOrDefault(SOCKETS, new ArrayList<>());
        List<Socket> newSockets = new ArrayList<>(sockets);
        boolean slotUsed = false;


        for (int i = 0; i < sockets.size(); i++) {
            Socket cur = sockets.get(i);

            if (cur.sameTypeAs(socketToApply)) {
                if (cur.getCount() < cur.maxCount) {
                    newSockets.set(i, cur.copy().increaseCount());
                    slotUsed = true;
                } else {
                    return ItemStack.EMPTY;
                }
                break;
            }

            if (cur.isEmpty()) {
                newSockets.set(i, socketToApply);
                slotUsed = true;
                break;
            }
        }

        ItemStack resultStack = itemStack.copy();

        if (!slotUsed) {
            return ItemStack.EMPTY;
        }

        resultStack.set(SOCKETS, newSockets);
        socketToApply.apply(resultStack);
        return resultStack;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public DataComponentType<?> getDataComponentType() {
        return dataComponentType;
    }

    public static final Codec<Socket> SOCKET_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    DataComponentType.PERSISTENT_CODEC.fieldOf("type")
                            .forGetter(Socket::getDataComponentType),
                    Codec.INT.fieldOf("count")
                            .forGetter(Socket::getCount),
                    Codec.INT.fieldOf("max_value")
                            .forGetter(Socket::getMaxCount)
            ).apply(instance, Socket::new)
    );

    public static final Codec<List<Socket>> SOCKET_LIST_CODEC =
            Codec.list(SOCKET_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Socket> SOCKET_STREAM_CODEC =
            StreamCodec.composite(
                    DataComponentType.STREAM_CODEC,
                    Socket::getDataComponentType,

                    ByteBufCodecs.INT,
                    Socket::getCount,

                    Socket::new
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Socket>> SOCKET_LIST_STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public void encode(RegistryFriendlyByteBuf buf, List<Socket> list) {
                    buf.writeVarInt(list.size());
                    for (Socket s : list) {
                        SOCKET_STREAM_CODEC.encode(buf, s);
                    }
                }

                @Override
                public List<Socket> decode(RegistryFriendlyByteBuf buf) {
                    int size = buf.readVarInt();
                    List<Socket> list = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        list.add(SOCKET_STREAM_CODEC.decode(buf));
                    }
                    return list;
                }
            };

    @Override
    public int compareTo(@NotNull Socket o) {
        if (count < o.getCount() && sameTypeAs(o))
            return -1;
        else if (count == o.getCount() && sameTypeAs(o))
            return 0;
        else return 1;
    }

    public boolean sameTypeAs(Socket o){
        return dataComponentType.equals(o.getDataComponentType());
    }

    public Socket increaseCount(){
        this.count++;
        return this;
    }

    public Socket copy(){
        return new Socket(dataComponentType, count);
    }


    @Override
    public String toString() {
        return "Socket{" +
                "dataComponentType=" + dataComponentType +
                ", count=" + count +
                ", maxCount=" + maxCount +
                '}';
    }
}

