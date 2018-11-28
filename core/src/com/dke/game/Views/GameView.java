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
import com.dke.game.Controller.GameLoop;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Coordinate;
import com.dke.game.Models.DataStructs.Piece;
import com.dke.game.Models.GraphicalModels.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class GameView extends View2D {


    public static final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private GameLoop gameLoop;
    private Stage stage;
    private Stage ui;
    private boolean displayUI;
    private UIOverlaySquare uiOverlaySquare;
    private Cell[][] boardCoordinates;
    private boolean displayOverlay = false;
    private Amazon2D selectedAmazon = null;
    private ArrayList<Actor> actors;
    private Board2D board2D;
    private Amazon2D[] amazons;
    private ArrayList<Arrow2D> arrow2DS;
    private boolean repeat = false;
    private Player player1;
    private Player player2;
    private Stack<Amazon2D> lastMoved = new Stack<>();
    private static BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));


    public GameView(ViewManager viewManager, Board2D board2D, Cell[][] boardCoordinates, Amazon2D[] amazons, ArrayList<Arrow2D> arrow2Ds, GameLoop gameLoop, Player player1, Player player2) {
        super(viewManager);
        this.gameLoop = gameLoop;
        this.arrow2DS = arrow2Ds;
        this.boardCoordinates = boardCoordinates;
        this.board2D = board2D;
        this.amazons = amazons;
        uiOverlaySquare = new UIOverlaySquare(board2D, shapeRenderer);
        stage = new Stage();
        stage.addActor(new Background());
        ui = new Stage();
        Gdx.input.setInputProcessor(new InputMultiplexer(ui, stage));
        displayUI = false;
        this.actors = new ArrayList<>();
        this.arrow2DS = arrow2Ds;
        this.player1 = player1;
        this.player2 = player2;


        font.setColor(Color.BLACK);
        font.getData().setScale(1);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
synchronized (this) {
    float delta = Gdx.graphics.getDeltaTime();
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    printArrows();
    stage.act(delta);
    stage.draw();
    ui.act(delta);
    ui.draw();
    handleInput();
    if (!gameLoop.isRunning()) {
        //System.out.println("Game over");
        Amazon2D[] white = {amazons[0], amazons[1], amazons[2], amazons[3]};
        Amazon2D[] black = {amazons[4], amazons[5], amazons[6], amazons[7]};
        //System.out.println(getTerritory(white) + "   " + getTerritory(black));
        gameLoop.endGame(getTerritory(white), getTerritory(black));

    }
}

    }
    private int getTerritory(Amazon2D[] colour){
        int[][] territory = new int[10][10];
        for(int i = 0; i<colour.length; i++) {
            int terSize = (colour[i].countTerritory(boardCoordinates))[0].length;
            for (int j = 0; j < terSize; j++) {
                territory[colour[i].countTerritory(boardCoordinates)[0][j]][colour[i].countTerritory(boardCoordinates)[1][j]] = 1;
            }
        }
        int oneCount = 0;
        for(int i = 0; i<10; i++){
            for(int j = 0; j<10; j++){
                //System.out.print(territory[j][i]);
                if(territory[j][i] == 1){
                    oneCount++;
                }
            }
            //System.out.println();
        }
        //System.out.println(oneCount);

        return oneCount;
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

    private void printArrows(){
        for (Arrow2D arrow:arrow2DS) {
            if(!arrow.isAlive()){
                stage.getActors().removeValue(arrow,false);
            }
            if(!stage.getActors().contains(arrow,false)&&arrow.isAlive()){
                stage.addActor(arrow);
            }

        }
    }

    @Override
    protected Cell getSelectedCell() {

        int x = Gdx.input.getX();
        int y = Gdx.graphics.getHeight() - Gdx.input.getY();
        Cell c = findCell(x, y);
        return c;

    }

    @Override
    protected void handleInput() {

        super.handleInput();

        if (Gdx.input.justTouched() || repeat) {
            repeat = false;
            if (gameLoop.getPhase() == 1) {
                phaseOne();
            } else if (gameLoop.getPhase() == 2) {
                phaseTwo();
            } else if (gameLoop.getPhase() == 3) {
                phaseThree();

                if(gameLoop.getCurrentSide() == 'W'){
                    gameLoop.setCurrentSide('B');
                }else if(gameLoop.getCurrentSide() == 'B'){
                    gameLoop.setCurrentSide('W');
                }else{
                    Gdx.app.error("Unknown side", "Unknown/unexpected side");
                }
            } else {
                Gdx.app.error("Unknown Phase", "Unknown/unexpected phase");
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F2)){
            if(!lastMoved.isEmpty()) {
                lastMoved.peek().undoLastShot();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F1)){
            if(!lastMoved.isEmpty()) {
                lastMoved.peek().undoLastMove();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F3)){
            System.out.println("enter coords for lastMovedAmazon");
            Scanner s = new Scanner(System.in);
            int i=Integer.parseInt(s.next());
            int j=Integer.parseInt(s.next());
            if(!lastMoved.isEmpty()) {
                lastMoved.peek().move(board2D.getBoardCoordinates()[i][j]);
            }

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F4)){
            if(!lastMoved.isEmpty()){
                lastMoved.pop();
            }
        }
    }

    //<editor-fold desc="Old code">
        /*
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
                            
                            ui.clear();
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
            */
    //</editor-fold>
    private void phaseOne() {

        Cell c = getSelectedCell();
        if (c != null) {
            if (c.isOccupied()) {

                Piece content = c.getContent();
                if (content instanceof Amazon2D) {
                    if (((Amazon2D) content).getSide() == gameLoop.getCurrentSide()) {
                        if (selectedAmazon == null) {
                            selectedAmazon = (Amazon2D) content;        //Amazon is selected
                            displayOverlay = true;
                            gameLoop.setPhase(2);
                        } else if (selectedAmazon == content) {
                            ui.clear();                                 //Toggle visibility of certain amazon
                            //<editor-fold desc="Toggle displayOverlay">
                            if (displayOverlay) {
                                displayOverlay = false;
                                gameLoop.setPhase(1);
                            } else {
                                displayOverlay = true;
                                gameLoop.setPhase(2);
                            }
                            //</editor-fold>
                        } else {
                            ui.clear();
                            selectedAmazon = (Amazon2D) content; //Different amazon was selected
                            displayOverlay = true;
                            gameLoop.setPhase(2);
                        }

                        selectedAmazon.possibleMoves(board2D);
                        uiOverlaySquare = new UIOverlaySquare(selectedAmazon.getPossibleMoves(), board2D, shapeRenderer);
                        if (displayOverlay) {
                            ui.addActor(uiOverlaySquare);

                        }
                    }
                }
            }
        }
    }

    private void phaseTwo() {
        Cell c = this.getSelectedCell();
        if (c != null) {

            Piece content = c.getContent();
            if (content != null) {
                this.gameLoop.setPhase(1);
                repeat = true;

            } else {
                ArrayList<Cell> pm = selectedAmazon.getPossibleMoves();
                for (Cell test : pm) {
                    if (test.getI() == c.getI() && test.getJ() == c.getJ()) {
                        selectedAmazon.move(test);
                        if(selectedAmazon.getSide()=='B') {
                            lastMoved.add(selectedAmazon);
                        }
                        ui.clear();
                        selectedAmazon.possibleMoves(board2D);
                        uiOverlaySquare = new UIOverlaySquare(selectedAmazon.getPossibleMoves(), board2D, shapeRenderer);
                        ui.addActor(uiOverlaySquare);
                        gameLoop.setPhase(3);
                    }

                }
            }
        }
    }

    private void phaseThree() {
        Cell c = this.getSelectedCell();
        if(c!=null){
            if(!c.isOccupied()){
                ArrayList<Cell> pm = selectedAmazon.getPossibleMoves();
                for (Cell test : pm) {
                    if (test.getI() == c.getI() && test.getJ() == c.getJ()) {
                        arrow2DS.add(selectedAmazon.shoot(test));
                        ui.clear();
                        gameLoop.setPhase(1);
                    }
                }
            }
        }
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


    public Stage getStage() {
        return stage;
    }
}


