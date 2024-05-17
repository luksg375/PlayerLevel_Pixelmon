package com.stalix.shardspixelmon.listener;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.PokemonSendOutEvent;
import com.pixelmonmod.pixelmon.api.events.battles.AttackEvent;
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
    private boolean cancelEvent;

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
    public void battleVerifyLevelPlayer(AttackEvent.Use event) {

        PixelmonWrapper wrapper = event.user;
        Pokemon pokemonWrapper = wrapper.pokemon;
        ServerPlayerEntity serverPlayerEntity = wrapper.getPlayerOwner();

        if (serverPlayerEntity == null) {
            System.out.println("Uma entidade que não é um player começou uma batalha.");
        } else {
            if (serverPlayerEntity instanceof ServerPlayerEntity) {

                try (Connection conn = DatabaseConnection.getConnection()) {
                    verifyUUID = PlayerLevel.verifyPlayerWithUUID(serverPlayerEntity.getUUID(), conn);

                    if (!verifyUUID) {
                        return;
                    }

                        ServerPlayerEntity playerLevel = PlayerLevel.PlayerLevelConstructor(
                                serverPlayerEntity.getServer(),
                                serverPlayerEntity.getLevel(),
                                serverPlayerEntity.getGameProfile(),
                                serverPlayerEntity.gameMode,
                                conn,
                                serverPlayerEntity.getUUID()
                        );

                        PlayerPartyStorage playerPartyStorages = wrapper.entity.getPlayerParty();
                        List<Pokemon> pokemons = playerPartyStorages.getTeam();

                        for (Pokemon pokemon : pokemons) {
                            if(checkLevelPokemonWithPlayer(pokemon, (PlayerLevel) playerLevel, serverPlayerEntity)) {
                                event.setCanceled(true);
                            }
                            System.out.println(pokemon.getDisplayName() + " " + event.attack);
                        }
                    } catch(SQLException e){
                        e.printStackTrace();
                    }
                }
        }
    }

    private boolean checkLevelPokemonWithPlayer(Pokemon pokemon, PlayerLevel playerLevel, ServerPlayerEntity serverPlayerEntity) {
        if (pokemon.getPokemonLevel() > playerLevel.getLevelPlayer()) {
            cancelEvent = true;
            if (pokemon.getHealth() != 0) {
                pokemon.setHealth(0);
                if (serverPlayerEntity.connection != null) {
                    serverPlayerEntity.sendMessage(new StringTextComponent(
                                    "O Pokémon " + pokemon.getDisplayName() + " não atendeu aos seus comandos e acabou se machucando. Seu nível: " + playerLevel.getLevelPlayer()),
                            serverPlayerEntity.getUUID()
                    );
                }
            }
            return true;
        } else {
            return false;
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
