package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.DataStructs.Square;
import com.dke.game.Models.GraphicalModels.Board2D;

public class GameView extends View2D {

    private Board2D board2D;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Stage ui;
    //private Viewport vp;
    private static BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));


    public GameView(ViewManager viewManager) {
        super(viewManager);

    }

    @Override
    public void create() {
        //vp = new ExtendViewport(50,50);
        stage = new Stage();
        ui = new Stage();
        Gdx.input.setInputProcessor(new InputMultiplexer(ui,stage));
        shapeRenderer = new ShapeRenderer();
        board2D = new Board2D(shapeRenderer);

        stage.addActor(board2D);

        font.setColor(Color.BLACK);
        font.getData().setScale(1);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            ShapeRenderer r =new ShapeRenderer();
            r.setColor(Color.OLIVE);
            r.begin(ShapeRenderer.ShapeType.Filled);
            r.circle(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY(),10);
            r.end();
        }

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

//    findSquare(e.getX(), e.getY());
//        ui.addActor(new UIOverlaySquare());
//        ui.act(Gdx.graphics.getDeltaTime());
//        ui.draw();


    //<editor-fold desc="MouseListener methods">




    private Square[][] boardCoordinates;

    private Square findSquare(int x, int y) {
        if(this.boardCoordinates == null){
            this.boardCoordinates = board2D.getBoardCoordinates();
        }
        for (int i = 0; i < boardCoordinates.length; i++) {
            for (int j = 0; j < boardCoordinates[i].length; j++) {
                if (boardCoordinates[i][j].getTopLeft().getX() > x && x > boardCoordinates[i][j].getTopRight().getX()) {
                    if (boardCoordinates[i][j].getTopLeft().getY() > y && y > boardCoordinates[i][j].getBottomLeft().getY()) {
                        return boardCoordinates[i][j];
                    }
                }
            }
        }
        return null;
    }

//</editor-fold>
}
