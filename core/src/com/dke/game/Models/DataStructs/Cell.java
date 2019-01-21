package com.dke.game.Models.DataStructs;

import com.dke.game.Models.AI.MINMAX.TestBoard;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Board2D;

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
    public  String belongsTo;
    public int moveNum;

    public int getMoveNum() {
        return moveNum;
    }

    public void setMoveNum(int moveNum) {
        this.moveNum = moveNum;
    }
    public Cell() {}

    public String getMoveID() {
        return belongsTo;
    }

    public void setMoveID(String moveID) {
        this.belongsTo = moveID;
    }

    //<editor-fold desc="PossibleMovesOfCell">
    //gives back a list of all possible queenlike moves of a cell
    public ArrayList<Cell> PossibleMovesOfCell(TestBoard testBoard, Cell cell){

        Cell[][] board = testBoard.getBoard();
        diagonal(cell, board);
        vertical(cell,board);
        horisontal(cell,board);
        return possibleMovesCell;
    }

    int counterD=1;
    ArrayList<Cell> possibleMovesCell= new ArrayList<>();

    public void diagonal(Cell cell,Cell[][] board){
        int i = cell.getI();
        int j = cell.getJ();

            if( !board[i+counterD][j+counterD].isOccupied() && i+counterD < board.length && j+counterD< board.length) {

                Cell newC = board[i + counterD][j + counterD];
            possibleMovesCell.add(board[i + counterD][j + counterD]);
            counterD++;
            diagonal(newC, board);
        }
        return;
    }
    int counterV=1;
    public void vertical(Cell cell,Cell[][] board) {
        int i = cell.getI();
        int j = cell.getJ();

        if (!board[i + counterV][j + counterV].isOccupied() && i + counterV < board.length && j + counterV < board.length) {
            Cell newC = board[i + counterV][j ]; // goes up, stays horisontally in the same position
            Cell newCell = board[i - counterV][j ];//goes down
            possibleMovesCell.add(newC);
            possibleMovesCell.add(newCell);
            counterV++;
            vertical(newCell,board);
            vertical(newC,board);
        }
    }
    int counterH=1;
    public void horisontal(Cell cell,Cell[][] board) {
        int i = cell.getI();
        int j = cell.getJ();

        if (!board[i + counterH][j + counterH].isOccupied() && i + counterH < board.length && j + counterH < board.length) {
            Cell newC = board[i][j + counterH];// goes right
            Cell newCell = board[i][j - counterH];//goes left
            possibleMovesCell.add(newC);
            possibleMovesCell.add(newCell);
            counterH++;
            horisontal(newCell,board);
            horisontal(newC,board);


        }
        return;
    }

