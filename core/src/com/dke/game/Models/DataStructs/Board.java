package com.dke.game.Models.DataStructs;

public abstract class Board {
    protected final int height = 10;
    protected final int width = 10;
    protected Cell[][] board = new Cell[height][width];
}
