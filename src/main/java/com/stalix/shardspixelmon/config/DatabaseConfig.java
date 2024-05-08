 package com.stalix.shardspixelmon.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseConfig {
    private String url;
    private String username;
    private String password;
    private String driver;

    public static DatabaseConfig load(String configPath) {
        try (InputStream inputStream = Files.newInputStream(Paths.get(configPath))) {
            if (inputStream == null ) {
                System.out.println("falha ao carregar o arquivo");
                return null;
            }
            Yaml yaml = new Yaml(new Constructor(DatabaseConfig.class));
            System.out.println("arquivo carregado");
            return yaml.load(inputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
