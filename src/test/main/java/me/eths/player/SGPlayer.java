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

    private Player player;
    private UUID uuid;
    private FastBoard board;

    private GameManager gameManager;
    private HubManager hubManager;
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

    private void loadData() {
        File file = new File(SGPlugin.instance.getDataFolder() + File.separator + "playerData" + File.separator + uuid.toString() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                data = YamlConfiguration.loadConfiguration(file);
                firstJoin = System.currentTimeMillis();
                kills = 0;
                deaths = 0;
                wins = 0;
                rating = 1000;
                coins = 100;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            data = YamlConfiguration.loadConfiguration(file);
            firstJoin = data.getLong("Stats.FirstJoin");
            kills = data.getInt("Stats.Kills");
            deaths = data.getInt("Stats.Deaths");
            wins = data.getInt("Stats.Wins");
            coins = data.getInt("Stats.Coins");
            rating = data.getInt("Stats.Rating");
        }
    }

    public void saveData() {
        File file = new File(SGPlugin.instance.getDataFolder() + File.separator + "playerData" + File.separator + uuid.toString() + ".yml");
        data.set("Stats.Kills", kills);
        data.set("Stats.Deaths", deaths);
        data.set("Stats.Wins", wins);
        data.set("Stats.Coins", coins);
        data.set("Stats.Rating", rating);
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        gameManager = null;
        hubManager = null;

        player = null;
        board = null;

        saveData();

    }

    public void refresh() {

    }

}
