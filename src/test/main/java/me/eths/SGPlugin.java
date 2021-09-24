package me.eths;

import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import me.eths.game.GameManager;
import me.eths.hub.HubManager;
import me.eths.player.PlayerManager;
import me.eths.server.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class SGPlugin extends JavaPlugin {

    public static SGPlugin instance;
    public static Plugin plugin;

    private GameManager gameManager;
    private HubManager hubManager;
    private PlayerManager playerManager;
    private PluginManager pluginManager;
    private ServerManager serverManager;

    public void onEnable() {
        instance = this;
        plugin = this;

        gameManager = new GameManager();
        hubManager = new HubManager();
        playerManager = new PlayerManager();
        serverManager = new ServerManager();
        pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(playerManager, instance);
        pluginManager.registerEvents(serverManager, instance);

        new BukkitRunnable() {
            public void run() {
                tick();
            }
        }.runTaskTimer(this, 1, 1);
    }

    public void onDisable() {
        instance = null;

        gameManager = null;
        hubManager = null;
        playerManager = null;

    }

    private void tick() {

        hubManager.tick();
        gameManager.tick();

    }

}
