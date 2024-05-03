package com.stalix.shardspixelmon.services;

import com.pixelmonmod.pixelmon.api.events.EvolveEvent;
import com.pixelmonmod.pixelmon.api.events.LevelUpEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.DEDICATED_SERVER)
public class EventLevelPlayer {
    @SubscribeEvent
    public static void levelUpEvent(LevelUpEvent event) {

    }
}
