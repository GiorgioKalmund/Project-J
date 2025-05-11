package com.mgmstudios.projectj.item.custom.armor;

import com.mgmstudios.projectj.component.ModDataComponents;
import com.mgmstudios.projectj.entity.client.armor.AwakenedSunArmorRenderer;
import com.mgmstudios.projectj.item.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static com.mgmstudios.projectj.item.custom.armor.JadeArmorItem.getEquippedArmor;

public final class AwakenedSunArmorItem extends ArmorItem implements GeoItem {
    ArmorType armorType;
    public AwakenedSunArmorItem(ArmorMaterial material, ArmorType armorType, Properties properties) {
        super(material, armorType, properties);
        this.armorType = armorType;
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> {
            // Apply our generic idle animation.
            // Whether it plays or not is decided down below.
            state.getController().setAnimation(DefaultAnimations.IDLE);

            // Let's gather some data from the state to use below
            // This is the entity that is currently wearing/holding the item
            Entity entity = state.getData(DataTickets.ENTITY);

            // We'll just have ArmorStands always animate, so we can return here
            if (entity instanceof ArmorStand)
                return PlayState.CONTINUE;

            if (entity instanceof LivingEntity livingEntity){

                // For this example, we only want the animation to play if the entity is wearing all pieces of the armor
                // Let's collect the armor pieces the entity is currently wearing
                Set<Item> wornArmor = getEquippedArmor(livingEntity);
                if (wornArmor.isEmpty())
                    return PlayState.STOP;

                // Check each of the pieces match our set
                boolean isFullSet = hasFullSuitOfArmorOn(wornArmor);


                // Play the animation if the full set is being worn, otherwise stop
                return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
            }

            return PlayState.STOP;
        }));
    }

    private static boolean hasFullSuitOfArmorOn(Set<Item> wornArmor){
        return wornArmor.containsAll(ObjectArrayList.of(
                ModItems.AWAKENED_SUN_ARMOR_BOOTS.get(),
                ModItems.AWAKENED_SUN_ARMOR_LEGGINGS.get(),
                ModItems.AWAKENED_SUN_ARMOR_HELMET.get(),
                ModItems.AWAKENED_SUN_ARMOR_CHESTPLATE.get()));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && !level.isClientSide()) {
            Set<Item> wornArmor = getEquippedArmor(player);
            if (wornArmor.isEmpty())
                return;

            // Check each of the pieces match our set
            boolean isFullSet = hasFullSuitOfArmorOn(wornArmor);
            boolean charged = stack.getOrDefault(ModDataComponents.AWAKENED_ARMOR_CHARGED, false);

            if (!isFullSet && charged){
                stack.remove(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
                stack.set(DataComponents.RARITY, Rarity.RARE);
                stack.set(ModDataComponents.AWAKENED_ARMOR_CHARGED, false);
                if (armorType.equals(ArmorType.CHESTPLATE))
                    player.playNotifySound(SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 0.5f, 0.8f);
        } else if (isFullSet && !charged && isInArmorSlot(slotId)){
                stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                stack.set(DataComponents.RARITY, Rarity.EPIC);
                stack.set(ModDataComponents.AWAKENED_ARMOR_CHARGED, true);
                if (armorType.equals(ArmorType.CHESTPLATE))
                    player.playNotifySound(SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 0.5f, 1.1f);
            }

            if (isFullSet){
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0, true, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0, true, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 200, 0, true, false, false));
            }
        }
    }

    private boolean isInArmorSlot(int slot){
        return slot >= 36 && slot <= 39;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.has(DataComponents.ENCHANTMENT_GLINT_OVERRIDE) && Boolean.TRUE.equals(stack.get(DataComponents.ENCHANTMENT_GLINT_OVERRIDE)))
            tooltipComponents.add(Component.translatable("tooltip.projectj.awakened_armor.charged.tooltip"));
        else
            tooltipComponents.add(Component.translatable("tooltip.projectj.awakened_armor.tooltip"));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <E extends LivingEntity, S extends HumanoidRenderState> @NotNull HumanoidModel<?> getGeoArmorRenderer(@org.jetbrains.annotations.Nullable E livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, EquipmentClientInfo.LayerType type, HumanoidModel<S> original) {
                if(this.renderer == null) // Important that we do this. If we just instantiate  it directly in the field it can cause incompatibilities with some mods.
                    this.renderer = new AwakenedSunArmorRenderer();

                return this.renderer;
            }
        });
    }
}
