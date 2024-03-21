package org.zornco.elytrathrusters.Items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
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
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.zornco.elytrathrusters.DataAttachments;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

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

    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack itemstack = context.getItemInHand();
        if (!level.isClientSide) {
            EnergyStorage data = itemstack.getData(DataAttachments.ITEM_ENERGY_ATTACHMENT);
            if (data.getEnergyStored() < 500) return InteractionResult.PASS;

            Vec3 vec3 = context.getClickLocation();
            Direction direction = context.getClickedFace();
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(level, context.getPlayer(), vec3.x + (double)direction.getStepX() * 0.15, vec3.y + (double)direction.getStepY() * 0.15, vec3.z + (double)direction.getStepZ() * 0.15, itemstack);
            level.addFreshEntity(fireworkrocketentity);
            if (!Objects.requireNonNull(context.getPlayer()).getAbilities().instabuild) {
                data.extractEnergy(500, false);
                itemstack.setData(DataAttachments.ITEM_ENERGY_ATTACHMENT, data);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        if (player.isFallFlying()) {
            ItemStack itemstack = player.getItemInHand(hand);
            if (!level.isClientSide) {
                EnergyStorage data = itemstack.getData(DataAttachments.ITEM_ENERGY_ATTACHMENT);

                if (data.getEnergyStored() < 500) return InteractionResultHolder.pass(player.getItemInHand(hand));

                FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(level, itemstack, player);
                level.addFreshEntity(fireworkrocketentity);
                if (!player.getAbilities().instabuild) {
                    //itemstack.shrink(1);
                    data.extractEnergy(500, false);
                    itemstack.setData(DataAttachments.ITEM_ENERGY_ATTACHMENT, data);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
        } else {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag tooltipFlag) {
        CompoundTag compoundtag = stack.getTagElement("Fireworks");
        if (compoundtag != null) {
            if (compoundtag.contains("Flight", 99)) {
                tooltip.add(Component.translatable("item.minecraft.firework_rocket.flight").append(CommonComponents.SPACE).append(String.valueOf(compoundtag.getByte("Flight"))).withStyle(ChatFormatting.GRAY));
            }
        }

        if (!creative) {
            final IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
            if (energy == null) {
                return;
            }
            tooltip.add(Component.translatable("misc.refinedstorage.energy_stored", energy.getEnergyStored(), energy.getMaxEnergyStored()).setStyle(GRAY));
        }
    }

    public static void setDuration(ItemStack p_260106_, byte p_260332_) {
        p_260106_.getOrCreateTagElement("Fireworks").putByte("Flight", p_260332_);
    }

    public int getEnergyCapacity() {
        return energyCapacity;
    }

    public @NotNull ItemStack getDefaultInstance() {
        ItemStack itemstack = new ItemStack(this);
        setDuration(itemstack, (byte)1);
        return itemstack;
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return !creative;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) {
            return 0;
        }
        float stored = (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored();
        return Math.round(stored * 13F);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) {
            return super.getBarColor(stack);
        }
        return Mth.hsvToRgb(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
    }
}
