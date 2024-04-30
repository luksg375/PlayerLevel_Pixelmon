package com.stalix.shardspixelmon.items;

import com.stalix.shardspixelmon.Enums.ShardsType;
import com.stalix.shardspixelmon.ModFile;
import com.stalix.shardspixelmon.items.custom.Shard;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Items {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ModFile.MOD_ID);

    public static final RegistryObject<Item> FIRE_SHARD = ITEMS.register("fire_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> WATER_SHARD = ITEMS.register("water_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> GRASS_SHARD = ITEMS.register("grass_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> STEEL_SHARD = ITEMS.register("steel_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> ROCK_SHARD = ITEMS.register("rock_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> NORMAL_SHARD = ITEMS.register("normal_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> ELECTRIC_SHARD = ITEMS.register("electric_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> ICE_SHARD = ITEMS.register("ice_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> FIGHTING_SHARD = ITEMS.register("fighting_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> POISON_SHARD = ITEMS.register("poison_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> GROUND_SHARD = ITEMS.register("ground_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> FLYING_SHARD = ITEMS.register("flying_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> PSYCHIC_SHARD = ITEMS.register("psychic_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> BUG_SHARD = ITEMS.register("bug_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> GHOST_SHARD = ITEMS.register("ghost_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> DRAGON_SHARD = ITEMS.register("dragon_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> DARK_SHARD = ITEMS.register("dark_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static final RegistryObject<Item> FAIRY_SHARD = ITEMS.register("fairy_shard", () -> new Shard(new Item.Properties().rarity(Rarity.RARE), ShardsType.FIRE));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
