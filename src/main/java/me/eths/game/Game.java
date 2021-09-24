package me.eths.game;

import lombok.Getter;
import me.eths.player.SGPlayer;
import me.eths.utils.Color;

import java.util.ArrayList;

@Getter
public class Game {

    private ArrayList<SGPlayer> players;
    private GameState state;

    public Game() {
        players = new ArrayList<>();
        state = GameState.LOBBY;
    }

    public void tick() {
        updateBoard();
        if (state == GameState.GAME) {

        }
    }

    private void updateBoard() {
        String title = Color.translate("&6&lSGHQ &7┃ &fHub");
        ArrayList<String> lines = new ArrayList<>();

        for (SGPlayer player : players) {

            lines.clear();

            lines.add(Color.translate("&7&m------------------"));
            lines.add(Color.translate("&6Starting in: &f0"));
            lines.add(Color.translate("&6Players: &f1/24"));
            lines.add(Color.translate("&6Map Pool:"));
            lines.add(Color.translate("» Zone 85 (1)"));
            lines.add(Color.translate("» Par 72 (1)"));
            lines.add(Color.translate("» SG Adrenaline (10)"));
            lines.add(Color.translate(""));
            lines.add(Color.translate("&ewww.sghq.us"));
            lines.add(Color.translate("&7&m------------------"));

            player.getBoard().updateTitle(title);
            player.getBoard().updateLines(lines);
        }
    }


}
