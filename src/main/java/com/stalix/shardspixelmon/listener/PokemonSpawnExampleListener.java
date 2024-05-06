package com.stalix.shardspixelmon.listener;

import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.spawning.SpawnAction;
import com.pixelmonmod.pixelmon.api.spawning.SpawnLocation;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.pokemon.SpawnActionPokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import com.stalix.shardspixelmon.services.ModCapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PokemonSpawnExampleListener {

    int currentLevel;

    @SubscribeEvent
    public void onEntitySpawn(SpawnEvent event) {
        SpawnAction<? extends Entity> action = event.action;

        if (!(action instanceof SpawnActionPokemon)) {
            return;
        }

        PixelmonEntity pixelmon = ((SpawnActionPokemon)action).getOrCreateEntity();
        Pokemon pokemon = ((SpawnActionPokemon)action).pokemon;
        SpawnLocation location = ((SpawnActionPokemon) action).spawnLocation;
        if (location.cause instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) location.cause;
            player.getCapability(ModCapabilities.PLAYER_LEVEL_CAPABILITY).ifPresent(iLevelManager -> {
                    currentLevel = iLevelManager.getLevel(); });
            player.sendMessage(new StringTextComponent("Deu certo" + " nome do pokemon: " + pokemon.getDisplayName() + " nível do player " + currentLevel), player.getUUID());
        }



        //TODO: logic goes here
    }

}
