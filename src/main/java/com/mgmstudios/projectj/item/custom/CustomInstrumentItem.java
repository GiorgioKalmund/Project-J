package com.mgmstudios.projectj.item.custom;

import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class CustomInstrumentItem extends Item {

    private SoundEvent sound;
    private float soundDuration;

    public CustomInstrumentItem(SoundEvent sound, Item.Properties properties, float soundDuration) {
        super(properties);
        this.sound = sound;
        this.soundDuration = soundDuration;
    }

    @Override
    public void appendHoverText(ItemStack p_220115_, Item.TooltipContext p_339630_, List<Component> p_220117_, TooltipFlag p_220118_) {
        super.appendHoverText(p_220115_, p_339630_, p_220117_, p_220118_);
        HolderLookup.Provider holderlookup$provider = p_339630_.registries();
    }

    public static ItemStack create(Item item, Holder<Instrument> instrument) {
        ItemStack itemstack = new ItemStack(item);
        itemstack.set(DataComponents.INSTRUMENT, instrument);
        return itemstack;
    }

    @Override
    public InteractionResult use(Level p_220123_, Player p_220124_, InteractionHand p_220125_) {
        ItemStack itemstack = p_220124_.getItemInHand(p_220125_);
        if(itemstack.getItem() instanceof CustomInstrumentItem) {
            
            p_220124_.startUsingItem(p_220125_);
            play(p_220123_, p_220124_, sound);
            p_220124_.getCooldowns().addCooldown(itemstack, (int)(soundDuration * 20.0F));
            p_220124_.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.FAIL;
        }
    }

    @Override
    public int getUseDuration(ItemStack p_220131_, LivingEntity p_345916_) {
        return (int)(soundDuration * 20.0F);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack p_220133_) {
        return ItemUseAnimation.TOOT_HORN;
    }

    private static void play(Level level, Player player, SoundEvent soundevent) {
        level.playSound(player, player, soundevent, SoundSource.RECORDS, 16, 1.0F);
        level.gameEvent(GameEvent.INSTRUMENT_PLAY, player.position(), GameEvent.Context.of(player));
    }
}
