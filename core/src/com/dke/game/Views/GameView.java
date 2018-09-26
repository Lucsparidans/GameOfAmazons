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
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Coordinate;
import com.dke.game.Models.GraphicalModels.*;

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
    private Arrow2D[] arrows;
    private boolean whiteTurn = true;

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
//TODO each iteration, every cell of the board is newly created... to fix this put all the cells of the board in an array and then draw each element in that array each draw loop
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (turnStart) {
            System.out.println(boardCoordinates[6][0].getContentID());
            consoleRender();

            if(!amazons[0].endMe(boardCoordinates) || !amazons[1].endMe(boardCoordinates) || !amazons[3].endMe(boardCoordinates) || !amazons[4].endMe(boardCoordinates)){
                System.out.println("not isolated");
            }
            else {
                System.out.println("isolated");
            }



            if(whiteTurn){
                //requiring input method

                whiteTurn = false;
            }
            else{
                //requiring input method

                whiteTurn = true;
            }
            turnStart = false;
        }

        stage.act(delta);
        stage.draw();
        ui.act(delta);
        ui.draw();
        handleInput();

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
            if (c != null) {
                if(!c.isOccupied()) {
                    uiOverlaySquare.addObject(getCellCenter(c));
                }
            }
            ui.addActor(uiOverlaySquare);


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
                        //System.out.printf("i: %d & j: %d \n",i,j);
                        //System.out.println("x = [" + x + "], y = [" + y + "]");

                        return boardCoordinates[i][j];

                    }
                }
            }
        }
//        System.out.println("x = [" + x + "], y = [" + y + "]");
//        System.out.println("null");
        return null;

    }


    private Coordinate getCellCenter(Cell cell) {
        Cell s = boardCoordinates[cell.getI()][cell.getJ()];
//        System.out.printf("topLeft x: %d & y: %d\n", boardCoordinates[i][j].getTopLeft().getX(),boardCoordinates[i][j].getTopLeft().getY());
//        System.out.printf("bottomLeft x: %d & y: %d\n", boardCoordinates[i][j].getBottomLeft().getX(),boardCoordinates[i][j].getBottomLeft().getY());
//        System.out.printf("bottomRight x: %d & y: %d\n", boardCoordinates[i][j].getBottomRight().getX(),boardCoordinates[i][j].getBottomRight().getY());
        return new Coordinate(s.getBottomLeft().getX() + ((s.getBottomRight().getX() - s.getBottomLeft().getX()) / 2),
                s.getBottomLeft().getY() + ((s.getTopLeft().getY() - s.getBottomLeft().getY()) / 2));
    }

//</editor-fold>
     /*

        private void drawPath() {
        if (!this.displayUI) {
        this.displayUI = true;
        if (ui.getActors().size == 0) {
        this.ui.addActor(uiOverlaySquare);
        }

        } else {
        this.displayUI = false;
        this.ui.clear();
        }

        }
        */
}
