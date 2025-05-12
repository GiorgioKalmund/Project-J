package com.mgmstudios.projectj.item.custom.armor;

import com.mgmstudios.projectj.entity.client.armor.JadeArmorRenderer;
import com.mgmstudios.projectj.entity.client.armor.SunArmorRenderer;
import com.mgmstudios.projectj.item.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
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

import java.util.Set;
import java.util.function.Consumer;

import static com.mgmstudios.projectj.item.custom.armor.JadeArmorItem.getEquippedArmor;

public final class SunArmorItem extends SocketGeoArmorItem {
    public SunArmorItem(ArmorMaterial material, ArmorType armorType, Properties properties) {
        super(material, armorType, properties);
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
                boolean isFullSet = wornArmor.containsAll(ObjectArrayList.of(
                        ModItems.JADE_BOOTS.get(),
                        ModItems.JADE_LEGGINGS.get(),
                        ModItems.JADE_CHESTPLATE.get(),
                        ModItems.JADE_HELMET.get()));

                // Play the animation if the full set is being worn, otherwise stop
                return isFullSet ? PlayState.CONTINUE : PlayState.STOP;
            }

            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <E extends LivingEntity, S extends HumanoidRenderState> @NotNull HumanoidModel<?> getGeoArmorRenderer(@org.jetbrains.annotations.Nullable E livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, EquipmentClientInfo.LayerType type, HumanoidModel<S> original) {
                if(this.renderer == null) // Important that we do this. If we just instantiate  it directly in the field it can cause incompatibilities with some mods.
                    this.renderer = new SunArmorRenderer();

                return this.renderer;
            }
        });
    }
}
