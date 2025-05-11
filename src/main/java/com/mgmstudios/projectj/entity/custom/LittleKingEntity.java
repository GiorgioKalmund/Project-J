package com.mgmstudios.projectj.entity.custom;

import com.google.common.collect.ImmutableMap;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.entity.VoodooEntity;
import com.mgmstudios.projectj.item.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.providers.EnchantmentProvider;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.neoforged.neoforge.common.BasicItemListing;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.mgmstudios.projectj.entity.custom.LittleManEntity.toIntMap;

public class LittleKingEntity extends AbstractVillager implements VoodooEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public LittleKingEntity(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    @Override
    public SoundEvent getVoodooSound() {
        return SoundEvents.WANDERING_TRADER_DISAPPEARED;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new TemptGoal(this, 1.1, stack -> stack.is(ModItems.JADE.get()), false));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Zombie.class, 8.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Evoker.class, 12.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Vindicator.class, 8.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Vex.class, 8.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Pillager.class, 15.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Illusioner.class, 12.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Zoglin.class, 10.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new PanicGoal(this, (double)0.5F));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.35));
        this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.TEMPT_RANGE, 20.0)
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
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
    public boolean showProgressBar() {
        return false;
    }


    @Override
    public Item getVoodoo() {
        return ModItems.LITTLE_KING_VOODOO.get();
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

        VillagerTrades.ItemListing[] itemListings = LITTLE_KING_TRADES.get(1);
        VillagerTrades.ItemListing[] itemListings1 = LITTLE_KING_TRADES.get(2);


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

    public static final Int2ObjectMap<VillagerTrades.ItemListing[]> LITTLE_KING_TRADES = toIntMap(
            ImmutableMap.of(
                    1,
                    new VillagerTrades.ItemListing[]{
                            jadeBasicListing(16, ModItems.LIQUID_PYRITE_BUCKET.get(),  1, 3, 1),
                            new JadeAndItemListing( Items.FEATHER, 8, new ItemStack(ModItems.QUETZAL_FEATHER.get(), 8), 4, 3, 1),
                            jadeBasicListing(2, Items.SLIME_BALL, 4, 8, 3, 1),
                            jadeBasicListing(6, ModItems.TROWEL, 1, 3, 3, 1),
                            jadeBasicListing(2, ModBlocks.ADOBE_BRICKS, 32, 3, 3, 1),
                            basicListing(ModItems.RAW_PYRITE, 1, ModItems.PYRITE_INGOT, 1, 24, 3, 1),
                            new TreasureMapForSecondCurrency(ModItems.JADE, 12, StructureTags.ON_TRIAL_CHAMBERS_MAPS, "filled_map.trial_chambers", MapDecorationTypes.TRIAL_CHAMBERS, 12, 10),
                            new TwoItemListing(ModItems.RAW_JADE.get(), 8, Items.COAL, new ItemStack(ModItems.JADE.get(), 16), 4, 3, 1)
                    },
                    2,
                    new VillagerTrades.ItemListing[]{
                            jadeBasicListing(32, ModItems.SUN_ARMOR_HELMET,  1, 5, 1.1F),
                            jadeBasicListing(48, ModItems.SUN_ARMOR_CHESTPLATE,  1, 5, 1.4F),
                            jadeBasicListing(27, ModItems.SUN_ARMOR_LEGGINGS,  1, 5, 1.3F),
                            jadeBasicListing(23, ModItems.SUN_ARMOR_BOOTS,  1, 5, 1.2F),
                            new JadeAndItemListing(16, ModItems.VOODOO_CATCHER.get(),  ModItems.LITTLE_MAN_VOODOO.toStack(), 1, 6, 1.4F),
                            new JadeAndItemListing(8, Items.FILLED_MAP,  ModItems.LITTLE_MAN_VOODOO.toStack(), 1, 6, 1.4F),
                            new TwoItemListing(Items.BOWL, ModItems.LIQUID_PYRITE_BUCKET.get(), ModItems.CRUDE_SACRIFICE_BOWL.toStack(), 3, 3, 1)
                    }
            )
    );

    protected static final BasicItemListing basicListing(ItemLike item, int count, ItemLike resultItem, int resultCount, int maxTrades, int xp, float priceMult){
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(resultItem, resultCount), maxTrades, xp, priceMult);
    }

    protected static final BasicItemListing jadeBasicListing(int count, ItemLike resultItem, int resultCount, int maxTrades, int xp, float priceMult){
        return new BasicItemListing(new ItemStack(ModItems.JADE.get(), count), new ItemStack(resultItem, resultCount), maxTrades, xp, priceMult);
    }
    protected static final BasicItemListing jadeBasicListing(ItemLike resultItem, int resultCount, int maxTrades, int xp, float priceMult){
        return jadeBasicListing(1, resultItem, resultCount, maxTrades, xp, priceMult);
    }
    protected static final BasicItemListing jadeBasicListing(int count, ItemLike resultItem, int maxTrades, int xp, float priceMult){
        return jadeBasicListing(count, resultItem, 1, maxTrades, xp, priceMult);
    }
    protected static final BasicItemListing jadeBasicListing(ItemLike resultItem, int maxTrades, int xp, float priceMult){
        return jadeBasicListing(1, resultItem, 1, maxTrades, xp, priceMult);
    }

    public static class JadeAndItemListing extends TwoItemListing{

        public JadeAndItemListing(ItemLike secondItem, int secondItemCost, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier, Optional<ResourceKey<EnchantmentProvider>> enchantmentProvider) {
            super(ModItems.JADE.get(), secondItem, secondItemCost, resultItem, maxUses, villagerXp, priceMultiplier, enchantmentProvider);
        }

        public JadeAndItemListing(ItemLike secondItem, int secondItemCost, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier) {
            super(ModItems.JADE.get(), secondItem, secondItemCost, resultItem, maxUses, villagerXp, priceMultiplier);
        }

        public JadeAndItemListing(int firstItemCost, ItemLike secondItem, int secondItemCost, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier, Optional<ResourceKey<EnchantmentProvider>> enchantmentProvider) {
            super(ModItems.JADE.get(), firstItemCost, secondItem, secondItemCost, resultItem, maxUses, villagerXp, priceMultiplier, enchantmentProvider);
        }

        public JadeAndItemListing(int firstItemCost, ItemLike secondItem, int secondItemCost, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier) {
            super(ModItems.JADE.get(), firstItemCost, secondItem, secondItemCost, resultItem, maxUses, villagerXp, priceMultiplier, Optional.empty());
        }

        public JadeAndItemListing(int firstItemCost, ItemLike secondItem, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier) {
            super(ModItems.JADE.get(), firstItemCost, secondItem, 1, resultItem, maxUses, villagerXp, priceMultiplier, Optional.empty());
        }
    }


    public static class TwoItemListing implements VillagerTrades.ItemListing {
        private final ItemLike firstItem;
        private final int firstItemCost;
        private final ItemLike secondItem;
        private final int secondItemCost;
        private final ItemStack resultItem;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;
        private final Optional<ResourceKey<EnchantmentProvider>> enchantmentProvider;


        public TwoItemListing(ItemLike firstItem, ItemLike secondItem, int secondItemCost, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier, Optional<ResourceKey<EnchantmentProvider>> enchantmentProvider) {
            this(firstItem, 1, secondItem, secondItemCost,   resultItem, maxUses,  villagerXp, priceMultiplier, enchantmentProvider);
        }

        public TwoItemListing(ItemLike firstItem, ItemLike secondItem, int secondItemCost, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier) {
            this(firstItem, 1, secondItem, secondItemCost,   resultItem, maxUses,  villagerXp, priceMultiplier, Optional.empty());
        }

        public TwoItemListing(ItemLike firstItem, ItemLike secondItem, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier, Optional<ResourceKey<EnchantmentProvider>> enchantmentProvider) {
            this(firstItem, 1, secondItem, 1,   resultItem, maxUses,  villagerXp, priceMultiplier, enchantmentProvider);
        }

        public TwoItemListing(ItemLike firstItem, ItemLike secondItem, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier) {
            this(firstItem, 1, secondItem, 1,   resultItem, maxUses,  villagerXp, priceMultiplier, Optional.empty());
        }
        public TwoItemListing(ItemLike firstItem, int firstItemCost, ItemLike secondItem, int secondItemCost, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier, Optional<ResourceKey<EnchantmentProvider>> enchantmentProvider) {
            this.firstItem = firstItem;
            this.firstItemCost = firstItemCost;
            this.secondItem = secondItem;
            this.secondItemCost= secondItemCost;
            this.resultItem = resultItem;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = priceMultiplier;
            this.enchantmentProvider = enchantmentProvider;
        }

        public TwoItemListing(ItemLike firstItem, int firstItemCost, ItemLike secondItem, int secondItemCost, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier) {
           this(firstItem, firstItemCost, secondItem, secondItemCost, resultItem, maxUses, villagerXp, priceMultiplier, Optional.empty()) ;
        }

        public TwoItemListing(ItemLike firstItem, int firstItemCost, ItemLike secondItem, ItemStack resultItem, int maxUses, int villagerXp, float priceMultiplier) {
            this(firstItem, firstItemCost, secondItem, 1, resultItem, maxUses, villagerXp, priceMultiplier, Optional.empty()) ;
        }

        @javax.annotation.Nullable
        public MerchantOffer getOffer(Entity p_219696_, RandomSource p_219697_) {
            ItemStack itemstack = this.resultItem.copy();
            Level level = p_219696_.level();
            this.enchantmentProvider.ifPresent((p_348335_) -> EnchantmentHelper.enchantItemFromProvider(itemstack, level.registryAccess(), p_348335_, level.getCurrentDifficultyAt(p_219696_.blockPosition()), p_219697_));
            return new MerchantOffer(new ItemCost(this.firstItem, this.firstItemCost), Optional.of(new ItemCost(this.secondItem, this.secondItemCost)), itemstack, 0, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class TreasureMapForSecondCurrency implements VillagerTrades.ItemListing {
        private final int emeraldCost;
        private final TagKey<Structure> destination;
        private final String displayName;
        private final Holder<MapDecorationType> destinationType;
        private final int maxUses;
        private final int villagerXp;
        private final ItemLike currency;

        public TreasureMapForSecondCurrency(ItemLike currency, int emeraldCost, TagKey<Structure> destination, String displayName, Holder<MapDecorationType> destinationType, int maxUses, int villagerXp) {
            this.emeraldCost = emeraldCost;
            this.destination = destination;
            this.displayName = displayName;
            this.destinationType = destinationType;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.currency = currency;
        }

        @javax.annotation.Nullable
        public MerchantOffer getOffer(Entity p_219708_, RandomSource p_219709_) {
            if (!(p_219708_.level() instanceof ServerLevel)) {
                return null;
            } else {
                ServerLevel serverlevel = (ServerLevel)p_219708_.level();
                BlockPos blockpos = serverlevel.findNearestMapStructure(this.destination, p_219708_.blockPosition(), 100, true);
                if (blockpos != null) {
                    ItemStack itemstack = MapItem.create(serverlevel, blockpos.getX(), blockpos.getZ(), (byte)2, true, true);
                    MapItem.renderBiomePreviewMap(serverlevel, itemstack);
                    MapItemSavedData.addTargetDecoration(itemstack, blockpos, "+", this.destinationType);
                    itemstack.set(DataComponents.ITEM_NAME, Component.translatable(this.displayName));
                    return new MerchantOffer(new ItemCost(currency, this.emeraldCost), Optional.of(new ItemCost(Items.COMPASS)), itemstack, this.maxUses, this.villagerXp, 0.2F);
                } else {
                    return null;
                }
            }
        }
    }
}
