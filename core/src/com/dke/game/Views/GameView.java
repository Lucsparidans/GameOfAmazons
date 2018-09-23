package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.DataStructs.Coordinate;
import com.dke.game.Models.DataStructs.Square;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Background;
import com.dke.game.Models.GraphicalModels.Board2D;
import com.dke.game.Models.GraphicalModels.UIOverlaySquare;

public class GameView extends View2D {

    private Board2D board2D;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Stage ui;
    private boolean displayUI;
    private UIOverlaySquare uiOverlaySquare;
    private Amazon2D queenW;
    private Amazon2D queenB;
    //private Viewport vp;
    private static BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));


    public GameView(ViewManager viewManager) {
        super(viewManager);

    }

    @Override
    public void create() {
        //vp = new ExtendViewport(50,50);
        stage = new Stage();
        stage.addActor(new Background());
        ui = new Stage();
        Gdx.input.setInputProcessor(new InputMultiplexer(ui, stage));
        shapeRenderer = new ShapeRenderer();
        board2D = new Board2D(shapeRenderer);
        displayUI = false;
        stage.addActor(board2D);
        uiOverlaySquare = new UIOverlaySquare(board2D);
        initBoard();
        queenW = new Amazon2D('W', shapeRenderer, boardCoordinates[2][0].getBottomLeft());

        queenB = new Amazon2D('B', shapeRenderer,boardCoordinates[9][5].getBottomLeft());
        stage.addActor(queenB);
        stage.addActor(queenW);

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
        ui.act(delta);
        ui.draw();
        handleInput();
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

    private void initBoard(){
        stage.act();
        stage.draw();
        boardCoordinates = board2D.getBoardCoordinates();
    }


    //<editor-fold desc="Click Feedback">


    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            uiOverlaySquare.addObject(new Coordinate(x, y));
            shapeRenderer.setColor(Color.ROYAL);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.circle(x, y, 10);
            shapeRenderer.end();
            drawPath();
            System.out.println(Boolean.toString(displayUI));

        }
    }

    private void drawPath() {
        if (!this.displayUI) {
            this.displayUI = true;

            this.ui.addActor(uiOverlaySquare);

        } else {
            this.displayUI = false;
            this.ui.clear();
        }

    }

    private Square[][] boardCoordinates;

    private Square findSquare(int x, int y) {
        if (this.boardCoordinates == null) {
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
