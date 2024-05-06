package com.stalix.shardspixelmon.listener;

import com.stalix.shardspixelmon.database.DatabaseConnection;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerLevelVerify {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        DatabaseConnection conn = new DatabaseConnection();

        conn.getConnection()
    }
}
