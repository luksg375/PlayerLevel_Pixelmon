package com.stalix.shardspixelmon.database;

import com.stalix.shardspixelmon.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private DatabaseConfig config = DatabaseConfig.load("config/ModId/config.yml");

    public DatabaseConnection() {
        this.config = config;
        try {
            Class.forName(config.getDriver());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
    }
}
