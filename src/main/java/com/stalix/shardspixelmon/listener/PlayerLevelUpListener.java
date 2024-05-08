package com.stalix.shardspixelmon.listener;

import com.pixelmonmod.pixelmon.api.events.LevelUpEvent;
import com.stalix.shardspixelmon.database.DatabaseConnection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.sql.Connection;
import java.sql.SQLException;

@Mod.EventBusSubscriber
public class PlayerLevelUpListener {

    private int level;

    @SubscribeEvent
    public static void playerLevelUp(LevelUpEvent.Pre event) {
        PlayerEntity player = event.getPlayer();
        try (Connection conn = DatabaseConnection.getConnection()) {
            if ()
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        player.sendMessage(new StringTextComponent("upou o nivel do pokemon? " + event.getPokemon().getDisplayName()), player.getUUID());
    }

}
