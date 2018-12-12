package com.dke.game.Models.DataStructs;

import com.dke.game.Models.AI.Luc.MyAlgo.TestBoard;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import com.dke.game.Models.GraphicalModels.Board2D;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The amazon/queen piece and all its representational data
 */
public abstract class Amazon extends Piece {
    private final char side;    //B for black & W for white.
    private String idString = "Q";
    private static int ID = 0;
    private Integer idNumber; // what is this exactly??
    private Cell cell;
    private ArrayList<Cell> possibleMoves;
    private ArrayList<Arrow2D> arrowShots = new ArrayList<>();
    private Stack<Cell> lastMove = new Stack<>();
    private Stack<Cell> lastShot = new Stack<>();



    /* constructor
     * @param side color of amazon
     * @param cell position of amazon
     * position of amazon saved
     * color and number of amazon are saved*/

    public Amazon(char side, Cell cell) {
        super(cell);
        this.cell = cell;
        this.side = side;
        this.idNumber = ID++;


    }

    @Override
    protected String getType() {
        return Character.toString(side);
    }

    //returns id num(1-4) and color)
    @Override
    protected String getID() {
        return idString;
    }

    public char getSide() {
        return side;
    }


    /*@param c change of cell position*/
    public void updateCell(Cell c) {
        this.cell = c;
        updateLocation(c.getBottomLeft());
    }

    /* where is location????
     * @param c for coordinate
     * */
    protected void updateLocation(Coordinate c) {
        this.location = c;
    }

    /*@param cell
     * moves a piece to the given cell, frees the previous one */
    public void move(Cell cell) {
        lastMove.add(this.cell);
        this.cell.unOccupy();
        updateCell(cell);
        cell.occupy(this);


    }

    public ArrayList<Arrow2D> getArrowShots() {
        return arrowShots;
    }

    /*
     * @param board2D current board state
     * @param cell self explanatory
     * places an arrow in the given cell,
     * marks the cell occupied in the board
     * and gives back the arrow
     * */
    public void shoot(Cell cell) {
        Arrow2D arrow = new Arrow2D(cell, false);
        cell.occupy(arrow);
        arrowShots.add(arrow);
        lastShot.add(cell);
    }

    public void undoShot() {
        if (!lastShot.empty()) {
//            try {
                Cell lastShot = this.lastShot.pop();
                if (lastShot.getContent() instanceof Arrow) {
                    Arrow s = (Arrow) lastShot.getContent();
                    lastShot.unOccupy();
                    s.kill();
                }
//            }catch(Exception e){
//                System.out.println(cell.getContentType());
//            }
        }
    }

    public void undoMove() {
        if (!lastMove.empty()) {
            Cell lastMove = this.lastMove.pop();
            move(lastMove);
        }
    }

    /* omg this is long not gonna read
     *counts the territorry around a queen*/

