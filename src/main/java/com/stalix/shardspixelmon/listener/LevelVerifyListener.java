package com.stalix.shardspixelmon.listener;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.PokemonSendOutEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.stalix.shardspixelmon.database.DatabaseConnection;
import com.stalix.shardspixelmon.entities.PlayerLevel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
        try (Connection conn = DatabaseConnection.getConnection()) {
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

        List<PlayerParticipant> participants = event.bc.getPlayers();

        PlayerParticipant player = event.bc.getPlayer((PlayerEntity) participants.get(0).getEntity());

        if (player instanceof PlayerParticipant) {

        PlayerPartyStorage playerPartyStorages = player.party;

        List<Pokemon> pokemons = playerPartyStorages.getTeam();

        List<PixelmonWrapper> pixelmonWrappers1 = event.bc.getActivePokemon();
        System.out.println(pixelmonWrappers1.size());

        for (Pokemon pokemon : pokemons) {
                System.out.println(pokemon.getDisplayName());
                player.player.sendMessage(new StringTextComponent("Nome do pokemon:" + pokemon.getDisplayName()), player.player.getUUID());
        }

        }
    }
}
