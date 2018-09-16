package com.dke.game.Views;

import com.badlogic.gdx.Gdx;

import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.DataStructs.Board;
import com.dke.game.Models.GraphicalModels.Board2D;

public class GameView extends View2D {

    private Board2D board2D;


    public GameView(ViewManager viewManager) {
        super(viewManager);

    }

    @Override
    public void create() {

        board2D = new Board2D();
        Gdx.gl.glClearColor(0,0,1,1);
        Board board = new Board();
        board.SetAmazonsForStart();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        board2D.draw();
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
