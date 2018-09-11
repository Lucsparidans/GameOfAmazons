package com.dke.game.Models.DataStructs;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Board extends Actor {
    protected final int height = 10;
    protected final int width = 10;
    protected Cell[][] board = new Cell[height][width];
}
