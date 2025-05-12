package com.mgmstudios.projectj.util;

import com.mgmstudios.projectj.component.ModDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

import static com.mgmstudios.projectj.component.ModDataComponents.Sockets.SOCKETS;
import static net.minecraft.core.component.DataComponents.MAX_DAMAGE;

public class Socket {
   private final DataComponentType<?> dataComponentType;
   private final int count;

   public Socket(DataComponentType<?> dataComponentType, int count){
       this.dataComponentType = dataComponentType;
       this.count = count;
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
       return properties;
    }

    public static void addSocket(ItemStack itemStack, Socket socket){
        List<Socket> sockets = itemStack.getOrDefault(SOCKETS, new ArrayList<>());
        for (int index = 0; index < sockets.size(); index++){
            Socket socket1 = sockets.get(index);
            if (socket1.isEmpty())
                sockets.set(index, socket);
        }
        itemStack.set(SOCKETS, sockets);
    }

    public DataComponentType<?> getDataComponentType() {
        return dataComponentType;
    }

    public static final Codec<Socket> SOCKET_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    DataComponentType.PERSISTENT_CODEC.fieldOf("type")
                            .forGetter(Socket::getDataComponentType),
                    Codec.INT.fieldOf("count")
                            .forGetter(Socket::getCount)
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
}

