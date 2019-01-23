package com.dke.game.Models.DataStructs;

import com.dke.game.Models.AI.MINMAX.TestBoard;

import java.util.ArrayList;

/**
 * The cell class of which the array in the board class exists, this is basically a container for the datastructures we might need
 */
public class Cell {
    private Piece content;
    private boolean isAvailable = false;
    public static int CELL_SIZE = 50;
    private Coordinate topLeft, topRight, bottomRight, bottomLeft;
    private int i,j;
    private String belongsTo;
    private int moveNumWhite;
    private int moveNumBlack;

    public int getMoveNumBlack() {
        return moveNumBlack;
    }
    public void setMoveNumBlack(int moveNumBlack) {
        this.moveNumBlack = moveNumBlack;
    }
    public void setMoveNumWhite(int moveNumWhite) {
        this.moveNumWhite = moveNumWhite;
    }

    public int getMoveNumWhite() {
        return moveNumWhite;
    }
    public String getMoveID() {
        return belongsTo;
    }

    public void setMoveID(String moveID) {
        this.belongsTo = moveID;
    }


    public Cell(Piece content, Coordinate topLeft, Coordinate topRight, Coordinate bottomRight, Coordinate bottomLeft, int i, int j) {
        this.content = content;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
        this.i = i;
        this.j = j;
    }

    public Cell(Coordinate topLeft, Coordinate topRight, Coordinate bottomRight, Coordinate bottomLeft, int i, int j) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
        this.i = i;
        this.j = j;
    }

    public Coordinate getTopLeft() {
        return topLeft;
    }

    public Coordinate getTopRight() {
        return topRight;
    }

    public Coordinate getBottomRight() {
        return bottomRight;
    }

    public Coordinate getBottomLeft() {
        return bottomLeft;
    }

//    @Override
//    public String toString() {
//        return "bottomLeft : " + "(" + bottomLeft.getX() + "," + bottomLeft.getY() + ")" + "\n"
//                + "topLeft : " + "(" + topLeft.getX() + "," + topLeft.getY() + ")" + "\n"
//                + "topRight : " + "(" + topRight.getX() + "," + topRight.getY() + ")" + "\n"
//                + "bottomRight : " + "(" + bottomRight.getX() + "," + bottomRight.getY() + ")" + "\n";
//    }

    public String getContentID(){
        if(content != null){
            return content.getID();
        }
        else{
            return "E";
        }
    }
    public String getContentType(){
        if(content != null){
            return content.getType();
        }
        else{
            return " ";
        }
    }

    public boolean isOccupied(){
        if(content != null){
            return true;
        }
        else{
            return false;
        }
    }

    public void setAvailable(String a){
        if(a == "true")
            this.isAvailable = true;

        else
            this.isAvailable = false;
    }

    public boolean getAvailable(){
        return isAvailable;
    }




    public void occupy(Piece piece){
        this.content = piece;
    }
    public void unOccupy() { this.content = null; }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Piece getContent() {
        return content;
    }
    //<editor-fold desc="PossibleMovesOfCell">

    ArrayList<Cell> possibleMovesCell= new ArrayList<>();
    /*
     * @param queenLikeMoves true if queenlike possible moves, false if kinglike possible moves
     * @return  a list of all possible queenlike moves of a cell
     * */
    int counter =1;
    public ArrayList<Cell> possibleMovesOfCell(TestBoard testBoard, Cell cell, boolean queenLikeMoves){

        Cell[][] board = testBoard.getBoard();
        int i = cell.getI();
        int j = cell.getJ();

        boolean ur = false;
        boolean ul = false;
        boolean dr = false;
        boolean dl = false;

        boolean vu = false;
        boolean vd = false;
        boolean hr = false;
        boolean hl = false;
        if (queenLikeMoves){
            while (!ur || !ul || !dr || !dl||!vu||!vd ||!hr||hl) {

                if (!ur && !board[i + counter][j + counter].isOccupied() && i + counter < board.length && j + counter < board.length) {
                    Cell newC = board[i + counter][j + counter];
                    possibleMovesCell.add(board[i + counter][j + counter]);

                } else
                    ur = true;

                if (!dl && !board[i - counter][j - counter].isOccupied() && i - counter < board.length && j - counter < board.length) {

                    Cell newCe = board[i - counter][j - counter];
                    possibleMovesCell.add(board[i - counter][j - counter]);

                } else
                    dl = true;

                if (!ul && !board[i + counter][j - counter].isOccupied() && i + counter < board.length && j - counter < board.length) {
                    Cell newCel = board[i + counter][j - counter];
                    possibleMovesCell.add(board[i + counter][j - counter]);

                } else
                    ul = true;

                if (!dr && !board[i - counter][j + counter].isOccupied() && i - counter < board.length && j + counter < board.length) {
                    Cell newCell = board[i - counter][j + counter];
                    possibleMovesCell.add(board[i - counter][j + counter]);


                } else
                    dr = true;
                if (!vu && !board[i + counter][j].isOccupied() && i + counter < board.length && j + counter < board.length) {
                    Cell newC = board[i + counter][j]; // goes up, stays horisontally in the same position
                    possibleMovesCell.add(newC);
                } else
                    vu = true;

                if (!vd && !board[i - counter][j].isOccupied() && i + counter < board.length && j + counter < board.length) {
                    Cell newCell = board[i - counter][j];//goes down

                    possibleMovesCell.add(newCell);

                } else
                    vd = true;

                if (!hr && !board[i][j + counter].isOccupied() && i + counter < board.length && j + counter < board.length) {
                    Cell newC = board[i][j + counter];// goes right
                    possibleMovesCell.add(newC);

                } else hr = true;

                if (!hl && !board[i][j + counter].isOccupied() && i + counter < board.length && j + counter < board.length) {

                    Cell newCell = board[i][j - counter];//goes left

                    possibleMovesCell.add(newCell);
                } else
                    hl = true;

                counter++;
            }

        } else {//kingmoves

            counter = 1;

            if (!ur && !board[i + counter][j + counter].isOccupied() && i + counter < board.length && j + counter < board.length) {
                Cell newC = board[i + counter][j + counter];
                possibleMovesCell.add(board[i + counter][j + counter]);

            } else
                ur = true;

            if (!dl && !board[i - counter][j - counter].isOccupied() && i - counter < board.length && j - counter < board.length) {

                Cell newCe = board[i - counter][j - counter];
                possibleMovesCell.add(board[i - counter][j - counter]);

            } else
                dl = true;

            if (!ul && !board[i + counter][j - counter].isOccupied() && i + counter < board.length && j - counter < board.length) {
                Cell newCel = board[i + counter][j - counter];
                possibleMovesCell.add(board[i + counter][j - counter]);

            } else
                ul = true;

            if (!dr && !board[i - counter][j + counter].isOccupied() && i - counter < board.length && j + counter < board.length) {
                Cell newCell = board[i - counter][j + counter];
                possibleMovesCell.add(board[i - counter][j + counter]);


            } else
                dr = true;
            if (!vu && !board[i + counter][j].isOccupied() && i + counter < board.length && j + counter < board.length) {
                Cell newC = board[i + counter][j]; // goes up, stays horisontally in the same position
                possibleMovesCell.add(newC);
            } else
                vu = true;

            if (!vd && !board[i - counter][j].isOccupied() && i + counter < board.length && j + counter < board.length) {
                Cell newCell = board[i - counter][j];//goes down

                possibleMovesCell.add(newCell);

            } else
                vd = true;

            if (!hr && !board[i][j + counter].isOccupied() && i + counter < board.length && j + counter < board.length) {
                Cell newC = board[i][j + counter];// goes right
                possibleMovesCell.add(newC);

            } else hr = true;

            if (!hl && !board[i][j + counter].isOccupied() && i + counter < board.length && j + counter < board.length) {

                Cell newCell = board[i][j - counter];//goes left

                possibleMovesCell.add(newCell);
            } else
                hl = true;

        }
        return possibleMovesCell;

    }


