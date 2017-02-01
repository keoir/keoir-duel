package com.keoir.duel.GUI;

import com.keoir.duel.Main;
import com.keoir.duel.Util.ItemUtil;
import net.minecraft.server.v1_9_R2.NBTBase;
import net.minecraft.server.v1_9_R2.NBTTagString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by keoir on 1/31/2017.
 */
public class GUI implements Listener {
    private String name;
    private int size;
    private Player currentPlayer;
    private Inventory inventory;
    static HashMap<UUID, Runnable> runnableHashMap = new HashMap<>();
    static HashMap<UUID, Inventory> inInventory = new HashMap<>();

    public GUI(String name, int size) {
        this.name = name;
        this.size = size;
        this.inventory = Bukkit.createInventory(new InventoryHolder() {
            @Override
            public Inventory getInventory() {
                return null;
            }
        }, this.size, this.name);

        Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addItem(GUIItem item) {
        if (item.getOnClick() != null) {
            runnableHashMap.put(item.getUuid(), item.getOnClick());
        }

        inventory.addItem(item.getStack());
    }

    public void setItem(GUIItem item, int pos) {
        if (item.getOnClick() != null) {
            runnableHashMap.put(item.getUuid(), item.getOnClick());
        }

        inventory.setItem(pos, item.getStack());
    }

    public void fillItem(GUIItem item, int... spaces) {
        for (int space : spaces) {
            setItem(item, space);
        }
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
        this.currentPlayer = player;
        inInventory.put(player.getUniqueId(), this.inventory);
    }

    public void close() {
        this.currentPlayer.closeInventory();
    }

    @EventHandler
    public void playerClickInInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (player.isOnline() && inInventory.containsKey(player.getUniqueId()) && event.getInventory().getName() == inInventory.get(player.getUniqueId()).getName()) {
            NBTBase itemUUID = ItemUtil.getTag(event.getCurrentItem(), "onClick");
            if (itemUUID != null) {
                NBTTagString nbtString = (NBTTagString) itemUUID;
                UUID uuid = UUID.fromString(nbtString.a_());
                if (runnableHashMap.containsKey(uuid)) {
                    Runnable run = runnableHashMap.get(uuid);
                    run.run();
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void playerCloseInventoryEvent(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (inInventory.containsKey(player.getUniqueId())) {
            inInventory.remove(player.getUniqueId());
            HandlerList.unregisterAll(this);
        }
    }
}
