//package com.dke.game.Models.AI.Luc;
//
//import com.dke.game.Controller.Player.AI;
//import com.dke.game.Controller.Player.Player;
//import com.dke.game.Models.DataStructs.Amazon;
//import com.dke.game.Models.DataStructs.Cell;
//import com.dke.game.Models.GraphicalModels.Amazon2D;
//import com.dke.game.Models.GraphicalModels.Arrow2D;
//import com.dke.game.Models.GraphicalModels.Board2D;
//
//import java.util.ArrayList;
//
//public class GameState {
//    private Board2D board2D;
//    private Amazon2D[] amazon2DArrayList;
//    private ArrayList<Arrow2D> arrow2DArrayList;
//    private Move move;
//    private Player player;
//    private double heuristicValue;
//    private boolean maximizing;
//
//
//    public GameState(Amazon2D[] amazon2DArrayList, ArrayList<Arrow2D> arrow2DArrayList, Move move, Boolean maximizing, Player player) {
//        board2D = new Board2D();
//        board2D.placePieces(amazon2DArrayList, arrow2DArrayList);
//        this.amazon2DArrayList = amazon2DArrayList;
//        this.arrow2DArrayList = arrow2DArrayList;
//        this.move = move;
//        this.player = player;
//        this.maximizing = maximizing;
//        this.heuristicValue = 0;
//    }
//
//    public Board2D getBoard2D() {
//        return board2D;
//    }
//
//    public Amazon2D[] getAmazon2DArrayList() {
//        return amazon2DArrayList;
//    }
//
//    public ArrayList<Arrow2D> getArrow2DArrayList() {
//        return arrow2DArrayList;
//    }
//
//    public boolean isMaximizing() {
//        return maximizing;
//    }
//
//    public Move getMove() {
//        return move;
//    }
//
//    public boolean checkEnd(){
//        int checkCount = 0;
//
//        for(int i = 0; i<amazon2DArrayList.length; i++){
//
//            if(amazon2DArrayList[i].endMe(board2D.getBoardCoordinates())){
//                checkCount++;
//            }
//        }
//        int current = 0;
//        for(int i = 0; i<amazon2DArrayList.length; i++) {
//
//            if (!(amazon2DArrayList[i].endMe(board2D.getBoardCoordinates()))) {
//                amazon2DArrayList[i].possibleMoves(board2D);
//                if((amazon2DArrayList[i].getPossibleMoves()).size() == 0) {
//                    current++;
//                }
//            }
//        }
//        int currentWhite = 0;
//        for(int j = 0; j<4; j++){
//            amazon2DArrayList[j].possibleMoves(board2D);
//            if(amazon2DArrayList[j].getPossibleMoves().size() == 0){
//                currentWhite++;
//            }
//        }
//        int currentBlack = 0;
//        for(int j = 4; j<8; j++){
//            amazon2DArrayList[j].possibleMoves(board2D);
//            if(amazon2DArrayList[j].getPossibleMoves().size() == 0){
//                currentBlack++;
//            }
//        }
//        //if all isolated
//        if(checkCount==amazon2DArrayList.length){
//            return true;
//        }
//        //if all isolated or immobile
//        else if(current == amazon2DArrayList.length - checkCount || currentWhite == 4 || currentBlack == 4){
//            return true;
//        }
//
//        return false;
//    }
//
//    public int compareTerritory(){
//        int whoWon = 0;
//        Amazon2D[] white = {amazon2DArrayList[0], amazon2DArrayList[1], amazon2DArrayList[2], amazon2DArrayList[3]};
//        Amazon2D[] black = {amazon2DArrayList[4], amazon2DArrayList[5], amazon2DArrayList[6], amazon2DArrayList[7]};
//        if(getTerritory(black) > getTerritory(white)){
//            whoWon = 1;
//        }
//        return whoWon;
//    }
//    private int getTerritory(Amazon2D[] colour){
//        //Only used at the end of the game, basically using countTerritory it gives the 2 territory totals on end screen
//        int[][] territory = new int[10][10];
//        for(int i = 0; i<colour.length; i++) {
//            int terSize = (colour[i].countTerritory(board2D.getBoardCoordinates())[0].length);
//            for (int j = 0; j < terSize; j++) {
//                territory[colour[i].countTerritory(board2D.getBoardCoordinates())[0][j]][colour[i].countTerritory(board2D.getBoardCoordinates())[1][j]] = 1;
//            }
//        }
//        int oneCount = 0;
//        for(int i = 0; i<10; i++){
//            for(int j = 0; j<10; j++){
//                //System.out.print(territory[j][i]);
//                if(territory[j][i] == 1){
//                    oneCount++;
//                }
//            }
//            //System.out.println();
//        }
//        //System.out.println(oneCount);
//
//        return oneCount;
//    }
//
//
//    public Board2D executeMove() {
//        Amazon2D amazon2D = move.getQueen();
//        Cell queenTo = move.getQueenTo();
//        Cell arrowTo = move.getArrowTo();
//        amazon2D.move(queenTo);
//        amazon2D.shoot(arrowTo);
//        return board2D;
//    }
//
//    public double evaluateState() {
//        if (player instanceof AI) {
//            AI playerAI = (AI) player;
//            if (this.maximizing) {
//                return shotsHeuristics(playerAI.getMyAmazons(), playerAI.getEnemyAmazons())
//                        + positioHheuristics(playerAI.getMyAmazons(), playerAI.getEnemyAmazons());
//            } else {
//                return shotsHeuristics(playerAI.getEnemyAmazons(), playerAI.getMyAmazons())
//                        + positioHheuristics(playerAI.getEnemyAmazons(), playerAI.getMyAmazons());
//            }
//        }
//        return 0;
//    }
//
//
//    //<editor-fold desc="Heuristics">
//    //calculating the score for the node
//    private double positioHheuristics(Amazon2D[] ourQueens, Amazon2D[] enemyQueens) {
//        double value = 0;
//
//        for (Amazon queen : ourQueens) {
//
//            //score: in range
//
//            ArrayList<Cell> possibleShots = queen.getPossibleMoves();// evaluate all possible shots of that cell(as moves are the same as shots we use moves)
//            //all possible shots questioned
//            for (Cell arrowCell : possibleShots) {
//                try {
//                    for (int i = arrowCell.getI() - 1; i <= arrowCell.getI() + 1; i++) {
//                        for (int j = arrowCell.getJ() - 1; j <= arrowCell.getJ() + 1; j++) { // going around  and over the potential arrow placement
//                            if (i != arrowCell.getI() && j != arrowCell.getJ()) { //avoiding the cell in questioning
//                                for (Amazon enemyQueen : enemyQueens) {
//                                    //need list of enemy queens
//
//                                    if (i == enemyQueen.getX() && j == enemyQueen.getY()) {
//                                        value = +1;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } catch (IndexOutOfBoundsException ex) {
//                    continue;
//                }
//            }//Score "in range" ends
//            //
//            for (Amazon ourQueen : ourQueens) {
//                //score: territory
//                Cell ourQueenCell = queen.getCell();
//                int i = ourQueenCell.getI();
//                int j = ourQueenCell.getJ();
//                int[][] territory = queen.countTerritory(this.board2D.getBoardCoordinates()); //basically how many cells are free to use from that position
//                //WARNING: due to no coordinates, can't put the location of the queen in question.
//                value = +territoryToInt(territory);
//                //Score"territory" ends
//            }
//
//        }
//        return value;
//
//    }
//
//    private int territoryToInt(int[][] territory) {
//        int count = 0;
//        for (int i = 0; i < territory.length; i++) {
//            for (int j = 0; j < territory[i].length; j++) {
//                if (territory[i][j] == 1) {
//                    count++;
//                }
//            }
//        }
//        return count;
//    }
//
//    private double shotsHeuristics(Amazon2D[] ourQueens, Amazon2D[] enemyQueens) {
//        double value = 0;
//        for (Amazon queen : ourQueens) {
//
//            //score: in range
//
//            ArrayList<Cell> possibleShots = queen.getPossibleMoves();// evaluate all possible shots of that cell(as moves are the same as shots we use moves)
//            //all possible shots questioned
//            for (Cell arrowCell : possibleShots) {
//                try {
//                    for (int i = arrowCell.getI() - 1; i <= arrowCell.getI() + 1; i++) {
//                        for (int j = arrowCell.getJ() - 1; j <= arrowCell.getJ() + 1; j++) { // going around  and over the potential arrow placement
//
//                            for (Amazon enemyQueen : enemyQueens) {
//                                //need list of enemy queens
//
//                                if (i == enemyQueen.getX() && j == enemyQueen.getY()) {
//                                    value = +1;
//                                }
//                            }
//                        }
//
//                    }
//                } catch (IndexOutOfBoundsException ex) {
//                    continue;
//                }
//            }//Score "in range" ends
//
//
//        }
//        return value;
//
//    }
//    //</editor-fold>
//
//}
