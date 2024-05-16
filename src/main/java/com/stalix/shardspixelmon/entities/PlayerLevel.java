package com.stalix.shardspixelmon.entities;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.world.server.ServerWorld;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerLevel extends ServerPlayerEntity {

    private static int levelPlayer;

    public PlayerLevel(MinecraftServer p_i45285_1_, ServerWorld p_i45285_2_, GameProfile p_i45285_3_, PlayerInteractionManager p_i45285_4_, int level) {
        super(p_i45285_1_, p_i45285_2_, p_i45285_3_, p_i45285_4_);
        this.levelPlayer = level;
    }

    public static ServerPlayerEntity PlayerLevelConstructor(MinecraftServer server, ServerWorld world, GameProfile profile, PlayerInteractionManager interactionManager, Connection conn, UUID uuid) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement("SELECT UUID, LEVEL FROM player_levels WHERE UUID = ?")) {
            stmt.setString(1, String.valueOf(uuid));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int level = rs.getInt("LEVEL");
                return new PlayerLevel(server, world, profile, interactionManager, level);
            } else {
                System.out.println("Nenhum jogador encontrado!");
                return null;
            }
        }
    }

    public static boolean verifyPlayerWithUUID(UUID uuid, Connection conn) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement("SELECT UUID FROM player_levels WHERE UUID = ?")) {
            stmt.setString(1, String.valueOf(uuid));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        }
    }


    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    public int getLevelPlayer() {
        return levelPlayer;
    }

    public void setLevelPlayer(int levelPlayer) {
        this.levelPlayer = levelPlayer;
    }


}


