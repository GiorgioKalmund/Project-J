package com.mgmstudios.projectj.entity.custom;

import com.google.common.collect.ImmutableMap;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.entity.VoodooEntity;
import com.mgmstudios.projectj.item.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import org.jetbrains.annotations.Nullable;

public class LittleManEntity extends AbstractVillager implements VoodooEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public LittleManEntity(EntityType<? extends LittleManEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TemptGoal(this, 1.1, stack -> stack.is(ModItems.JADE.get()), false));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6));
        this.goalSelector.addGoal(6, new MoveToBlockGoal(this, 1.15, 30) {
            @Override
            protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
                return levelReader.getBlockState(blockPos).is(ModBlocks.EMPTY_LITTLE_MAN_STATUE_BLOCK.get());
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.TEMPT_RANGE, 20.0)
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.ATTACK_DAMAGE, 15.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()){
            this.setupAnimationStates();
        }
    }

    @Override
    public Item getVoodoo() {
        return ModItems.LITTLE_MAN_VOODOO.get();
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }



    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!itemstack.is(ModItems.VOODOO_CATCHER) && this.isAlive() && !this.isTrading() && !this.isBaby()) {
            if (hand == InteractionHand.MAIN_HAND) {
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (!this.level().isClientSide) {
                if (this.getOffers().isEmpty()) {
                    return InteractionResult.CONSUME;
                }

                this.setTradingPlayer(player);
                this.openTradingScreen(player, this.getDisplayName(), 1);
            }

            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    protected void updateTrades() {

        VillagerTrades.ItemListing[] itemListings = LITTLE_MAN_TRADES.get(1);
        VillagerTrades.ItemListing[] itemListings1 = LITTLE_MAN_TRADES.get(2);


        if (itemListings != null && itemListings1 != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addOffersFromItemListings(merchantoffers, itemListings, 5);
            int i = this.random.nextInt(itemListings1.length);
            VillagerTrades.ItemListing villagertrades$itemlisting = itemListings1[i];
            MerchantOffer merchantoffer = villagertrades$itemlisting.getOffer(this, this.random);
            if (merchantoffer != null) {
                merchantoffers.add(merchantoffer);
            }
        }
    }


     @Override
    protected void rewardTradeXp(MerchantOffer offer) {
        if (offer.shouldRewardExp()) {
            int i = 3 + this.random.nextInt(4);
            this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), i));
        }
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {
        if (!this.level().isClientSide && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20) {
            this.ambientSoundTime = -this.getAmbientSoundInterval();
            this.makeSound(this.getTradeUpdatedSound(!stack.isEmpty()));
        }
    }

    protected SoundEvent getTradeUpdatedSound(boolean isYesSound) {
        return isYesSound ? SoundEvents.VILLAGER_YES : SoundEvents.VILLAGER_NO;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_150046_, AgeableMob p_150047_) {
        return null;
    }

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> map) {
        return new Int2ObjectOpenHashMap<>(map);
    }

    public static final Int2ObjectMap<VillagerTrades.ItemListing[]> LITTLE_MAN_TRADES = toIntMap(
            ImmutableMap.of(
                    1,
                    new VillagerTrades.ItemListing[]{
                            new BasicItemListing(new ItemStack(ModItems.JADE.get(), 16), new ItemStack(ModItems.LIQUID_PYRITE_BUCKET.get(), 1), 1, 3, 1),
                            new BasicItemListing(new ItemStack(ModItems.RAW_PYRITE.get(), 1), new ItemStack(ModItems.PYRITE_INGOT.get(), 1), 24, 3, 1),
                            new VillagerTrades.ItemsForEmeralds(Items.SEA_PICKLE, 2, 1, 5, 1),
                            new VillagerTrades.ItemsForEmeralds(Items.SLIME_BALL, 4, 1, 5, 1),
                            new VillagerTrades.ItemsForEmeralds(Items.GLOWSTONE, 2, 1, 5, 1),
                            new VillagerTrades.ItemsForEmeralds(Items.NAUTILUS_SHELL, 5, 1, 5, 1),
                            new VillagerTrades.ItemsForEmeralds(Items.FERN, 1, 1, 12, 1),
                    },
                    2,
                    new VillagerTrades.ItemListing[]{
                            new BasicItemListing(new ItemStack(Items.DIAMOND, 6), new ItemStack(Items.COBBLESTONE, 6), 10, 3, 1),
                            new VillagerTrades.ItemsForEmeralds(Items.TROPICAL_FISH_BUCKET, 5, 1, 4, 1),
                            new VillagerTrades.ItemsForEmeralds(Items.GUNPOWDER, 1, 1, 8, 1),
                            new VillagerTrades.ItemsForEmeralds(Items.PODZOL, 3, 3, 6, 1)
                    }
            )
    );

}
