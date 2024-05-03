package com.stalix.shardspixelmon.services;

import com.stalix.shardspixelmon.capabilities.ILevelManager;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class PlayerLevelStorage implements Capability.IStorage<ILevelManager> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<ILevelManager> capability, ILevelManager instance, Direction side) {
        return IntNBT.valueOf(instance.getLevel());
    }

    @Override
    public void readNBT(Capability<ILevelManager> capability, ILevelManager instance, Direction side, INBT inbt) {
        instance.setLevel(((IntNBT) inbt).getAsInt());
    }
}
