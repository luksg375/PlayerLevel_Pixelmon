package com.stalix.shardspixelmon.database;

import com.stalix.shardspixelmon.config.DatabaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static HikariDataSource dataSource;

    static DatabaseConfig dbConfig;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbConfig.getUrl());
        config.setUsername(dbConfig.getUsername());
        config.setPassword(dbConfig.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setDriverClassName(dbConfig.getDriver());

        dataSource = new HikariDataSource(config);
    }



   /* public DatabaseConnection(DatabaseConfig config) {
        this.config = config;
        try {
            Class.forName(config.getDriver());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    } */

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
