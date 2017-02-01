package com.keoir.duel.Duel;

import com.keoir.duel.GUI.GUI;
import com.keoir.duel.GUI.GUIItem;
import com.keoir.duel.Main;
import com.keoir.duel.Util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Created by keoir on 1/31/2017.
 */
public class DuelListener implements Listener {

    public DuelListener() {
        Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    /*
    * Watch for a player to "interact" with another w/ Right Click
    * */
    @EventHandler
    public void playerInteractEvent(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            GUI gui = new GUI(((Player) event.getRightClicked()).getName() + " Interaction Menu", 9);

            ItemStack startPvP = new ItemBuilder(ChatColor.GREEN + "" + ChatColor.BOLD + "Duel Player", Material.DIAMOND_SWORD, 1)
                    .addEnchatment(Enchantment.LURE, 1)
                    .addFlag(ItemFlag.HIDE_ENCHANTS).addFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .get();

            GUIItem guiItem = new GUIItem(startPvP, new Runnable() {
                @Override
                public void run() {
                    Duel duel = new Duel(new DuelMember(event.getPlayer()), new DuelMember((Player) event.getRightClicked()));
                    event.getPlayer().closeInventory();
                    duel.request();
                }
            });

            // Check if they are currently in a duel or not
            if (!Duel.playerInDuel(event.getPlayer())) {
                gui.addItem(guiItem);
            }

            gui.open(event.getPlayer());
        }
    }

    /*
    * Watch for players in duels to die, if so end duel and announce winner.
    * */
    @EventHandler
    public void playerDieEvent(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        if (Duel.playerInDuel(player)) {
            Duel duel = Duel.getCurrentDuel(player);
            assert duel != null;
            duel.stop();
        }
    }

    /*
    * Make sure members in duels can only attack each other.
    * */
    @EventHandler(priority = EventPriority.HIGH)
    public void playerDamageEvent(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
            Player attacked = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();

            if (!Duel.playerInDuel(attacked) || !Duel.playerInDuel(attacker)) {
                event.setCancelled(true);
            }
        }
    }

}
