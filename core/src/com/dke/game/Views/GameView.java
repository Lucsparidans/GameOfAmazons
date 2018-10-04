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
import com.dke.game.Controller.GameLoop;
import com.dke.game.Controller.MainLoop;
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

    private Cell[][] boardCoordinates;
    private boolean first = true;
    private boolean turnStart = true;
    private Board2D board2D;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Stage ui;
    private boolean displayUI;
    private UIOverlaySquare uiOverlaySquare;
    private Amazon2D[] amazons;
    private Arrow2D arrow;
    private boolean whiteTurn = true;
    private boolean displayOverlay = false;
    private Piece lastCell;
    private boolean amazonSelected = false;
    private Amazon2D selectedAmazon;
    private static final GameLoop gameLoop = MainLoop.gameLoop;

    private static BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));


    public GameView(ViewManager viewManager) {
        super(viewManager);

    }

    @Override
    public void create() {
        stage = new Stage();
        stage.addActor(new Background());
        ui = new Stage();
        Gdx.input.setInputProcessor(new InputMultiplexer(ui, stage));
        shapeRenderer = new ShapeRenderer();
        board2D = new Board2D(shapeRenderer);
        displayUI = false;
        stage.addActor(board2D);
        uiOverlaySquare = new UIOverlaySquare(board2D, shapeRenderer);
        initBoard();



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


        if (turnStart) {
            //consoleRender();
            turnOrder();
            //consoleRender();
            turnStart = false;
        }

        stage.act(delta);
        stage.draw();
        ui.act(delta);
        ui.draw();
        handleInput();

    }


    public void turnOrder(){
        int isolCount = 0;
        for(int i = 0; i<amazons.length; i++) {
            if (amazons[i].endMe(boardCoordinates)) {
                isolCount++;
            }
        }
        if (isolCount == amazons.length){
            System.out.println("Game ends");
        }
        int phase = 1;
        if(boardCoordinates[0][3].isValidChoice(phase, true, boardCoordinates, 0, 3)) {

                ((Amazon2D) (boardCoordinates[0][3].getContent())).possibleMoves(board2D);
                phase++;
                if (boardCoordinates[0][5].isValidChoice(phase, true, boardCoordinates, 0, 5) && ((Amazon2D)(boardCoordinates[0][3].getContent())).getPossibleMoves().contains(boardCoordinates[0][5])) {
                    ((Amazon2D) (boardCoordinates[0][3].getContent())).move(boardCoordinates[0][5]);

                    phase++;
                    ((Amazon2D) (boardCoordinates[0][5].getContent())).possibleMoves(board2D);
                    if (boardCoordinates[5][5].isValidChoice(phase, true, boardCoordinates, 5, 5) && ((Amazon2D)(boardCoordinates[0][5].getContent())).getPossibleMoves().contains(boardCoordinates[5][5])) {
                        arrow = new Arrow2D(boardCoordinates[5][5]);
                    }
                }
        }
        isolCount = 0;
        for(int i = 0; i<amazons.length; i++) {
            if (amazons[i].endMe(boardCoordinates)) {
                isolCount++;
            }
        }
        if (isolCount == amazons.length){
            System.out.println("Game ends");
        }
        /*phase = 1;
        if(boardCoordinates[0][3].isValidChoice(phase, false, boardCoordinates, 0, 3)) {

            ((Amazon2D) (boardCoordinates[0][3].getContent())).possibleMoves(board2D);
            phase++;
            if (boardCoordinates[0][5].isValidChoice(phase, false, boardCoordinates, 0, 5)) {
                ((Amazon2D) (boardCoordinates[0][3].getContent())).move(boardCoordinates[0][5]);
                phase++;
                if (boardCoordinates[5][5].isValidChoice(phase, false, boardCoordinates, 5, 5)) {
                    arrow = new Arrow2D(boardCoordinates[5][5]);
                }
            }

        }
        //then black turn, change endMe to a loop to check all amazons

*/

    }

    public void consoleRender(){
        for(int i = 0;i<10;i++){
            for (int j = 0; j < 10; j++) {
                if(boardCoordinates[j][i].getContentID().equals("This cell is empty")) {
                    System.out.print("_ ");
                }
                else if(boardCoordinates[j][i].getContentID().contains("Amazon")){
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

    private void initBoard() {
        stage.act();
        stage.draw();
        this.boardCoordinates = board2D.getBoardCoordinates();
        amazons = new Amazon2D[8];
        placePieces();

    }

    private void placePieces() {
        amazons[0] = new Amazon2D('W', boardCoordinates[0][3]);
        amazons[1] = new Amazon2D('W', boardCoordinates[9][3]);
        amazons[2] = new Amazon2D('W', boardCoordinates[3][0]);
        amazons[3] = new Amazon2D('W', boardCoordinates[6][0]);
        amazons[4] = new Amazon2D('B', boardCoordinates[0][6]);
        amazons[5] = new Amazon2D('B', boardCoordinates[9][6]);
        amazons[6] = new Amazon2D('B', boardCoordinates[3][9]);
        amazons[7] = new Amazon2D('B', boardCoordinates[6][9]);
        for (Amazon2D a : amazons) {
            stage.addActor(a);

        }

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
