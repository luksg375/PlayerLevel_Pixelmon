package com.stalix.shardspixelmon.listener;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.PokemonSendOutEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.stalix.shardspixelmon.database.DatabaseConnection;
import com.stalix.shardspixelmon.entities.PlayerLevel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class LevelVerifyListener {

    List<PixelmonWrapper> pixelmonWrapperList;

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
    public void battleVerifyLevelPlayer(BattleStartedEvent event) {
        BattleStartedEvent bse = new BattleStartedEvent(event.bc, event.participant1, event.participant2);

        System.out.println("Evento de batalha iniciado!");
        System.out.println(event.participant1.length);

        List<BattleParticipant> battleParticipantList;

        battleParticipantList = Arrays.stream(event.participant1).collect(Collectors.toList());

        List<PixelmonWrapper> pixelmonWrapperList1;


        for (BattleParticipant battleParticipant : battleParticipantList) {
            System.out.println(battleParticipant);
            System.out.println(battleParticipant.getEntity().getDisplayName());
            pixelmonWrapperList1 = event.bc.getTeamPokemon(battleParticipant);

            for (PixelmonWrapper pixelmonWrapper : pixelmonWrapperList1) {
                System.out.println(pixelmonWrapper);
            }

        }

       /* for (PixelmonWrapper wrapper : pixelmonWrapperList) {
            System.out.println(wrapper.getPokemonName());
        }
*/

    }
}
