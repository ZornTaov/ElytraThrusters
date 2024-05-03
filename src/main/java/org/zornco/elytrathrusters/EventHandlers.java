package org.zornco.elytrathrusters;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.zornco.elytrathrusters.Items.RocketThrusterItem;
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class EventHandlers {
    @SubscribeEvent
    public static void playerTickEvent(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        ItemStack thruster = null;
        for (ItemStack stack : player.getHandSlots()) {
            if (stack.getItem() instanceof RocketThrusterItem) thruster = stack;
        }
        if (player.level().isClientSide() ) {
            Vec3 vec3;
            if (player.isFallFlying() && thruster != null && player.isUsingItem()) {
                ElytraThrusters.LOGGER.info("PlayerD: "+ player.getDeltaMovement());
                Vec3 vec31 = player.getLookAngle();
                double d0 = 1.5;
                double d1 = 0.1;
                Vec3 vec32 = player.getDeltaMovement();
                Vec3 thrustVector = vec32.add(
                    vec31.x * d1 + (vec31.x * d0 - vec32.x) * 0.5,
                    vec31.y * d1 + (vec31.y * d0 - vec32.y) * 0.5,
                    vec31.z * d1 + (vec31.z * d0 - vec32.z) * 0.5
                );
                player.setDeltaMovement(thrustVector);
                //ElytraThrusters.LOGGER.info("ThrusterD: "+ thrustVector);
            }
            else {
                player.stopUsingItem();
            }

            //this.setPos(player.getX() + vec3.x, player.getY() + vec3.y, player.getZ() + vec3.z);
            //this.setDeltaMovement(player.getDeltaMovement());
        }
    }
}
