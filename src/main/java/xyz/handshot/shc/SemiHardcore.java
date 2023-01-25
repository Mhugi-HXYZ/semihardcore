package xyz.handshot.shc;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class SemiHardcore implements ModInitializer {

    public static Logger LOGGER = Logger.getLogger("semihardcore");

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private MinecraftDedicatedServer server;

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(srv -> {
            server = (MinecraftDedicatedServer) srv;
        });
        ServerPlayConnectionEvents.JOIN.register(new JoinListener(server));
        executor.scheduleAtFixedRate(new RespawnTask(server), calculateDelay(), TimeUnit.MILLISECONDS.convert(7, TimeUnit.DAYS), TimeUnit.MILLISECONDS);
    }

    private long calculateDelay() {
        LocalDateTime nextMonday = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        return LocalDateTime.now().until(nextMonday, ChronoUnit.MILLIS);
    }

}
