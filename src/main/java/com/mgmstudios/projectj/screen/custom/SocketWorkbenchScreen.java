package com.mgmstudios.projectj.screen.custom;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class SocketWorkbenchScreen extends ItemCombinerScreen<SocketWorkbenchMenu> {
    private static final ResourceLocation TEXT_FIELD_SPRITE = ResourceLocation.withDefaultNamespace("container/anvil/text_field");
    private static final ResourceLocation TEXT_FIELD_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/anvil/text_field_disabled");
    private static final ResourceLocation ERROR_SPRITE = ResourceLocation.withDefaultNamespace("container/anvil/error");
    private static final ResourceLocation ANVIL_LOCATION = ResourceLocation.withDefaultNamespace("textures/gui/container/anvil.png");
    private static final Component TOO_EXPENSIVE_TEXT = Component.translatable("container.repair.expensive");
    private final CyclingSlotBackground templateIcon = new CyclingSlotBackground(1);
    private final Player player;
    private static final List<ResourceLocation> EMPTY_SLOT_SMITHING_TEMPLATES;
    private static final ResourceLocation EMPTY_SLOT_REGULAR_GEM = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "container/slot/gem");
    private static final ResourceLocation EMPTY_SLOT_GLIDER_GEM = ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, "container/slot/glider_gem");
    private static final Component GEM_TOOLTIP = Component.translatable("container.projectj.socket_workbench.gem_tooltip");

    public SocketWorkbenchScreen(SocketWorkbenchMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, ANVIL_LOCATION);
        this.player = playerInventory.player;
        this.titleLabelX = 60;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.templateIcon.tick(EMPTY_SLOT_SMITHING_TEMPLATES);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        this.init(minecraft, width, height);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.minecraft.player.closeContainer();
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void renderLabels(GuiGraphics p_281442_, int p_282417_, int p_283022_) {
        super.renderLabels(p_281442_, p_282417_, p_283022_);
        //int i = this.menu.getCost();
        int i = 0;
        if (i > 0) {
            int j = 8453920;
            Component component;
            if (i >= 40 && !this.minecraft.player.getAbilities().instabuild) {
                component = TOO_EXPENSIVE_TEXT;
                j = 16736352;
            } else if (!this.menu.getSlot(2).hasItem()) {
                component = null;
            } else {
                component = Component.translatable("container.repair.cost", i);
                if (!this.menu.getSlot(2).mayPickup(this.player)) {
                    j = 16736352;
                }
            }

            if (component != null) {
                int k = this.imageWidth - 8 - this.font.width(component) - 2;
                int l = 69;
                p_281442_.fill(k - 2, 67, this.imageWidth - 8, 79, 1325400064);
                p_281442_.drawString(this.font, component, k, 69, j);
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        super.render(guiGraphics, mouseX, mouseY, v);
        renderOnboardingTooltips(guiGraphics, mouseX, mouseY);
    }

    private void renderOnboardingTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Optional<Component> optional = Optional.empty();
        if (this.hoveredSlot != null) {
            if (this.hoveredSlot.index == 1) {
                optional = Optional.of(GEM_TOOLTIP);
            }
        }

        optional.ifPresent((component) -> guiGraphics.renderTooltip(this.font, this.font.split(component, 115), mouseX, mouseY));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, v, mouseX, mouseY);
        guiGraphics.blitSprite(
                RenderType::guiTextured,
                this.menu.getSlot(0).hasItem() ? TEXT_FIELD_SPRITE : TEXT_FIELD_DISABLED_SPRITE,
                this.leftPos + 59,
                this.topPos + 20,
                110,
                16
        );
        System.out.println("leftPos: " + this.leftPos);
        this.templateIcon.render(this.menu, guiGraphics, v, this.leftPos, this.topPos);
    }

    @Override
    protected void renderErrorIcon(GuiGraphics p_282905_, int p_283237_, int p_282237_) {
        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(this.menu.getResultSlot()).hasItem()) {
            p_282905_.blitSprite(RenderType::guiTextured, ERROR_SPRITE, p_283237_ + 99, p_282237_ + 45, 28, 21);
        }
    }


    static {
        EMPTY_SLOT_SMITHING_TEMPLATES = List.of(EMPTY_SLOT_REGULAR_GEM, EMPTY_SLOT_GLIDER_GEM);
    }
}
