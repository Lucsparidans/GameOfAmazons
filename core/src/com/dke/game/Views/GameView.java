package com.dke.game.Views;

import com.badlogic.gdx.Gdx;


import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.GraphicalModels.Board2D;

public class GameView extends View2D {

    private Board2D board2D;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Stage stage;


    public GameView(ViewManager viewManager) {
        super(viewManager);

    }

    @Override
    public void create() {
        stage = new Stage(new ExtendViewport(100,100));
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        board2D = new Board2D();
        stage.addActor(board2D);
        Gdx.gl.glClearColor(0,0,1,1);
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
