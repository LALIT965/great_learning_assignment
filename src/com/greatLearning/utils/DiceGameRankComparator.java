package com.greatLearning.utils;

import com.greatLearning.model.Player;

import java.util.Comparator;

public class DiceGameRankComparator implements Comparator<Player> {

    @Override
    public int compare(Player firstPlayer, Player secondPlayer) {
        return firstPlayer.getRank() - secondPlayer.getRank();
    }
}
