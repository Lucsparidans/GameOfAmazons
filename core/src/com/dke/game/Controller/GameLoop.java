package com.dke.game.Controller;

import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import com.dke.game.Models.GraphicalModels.Board2D;
import com.dke.game.Views.GameView;

import java.util.ArrayList;

public class GameLoop {

    private GameView gameView;
    private Board2D board2D;
    private boolean running = false;
    private Cell[][] boardCoordinates;
    private Amazon2D[] amazons;
    private ArrayList<Arrow2D> arrow;
    private Thread thread;
    private ViewManager viewManager;


    public GameLoop(ViewManager viewmanager) {
        this.viewManager = viewmanager;
        initialiseGame();
        gameView = new GameView(this.viewManager, board2D, boardCoordinates, amazons, arrow, this);
        this.viewManager.push(gameView);
        thread = new Thread(new GameThread());
        thread.start();
    }

    public boolean isRunning(){
        if (running){
            return true;
        }
        else{
            return false;
        }
    }
    private void update(){















    }

   private void initialiseGame(){
       board2D = new Board2D();
       boardCoordinates = board2D.getBoardCoordinates();
       this.amazons = new Amazon2D[8];
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
            gameView.addActor(a);

        }

    }


    public Board2D getBoard2D(){
        return this.board2D;
    }
    class GameThread implements Runnable{

        @Override
        public void run() {
            update();


        }
    }




}



/*
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


