package com.stalix.shardspixelmon.listener;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.PokemonSendOutEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.stalix.shardspixelmon.database.DatabaseConnection;
import com.stalix.shardspixelmon.entities.PlayerLevel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class LevelVerifyListener {

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
    public static void battleVerifyLevelPlayer(BattleStartedEvent event) {
        BattleStartedEvent bse = new BattleStartedEvent(event.bc, event.participant1, event.participant2);
        System.out.println("Evento de batalha iniciado!");
        Iterator var1 = Arrays.stream(event.participant1).iterator();
        List<BattleParticipant> battleParticipantList = new ArrayList<>();
        while (var1.hasNext()) {
            battleParticipantList.add((BattleParticipant) var1.next());
            System.out.println(var1.next());
        }

        Iterator var = battleParticipantList.iterator();

        while (var.hasNext()) {
            PlayerParticipant playerParticipant = (PlayerParticipant) var.next();
            System.out.println(playerParticipant.player.getName());
            if (playerParticipant instanceof PlayerParticipant) {
                playerParticipant.player.sendMessage(new StringTextComponent("Deu certo" + playerParticipant.player.getName()), playerParticipant.player.getUUID());
            }
        }
    }
}
