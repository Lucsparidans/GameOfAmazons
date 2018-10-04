package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.dke.game.Controller.GameLoop;
import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Coordinate;
import com.dke.game.Models.DataStructs.Piece;
import com.dke.game.Models.GraphicalModels.*;

import java.util.ArrayList;

//TODO:
/*
- Move the gameLoop related parts to the gameLoop class, in order to respect the model view controller architecture.
- Restructure input handling to be able to provide the received input/info to the gameLoop where it will be precessed
    + This class can still do a bit of the pre-processing like finding the cell relative to the selected coordinates.
- Join the game logic with the graphics part.
- Setup working phase system.
    + Work out the foreseen complications.
-
 */
public class GameView extends View2D {


    public static final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private GameLoop gameLoop;
    private Stage stage;
    private Stage ui;
    private boolean displayUI;
    private UIOverlaySquare uiOverlaySquare;
    private Cell[][] boardCoordinates;
    private boolean displayOverlay = false;
    private Piece lastCell;
    private boolean amazonSelected = false;
    private Amazon2D selectedAmazon;
    private ArrayList<Actor> actors;
    private Board2D board2D;
    private Amazon2D[] amazons;
    private ArrayList<Arrow2D> arrow2DS;

    private static BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));


    public GameView(ViewManager viewManager, Board2D board2D, Cell[][] boardCoordinates, Amazon2D[] amazons, ArrayList<Arrow2D> arrow2Ds, GameLoop gameLoop) {
        super(viewManager);
        this.gameLoop = gameLoop;
        this.arrow2DS = arrow2Ds;
        this.boardCoordinates = boardCoordinates;
        this.board2D = board2D;
        this.amazons = amazons;
        uiOverlaySquare = new UIOverlaySquare(board2D, shapeRenderer);

    }

    @Override
    public void create() {
        stage = new Stage();
        stage.addActor(new Background());
        ui = new Stage();
        Gdx.input.setInputProcessor(new InputMultiplexer(ui, stage));
        displayUI = false;
        this.actors = new ArrayList<>();





        font.setColor(Color.BLACK);
        font.getData().setScale(1);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    }
    private void addActorsToStage(){
        Array<Actor> stageActors = stage.getActors();
        for (Actor actor: actors) {
            if(!stageActors.contains(actor,false)){
                stage.addActor(actor);
            }

        }
    }

    public void addActor(Actor actor){
        this.actors.add(actor);
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





    //<editor-fold desc="Click Feedback">



    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            Cell c = findCell(x, y);
            if (!amazonSelected) {

                if (!displayOverlay) {
                    ui.clear();
                }

                if (c != null) {
                    if (c.isOccupied()) {

                        Piece content = c.getContent();
                        if (content instanceof Amazon2D) {
                            amazonSelected = true;
                            selectedAmazon = (Amazon2D) content;
                            if (lastCell == null) {
                                lastCell = content;
                            }
                            if (lastCell == content) {
                                displayOverlay = false;
                            } else {
                                ui.clear();
                            }
                            displayOverlay = true;
                            ((Amazon2D) content).possibleMoves(board2D);
                            uiOverlaySquare = new UIOverlaySquare(((Amazon2D) content).getPossibleMoves(), board2D, shapeRenderer);
                            ui.addActor(uiOverlaySquare);
                        }
                    } else {
                        displayOverlay = false;
                        ui.clear();
                    }
                }
                if (displayOverlay) {
                    ui.addActor(uiOverlaySquare);
                }
            } else {
                if (c != null) {

                    Piece content = c.getContent();
                    if(content != null) {
                        if (content instanceof Amazon2D) {
                            ui.clear();
                            selectedAmazon = (Amazon2D) content;

                            displayOverlay = true;
                            selectedAmazon.possibleMoves(board2D);
                            uiOverlaySquare = new UIOverlaySquare(selectedAmazon.getPossibleMoves(), board2D, shapeRenderer);
                            ui.addActor(uiOverlaySquare);
                        }
                    }
                }


                ArrayList<Cell> pm = selectedAmazon.getPossibleMoves();
                for (Cell cC : pm) {
                    if (cC.getI() == c.getI() && cC.getJ() == c.getJ()) {
                        selectedAmazon.move(cC);
                        selectedAmazon = null;
                        amazonSelected = false;
                        ui.clear();
                    }

                }


            }
        }

        //This is temporary-----------------------------------------
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                Gdx.app.exit();
            }
            //----------------------------------------------------------
        }



    private Cell findCell(int x, int y) {
        //Return the center of the selected square.
        for (int i = 0; i < boardCoordinates.length; i++) {
            for (int j = 0; j < boardCoordinates[i].length; j++) {
                if (boardCoordinates[i][j].getTopLeft().getX() < x && x < boardCoordinates[i][j].getTopRight().getX()) {
                    if (boardCoordinates[i][j].getTopLeft().getY() > y && y > boardCoordinates[i][j].getBottomLeft().getY()) {

                        return boardCoordinates[i][j];

                    }
                }
            }
        }
        return null;

    }


    private Coordinate getCellCenter(Cell cell) {
        Cell s = boardCoordinates[cell.getI()][cell.getJ()];
        return new Coordinate(s.getBottomLeft().getX() + ((s.getBottomRight().getX() - s.getBottomLeft().getX()) / 2),
                s.getBottomLeft().getY() + ((s.getTopLeft().getY() - s.getBottomLeft().getY()) / 2));
    }

//</editor-fold>

}
