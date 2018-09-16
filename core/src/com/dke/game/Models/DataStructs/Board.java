package com.dke.game.Models.DataStructs;

public abstract class Board {
    protected final int height = 10;
    protected final int width = 10;
    protected Cell[][] board = new Cell[height][width];
    protected void createBoard(){
        for(int i =0; i < this.height; i++){
            for(int j = 0; j < this.width; j++){
                board[i][j] = new Cell();
            }
        }
    }
    //TODO
}
