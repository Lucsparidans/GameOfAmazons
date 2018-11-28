package com.dke.game.Controller;
/**
 * Class that represents the controller from the model view controller architecture
 */

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Human;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.Luc.MiniMax;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Pieces;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import com.dke.game.Models.GraphicalModels.Board2D;
import com.dke.game.Views.GameView;
import com.dke.game.Views.ScoreView;

import java.util.ArrayList;

public class GameLoop {

    private GameView gameView;
    private Board2D board2D;
    private boolean running = false;
    private Cell[][] boardCoordinates;
    private Amazon2D[] amazons;
    private ArrayList<Arrow2D> arrow;
    private volatile Thread thread;
    private ViewManager viewManager;
    private Player white;
    private Player black;
    private Player currentPlayer;
    private Algorithm algo = new MiniMax();



    // get current board
    public Board2D getBoard2D() {
        return board2D;
    }
    // another constructor to avoid static fields
    public GameLoop(ViewManager viewManager, String white_Type, String black_Type){
        this.viewManager = viewManager;
        arrow = new ArrayList<>();
        initialiseGame();
        gameView = new GameView(this.viewManager, board2D, boardCoordinates, amazons, arrow, this);
        createPlayers(white_Type,black_Type,gameView);
        gameView.setPlayers(white,black);
        gameView.getStage().addActor(board2D);
        placePieces();
        this.viewManager.push(gameView);
        currentPlayer = white;

        thread = new GameThread();
        thread.start();
    }
    public GameLoop(ViewManager viewmanager) {
       this(viewmanager,"Human","Human");
    }
    //Thread stuff
    public void createPlayers(String white_Type,String black_Type,GameView gameView){
        if(white_Type == "Human"){
            if(black_Type=="AI"){
                white = new Human('W',gameView);
                black = new AI('B',algo,board2D);
            }
            else if(black_Type=="Human"){
                white = new Human('W',gameView);
                black = new Human('B',gameView);
            }
        }
        else if(white_Type == "AI"){
            if(black_Type=="AI"){
                white = new AI('W',algo,board2D);
                black = new AI('B',algo,board2D);
            }
            else if(black_Type=="Human"){
                white = new AI('B',algo,board2D);
                black = new Human('B',gameView);
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    //Tread stuff
    private void update() {
        if(gameView.getTurnCounter()%2==0){
            currentPlayer=white;
        }
        else{
            currentPlayer=black;
        }
        if(currentPlayer instanceof Human) {
            Human p = (Human)currentPlayer;
            if (this.checkEnd()&&p.getPhase()==1) {
                running = false;
            }
        }
        else{
            if (this.checkEnd()) {
                running = false;
            }
        }


    }

    //initial setup of the game
    private void initialiseGame() {
        board2D = new Board2D();
        boardCoordinates = board2D.getBoardCoordinates();
        this.amazons = new Amazon2D[8];


    }
    //Place the amazons on the board
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
            gameView.getStage().addActor(a);

        }

    }
    //Push new view and some thread stuff
    public void endGame(int wScore, int bScore){
        ScoreView scoreTime = new ScoreView(viewManager, wScore, bScore);
        this.viewManager.push(scoreTime);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //Check if the game has reached an end condition
    public boolean checkEnd(){
       int checkCount = 0;

       for(int i = 0; i<amazons.length; i++){

           if(amazons[i].endMe(boardCoordinates)){
               checkCount++;
           }
       }
        int current = 0;
        for(int i = 0; i<amazons.length; i++) {

            if (!(amazons[i].endMe(boardCoordinates))) {
                amazons[i].possibleMoves(board2D);
                if((amazons[i].getPossibleMoves()).size() == 0) {
                    current++;
                }
            }
        }
        int currentWhite = 0;
        for(int j = 0; j<4; j++){
            amazons[j].possibleMoves(board2D);
            if(amazons[j].getPossibleMoves().size() == 0){
                currentWhite++;
        }
        }
        int currentBlack = 0;
        for(int j = 4; j<8; j++){
            amazons[j].possibleMoves(board2D);
            if(amazons[j].getPossibleMoves().size() == 0){
                currentBlack++;
            }
        }
        //if all isolated
       if(checkCount==amazons.length){
           return true;
       }
       //if all isolated or immobile
       else if(current == amazons.length - checkCount || currentWhite == 4 || currentBlack == 4){
           return true;
       }

       return false;
    }






    //Thread class
    class GameThread extends Thread {

        @Override
        public void run() {
            running = true;
            while(running) {
                update();
            }


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
                    //arrow = new Arrow2D(boardCoordinates[5][5]);
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

        */
        //then black turn, change endMe to a loop to check all amazons







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


