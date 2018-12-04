package com.dke.game.Views;

import com.badlogic.gdx.Gdx;
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
import com.dke.game.Models.GraphicalModels.*;

import java.util.ArrayList;

public class GameView extends View2D {
    /**
     * The view that is displayed during the game
     */

    public static final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private GameLoop gameLoop;
    private Stage stage;
    private Stage ui;
    private Cell[][] boardCoordinates;
    private boolean displayUI;
    private UIOverlaySquare uiOverlaySquare;
    private boolean displayOverlay = false;
    private Amazon2D selectedAmazon = null;
    private ArrayList<Actor> actors;
    private Board2D board2D;
    private Amazon2D[] amazons;
    private boolean repeat = false;
    private int turnCounter = 0;
    private Player white;
    private Player black;

    private static BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));


    public GameView(ViewManager viewManager, Board2D board2D, Cell[][] boardCoordinates, Amazon2D[] amazons, ArrayList<Arrow2D> arrow2Ds, GameLoop gameLoop) {
        super(viewManager);
        this.gameLoop = gameLoop;
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


        font.setColor(Color.BLACK);
        font.getData().setScale(1);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void create() {

    }

    /*Weak spot in the code, couldn't think of a solution
    TODO fix
     */
    public void setPlayers(Player white, Player black){
        this.white = white;
        this.black=black;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override //Method from libGdX which renders anything you put in there on a loop
    public void render() {
synchronized (this) {//<-thread stuff
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
    //Way to calculate the amount of territory a player has left
    private int getTerritory(Amazon2D[] colour){
        //Only used at the end of the game, basically using countTerritory it gives the 2 territory totals on end screen
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

    //Print the arrows played to the screen by putting them in the stage-actors array
    private void printArrows(){
        for (Amazon2D amazon:amazons) {
            for (Arrow2D arrow:amazon.getArrowShots()) {
                if(!arrow.isAlive()){
                    stage.getActors().removeValue(arrow,false);
                }
                if(!stage.getActors().contains(arrow,false)&&arrow.isAlive()){
                    stage.addActor(arrow);
                }
            }
        }
    }

    @Override //Returns the cell the player clicked on according to the coordinates they selected with the mouse
    public Cell getSelectedCell() {

        int x = Gdx.input.getX();
        int y = Gdx.graphics.getHeight() - Gdx.input.getY();
        Cell c = findCell(x, y);
        return c;

    }

    @Override //Use  input to do somethings like moving the pieces
    protected void handleInput() {
        synchronized (this) {
            super.handleInput();

            if (Gdx.input.justTouched() || repeat) {
                if(turnCounter%2==0){
//                    repeat = false;
//                    if (gameLoop.getPhase() == 1) {
//                        phaseOne();
//                    } else if (gameLoop.getPhase() == 2) {
//                        phaseTwo();
//                    } else if (gameLoop.getPhase() == 3) {
//                        phaseThree();
//
//                    }
                    white.performTurn();
//                    if(!repeat){
//                        turnCounter++;
//                    }
                }
                else{
//                    repeat = false;
//                    if (gameLoop.getPhase() == 1) {
//                        phaseOne();
//                    } else if (gameLoop.getPhase() == 2) {
//                        phaseTwo();
//                    } else if (gameLoop.getPhase() == 3) {
//                        phaseThree();
//
//                    }
                    black.performTurn();
//                    if(!repeat){
//                        turnCounter++;
//                    }
                }

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

    //<editor-fold desc="Getters & Setters">
    public static ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getUi() {
        return ui;
    }

    public void setUi(Stage ui) {
        this.ui = ui;
    }

    public Cell[][] getBoardCoordinates() {
        return boardCoordinates;
    }

    public void setBoardCoordinates(Cell[][] boardCoordinates) {
        this.boardCoordinates = boardCoordinates;
    }

    public boolean isDisplayUI() {
        return displayUI;
    }

    public void setDisplayUI(boolean displayUI) {
        this.displayUI = displayUI;
    }

    public UIOverlaySquare getUiOverlaySquare() {
        return uiOverlaySquare;
    }

    public void setUiOverlaySquare(UIOverlaySquare uiOverlaySquare) {
        this.uiOverlaySquare = uiOverlaySquare;
    }

    public boolean isDisplayOverlay() {
        return displayOverlay;
    }

    public void setDisplayOverlay(boolean displayOverlay) {
        this.displayOverlay = displayOverlay;
    }

    public Amazon2D getSelectedAmazon() {
        return selectedAmazon;
    }

    public void setSelectedAmazon(Amazon2D selectedAmazon) {
        this.selectedAmazon = selectedAmazon;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    public Board2D getBoard2D() {
        return board2D;
    }

    public void setBoard2D(Board2D board2D) {
        this.board2D = board2D;
    }

    public Amazon2D[] getAmazons() {
        return amazons;
    }

    public void setAmazons(Amazon2D[] amazons) {
        this.amazons = amazons;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public Player getWhite() {
        return white;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public static BitmapFont getFont() {
        return font;
    }

    public static void setFont(BitmapFont font) {
        GameView.font = font;
    }
    //</editor-fold>
}


