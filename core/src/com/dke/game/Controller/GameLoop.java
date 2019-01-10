package com.dke.game.Controller;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Human;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.Luc.MINMAX.MiniMax;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import com.dke.game.Models.GraphicalModels.Board2D;
import com.dke.game.Views.GameView;
import com.dke.game.Views.ScoreView;

import java.util.ArrayList;
/**
 * Class that represents the controller from the model view controller architecture
 */

public class GameLoop {

    private GameView gameView;
    private Board2D board2D;
    private boolean running;
    private Cell[][] boardCoordinates;
    private Amazon2D[] amazons;
    private ArrayList<Arrow2D> arrow;
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
    public GameLoop(ViewManager viewManager, String white_Type, String black_Type) {
        this.viewManager = viewManager;
        arrow = new ArrayList<>();
        initialiseGame();
        gameView = new GameView(this.viewManager, board2D, boardCoordinates, amazons, arrow, this);
        createPlayers(white_Type, black_Type, gameView);
        gameView.setPlayers(white, black);
        gameView.getStage().addActor(board2D);
        placePieces();
        this.viewManager.push(gameView);
        currentPlayer = white;
        running = true;
    }

    public GameLoop(ViewManager viewmanager) {
        this(viewmanager, "Human", "Human");
    }

    //Thread stuff
    private void createPlayers(String white_Type, String black_Type, GameView gameView) {
        if (white_Type.equals("Human")) {
            if (black_Type.equals("AI")) {
                white = new Human('W', gameView, this);
                black = new AI('B', algo, this);
            } else if (black_Type.equals("Human")) {
                white = new Human('W', gameView, this);
                black = new Human('B', gameView, this);
            }
        } else if (white_Type.equals("AI")) {
            if (black_Type.equals("AI")) {
                white = new AI('W', algo, this);
                black = new AI('B', algo, this);
            } else if (black_Type.equals("Human")) {
                white = new AI('B', algo, this);
                black = new Human('B', gameView, this);
            }
        }
    }

    public ArrayList<Arrow2D> getArrows() {
        ArrayList<Arrow2D> arrows = new ArrayList<>();
        for (Amazon2D a : this.getAmazons()) {
            arrows.addAll(a.getArrowShots());
        }
        return arrows;
    }

    public boolean isRunning() {
        return running;
    }

    //Tread stuff
    public void update() {

        if (gameView.getTurnCounter() % 2 == 0) {
            currentPlayer = white;
        } else {
            currentPlayer = black;
        }
        if (currentPlayer instanceof Human) {
            Human p = (Human) currentPlayer;

            if (this.checkEnd() && p.getPhase() == 1) {
                running = false;
            }
        } else {
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
        if(boardCoordinates.length == 5 && boardCoordinates[0].length == 6) {
            amazons[0] = new Amazon2D('W', boardCoordinates[0][1], false, 0);
            amazons[1] = new Amazon2D('W', boardCoordinates[1][0], false, 1);
            amazons[2] = new Amazon2D('W', boardCoordinates[3][0], false, 2);
            amazons[3] = new Amazon2D('W', boardCoordinates[4][1], false, 3);
            amazons[4] = new Amazon2D('B', boardCoordinates[0][4], false, 4);
            amazons[5] = new Amazon2D('B', boardCoordinates[1][5], false, 5);
            amazons[6] = new Amazon2D('B', boardCoordinates[3][5], false, 6);
            amazons[7] = new Amazon2D('B', boardCoordinates[4][4], false, 7);
        }

        if(boardCoordinates.length == 10 && boardCoordinates[0].length == 10) {
            amazons[0] = new Amazon2D('W', boardCoordinates[0][3], false, 0);
            amazons[1] = new Amazon2D('W', boardCoordinates[9][3], false, 1);
            amazons[2] = new Amazon2D('W', boardCoordinates[3][0], false, 2);
            amazons[3] = new Amazon2D('W', boardCoordinates[6][0], false, 3);
            amazons[4] = new Amazon2D('B', boardCoordinates[0][6], false, 4);
            amazons[5] = new Amazon2D('B', boardCoordinates[9][6], false, 5);
            amazons[6] = new Amazon2D('B', boardCoordinates[3][9], false, 6);
            amazons[7] = new Amazon2D('B', boardCoordinates[6][9], false, 7);
        }
    }

    //Place the amazons on the board
    private void placePieces() {

        for (Amazon2D a : amazons) {
            gameView.getStage().addActor(a);

        }

        //preloadBoard(false);

    }

    private void preloadBoard(boolean random) {
        if (random) {
            for (int i = 0; i < (boardCoordinates.length * boardCoordinates[0].length) / 2; i++) {
                boolean madeShot = false;
                while (!madeShot) {
                    Cell option = boardCoordinates[(int) (boardCoordinates.length * Math.random())][(int) (boardCoordinates[0].length * Math.random())];
                    if (!option.isOccupied()) {
                        amazons[0].shoot(option);
                        madeShot = true;
                    }
                }
            }
        }
        else{
            for (int i = 2; i < 8; i++) {
                for (int j = 2; j < 8; j++) {
                    amazons[0].shoot(boardCoordinates[i][j]);
                }
            }
        }
    }

    //Push new view and some thread stuff
    public void endGame(int wScore, int bScore) {
        ScoreView scoreTime = new ScoreView(viewManager, wScore, bScore);
        this.viewManager.push(scoreTime);
    }

    //Check if the game has reached an end condition
    private boolean checkEnd() {
        this.board2D.printBoard();
        int checkCount = 0;

        for (int i = 0; i < amazons.length; i++) {

            if (amazons[i].endMe(boardCoordinates)) {
                checkCount++;
            }
        }
        int current = 0;
        for (int i = 0; i < amazons.length; i++) {

            if (!(amazons[i].endMe(boardCoordinates))) {
                amazons[i].possibleMoves(board2D);
                if ((amazons[i].getPossibleMoves()).size() == 0) {
                    current++;
                }
            }
        }
        int currentWhite = 0;
        for (int j = 0; j < 4; j++) {
            amazons[j].possibleMoves(board2D);
            if (amazons[j].getPossibleMoves().size() == 0) {
                currentWhite++;
            }
        }
        int currentBlack = 0;
        for (int j = 4; j < 8; j++) {
            amazons[j].possibleMoves(board2D);
            if (amazons[j].getPossibleMoves().size() == 0) {
                currentBlack++;
            }
        }
        //if all isolated
        if (checkCount == amazons.length) {
            return true;
        }
        //if all isolated or immobile
        else if (current == amazons.length - checkCount || currentWhite == 4 || currentBlack == 4) {
            return true;
        }

        return false;
    }


    //<editor-fold desc="Getters and Setters">
    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void setBoard2D(Board2D board2D) {
        this.board2D = board2D;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Cell[][] getBoardCoordinates() {
        return boardCoordinates;
    }

    public void setBoardCoordinates(Cell[][] boardCoordinates) {
        this.boardCoordinates = boardCoordinates;
    }

    public Amazon2D[] getAmazons() {
        return amazons;
    }

    public void setAmazons(Amazon2D[] amazons) {
        this.amazons = amazons;
    }

    public void setArrow(ArrayList<Arrow2D> arrow) {
        this.arrow = arrow;
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Algorithm getAlgo() {
        return algo;
    }

    public void setAlgo(Algorithm algo) {
        this.algo = algo;
    }
    //</editor-fold>
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


