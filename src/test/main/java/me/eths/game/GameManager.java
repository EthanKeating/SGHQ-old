package me.eths.game;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class GameManager {

    private ArrayList<Game> games;

    public GameManager() {
        games = new ArrayList<>();
    }

    public void tick() {
        for (Game game : games) {
            game.tick();
        }
    }

}
