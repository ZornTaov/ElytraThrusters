package org.zornco.elytrathrusters.Items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class RocketThrusterItem extends Item {
    private final int energyCapacity;
    private final boolean creative;
    public static final byte[] CRAFTABLE_DURATIONS = new byte[]{1, 2, 3};
    public static final String TAG_FLIGHT = "Flight";
    public static final double ROCKET_PLACEMENT_OFFSET = 0.15;
    public static final Style GRAY = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY));

    public RocketThrusterItem(Item.Properties properties, boolean creative, int energyCapacity) {
        super(properties);
        this.creative = creative;
        this.energyCapacity = energyCapacity;
    }
    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }
    /**
     * Returns the action that specifies what animation to play when the item is being used.
     */
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (pPlayer.isFallFlying()) {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

//    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
//        if (player.isFallFlying()) {
//            ItemStack itemstack = player.getItemInHand(hand);
//            if (!level.isClientSide) {
//                //EnergyStorage data = itemstack.getData(DataAttachments.ITEM_ENERGY_ATTACHMENT);
//
//                //if (data.getEnergyStored() < 500) return InteractionResultHolder.pass(player.getItemInHand(hand));
//
//                //FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(level, itemstack, player);
//                //level.addFreshEntity(fireworkrocketentity);
//                if (!player.getAbilities().instabuild) {
//                    //itemstack.shrink(1);
//                    //data.extractEnergy(500, false);
//                    //itemstack.setData(DataAttachments.ITEM_ENERGY_ATTACHMENT, data);
//                }
//
//                player.awardStat(Stats.ITEM_USED.get(this));
//            }
//            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
//        } else {
//            return InteractionResultHolder.pass(player.getItemInHand(hand));
//        }
//    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext pContext, @NotNull List<Component> tooltip, @NotNull TooltipFlag tooltipFlag) {
        Fireworks fireworks = (Fireworks)stack.get(DataComponents.FIREWORKS);
        if (fireworks != null) {
            Objects.requireNonNull(tooltip);
            fireworks.addToTooltip(pContext, tooltip::add, tooltipFlag);
        }

//        if (!creative) {
//            final IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
//            if (energy == null) {
//                return;
//            }
//            tooltip.add(Component.translatable("misc.refinedstorage.energy_stored", energy.getEnergyStored(), energy.getMaxEnergyStored()).setStyle(GRAY));
//        }
    }

//    public int getEnergyCapacity() {
//        return energyCapacity;
//    }
//
//    @Override
//    public boolean isBarVisible(@NotNull ItemStack stack) {
//        return !creative;
//    }
//
//    @Override
//    public int getBarWidth(ItemStack stack) {
//        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
//        if (energy == null) {
//            return 0;
//        }
//        float stored = (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored();
//        return Math.round(stored * 13F);
//    }
//
//    @Override
//    public int getBarColor(ItemStack stack) {
//        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
//        if (energy == null) {
//            return super.getBarColor(stack);
//        }
//        return Mth.hsvToRgb(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
//    }
}
