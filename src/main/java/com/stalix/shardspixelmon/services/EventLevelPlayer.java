package com.stalix.shardspixelmon.services;

import com.pixelmonmod.pixelmon.api.events.EvolveEvent;
import com.pixelmonmod.pixelmon.api.events.LevelUpEvent;
import com.stalix.shardspixelmon.capabilities.ILevelManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


@Mod.EventBusSubscriber(value = Dist.DEDICATED_SERVER)
public class EventLevelPlayer {
    @SubscribeEvent
    public static void levelUpEvent(LevelUpEvent event) {

    }

    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ILevelManager.class, new PlayerLevelStorage(), PlayerLevel::new);
    }


    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof PlayerEntity) {
            PlayerLevel levelCapability = new PlayerLevel();
            ICapabilityProvider provider = new ICapabilityProvider() {

               private final LazyOptional<ILevelManager> instance = LazyOptional.of(() -> levelCapability);

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    return cap == ModCapabilities.PLAYER_LEVEL_CAPABILITY ? instance : LazyOptional.empty();
                }
            };
            event.addCapability(new ResourceLocation("shardspixelmon", "level"), provider);
        }
    }
}
