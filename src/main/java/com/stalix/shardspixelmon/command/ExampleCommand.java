package com.stalix.shardspixelmon.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stalix.shardspixelmon.capabilities.ILevelManager;
import com.stalix.shardspixelmon.services.ModCapabilities;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import com.stalix.shardspixelmon.ModFile;
import net.minecraft.util.text.TextComponent;

public class ExampleCommand {

    /**
     *
     * Used for registering the command on the {@link net.minecraftforge.event.RegisterCommandsEvent}
     *
     * For more information about brigadier, how it works, what things mean, and lots of examples please read the
     * GitHub READ ME here <a href="https://github.com/Mojang/brigadier/blob/master/README.md">URL</a>
     *
     * @param dispatcher The dispatcher from the event
     */
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource>literal("example")
                        .executes(context -> {
            CommandSource source = context.getSource();
            try {
                PlayerEntity player = source.getPlayerOrException();
                player.getCapability(ModCapabilities.PLAYER_LEVEL_CAPABILITY).ifPresent(ILevelManager::incrementLevel);
                source.sendSuccess(new StringTextComponent(ModFile.getConfig().getExampleField()), false); // Sends a message to the sender - if true it will broadcast to all ops (like how /op does)
                return 1;
            }
            catch (CommandSyntaxException e) {
                source.sendFailure(new StringTextComponent("Falhou"));
                return 0;
            }

        }));
    }
}

