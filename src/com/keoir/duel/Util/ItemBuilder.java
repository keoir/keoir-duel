package com.keoir.duel.Util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by keoir on 1/15/2017.
 */
public class ItemBuilder {

    private String name;
    private Material material;
    private byte subType;
    private int count;
    private ArrayList<String> lore = new ArrayList<>();

    private ItemStack stack;

    public ItemBuilder(String name, Material material, byte subType, int count) {
        this.name = name;
        this.material = material;
        this.subType = subType;
        this.count = count;

        build();
    }

    public ItemBuilder(String name, Material material) {
        this.name = name;
        this.material = material;
        this.subType = (byte) 0;
        this.count = 1;

        build();
    }

    public ItemBuilder(String name, Material material, int count) {
        this.name = name;
        this.material = material;
        this.count = count;
        this.subType = (byte) 0;

        build();
    }

    public ItemBuilder(String name, Material material, byte subType) {
        this.name = name;
        this.material = material;
        this.count = 1;
        this.subType = subType;

        build();
    }

    public ItemBuilder(byte subType, Material material, String name) {
        this.subType = subType;
        this.material = material;
        this.name = name;
        this.count = 1;

        build();
    }

    public void build() {
        ItemStack item = new ItemStack(this.material, this.count);
        MaterialData data = item.getData();
        data.setData(this.subType);
        item.setData(data);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.name);

        item.setItemMeta(meta);

        this.stack = item;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        ItemStack item = this.stack;
        ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(flag);

        item.setItemMeta(meta);

        this.stack = item;

        return this;
    }

    public ItemBuilder addLore(String lore) {
        ItemStack item = this.stack;
        ItemMeta meta = item.getItemMeta();

        this.lore.add(lore);
        meta.setLore(this.lore);
        item.setItemMeta(meta);

        this.stack = item;

        return this;
    }

    public ItemBuilder setDisplayName(String string) {
        ItemStack item = this.stack;
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(string);

        item.setItemMeta(meta);

        this.stack = item;

        return this;
    }

    public ItemStack get() {
        return this.stack;
    }

    public ItemBuilder addEnchatment(Enchantment enchantment, int level) {
        ItemStack item = this.stack;
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(enchantment, level, true);

        item.setItemMeta(meta);

        this.stack = item;

        return this;
    }
}
