package com.stalix.shardspixelmon.items.custom;

import com.stalix.shardspixelmon.Enums.ShardsType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public class Shard extends Item {

    private ShardsType type;

    private long lastUseTime = 0;
    public Shard(Properties properties, ShardsType type) {
        super(properties);
        this.type = type;
    }

    @Override
    @Nonnull
    public ActionResultType useOn (ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResultType.FAIL;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUseTime < 1000) {
            return ActionResultType.FAIL;
        }
        lastUseTime = currentTime;

        ItemStack itemInHand = player.getItemInHand(context.getHand());
        int count = itemInHand.getCount();

        if (count == 50) {
            player.sendMessage(new StringTextComponent("Count: "+ count), player.getUUID());
            return ActionResultType.PASS;
        } else {
            return ActionResultType.FAIL;
        }

    }

    public ShardsType getType() {
        return type;
    }
}
