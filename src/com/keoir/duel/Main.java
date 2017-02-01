package com.keoir.duel;

import com.keoir.duel.Duel.DuelListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by keoir on 1/30/2017.
 */
public class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        new DuelListener();
    }

    @Override
    public void onDisable() {

    }
}
