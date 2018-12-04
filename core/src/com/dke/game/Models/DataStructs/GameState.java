package com.dke.game.Models.DataStructs;

import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import com.dke.game.Models.GraphicalModels.Board2D;

import java.util.ArrayList;

public class GameState {
    private Board2D board2D;
    private Amazon2D[] amazon2DArrayList;
    private ArrayList<Arrow2D> arrow2DArrayList;
    private Move move;
    private int playerNo;
    private int visitCount;
    private double winScore;


    public GameState(Amazon2D[] amazon2DArrayList, ArrayList<Arrow2D> arrow2DArrayList, Move move) {
        board2D = new Board2D();
        board2D.placePieces(amazon2DArrayList,arrow2DArrayList);
        this.amazon2DArrayList = amazon2DArrayList;
        this.arrow2DArrayList = arrow2DArrayList;
        this.move = move;
    }
    /*public GameState(GameState gameState){
        this.board2D = new Board2D(gameState.board2D);
    }*/

    public Board2D getBoard2D() {
        return board2D;
    }

    public Amazon2D[] getAmazon2DArrayList() {
        return amazon2DArrayList;
    }

    public ArrayList<Arrow2D> getArrow2DArrayList() {
        return arrow2DArrayList;
    }

    public Move getMove() {
        return move;
    }
    public Board2D executeMove(){
        Amazon2D amazon2D = move.getQueen();
        Cell queenTo = move.getQueenTo();
        Cell arrowTo = move.getArrowTo();
        amazon2D.move(queenTo);
        amazon2D.shoot(arrowTo);
        return board2D;
    }

    public boolean checkEnd(){
        int checkCount = 0;

        for(int i = 0; i<amazon2DArrayList.length; i++){

            if(amazon2DArrayList[i].endMe(board2D.getBoardCoordinates())){
                checkCount++;
            }
        }
        int current = 0;
        for(int i = 0; i<amazon2DArrayList.length; i++) {

            if (!(amazon2DArrayList[i].endMe(board2D.getBoardCoordinates()))) {
                amazon2DArrayList[i].possibleMoves(board2D);
                if((amazon2DArrayList[i].getPossibleMoves()).size() == 0) {
                    current++;
                }
            }
        }
        int currentWhite = 0;
        for(int j = 0; j<4; j++){
            amazon2DArrayList[j].possibleMoves(board2D);
            if(amazon2DArrayList[j].getPossibleMoves().size() == 0){
                currentWhite++;
            }
        }
        int currentBlack = 0;
        for(int j = 4; j<8; j++){
            amazon2DArrayList[j].possibleMoves(board2D);
            if(amazon2DArrayList[j].getPossibleMoves().size() == 0){
                currentBlack++;
            }
        }
        //if all isolated
        if(checkCount==amazon2DArrayList.length){
            return true;
        }
        //if all isolated or immobile
        else if(current == amazon2DArrayList.length - checkCount || currentWhite == 4 || currentBlack == 4){
            return true;
        }

        return false;
    }

    public int compareTerritory(){
        int whoWon = 0;
        Amazon2D[] white = {amazon2DArrayList[0], amazon2DArrayList[1], amazon2DArrayList[2], amazon2DArrayList[3]};
        Amazon2D[] black = {amazon2DArrayList[4], amazon2DArrayList[5], amazon2DArrayList[6], amazon2DArrayList[7]};
        if(getTerritory(black) > getTerritory(white)){
            whoWon = 1;
        }
        return whoWon;
    }
    private int getTerritory(Amazon2D[] colour){
        //Only used at the end of the game, basically using countTerritory it gives the 2 territory totals on end screen
        int[][] territory = new int[10][10];
        for(int i = 0; i<colour.length; i++) {
            int terSize = (colour[i].countTerritory(board2D.getBoardCoordinates())[0].length);
            for (int j = 0; j < terSize; j++) {
                territory[colour[i].countTerritory(board2D.getBoardCoordinates())[0][j]][colour[i].countTerritory(board2D.getBoardCoordinates())[1][j]] = 1;
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
}
