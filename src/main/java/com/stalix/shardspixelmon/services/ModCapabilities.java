package com.stalix.shardspixelmon.services;

import com.stalix.shardspixelmon.capabilities.ILevelManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCapabilities {
    public static Capability<ILevelManager> PLAYER_LEVEL_CAPABILITY;

    private static void registerCapabilites() {
        CapabilityManager.INSTANCE.register(ILevelManager.class, new PlayerLevelStorage(), PlayerLevel::new);
    }
}
