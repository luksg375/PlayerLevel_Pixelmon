package com.stalix.shardspixelmon.listener;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.PokemonSendOutEvent;
import com.stalix.shardspixelmon.database.DatabaseConnection;
import com.stalix.shardspixelmon.entities.PlayerLevel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class LevelVerifyWithPokemons {

    @SubscribeEvent
    public void sendOutVerify(PokemonSendOutEvent.Pre event) {
        try(Connection conn = DatabaseConnection.getConnection()) {
            ServerPlayerEntity playerEntity = PlayerLevel.PlayerLevelConstructor(event.getPlayer().server, event.getPlayer().getLevel(), event.getPlayer().getGameProfile(), event.getPlayer().gameMode, conn, event.getPlayer().getUUID());
            if (playerEntity instanceof PlayerLevel) {
                 if (Objects.equals(event.getPokemon().getPokemonLevel(), ((PlayerLevel) playerEntity).getLevelPlayer()) || event.getPokemon().getPokemonLevel() < ((PlayerLevel) playerEntity).getLevelPlayer()) {

                 } else {
                     event.getPlayer().sendMessage(new StringTextComponent("Você é fraco demais para controlar esse pokémon! Seu nível: " + ((PlayerLevel) playerEntity).getLevelPlayer()), playerEntity.getUUID());
                     event.setCanceled(true);
                 }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @SubscribeEvent
    public static
}
