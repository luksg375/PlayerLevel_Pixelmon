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
@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class LevelVerifyListener {

    private boolean verifyUUID;

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


        PlayerParticipant playerParticipant = getPlayerParticipant(event);
        if (playerParticipant == null) {
            System.out.println("Uma entidade que não é um player começou uma batalha.");
        } else {
            if (playerParticipant instanceof PlayerParticipant) {

                try (Connection conn = DatabaseConnection.getConnection()) {
                    verifyUUID = PlayerLevel.verifyPlayerWithUUID(playerParticipant.player.getUUID(), conn);

                    if (!verifyUUID) {
                        return;
                    }

                        ServerPlayerEntity playerLevel = PlayerLevel.PlayerLevelConstructor(
                                playerParticipant.player.getServer(),
                                playerParticipant.player.getLevel(),
                                playerParticipant.player.getGameProfile(),
                                playerParticipant.player.gameMode,
                                conn,
                                playerParticipant.player.getUUID()
                        );

                        PlayerPartyStorage playerPartyStorages = playerParticipant.party;
                        List<Pokemon> pokemons = playerPartyStorages.getTeam();

                        for (Pokemon pokemon : pokemons) {
                            checkLevelPokemonWithPlayer(pokemon, (PlayerLevel) playerLevel, playerParticipant);
                            System.out.println(pokemon.getDisplayName());
                        }
                    } catch(SQLException e){
                        e.printStackTrace();
                    }
                }
        }
    }

    private void checkLevelPokemonWithPlayer(Pokemon pokemon, PlayerLevel playerLevel, PlayerParticipant playerParticipant) {
        if (pokemon.getPokemonLevel() > playerLevel.getLevelPlayer()) {
            if (pokemon.getHealth() != 0) {
                pokemon.setHealth(0);
                if (playerParticipant.player.connection != null) {
                    playerParticipant.player.sendMessage(new StringTextComponent(
                                    "O Pokémon " + pokemon.getDisplayName() + " não atendeu aos seus comandos e acabou se machucando. Seu nível: " + playerLevel.getLevelPlayer()),
                            playerParticipant.player.getUUID()
                    );
                } else {}
            } else { }
        }
    }

    private PlayerParticipant getPlayerParticipant(BattleStartedEvent event) {
        List<PlayerParticipant> participants = event.bc.getPlayers();

        if (!participants.isEmpty()) {
            PlayerParticipant player = event.bc.getPlayer((PlayerEntity) participants.get(0).getEntity());
            return player;
        }
        return null;
    }
}
