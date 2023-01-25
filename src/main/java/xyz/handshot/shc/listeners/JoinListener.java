package xyz.handshot.shc.listeners;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import xyz.handshot.shc.DeathList;

public class JoinListener implements ServerPlayConnectionEvents.Join {
    private final DeathList deathList;

    public JoinListener(DeathList deathList) {
        this.deathList = deathList;
    }

    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        if (!handler.player.isSpectator()) return;
        if (deathList.contains(handler.player.getUuid())) return;

        ServerPlayerEntity player = handler.player;
        player.changeGameMode(GameMode.SURVIVAL);
        player.setHealth(player.getMaxHealth());
        player.requestRespawn();
    }
}
