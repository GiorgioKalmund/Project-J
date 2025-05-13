package com.mgmstudios.projectj.item.custom.socket;

import static com.mgmstudios.projectj.component.ModDataComponents.Sockets.*;

import com.mgmstudios.projectj.util.Socket;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

import static com.mgmstudios.projectj.util.SocketComponents.socketFor;

public class SocketItem extends Item implements SocketHolder{

    public SocketItem(Properties properties, Socket ... sockets) {
        super(applySockets(properties, sockets));
    }

    public static Item.Properties applySockets(Item.Properties properties, Socket... sockets){
        for (Socket socket : sockets){
            socket.apply(properties);
        }
        return properties.component(SOCKETS, List.of(sockets));
    };

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (stack.has(SOCKETS)) {
            List<Socket> sockets = stack.get(SOCKETS);
            if (sockets != null && !sockets.isEmpty()){
                for (Socket socket : sockets){

                    if (socket.is(ZOMBIE_PACIFYING)){
                        littleManVoodooEffect(level, entity);
                    }

                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        socketHoverText(stack, tooltipComponents);
    }

    public static void socketHoverText(ItemStack stack, List<Component> tooltipComponents){
        if (stack.has(SOCKETS)){
            List<Socket> sockets = stack.get(SOCKETS);
            for (Socket socket : sockets){
                tooltipComponents.add(socketFor(socket));
            }
        }
    }

    @Override
    public List<DataComponentType<?>> getAllowedTypes() {
        return List.of();
    }

    @Override
    public List<DataComponentType<?>> getExcludedTypes() {
        return List.of();
    }

    private void littleManVoodooEffect(Level level, Entity entity){
        if(level.isClientSide()) return;

        if(entity instanceof Player player){

            if(!(player.getItemInHand(InteractionHand.MAIN_HAND).is(this) || player.getItemInHand(InteractionHand.OFF_HAND).is(this))){
                return;
            }

            List<Zombie> allEntities = level.getEntitiesOfClass(Zombie.class, new AABB(player.blockPosition()).inflate(35));
            for (Zombie zombie : allEntities) {

                NearestAttackableTargetGoal<Player> goal = new NearestAttackableTargetGoal<>(zombie, Player.class, true);

                if(player.getItemInHand(InteractionHand.MAIN_HAND).is(this) || player.getItemInHand(InteractionHand.OFF_HAND).is(this)){
                    goal.stop();
                }
            }
        }
    }
}