    public int[][] countTerritory(Cell[][] board) {
        int xPos = this.cell.getI();
        int yPos = this.cell.getJ();
        int[][] checkArray = new int[10][10];
        boolean stop = false;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j].getContent() instanceof Arrow2D || board[i][j].getContent() instanceof Amazon2D) {
                    checkArray[i][j] = 3;
                }
            }
        }
        Stack xStack = new java.util.Stack();
        Stack yStack = new java.util.Stack();
        Stack xStore = new java.util.Stack();
        Stack yStore = new java.util.Stack();
        while (!stop) {
            boolean moveMade = false;
            boolean taken = false;

            //Checking for free neighbouring tile
            if (xPos != 9) {
                if (checkArray[xPos + 1][yPos] == 0) {

                    xPos++;
                    moveMade = true;
                }
            }
            if (xPos != 9 && yPos != 9 && !moveMade) {
                if (checkArray[xPos + 1][yPos + 1] == 0) {

                    xPos++;
                    yPos++;
                    moveMade = true;
                }
            }
            if (yPos != 9 && !moveMade) {
                if (checkArray[xPos][yPos + 1] == 0) {

                    yPos++;
                    moveMade = true;
                }
            } else if (yPos != 9 && xPos != 0 && !moveMade) {
                if (checkArray[xPos - 1][yPos + 1] == 0) {

                    xPos--;
                    yPos++;
                    moveMade = true;
                }
            }
            if (xPos != 0 && !moveMade) {
                if (checkArray[xPos - 1][yPos] == 0) {

                    xPos--;
                    moveMade = true;
                }
            }
            if (xPos != 0 && yPos != 0 && !moveMade) {
                if (checkArray[xPos - 1][yPos - 1] == 0) {

                    xPos--;
                    yPos--;
                    moveMade = true;
                }
            }
            if (yPos != 0 && !moveMade) {
                if (checkArray[xPos][yPos - 1] == 0) {

                    yPos--;
                    moveMade = true;
                }
            }
            if (xPos != 9 && yPos != 0 && !moveMade) {
                if (checkArray[xPos + 1][yPos - 1] == 0) {

                    xPos++;
                    yPos--;
                    moveMade = true;
                }
            }

            for (int i = 0; i < xStore.size(); i++) {
                if (xStore.elementAt(i).equals(xPos) && yStore.elementAt(i).equals(yPos)) {
                    taken = true;
                }
            }
            if (checkArray[xPos][yPos] == 0) {
                xStack.push(xPos);
                yStack.push(yPos);
                checkArray[xPos][yPos] = 1;
                if (!taken) {
                    xStore.push(xPos);
                    yStore.push(yPos);

                }
            }
            if (xStack.isEmpty()) {
                //System.out.println("No amazon of opposite colour found");
                stop = true;
            }
            //Recurs
            if (!moveMade && !stop) {
                xStack.pop();
                yStack.pop();
                if (xStack.isEmpty()) {
                    //System.out.println("No amazon of opposite colour found");
                    stop = true;
                } else {
                    xPos = (int) xStack.peek();
                    yPos = (int) yStack.peek();
                }
            }

            //console representation
            if (!stop) {
                // System.out.println(xStack.peek() + "," + yStack.peek());
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        //    System.out.print(checkArray[j][i] + " ");
                    }
                    //System.out.println();
                }
            }
        }

        int[][] count = new int[2][xStore.size()];
        int loopSize = xStore.size();
        for (int i = 0; i < loopSize; i++) {
            count[0][i] = (int) xStore.pop();
            //System.out.println(count[0][i]);
            count[1][i] = (int) yStore.pop();
            //System.out.print(count[1][i]);

        }
        return count;
    }


    /*@param board[][]
     * counts how many cells are weaway from winning or losing???*/
    public boolean endMe(Cell[][] board) {
        boolean isolated = false;
        int xPos = this.cell.getI();
        int yPos = this.cell.getJ();

        boolean stop = false;
        int[][] checkArray = new int[10][10];
        for (int i = 0; i < 10; i++) {
            //System.out.print("|");
            for (int j = 0; j < 10; j++) {
                if (board[i][j].getContent() instanceof Arrow2D) {
                    //System.out.print(board[i][j].getContentType());
                    checkArray[i][j] = 3;
                }
                // System.out.print("|");
            }
            // System.out.println();
        }

        Stack xStack = new java.util.Stack();
        Stack yStack = new java.util.Stack();
        while (!stop) {
            boolean moveMade = false;
            checkArray[xPos][yPos] = 1;
            if (this.side == 'W') {
                if (board[xPos][yPos].isOccupied()) {
                    if (board[xPos][yPos].getContent() instanceof Amazon2D) {
                        Amazon2D queen = (Amazon2D) board[xPos][yPos].getContent();
                        if (queen.getSide() == 'B') {
                            //System.out.println("Found amazon of opposite colour");
                            break;
                        }
                    }
                }

            }
            if (this.side == 'B') {
                if (board[xPos][yPos].isOccupied()) {
                    if (board[xPos][yPos].getContent() instanceof Amazon2D) {
                        Amazon2D queen = (Amazon2D) board[xPos][yPos].getContent();
                        if (queen.getSide() == 'W') {
                            //System.out.println("Found amazon of opposite colour");
                            break;
                        }
                    }
                }
            }

            //Checking for free neighbouring tile
            if (xPos != 9) {
                if (checkArray[xPos + 1][yPos] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos++;
                    moveMade = true;
                }
            }
            if (xPos != 9 && yPos != 9 && !moveMade) {
                if (checkArray[xPos + 1][yPos + 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos++;
                    yPos++;
                    moveMade = true;
                }
            }
            if (yPos != 9 && !moveMade) {
                if (checkArray[xPos][yPos + 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    yPos++;
                    moveMade = true;
                }
            } else if (yPos != 9 && xPos != 0 && !moveMade) {
                if (checkArray[xPos - 1][yPos + 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos--;
                    yPos++;
                    moveMade = true;
                }
            }
            if (xPos != 0 && !moveMade) {
                if (checkArray[xPos - 1][yPos] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos--;
                    moveMade = true;
                }
            }
            if (xPos != 0 && yPos != 0 && !moveMade) {
                if (checkArray[xPos - 1][yPos - 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos--;
                    yPos--;
                    moveMade = true;
                }
            }
            if (yPos != 0 && !moveMade) {
                if (checkArray[xPos][yPos - 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    yPos--;
                    moveMade = true;
                }
            }
            if (xPos != 9 && yPos != 0 && !moveMade) {
                if (checkArray[xPos + 1][yPos - 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos++;
                    yPos--;
                    moveMade = true;
                }
            }
            if (xStack.isEmpty()) {
                //System.out.println("No amazon of opposite colour found");
                stop = true;
                isolated = true;

            }
            //Recurs
            if (!moveMade && !stop) {
                xStack.pop();
                yStack.pop();
                if (xStack.isEmpty()) {
                    //System.out.println("No amazon of opposite colour found");
                    stop = true;
                    isolated = true;
                } else {
                    xPos = (int) xStack.peek();
                    yPos = (int) yStack.peek();
                }
            }

            //console representation
            if (!stop) {
                //  System.out.println(xStack.peek() + "," + yStack.peek());
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        //     System.out.print(checkArray[j][i] + " ");
                    }
                    // System.out.println();
                }
            }
        }
        return isolated;
    }

    /*
     * @param board[][] what, how??????????*/
    public Cell[][] clearPossibleMoves(Cell[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j].setAvailable("false");
            }
        }

        return board;
    }

    public ArrayList<Cell> possibleMoves(Board2D board2D) {
        return possibleMoves(board2D.getBoardCoordinates());
    }

    public ArrayList<Cell> possibleMoves(TestBoard testBoard) {
        return possibleMoves(testBoard.getBoard());
    }

    /*
     * @param board2d
     * what does this do????
     * */
    private ArrayList<Cell> possibleMoves(Cell[][] cells) {
        Cell[][] boardCoordinates = cells;
        possibleMoves = new ArrayList<>();
        boolean positive = true;
        boolean negative = true;
        for (int i = 1; i < 10; i++) {
            if (this.cell.getI() + i >= boardCoordinates[0].length) {
                positive = false;
            }
            if (this.cell.getI() - i < 0) {
                negative = false;
            }
            if (positive) {
                if (!boardCoordinates[this.cell.getI() + i][this.cell.getJ()].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI() + i][this.cell.getJ()]);
                    //boardCoordinates[this.cell.getI() + i][this.cell.getJ()].setAvailable("true");
                } else {
                    positive = false;
                }
            }
            if (negative) {
                if (!boardCoordinates[this.cell.getI() - i][this.cell.getJ()].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI() - i][this.cell.getJ()]);
                    // boardCoordinates[this.cell.getI() - i][this.cell.getJ()].setAvailable("true");
                } else {
                    negative = false;
                }
            }
        }

        positive = true;
        negative = true;

        for (int i = 1; i < 10; i++) {
            if (this.cell.getJ() + i >= boardCoordinates.length) {
                positive = false;
            }
            if (this.cell.getJ() - i < 0) {
                negative = false;
            }

            if (positive) {
                if (!boardCoordinates[this.cell.getI()][this.cell.getJ() + i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI()][this.cell.getJ() + i]);
                    //boardCoordinates[this.cell.getI()][this.cell.getJ() + i].setAvailable("true");
                } else {
                    positive = false;
                }
            }

            if (negative) {
                if (!boardCoordinates[this.cell.getI()][this.cell.getJ() - i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI()][this.cell.getJ() - i]);
                    //boardCoordinates[this.cell.getI()][this.cell.getJ() - i].setAvailable("true");
                } else {
                    negative = false;
                }
            }


        }

        positive = true;
        negative = true;

        for (int i = 1; i < 10; i++) {

            if (this.cell.getI() + i >= boardCoordinates[0].length) {
                positive = false;
            }
            if (this.cell.getI() - i < 0) {
                negative = false;
            }
            if (this.cell.getJ() + i >= boardCoordinates[0].length) {
                positive = false;
            }
            if (this.cell.getJ() - i < 0) {
                negative = false;
            }

            if (positive) {
                if (!boardCoordinates[this.cell.getI() + i][this.cell.getJ() + i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI() + i][this.cell.getJ() + i]);
                    //boardCoordinates[this.cell.getI() + i][this.cell.getJ() + i].setAvailable("true");
                } else {
                    positive = false;
                }
            }
            if (negative) {
                if (!boardCoordinates[this.cell.getI() - i][this.cell.getJ() - i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI() - i][this.cell.getJ() - i]);
                    // boardCoordinates[this.cell.getI() - i][this.cell.getJ() - i].setAvailable("true");
                } else {
                    negative = false;
                }
            }


        }

        positive = true;
        negative = true;

        for (int i = 1; i < 10; i++) {
            if (this.cell.getI() - i < 0) {
                positive = false;
            }
            if (this.cell.getJ() + i >= boardCoordinates.length) {
                positive = false;
            }
            if (this.cell.getJ() - i < 0) {
                negative = false;
            }
            if (this.cell.getI() + i >= boardCoordinates[0].length) {
                negative = false;
            }

            if (positive) {
                if (!boardCoordinates[this.cell.getI() - i][this.cell.getJ() + i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI() - i][this.cell.getJ() + i]);
                    // boardCoordinates[this.cell.getI() - i][this.cell.getJ() + i].setAvailable("true");
                } else {
                    positive = false;
                }
            }

            if (negative) {
                if (!boardCoordinates[this.cell.getI() + i][this.cell.getJ() - i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI() + i][this.cell.getJ() - i]);
                    //boardCoordinates[this.cell.getI() + i][this.cell.getJ() - i].setAvailable("true");
                } else {
                    negative = false;
                }
            }
        }
        return possibleMoves;
    }

    /*
     * @return possibleMoves list
     * */
    public ArrayList<Cell> getPossibleMoves() {
        return possibleMoves;
    }

    /*
     * @return cell a specific cell */
    public Cell getCell() {
        return this.cell;
    }
}

