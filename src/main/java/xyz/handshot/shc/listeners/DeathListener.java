package xyz.handshot.shc.listeners;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import xyz.handshot.shc.DeathList;
import xyz.handshot.shc.SemiHardcore;

public class DeathListener implements ServerLivingEntityEvents.AfterDeath {
    private final DeathList deathList;

    public DeathListener(DeathList deathList) {
        this.deathList = deathList;
    }

    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        if (!entity.isPlayer()) return;
        SemiHardcore.LOGGER.info("Added player " + entity.getUuid());
        deathList.add(entity.getUuid());
    }
}
