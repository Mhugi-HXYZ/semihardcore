package xyz.handshot.shc;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class JoinListener implements ServerPlayConnectionEvents.Join {

    private final LocalDateTime time = LocalDateTime.now().plusMinutes(5);
    private final MinecraftDedicatedServer server;


    public JoinListener(MinecraftDedicatedServer server) {
        this.server = server;
    }

    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        ServerPlayerEntity player = handler.player;

        if (!player.isSpectator()) {
            return;
        }

        LocalDateTime lastDeath = lastDeath(player);
        LocalDateTime lastMonday = lastMonday();

        if (lastDeath.isAfter(lastMonday)) {
            return;
        }

        player.changeGameMode(GameMode.SURVIVAL);
        player.requestRespawn();
        player.setHealth(player.getMaxHealth());
    }

    private LocalDateTime lastDeath(ServerPlayerEntity player) {
        return LocalDateTime.now().minusSeconds(player.deathTime / 20L);
    }

    private LocalDateTime lastMonday() {
        return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
    }
}
