package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Board2D;

public class GameView extends View2D {

    private boolean first = true;
    private Board2D board2D;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Viewport vp;

    private Amazon2D amazon0;
    private Amazon2D amazon1;
    private Amazon2D amazon2;
    private Amazon2D amazon3;
    private Amazon2D amazon4;
    private Amazon2D amazon5;
    private Amazon2D amazon6;
    private Amazon2D amazon7;
    private char white = 'W';
    private char black = 'B';
    private Cell[][] board;

    public GameView(ViewManager viewManager) {
        super(viewManager);

    }

    @Override
    public void create() {
        vp = new ExtendViewport(100,100);
        stage = new Stage(vp);
        Gdx.input.setInputProcessor(stage);
        shapeRenderer = new ShapeRenderer();
        board2D = new Board2D(shapeRenderer);
        board = board2D.getBoard();
        stage.addActor(board2D);
        placeAmazons(board);
        consoleRender();

    }
    public void placeAmazons(Cell[][] board) {
        amazon0 = new Amazon2D(white, 9, 6, shapeRenderer);
        amazon1 = new Amazon2D(black, 0, 3, shapeRenderer);
        amazon2 = new Amazon2D(white, 0, 6, shapeRenderer);
        amazon3 = new Amazon2D(black, 3, 0, shapeRenderer);
        amazon4 = new Amazon2D(white, 3, 9, shapeRenderer);
        amazon5 = new Amazon2D(black, 6, 0, shapeRenderer);
        amazon6 = new Amazon2D(white, 6, 9, shapeRenderer);
        amazon7 = new Amazon2D(black, 9, 3, shapeRenderer);

        board[9][6].occupy(amazon0);
        board[0][6].occupy(amazon2);
        board[3][9].occupy(amazon4);
        board[6][9].occupy(amazon6);

        board[0][3].occupy(amazon1);
        board[3][0].occupy(amazon3);
        board[6][0].occupy(amazon5);
        board[9][3].occupy(amazon7);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (first) {
            amazon1.move(2, 3,board);
            amazon1.shoot(0, 2, board);
            amazon1.shoot(1, 2, board);
            amazon1.shoot(3, 2, board);
            amazon1.shoot(2, 2, board);
            amazon1.shoot(3, 3, board);
            amazon1.shoot(3, 4, board);
            amazon1.shoot(3, 5, board);
            amazon1.shoot(2, 5, board);
            amazon1.shoot(0, 5, board);
            amazon1.shoot(8, 5, board);
            amazon1.shoot(7, 5, board);
            amazon1.shoot(8, 7, board);
            amazon1.shoot(7, 7, board);
            amazon1.shoot(8, 6, board);
            consoleRender();

            if(!amazon1.endMe(board)){
                System.out.println("not isolated");
            }
            else {
                System.out.println("isolated");
            }
            first = false;
        }

        stage.act(delta);
        stage.draw();




    }

    public void consoleRender(){
        for(int i = 0;i<10;i++){
            for (int j = 0; j < 10; j++) {
                if(board[j][i].getContentID().equals("This cell is empty")) {
                    System.out.print("_ ");
                }
                else if(board[j][i].getContentID().contains("Amazon")){
                    System.out.print("Q ");
                }
                else{
                    System.out.print("x ");
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
        stage.dispose();
    }

}
