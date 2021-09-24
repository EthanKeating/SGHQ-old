package me.eths.hub;

import lombok.Getter;
import me.eths.player.SGPlayer;
import me.eths.utils.Color;

import java.util.ArrayList;

@Getter
public class HubManager {

    private ArrayList<SGPlayer> players;

    public HubManager() {
        players = new ArrayList<>();
    }

    public void addPlayer(SGPlayer player) {
        if (players.contains(player)) { removePlayer(player); }

        players.add(player);
    }

    public void removePlayer(SGPlayer player) {
        players.remove(player);
    }


    public void tick() {

        String title = Color.translate("&6&lSGHQ &7┃ &fHub");
        ArrayList<String> lines = new ArrayList<>();

        lines.add(Color.translate("&7&m------------------"));
        lines.add(Color.translate("&6Online: &f0"));
        lines.add(Color.translate("&6In Queue: &f0"));
        lines.add(Color.translate("&6In Game: &f0"));
        lines.add(Color.translate(""));
        lines.add(Color.translate("&ewww.sghq.us"));
        lines.add(Color.translate("&7&m------------------"));

        for (SGPlayer player : players) {
            player.getBoard().updateTitle(title);
            player.getBoard().updateLines(lines);
        }
    }



}
