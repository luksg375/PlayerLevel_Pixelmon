package com.stalix.shardspixelmon.services;

import com.pixelmonmod.pixelmon.api.events.LevelUpEvent;
import com.stalix.shardspixelmon.capabilities.ILevelManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.api.distmarker.Dist.DEDICATED_SERVER;


@Mod.EventBusSubscriber(DEDICATED_SERVER)
public class EventLevelPlayer {

        @SubscribeEvent()
        public static void onAttachCapabilitiesEvent (AttachCapabilitiesEvent < Entity > event) {
        if (event.getObject() instanceof PlayerEntity) {
            System.out.println("parte 1");
            ICapabilityProvider provider = new ICapabilityProvider() {

                final LazyOptional<ILevelManager> instance = LazyOptional.of(PlayerLevel::new);

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    System.out.println("parte 2");
                    return cap == ModCapabilities.PLAYER_LEVEL_CAPABILITY ? instance.cast() : LazyOptional.empty();
                }
            };

            event.addCapability(new ResourceLocation("shardspixelmon", "level"), provider);
        }
    }

}
