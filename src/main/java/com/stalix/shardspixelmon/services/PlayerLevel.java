package com.stalix.shardspixelmon.services;

import com.stalix.shardspixelmon.capabilities.ILevelManager;

public class PlayerLevel implements ILevelManager {

    private int level = 5;


    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }

}
