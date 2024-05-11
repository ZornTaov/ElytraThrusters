package org.zornco.elytrathrusters;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;
import org.zornco.elytrathrusters.Items.RocketThrusterItem;
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class EventHandlers {
    @SubscribeEvent
    public static void playerTickEvent(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        ItemStack thruster = null;
        for (ItemStack stack : player.getHandSlots()) {
            if (stack.getItem() instanceof RocketThrusterItem) thruster = stack;
        }
        if (player.level().isClientSide() ) {
            if (player.isFallFlying() && thruster != null && player.isUsingItem()) {
                int storedEnergy = thruster.getOrDefault(Registries.STORED_ENERGY, 0);
                if (storedEnergy > 0) {
                    Vec3 thrustVector = getThrustVector(player);
                    player.setDeltaMovement(thrustVector);
                    thruster.set(Registries.STORED_ENERGY, Math.max(storedEnergy - (500 / 20), 0));
                }

                if (player.level().isClientSide && player.tickCount % 2 == 1) {
                    player.level()
                        .addParticle(
                            storedEnergy > 0 ? ParticleTypes.FIREWORK : ParticleTypes.SMOKE,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            player.level().random.nextGaussian() * 0.05,
                            -player.getDeltaMovement().y * 0.5,
                            player.level().random.nextGaussian() * 0.05
                        );
                }
            }
            else {
                player.stopUsingItem();
            }
        }
    }

    private static @NotNull Vec3 getThrustVector(Player player) {
        Vec3 forwardVec = player.getLookAngle(); //normalized
        double d0 = 1.5;
        double d1 = 0.1;
        Vec3 lastDeltaVec = player.getDeltaMovement();
        return lastDeltaVec.add(
            forwardVec.x * d1 + (forwardVec.x * d0 - lastDeltaVec.x) * 0.5,
            forwardVec.y * d1 + (forwardVec.y * d0 - lastDeltaVec.y) * 0.5,
            forwardVec.z * d1 + (forwardVec.z * d0 - lastDeltaVec.z) * 0.5
        );
    }
}
