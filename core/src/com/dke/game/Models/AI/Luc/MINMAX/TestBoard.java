package com.dke.game.Models.AI.Luc.MINMAX;


import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.DataStructs.Board;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Piece;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;
import java.util.Stack;

public class TestBoard {
    /**
     * The graphical information of the board class, this class also contain all the information about the board there is.
     */

    private Cell[][] boardCoordinates;
    private Amazon2D[] amazons = new Amazon2D[8];
    private ArrayList<Arrow2D> arrows;
    private Stack<Move> executedMoves = new Stack<>();


    public TestBoard(Amazon2D[] amazons, ArrayList<Arrow2D> arrows) {
        boardCoordinates = new Cell[Board.height][Board.width];
        this.arrows = new ArrayList<>();
        initializeBoard();
        createTestPieces(amazons, arrows);
        placePieces(this.amazons, this.arrows);


        //TODO remove following lines:
        //ChristmasCarlo cc = new ChristmasCarlo();
        //cc.startalgo(boardCoordinates, true);
    }

    private void createTestPieces(Amazon2D[] amazons, ArrayList<Arrow2D> arrows) {
        int counter = 0;
        for (Amazon2D a : amazons) {
            this.amazons[counter] = new Amazon2D(a.getSide(), boardCoordinates[a.getCell().getI()][a.getCell().getJ()], true,counter);
            counter++;
        }

        for (Arrow2D a : arrows) {
            this.arrows.add(new Arrow2D(boardCoordinates[a.getCell().getI()][a.getCell().getJ()], true));
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < boardCoordinates.length; i++) {
            for (int j = 0; j < boardCoordinates[i].length; j++) {
                boardCoordinates[i][j] = new Cell(null, null, null, null, i, j);
            }

        }
    }

    //mark as occupied?????
    public Piece occupy(Piece piece, Cell cell) {
        this.boardCoordinates[cell.getI()][cell.getJ()].occupy(piece);
        return piece;
    }

    public void placePieces(Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS) {
        for (Amazon2D a : amazon2DS) {
            this.occupy(a, boardCoordinates[a.getCell().getI()][a.getCell().getJ()]);
            a.updateCell(boardCoordinates[a.getCell().getI()][a.getCell().getJ()]);
        }
        for (Arrow2D a : arrow2DS) {
            this.occupy(a, boardCoordinates[a.getCell().getI()][a.getCell().getJ()]);
            a.updateCell(boardCoordinates[a.getCell().getI()][a.getCell().getJ()]);
        }
    }

    public void executeMove(Move move) {
        Amazon2D a = move.getQueen();
        a.move(move.getQueenTo());
        a.shoot(move.getArrowTo());
        executedMoves.push(move);
    }

    public void resetMoves() {
        while (!executedMoves.empty()) {
            Move m = executedMoves.pop();
            m.getQueen().undoShot();
            m.getQueen().undoMove();
        }
    }

    public boolean checkEnd() {
        //this.board2D.printBoard();
        int checkCount = 0;

        for (int i = 0; i < amazons.length; i++) {

            if (amazons[i].endMe(boardCoordinates)) {
                checkCount++;
            }
        }
        int current = 0;
        for (int i = 0; i < amazons.length; i++) {

            if (!(amazons[i].endMe(boardCoordinates))) {
                amazons[i].possibleMoves(this);
                if ((amazons[i].getPossibleMoves()).size() == 0) {
                    current++;
                }
            }
        }
        int currentWhite = 0;
        for (int j = 0; j < 4; j++) {
            amazons[j].possibleMoves(this);
            if (amazons[j].getPossibleMoves().size() == 0) {
                currentWhite++;
            }
        }
        int currentBlack = 0;
        for (int j = 4; j < 8; j++) {
            amazons[j].possibleMoves(this);
            if (amazons[j].getPossibleMoves().size() == 0) {
                currentBlack++;
            }
        }
        //if all isolated
        if (checkCount == amazons.length) {
            return true;
        }
        //if all isolated or immobile
        else if (current == amazons.length - checkCount || currentWhite == amazons.length/2 || currentBlack == amazons.length/2) {
            return true;
        }

        return false;
    }


    public void printBoard() {
//        for (int i = 0; i < boardCoordinates.length; i++) {
//            System.out.print("|");
//            for (int j = 0; j < boardCoordinates[i].length; j++) {
//                System.out.print(this.boardCoordinates[i][j].getContentType() + "|");
//                if (this.boardCoordinates[i][j].getContentID().equals("A")) {
//                    System.out.println("ARRROOWWWW");
//                }
//            }
//            System.out.println();
//        }
//        System.out.println();
        for (int j = boardCoordinates.length-1; j >= 0 ; j--) {
            System.out.print("|");
            for (int i = 0; i < boardCoordinates[j].length; i++) {
                System.out.print(this.boardCoordinates[i][j].getContentType() + "|");
            }
            System.out.println();
        }
        System.out.println();
    }

    public Cell[][] getBoard() {
        return this.boardCoordinates.clone();
    }

    public Amazon2D[] getAmazons() {
        return amazons;
    }

    public ArrayList<Arrow2D> getArrows() {
        return arrows;
    }

    public boolean checkEnd() {
        //this.board2D.printBoard();
        int checkCount = 0;

        for (int i = 0; i < amazons.length; i++) {

            if (amazons[i].endMe(boardCoordinates)) {
                checkCount++;
            }
        }
        int current = 0;
        for (int i = 0; i < amazons.length; i++) {

            if (!(amazons[i].endMe(boardCoordinates))) {
                amazons[i].possibleMoves(this);
                if ((amazons[i].getPossibleMoves()).size() == 0) {
                    current++;
                }
            }
        }
        int currentWhite = 0;
        for (int j = 0; j < 4; j++) {
            amazons[j].possibleMoves(this);
            if (amazons[j].getPossibleMoves().size() == 0) {
                currentWhite++;
            }
        }
        int currentBlack = 0;
        for (int j = 4; j < 8; j++) {
            amazons[j].possibleMoves(this);
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

    public boolean whiteWon(){
        Amazon2D[] whiteAmazons = new Amazon2D[4];
        Amazon2D[] blackAmazons = new Amazon2D[4];

        for(int i = 0; i<amazons.length; i++){
            if(i<4){
                whiteAmazons[i] = amazons[i];
            }
            else{
                blackAmazons[i-4] = amazons[i];
            }
        }

        int terWhite = getTerritory(whiteAmazons);
        int terBlack = getTerritory(blackAmazons);
        if(terWhite>terBlack){
            return true;
        }
        return false;
    }

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
}
