package com.dke.game.Models.AI.MINMAX;


import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.DataStructs.Board;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Piece;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.lang.reflect.Array;
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


//    public void printBoard() {
////        for (int i = 0; i < boardCoordinates.length; i++) {
////            System.out.print("|");
////            for (int j = 0; j < boardCoordinates[i].length; j++) {
////                System.out.print(this.boardCoordinates[i][j].getContentType() + "|");
////                if (this.boardCoordinates[i][j].getContentID().equals("A")) {
////                    System.out.println("ARRROOWWWW");
////                }
////            }
////            System.out.println();
////        }
////        System.out.println();
//        for (int j = boardCoordinates.length-1; j >= 0 ; j--) {
//            System.out.print("|");
//            for (int i = 0; i < boardCoordinates[j].length; i++) {
//                System.out.print(this.boardCoordinates[i][j].getContentType() + "|");
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
public void printBoard() {
    for (int j = boardCoordinates[0].length-1; j >= 0 ; j--) {
        System.out.print("|");
        for (int i = 0; i < boardCoordinates.length; i++) {
            System.out.print(this.boardCoordinates[i][j].getContentType() + "|");
        }
        System.out.println();
    }
    System.out.println();

}

    public TestBoard deepCopy(){
        return new TestBoard(amazons.clone(),(ArrayList<Arrow2D>)arrows.clone());
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
}