//end possibleMovesCell
//</editor-fold>

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

    public int queenCells(Cell[][] cells) {
        int whiteTer = 0;
        int blackTer = 0;
        int ter =0;
        Cell[][] boardCoordinates = cells;
        for(int i = 0; i<boardCoordinates.length; i++ ){
            for(int j=0; j<boardCoordinates[0].length; j++){
                if(!isOccupied()) {
                    if(possibleCells(boardCoordinates, i, j) == 1){
                        whiteTer++;
                    }
                    else if(possibleCells(boardCoordinates, i, j) == 2){
                        blackTer++;
                    }
                }
            }
        }
        ter = whiteTer - blackTer;

        System.out.println("whiteter is: " + whiteTer);
        System.out.println("blackter is: " + blackTer);
        System.out.println("total ter is: " + ter);

        return ter;
    }

    public int possibleCells(Cell[][] cells, int x, int y) {
        int closest;
        Cell[][] boardCoordinates = cells;
        ArrayList<Cell> oneDisCells = new ArrayList<>();
        boolean positive = true;
        boolean negative = true;
        boolean whiteQ = false;
        boolean blackQ = false;

        for (int i = 0; i < boardCoordinates.length; i++) {
            if (x + i >= boardCoordinates[0].length) {
                positive = false;
            }
            if (x - i < 0) {
                negative = false;
            }
            if (positive) {
                if (boardCoordinates[x + i][y].isOccupied()) {
                    if(boardCoordinates[x + i][y].getContent() instanceof Amazon2D){
                        Amazon2D queen = (Amazon2D) boardCoordinates[x + i][y].getContent();
                        if (queen.getSide() == 'W'){
                            whiteQ = true;
                        }
                        else if(queen.getSide() == 'B'){
                            blackQ = true;
                        }
                        else{
                            oneDisCells.add(boardCoordinates[x+i][y]);
                        }

                    }
                }
            }
            if (negative) {
                if (boardCoordinates[x - i][y].isOccupied()) {
                    if(boardCoordinates[x - i][y].getContent() instanceof Amazon2D){
                        Amazon2D queen = (Amazon2D) boardCoordinates[x - i][y].getContent();
                        if (queen.getSide() == 'W'){
                            whiteQ = true;
                        }
                        else if(queen.getSide() == 'B'){
                            blackQ = true;
                        }
                        else{
                            oneDisCells.add(boardCoordinates[x-i][y]);
                        }

                    }
                    // boardCoordinates[this.cell.getI() - i][this.cell.getJ()].setAvailable("true");
                }
            }
        }

        positive = true;
        negative = true;

        for (int i = 0; i < boardCoordinates.length; i++) {
            if (i >= boardCoordinates.length) {
                positive = false;
            }
            if (i < 0) {
                negative = false;
            }

            if (positive) {
                if (boardCoordinates[x][y + i].isOccupied()) {
                    if(boardCoordinates[x][y + i].getContent() instanceof Amazon2D){
                    Amazon2D queen = (Amazon2D) boardCoordinates[x][y + i].getContent();
                         if (queen.getSide() == 'W'){
                            whiteQ = true;
                         }
                         else if(queen.getSide() == 'B'){
                             blackQ = true;
                         }
                         else{
                             oneDisCells.add(boardCoordinates[x][y + i]);
                         }

                    }
                    //boardCoordinates[this.cell.getI()][this.cell.getJ() + i].setAvailable("true");
                }
            }

            if (negative) {
                if (boardCoordinates[x][y - i].isOccupied()) {
                    if(boardCoordinates[x][y - i].getContent() instanceof Amazon2D){
                        Amazon2D queen = (Amazon2D) boardCoordinates[x][y - i].getContent();
                        if (queen.getSide() == 'W'){
                            whiteQ = true;
                        }
                        else if(queen.getSide() == 'B'){
                            blackQ = true;
                        }
                        else{
                            oneDisCells.add(boardCoordinates[x][y - i]);
                        }

                    }
                    //boardCoordinates[this.cell.getI()][this.cell.getJ() - i].setAvailable("true");
                }
            }


        }

        positive = true;
        negative = true;

        for (int i = 0; i < boardCoordinates.length; i++) {

            if (i >= boardCoordinates[0].length) {
                positive = false;
            }
            if (i < 0) {
                negative = false;
            }
            if (y + i >= boardCoordinates[0].length) {
                positive = false;
            }
            if (y - i < 0) {
                negative = false;
            }

            if (positive) {
                if (boardCoordinates[x + i][y + i].isOccupied()) {
                    if(boardCoordinates[x + i][y + i].getContent() instanceof Amazon2D){
                        Amazon2D queen = (Amazon2D) boardCoordinates[x + i][y + i].getContent();
                        if (queen.getSide() == 'W'){
                            whiteQ = true;
                        }
                        else if(queen.getSide() == 'B'){
                            blackQ = true;
                        }
                        else{
                            oneDisCells.add(boardCoordinates[x+i][y+i]);
                        }

                    }
                    //boardCoordinates[this.cell.getI() + i][this.cell.getJ() + i].setAvailable("true");
                }
            }
            if (negative) {
                if (boardCoordinates[x - i][y - i].isOccupied()) {
                    if(boardCoordinates[x - i][y - i].getContent() instanceof Amazon2D){
                        Amazon2D queen = (Amazon2D) boardCoordinates[x - i][y - i].getContent();
                        if (queen.getSide() == 'W'){
                            whiteQ = true;
                        }
                        else if(queen.getSide() == 'B'){
                            blackQ = true;
                        }
                        else{
                            oneDisCells.add(boardCoordinates[x+-i][y-i]);
                        }

                    }
                    // boardCoordinates[this.cell.getI() - i][this.cell.getJ() - i].setAvailable("true");
                }
            }


        }

        positive = true;
        negative = true;

        for (int i = 0; i < boardCoordinates.length; i++) {
            if (x - i < 0) {
                positive = false;
            }
            if (y + i >= boardCoordinates.length) {
                positive = false;
            }
            if (y - i < 0) {
                negative = false;
            }
            if (x + i >= boardCoordinates[0].length) {
                negative = false;
            }

            if (positive) {
                if (boardCoordinates[x - i][y + i].isOccupied()) {
                    if(boardCoordinates[x - i][y + i].getContent() instanceof Amazon2D){
                        Amazon2D queen = (Amazon2D) boardCoordinates[x - i][y + i].getContent();
                        if (queen.getSide() == 'W'){
                            whiteQ = true;
                        }
                        else if(queen.getSide() == 'B'){
                            blackQ = true;
                        }
                        else{
                            oneDisCells.add(boardCoordinates[x-i][y+i]);
                        }

                    }
                    // boardCoordinates[this.cell.getI() - i][this.cell.getJ() + i].setAvailable("true");
                }
            }

            if (negative) {
                if (boardCoordinates[x + i][y - i].isOccupied()) {
                    if(boardCoordinates[x + i][y - i].getContent() instanceof Amazon2D){
                        Amazon2D queen = (Amazon2D) boardCoordinates[x + i][y - i].getContent();
                        if (queen.getSide() == 'W'){
                            whiteQ = true;
                        }
                        else if(queen.getSide() == 'B'){
                            blackQ = true;
                        }
                        else{
                            oneDisCells.add(boardCoordinates[x+i][y-i]);
                        }

                    }
                    //boardCoordinates[this.cell.getI() + i][this.cell.getJ() - i].setAvailable("true");
                }
            }
        }

        // if white queen is closer than black queen,, closest is equal to 1
        if(whiteQ == true && blackQ == false){
            closest = 1;
        }

        // if black queen is closer than white queen,, closest is equal to 2
        else if(blackQ == true && whiteQ == false){
            closest = 2;
        }
        /*else if(blackQ == true && whiteQ == true) {
            for (Cell cell:oneDisCells) {
               cell.possibleCells();
            }
        }*/
        else { closest = 3;}
        return closest;
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
