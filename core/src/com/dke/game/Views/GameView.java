package com.dke.game.Views;

import com.badlogic.gdx.Gdx;

import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.DataStructs.Board;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Board2D;

public class GameView extends View2D {

    private boolean first = true;
    private Board2D board2D;
    private Amazon2D amazon1;
    private Amazon2D amazon2;
    private char white = 'W';
    private Cell[][] board;

    public GameView(ViewManager viewManager) {
        super(viewManager);

    }

    @Override
    public void create() {
        board2D = new Board2D();
        Gdx.gl.glClearColor(0, 0, 1, 1);

        board = board2D.getBoard();
        placeAmazons(board);
        consoleRender();
    }

    public void placeAmazons(Cell[][] board) {
        amazon1 = new Amazon2D(white, 0, 3);
        amazon2 = new Amazon2D(white, 0, 6);
        board[0][3].occupy(amazon1);
        board[0][6].occupy(amazon2);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        if (first) {
            amazon1.move(8, 3, board);
            consoleRender();

            first = false;
        }
        board2D.draw();
    }

    public void consoleRender(){
        for(int i = 0;i<10;i++){
            for (int j = 0; j < 10; j++) {
                if(board[j][i].getContentID() == "This cell is empty") {
                    System.out.print("_ ");
                }
                else{
                    System.out.print("Q ");
                }
        }
        System.out.println();
    }
        System.out.print("----------------------------------------------------");
        System.out.println();
}
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
