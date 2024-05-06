package com.stalix.shardspixelmon.services;

import com.stalix.shardspixelmon.capabilities.ILevelManager;

import java.util.concurrent.Callable;

public class FactoryLevel implements Callable<ILevelManager> {
    @Override
    public ILevelManager call() throws Exception {
        return new PlayerLevel();
    }
}
