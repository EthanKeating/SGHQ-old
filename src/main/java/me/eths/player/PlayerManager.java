package me.eths.player;

import me.eths.SGPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerManager implements Listener {

    private HashMap<Player, SGPlayer> profiles;

    public PlayerManager() {

        profiles = new HashMap<>();

        for(Player player : Bukkit.getOnlinePlayers()) {
            createProfile(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        Player player = e.getPlayer();

        createProfile(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage("");
        Player player = e.getPlayer();

        deleteProfile(player);

    }

    public void createProfile(Player player) {
        if (profiles.containsKey(player)) {
            deleteProfile(player);
        }

        SGPlayer profile = new SGPlayer(player);

        profiles.put(player, profile);
    }

    public void deleteProfile(Player player) {
        if (profiles.containsKey(player)) {
            profiles.get(player).delete();
            profiles.remove(player);
        }

    }

    public SGPlayer getProfile(Player player) {
        return profiles.get(player);
    }

}
