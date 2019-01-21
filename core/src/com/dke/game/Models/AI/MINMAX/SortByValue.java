package com.dke.game.Models.AI.MINMAX;

import java.util.Comparator;

public class SortByValue implements Comparator<MoveNode> {

    public int compare(MoveNode a, MoveNode b) {
        if (a.getValue() == b.getValue()) {
            return 0;
        } else if (a.getValue() > b.getValue()) {
            return 1;
        } else {
            return -1;
        }
    }
}
