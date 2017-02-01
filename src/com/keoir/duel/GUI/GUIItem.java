package com.keoir.duel.GUI;

import com.keoir.duel.Util.ItemUtil;
import net.minecraft.server.v1_9_R2.NBTTagString;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by keoir on 1/31/2017.
 */
public class GUIItem {

    private UUID uuid;
    private ItemStack stack;
    private Runnable onClick;

    public GUIItem(ItemStack stack, Runnable onClick) {
        this.stack = stack;
        this.onClick = onClick;
        this.uuid = UUID.randomUUID();
        assignUUID();
    }

    public GUIItem(ItemStack stack) {
        this.stack = stack;
        this.onClick = null;
        this.uuid = UUID.randomUUID();
        assignUUID();
    }

    private void assignUUID() {
        ItemStack newStack = ItemUtil.addTag(this.stack, "onClick", new NBTTagString(this.uuid.toString()));
        this.setStack(newStack);
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public Runnable getOnClick() {
        return onClick;
    }

    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    public UUID getUuid() {
        return uuid;
    }
}
