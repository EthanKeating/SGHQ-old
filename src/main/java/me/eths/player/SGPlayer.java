package me.eths.player;

import lombok.Getter;
import lombok.Setter;
import me.eths.SGPlugin;
import me.eths.game.Game;
import me.eths.game.GameManager;
import me.eths.hub.HubManager;
import me.eths.utils.Color;
import me.eths.utils.scoreboard.FastBoard;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
public class SGPlayer {

    private final Player player;
    private final UUID uuid;
    private final FastBoard board;

    private final GameManager gameManager;
    private final HubManager hubManager;
    private FileConfiguration data;


    private long firstJoin;
    private int kills, deaths, wins, rating, coins;

    public SGPlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        board = new FastBoard(player);

        gameManager = SGPlugin.instance.getGameManager();
        hubManager = SGPlugin.instance.getHubManager();

        SGPlugin.instance.getHubManager().addPlayer(this);

        loadData();

    }

    public void delete() {
        if (player.isOnline()) {
            player.kickPlayer(Color.translate("&cYour profile has been deleted unexpectedly! Please rejoin"));
        }

        if (hubManager.getPlayers().contains(this)) {
            hubManager.removePlayer(this);
        }

        for (Game game : gameManager.getGames()) {
            if (game.getPlayers().contains(this)) {
                game.getPlayers().remove(this);
            }
        }

        saveData();

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void loadData() {
        final File dir = new File(SGPlugin.plugin.getDataFolder() + File.separator + "playerData");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File player = new File(dir, uuid + ".yml");
        if (!player.exists()) {
            try {
                player.createNewFile();
                loadData();
            } catch (final Exception ignored) { }

        } else {
            final YamlConfiguration load = YamlConfiguration.loadConfiguration(player);

            kills = load.getInt("Kills");
            deaths = load.getInt("Deaths");
            wins = load.getInt("Wins");
            rating = load.getInt("Rating");
            coins = load.getInt("Coins");
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveData() {
        File dir = new File(SGPlugin.plugin.getDataFolder() + File.separator + "playerData");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File player = new File(dir, uuid + ".yml");
        if (!player.exists()) {
            try {
                player.createNewFile();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        final YamlConfiguration save = YamlConfiguration.loadConfiguration(player);
        save.set("Kills", kills);
        save.set("Deaths", deaths);
        save.set("Wins", wins);
        save.set("coins", coins);
        save.set("rating", rating);

        try {
            save.save(player);
        } catch (Exception ignored) {

        }
    }

}
