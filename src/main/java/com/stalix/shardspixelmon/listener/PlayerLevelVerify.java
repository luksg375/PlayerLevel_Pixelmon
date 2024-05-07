package com.stalix.shardspixelmon.listener;

import com.stalix.shardspixelmon.database.DatabaseConnection;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Mod.EventBusSubscriber
public class PlayerLevelVerify {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        DatabaseConnection dbConnection = new DatabaseConnection();
        boolean havePlayer = false;

    try (Connection conn = dbConnection.getConnection()){
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM ");


    }
    catch (SQLException e) {
        e.printStackTrace();
    }

    }
}
