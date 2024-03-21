package org.zornco.elytrathrusters;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.TickEvent;
import org.zornco.elytrathrusters.Items.RocketThrusterItem;

public class CommonSetup {

    @SubscribeEvent
    public static void onRegisterCapabilities(final RegisterCapabilitiesEvent e) {
        e.registerItem(
            Capabilities.EnergyStorage.ITEM,
            (stack, ctx) -> stack.getData(DataAttachments.ITEM_ENERGY_ATTACHMENT),
            Registries.PERSONAL_THRUSTER.get()
        );
    }
    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack thruster = null;
        for (ItemStack stack : player.getHandSlots()) {
            if (stack.getItem() instanceof RocketThrusterItem) thruster = stack;
        }
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            Vec3 vec3;
            if (player.isFallFlying() && thruster != null && player.isUsingItem()) {
                Vec3 vec31 = player.getLookAngle();
                double d0 = 1.5;
                double d1 = 0.1;
                Vec3 vec32 = player.getDeltaMovement();
                player.setDeltaMovement(vec32.add(vec31.x * 0.1 + (vec31.x * 1.5 - vec32.x) * 0.5, vec31.y * 0.1 + (vec31.y * 1.5 - vec32.y) * 0.5, vec31.z * 0.1 + (vec31.z * 1.5 - vec32.z) * 0.5));
                vec3 = player.getHandHoldingItemAngle(Items.FIREWORK_ROCKET);
            } else {
                vec3 = Vec3.ZERO;
            }

            //this.setPos(player.getX() + vec3.x, player.getY() + vec3.y, player.getZ() + vec3.z);
            //this.setDeltaMovement(player.getDeltaMovement());
        }
    }
}
