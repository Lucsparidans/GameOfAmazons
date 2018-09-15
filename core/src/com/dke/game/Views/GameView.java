package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.GraphicalModels.Board2D;

public class GameView extends View2D {

    private Board2D board2D;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Viewport vp;



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

        stage.addActor(board2D);


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
