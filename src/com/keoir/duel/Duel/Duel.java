package com.keoir.duel.Duel;

import com.keoir.duel.GUI.GUI;
import com.keoir.duel.GUI.GUIItem;
import com.keoir.duel.Main;
import com.keoir.duel.Util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by keoir on 1/30/2017.
 */
public class Duel {

    /*
    * List of Current Duels
    * */
    static ArrayList<Duel> duels = new ArrayList<>();

    private DuelMember member1;
    private DuelMember member2;
    private int starting;

    public Duel(DuelMember member1, DuelMember member2) {
        this.member1 = member1;
        this.member2 = member2;
    }

    /*
    * Check if player is current in a duel
    * */
    public static boolean playerInDuel(Player player) {
        return getCurrentDuel(player) != null;
    }

    /*
    * Get the current duel the player is in
    * */
    public static Duel getCurrentDuel(Player player) {
        if (duels.size() > 0) {
            for (Duel duel : duels) {
                if (player.getUniqueId() == duel.getMember1().getPlayer().getUniqueId()) {
                    return duel;
                } else if (player.getUniqueId() == duel.getMember2().getPlayer().getUniqueId()) {
                    return duel;
                }
            }
        }

        return null;
    }

    /*
    * Get the related DuelMember object from a player object
    * */
    public DuelMember getMember(Player player) {
        if (this.getMember1().getPlayer().getUniqueId() == player.getUniqueId()) {
            return this.getMember1();
        } else if (this.getMember2().getPlayer().getUniqueId() == player.getUniqueId()) {
            return this.getMember2();
        }

        return null;
    }

    /*
    * Start a duel
    * */
    public void start() {
        /*
        * Send Message to Start Duel to both players
        * */

        Duel currentDuel = this;

        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {

                Player member1 = getMember1().getPlayer();
                Player member2 = getMember2().getPlayer();

                member1.sendMessage("Duel Starting with " + getMember2().getPlayer().getName());
                member2.sendMessage("Duel Starting with " + getMember1().getPlayer().getName());

                duels.add(currentDuel);

                member1.playSound(member1.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                member2.playSound(member2.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
            }
        }, 20);
    }

    /*
    * Request the Duel
    * */
    public void request() {
        /*
        * Make Request GUI
        * */
        GUI gui = new GUI("Duel Request", 9);

        ItemStack accept = new ItemBuilder(ChatColor.BOLD + "" + ChatColor.GREEN + "ACCEPT", Material.WOOL, (byte) 5).get();
        ItemStack deny = new ItemBuilder(ChatColor.BOLD + "" + ChatColor.RED + "DENY", Material.WOOL, (byte) 14).get();

        Duel currentDuel = this;

        gui.setItem(new GUIItem(accept, new Runnable() {
            @Override
            public void run() {
                currentDuel.start();
                gui.close();
            }
        }), 2);

        gui.setItem(new GUIItem(deny, new Runnable() {
            @Override
            public void run() {
                currentDuel.sendMessage("Duel Rejected :(");
                gui.close();
            }
        }), 6);

        gui.open(this.member2.getPlayer());
    }

    /*
    * Stop a duel
    * */
    public void stop() {
        Player member1 = this.member1.getPlayer();
        Player member2 = this.member2.getPlayer();

        DuelMember winner = getWinner();

        if (winner != null) {
            member1.sendMessage("Duel Complete! The Winner is " + winner.getPlayer().getName() + "!");
            member2.sendMessage("Duel Complete! The Winner is " + winner.getPlayer().getName() + "!");
        } else {
            member1.sendMessage("Duel Complete! You both tied... best of luck next time!");
            member2.sendMessage("Duel Complete! You both tied... best of luck next time!");
        }

        this.member1.restoreHealth();
        this.member2.restoreHealth();

        member1.playSound(member1.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        member2.playSound(member1.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        duels.remove(this);
    }

    /*
    * Send Duel Memebrs a message
    * */
    public void sendMessage(String message) {
        this.member1.getPlayer().sendMessage(message);
        this.member2.getPlayer().sendMessage(message);
    }

    /*
    * Get the winner by looking at their health levels
    * */
    public DuelMember getWinner() {
        double health1 = this.getMember1().getPlayer().getHealth();
        double health2 = this.getMember2().getPlayer().getHealth();

        if (health1 > health2) {
            return this.member1;
        } else if (health2 > health1) {
            return this.member2;
        } else {
            return null;
        }
    }

    public DuelMember getMember1() {
        return member1;
    }

    public DuelMember getMember2() {
        return member2;
    }
}
