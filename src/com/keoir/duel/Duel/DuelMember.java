package com.keoir.duel.Duel;

import org.bukkit.entity.Player;

/**
 * Created by keoir on 1/30/2017.
 */
public class DuelMember {

    private Player player;
    private double startingHealth;

    public DuelMember(Player player) {
        this.player = player;
        this.startingHealth = this.player.getHealth();
    }

    /**
     * Restore Health of a player to the startingHealth
     */
    public void restoreHealth() {
        this.player.setHealth(this.startingHealth);
    }

    public Player getPlayer() {
        return player;
    }

    public double getStartingHealth() {
        return startingHealth;
    }
}
