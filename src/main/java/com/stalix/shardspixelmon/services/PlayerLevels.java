package com.stalix.shardspixelmon.services;

import com.stalix.shardspixelmon.config.DatabaseConfig;
import com.stalix.shardspixelmon.database.DatabaseConnection;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerLevels {

    PlayerEntity player;
    int level = 5;
    DatabaseConfig config = DatabaseConfig.load("config/ModId/config.yml");
    DatabaseConnection dbConnection = new DatabaseConnection(config);

}
