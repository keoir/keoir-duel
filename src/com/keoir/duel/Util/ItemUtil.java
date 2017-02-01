package com.keoir.duel.Util;

import net.minecraft.server.v1_9_R2.NBTBase;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Created by keoir on 1/24/2017.
 */
public class ItemUtil {

    public static ItemStack addTag(ItemStack stack, String tagName, NBTBase base) {
        net.minecraft.server.v1_9_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound comb = new NBTTagCompound();
        nmsStack.save(comb);

        if (!comb.hasKey("tag")) {
            comb.set("tag", new NBTTagCompound());
        }

        comb.getCompound("tag").set(tagName, base);

        nmsStack.c(comb);

        stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));

        return stack;
    }

    public static NBTBase getTag(ItemStack stack, String tagName) {
        NBTTagCompound comb = new NBTTagCompound();
        net.minecraft.server.v1_9_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

        if (nmsStack == null) return null;

        nmsStack.save(comb);
        if (!comb.hasKey("tag")) return null;

        if (!comb.getCompound("tag").hasKey(tagName)) return null;

        return comb.getCompound("tag").get(tagName);
    }

}