//end possibleMovesCell
//</editor-fold>
}
//<editor-fold desc="Old code">
/*
public boolean isValidChoice(int phase, boolean colour, Cell[][] board, int xPos, int yPos) {

        if (phase == 1) {
            if (this.getContentID().contains("Amazon")) {
                if (xPos == 0 && yPos == 0) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos][yPos + 1].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos][yPos + 1].isOccupied()))
                            return true;
                    }
                }
                if (xPos == 0 && yPos == board.length) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()))
                            return true;

                    }
                }
                if (xPos == board.length && yPos == board.length) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()))
                            return true;

                    }
                }
                if (xPos == board.length && yPos == 0) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()) || !(board[xPos - 1][yPos].isOccupied()))
                            return true;

                    }
                }
                if (xPos == 0) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()))
                            return true;

                    }

                }
                if (xPos == board.length) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()))
                            return true;
                    }
                }
                if (yPos == 0) {
                    if (colour == true && this.getContentID().contains("W")) {

                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()) || !(board[xPos][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos].isOccupied()))
                            return true;
                    } else if (colour == false && this.getContentID().contains("B")) {
                        if (!(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos + 1].isOccupied()) || !(board[xPos][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos + 1].isOccupied()) || !(board[xPos + 1][yPos].isOccupied()))
                            return true;
                    }
                }
                if (yPos == board.length) {

                }
                if (colour == true && this.getContentID().contains("W")) {

                    if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()))
                        return true;
                } else if (colour == false && this.getContentID().contains("B")) {
                    if (!(board[xPos + 1][yPos].isOccupied()) || !(board[xPos - 1][yPos].isOccupied()) || !(board[xPos - 1][yPos - 1].isOccupied()) || !(board[xPos][yPos - 1].isOccupied()) || !(board[xPos + 1][yPos - 1].isOccupied()))
                        return true;

                }
            }
        }
            if ((phase == 2) || (phase == 3)) {
                if (this.getContentID().contains("This cell is empty")) {
                    return true;
                }
            }
            return false;

    }
 */
//</editor-fold>
