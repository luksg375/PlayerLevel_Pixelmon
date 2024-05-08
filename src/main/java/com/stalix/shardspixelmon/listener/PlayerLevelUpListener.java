package com.stalix.shardspixelmon.listener;

import com.mojang.authlib.GameProfile;
import com.pixelmonmod.pixelmon.api.events.LevelUpEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.stalix.shardspixelmon.database.DatabaseConnection;
import com.stalix.shardspixelmon.entities.PlayerLevel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.SubscribeEvent;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

import static com.stalix.shardspixelmon.ModFile.LOGGER;

public class PlayerLevelUpListener {

    @SubscribeEvent
    public void PlayerLevelUp(LevelUpEvent event) {

        try(Connection conn = DatabaseConnection.getConnection()) {

            ServerPlayerEntity playerEntity = playerMatch(event.getPlayer().getServer(), event.getPlayer().getLevel(), event.getPlayer().getGameProfile(), event.getPlayer().gameMode, conn, event.getPlayer().getUUID());
            Pokemon pokemon = event.getPokemon();
            if (Objects.equals(pokemon.getPokemonLevel(), playerEntity.getLevel())) {
                incrementLevelPlayer(playerEntity.getUUID(), pokemon.getPokemonLevel(), conn);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private static ServerPlayerEntity playerMatch(MinecraftServer server, ServerWorld world, GameProfile profile, PlayerInteractionManager interactionManager, Connection conn, UUID uuid) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement("SELECT UUID, Level FROM player_levels WHERE UUID = ?")) {
            stmt.setString(1, String.valueOf(uuid));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int level = rs.getInt("LEVEL");
                System.out.println("Jogador encontrado.");
                return new PlayerLevel(server, world, profile, interactionManager, level);
            } else {
                System.out.println("Nenhum jogador encontrado!");
                return null;
            }
        }

    }

    private static void incrementLevelPlayer(UUID uuid, int level, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE player_levels SET Level = ? WHERE UUID = ?")) {
            stmt.setInt(1, level);
            stmt.setString(2, String.valueOf(uuid));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("O nível do jogador foi atualizado com sucesso! Nível atual: " + level);
            } else {
                System.out.println("Nenhum jogador encontrado com UUID:" + uuid);
            }
        }
    }
}
