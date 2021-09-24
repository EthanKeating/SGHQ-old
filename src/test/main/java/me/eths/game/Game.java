package me.eths.game;

import lombok.Getter;
import me.eths.player.SGPlayer;

import java.util.ArrayList;

@Getter
public class Game {

    private ArrayList<SGPlayer> players;

    public Game() {
        players = new ArrayList<>();
    }

    public void tick() {

    }


}
