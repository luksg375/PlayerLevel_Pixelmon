package com.stalix.shardspixelmon.listener;

import com.stalix.shardspixelmon.database.DatabaseConnection;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.sql.*;

@Mod.EventBusSubscriber
public class PlayerEnterServerListener {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        String playerUUID = event.getPlayer().getStringUUID();

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (!playerExists(conn, playerUUID)) {
                createPlayer(conn, playerUUID);
                System.out.println("Novo jogador registrado!");
            } else {
                System.out.println("Jogador existente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean playerExists(Connection conn, String uuid) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM player_levels WHERE UUID = ?")) {
            stmt.setString(1, uuid);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private static void createPlayer(Connection conn, String uuid) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO player_levels (UUID, Level) VALUES (?, ?)")) {
            stmt.setString(1, uuid);
            stmt.setInt(2, 5);  // Assume default level 5 for new players
            stmt.executeUpdate();
        }
    }
}
