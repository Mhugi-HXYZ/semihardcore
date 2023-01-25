package xyz.handshot.shc.tasks;

import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.world.GameMode;
import xyz.handshot.shc.DeathList;
import xyz.handshot.shc.SemiHardcore;

public class ClearDeathListTask implements Runnable {
    private final DeathList deathList;
    private final MinecraftDedicatedServer server;

    public ClearDeathListTask(DeathList deathList, MinecraftDedicatedServer server) {
        this.deathList = deathList;
        this.server = server;
    }

    @Override
    public void run() {
        SemiHardcore.LOGGER.info("Clearing death list");
        deathList.clear();
        server.getPlayerManager().getPlayerList().forEach(player -> {
            if (!player.isSpectator()) return;
            player.changeGameMode(GameMode.SURVIVAL);
            player.setHealth(player.getMaxHealth());
            player.requestRespawn();
        });
    }
}
