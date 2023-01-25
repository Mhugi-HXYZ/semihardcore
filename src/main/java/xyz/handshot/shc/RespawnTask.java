package xyz.handshot.shc;

import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;

public class RespawnTask implements Runnable {
    private final MinecraftDedicatedServer server;

    public RespawnTask(MinecraftDedicatedServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        SemiHardcore.LOGGER.info("Respawning all dead players");
        server.getPlayerManager().getPlayerList().forEach(this::respawnIfDead);
    }

    private void respawnIfDead(ServerPlayerEntity player) {
        if (!player.isSpectator()) return;
        player.changeGameMode(GameMode.SURVIVAL);
        player.requestRespawn();
        player.setHealth(player.getMaxHealth());
    }
}
