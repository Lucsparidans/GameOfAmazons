package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.DataStructs.Board;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Board2D;

public class GameView extends View2D {

    private boolean first = true;
    private Board2D board2D;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Viewport vp;


    private Amazon2D amazon1;
    private Amazon2D amazon2;
    private char white = 'W';
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
        consoleRender();
        stage.addActor(board2D);
        placeAmazons(board);


    }
    public void placeAmazons(Cell[][] board) {
        amazon1 = new Amazon2D(white, 0, 3, shapeRenderer);
        amazon2 = new Amazon2D(white, 0, 6, shapeRenderer);
        board[0][3].occupy(amazon1);
        board[0][6].occupy(amazon2);
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();




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
        stage.dispose();
    }

}
