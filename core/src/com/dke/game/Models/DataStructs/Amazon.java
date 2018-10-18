package com.dke.game.Models.DataStructs;

import com.dke.game.Models.GraphicalModels.Arrow2D;
import com.dke.game.Models.GraphicalModels.Board2D;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Amazon extends Piece{
    private final char side;    //B for black & W for white.
    private static String idString = "Amazon: ";
    private static int ID = 0;
    private Integer idNumber;
    private Cell cell;
    private ArrayList<Cell> possibleMoves;



    public Amazon(char side, Cell cell){
        super(cell);
        this.cell = cell;
        this.side = side;
        this.idNumber = ID++;


    }

    @Override
    protected String getID() {
        return idString.concat(idNumber.toString() + side);
    }
    public char getSide(){
        return side;
    }


protected void updateCell(Cell c){
        this.cell = c;
        updateLocation(c.getBottomLeft());
}
    protected void updateLocation(Coordinate c){
        this.location = c;
    }
    
    public void move(Cell cell){
        this.cell.unOccupy();
        updateCell(cell);
        cell.occupy(this);


    }

    public Arrow2D shoot(Board2D board2D, Cell cell){
        Arrow2D arrow = new Arrow2D(cell);
        board2D.occupy(arrow, cell);
        return arrow;
    }

    public int[][] countTerritory(Cell[][] board){
        int xPos = this.cell.getI();
        int yPos = this.cell.getJ();
        int[][] checkArray = new int[10][10];
        boolean stop = false;

        for (int i = 0; i<10; i++){
            for(int j = 0; j<10; j++){
                if(board[i][j].getContentID().contains("Arrow") || board[i][j].getContentID().contains("Amazon")){
                    checkArray[i][j] = 3;
                }
            }
        }
        Stack xStack = new java.util.Stack();
        Stack yStack = new java.util.Stack();
        Stack xStore = new java.util.Stack();
        Stack yStore = new java.util.Stack();
        while(!stop) {
            boolean moveMade = false;
            boolean taken = false;

            //Checking for free neighbouring tile
            if(xPos !=9){
                if (checkArray[xPos + 1][yPos] == 0) {

                    xPos++;
                    moveMade = true;
                }
            } if (xPos!=9 && yPos != 9 && !moveMade){
                if(checkArray[xPos + 1][yPos + 1] == 0) {

                    xPos++;
                    yPos++;
                    moveMade = true;
                }
            }
            if(yPos!=9 && !moveMade) {
                if (checkArray[xPos][yPos + 1] == 0) {

                    yPos++;
                    moveMade = true;
                }
            }
            else if(yPos != 9 && xPos !=0 && !moveMade) {
                if (checkArray[xPos - 1][yPos + 1] == 0) {

                    xPos--;
                    yPos++;
                    moveMade = true;
                }
            }
            if(xPos != 0 && !moveMade) {
                if (checkArray[xPos - 1][yPos] == 0) {

                    xPos--;
                    moveMade = true;
                }
            }
            if(xPos != 0 && yPos != 0 && !moveMade) {
                if (checkArray[xPos - 1][yPos - 1] == 0) {

                    xPos--;
                    yPos--;
                    moveMade = true;
                }
            }
            if(yPos != 0 && !moveMade) {
                if (checkArray[xPos][yPos - 1] == 0) {

                    yPos--;
                    moveMade = true;
                }
            }
            if(xPos != 9 && yPos != 0 && !moveMade) {
                if (checkArray[xPos + 1][yPos - 1] == 0) {

                    xPos++;
                    yPos--;
                    moveMade = true;
                }
            }

            for(int i = 0; i<xStore.size(); i++) {
                if (xStore.elementAt(i).equals(xPos) && yStore.elementAt(i).equals(yPos)) {
                    taken = true;
                }
            }
            if (checkArray[xPos][yPos] == 0 ) {
                xStack.push(xPos);
                yStack.push(yPos);
                checkArray[xPos][yPos] = 1;
                if (!taken) {
                    xStore.push(xPos);
                    yStore.push(yPos);

                }
            }
            if(xStack.isEmpty()){
                //System.out.println("No amazon of opposite colour found");
                stop = true;
            }
            //Recurs
            if(!moveMade && !stop){
                xStack.pop();
                yStack.pop();
                if(xStack.isEmpty()) {
                    //System.out.println("No amazon of opposite colour found");
                    stop = true;
                }
                else{
                    xPos = (int) xStack.peek();
                    yPos = (int) yStack.peek();
                }
            }

            //console representation
            if(!stop) {
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
        for(int i = 0; i<loopSize; i++){
            count[0][i] = (int)xStore.pop();
            //System.out.println(count[0][i]);
            count[1][i] = (int)yStore.pop();
            //System.out.print(count[1][i]);

        }
        return count;
    }




    public boolean endMe(Cell[][] board){
        boolean isolated = false;
        int xPos = this.cell.getI();
        int yPos = this.cell.getJ();

        boolean stop = false;
        int[][] checkArray = new int[10][10];
        for (int i = 0; i<10; i++){
            for(int j = 0; j<10; j++){
                if(board[i][j].getContent() instanceof Arrow2D){
                    checkArray[i][j] = 3;
                }
            }
        }

        Stack xStack = new java.util.Stack();
        Stack yStack = new java.util.Stack();
        while(!stop) {
            boolean moveMade = false;
            checkArray[xPos][yPos] = 1;
            if(this.side == 'W'){
                if (board[xPos][yPos].getContentID().contains("Amazon: 4") || board[xPos][yPos].getContentID().contains("Amazon: 5") || board[xPos][yPos].getContentID().contains("Amazon: 6") || board[xPos][yPos].getContentID().contains("Amazon: 7")) {
                    //System.out.println("Found amazon of opposite colour");
                    break;
                }
            }
            if(this.side == 'B'){
                if (board[xPos][yPos].getContentID().contains("Amazon: 0") || board[xPos][yPos].getContentID().contains("Amazon: 1") || board[xPos][yPos].getContentID().contains("Amazon: 2") || board[xPos][yPos].getContentID().contains("Amazon: 3")) {
                    //System.out.println("Found amazon of opposite colour");
                    break;
                }
            }

            //Checking for free neighbouring tile
            if(xPos !=9){
                if (checkArray[xPos + 1][yPos] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos++;
                    moveMade = true;
                }
            } if (xPos!=9 && yPos != 9 && !moveMade){
                if(checkArray[xPos + 1][yPos + 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos++;
                    yPos++;
                    moveMade = true;
                }
            }
            if(yPos!=9 && !moveMade) {
                if (checkArray[xPos][yPos + 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    yPos++;
                    moveMade = true;
                }
            }
            else if(yPos != 9 && xPos !=0 && !moveMade) {
                if (checkArray[xPos - 1][yPos + 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos--;
                    yPos++;
                    moveMade = true;
                }
            }
            if(xPos != 0 && !moveMade) {
                if (checkArray[xPos - 1][yPos] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos--;
                    moveMade = true;
                }
            }
            if(xPos != 0 && yPos != 0 && !moveMade) {
                if (checkArray[xPos - 1][yPos - 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos--;
                    yPos--;
                    moveMade = true;
                }
            }
            if(yPos != 0 && !moveMade) {
                if (checkArray[xPos][yPos - 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    yPos--;
                    moveMade = true;
                }
            }
            if(xPos != 9 && yPos != 0 && !moveMade) {
                if (checkArray[xPos + 1][yPos - 1] == 0) {
                    xStack.push(xPos);
                    yStack.push(yPos);
                    xPos++;
                    yPos--;
                    moveMade = true;
                }
            }
            if(xStack.isEmpty()){
                //System.out.println("No amazon of opposite colour found");
                stop = true;
                isolated = true;

            }
            //Recurs
            if(!moveMade && !stop){
                xStack.pop();
                yStack.pop();
                if(xStack.isEmpty()) {
                    //System.out.println("No amazon of opposite colour found");
                    stop = true;
                    isolated = true;
                }
                else{
                    xPos = (int) xStack.peek();
                    yPos = (int) yStack.peek();
                }
            }

            //console representation
            if(!stop) {
               // System.out.println(xStack.peek() + "," + yStack.peek());
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                    //    System.out.print(checkArray[j][i] + " ");
                    }
                    //System.out.println();
                }
            }
        }
        return isolated;
    }

    public Cell[][] clearPossibleMoves(Cell[][] board)
    {
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                board[i][j].setAvailable("false");
            }
        }

        return board;
    }

    public void possibleMoves(Board2D board2D)
    {
        Cell[][] boardCoordinates = board2D.getBoardCoordinates();
        possibleMoves = new ArrayList<>();
        boolean positive = true;
        boolean negative = true;
        for(int i=1; i<10 ; i++){
            if(this.cell.getI()+i >= board2D.width) {
                positive = false;
            }
            if(this.cell.getI()-i < 0) {
                negative = false;
            }
            if(positive){
                if(!boardCoordinates[this.cell.getI()+i][this.cell.getJ()].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI() + i][this.cell.getJ()]);
                    //boardCoordinates[this.cell.getI() + i][this.cell.getJ()].setAvailable("true");
                }
                else{
                    positive = false;
                }
            }
            if(negative){
                if(!boardCoordinates[this.cell.getI()-i][this.cell.getJ()].isOccupied() ) {
                    possibleMoves.add(boardCoordinates[this.cell.getI()-i][this.cell.getJ()]);
                   // boardCoordinates[this.cell.getI() - i][this.cell.getJ()].setAvailable("true");
                }
                else {
                    negative = false;
                }
            }
        }

        positive = true;
        negative = true;

        for(int i=1; i<10 ; i++){
            if(this.cell.getJ()+i >= board2D.height) {
                positive = false;
            }
            if(this.cell.getJ()-i < 0) {
                negative = false;
            }

            if(positive){
                if(!boardCoordinates[this.cell.getI()][this.cell.getJ()+i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI()][this.cell.getJ()+i]);
                    //boardCoordinates[this.cell.getI()][this.cell.getJ() + i].setAvailable("true");
                }
                else {
                    positive = false;
                }
            }

            if(negative){
                if(!boardCoordinates[this.cell.getI()][this.cell.getJ()-i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI()][this.cell.getJ()-i]);
                    //boardCoordinates[this.cell.getI()][this.cell.getJ() - i].setAvailable("true");
                }
                else {
                    negative = false;
                }
            }


        }

        positive = true;
        negative = true;

        for(int i=1; i<10 ; i++){

            if(this.cell.getI()+i >= board2D.width) {
                positive = false;
            }
            if(this.cell.getI()-i < 0) {
                negative = false;
            }
            if(this.cell.getJ()+i >= board2D.width) {
                positive = false;
            }
            if(this.cell.getJ()-i < 0) {
                negative = false;
            }

            if(positive){
                if(!boardCoordinates[this.cell.getI()+i][this.cell.getJ()+i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI()+i][this.cell.getJ()+i]);
                    //boardCoordinates[this.cell.getI() + i][this.cell.getJ() + i].setAvailable("true");
                }
                else {
                    positive = false;
                }
            }
            if(negative){
                if(!boardCoordinates[this.cell.getI()-i][this.cell.getJ()-i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI()-i][this.cell.getJ()-i]);
                   // boardCoordinates[this.cell.getI() - i][this.cell.getJ() - i].setAvailable("true");
                }
                else {
                    negative = false;
                }
            }


        }

        positive = true;
        negative = true;

        for(int i=1; i<10 ; i++){
            if(this.cell.getI()-i < 0) {
                positive = false;
            }
            if(this.cell.getJ()+i >= board2D.height) {
                positive = false;
            }
            if(this.cell.getJ()-i < 0) {
                negative = false;
            }
            if(this.cell.getI()+i >= board2D.width) {
                negative = false;
            }

            if(positive){
                if(!boardCoordinates[this.cell.getI()-i][this.cell.getJ()+i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI()-i][this.cell.getJ()+i]);
                   // boardCoordinates[this.cell.getI() - i][this.cell.getJ() + i].setAvailable("true");
                }
                else {
                    positive = false;
                }
            }

            if(negative){
                if(!boardCoordinates[this.cell.getI()+i][this.cell.getJ()-i].isOccupied()) {
                    possibleMoves.add(boardCoordinates[this.cell.getI()+i][this.cell.getJ()-i]);
                    //boardCoordinates[this.cell.getI() + i][this.cell.getJ() - i].setAvailable("true");
                }
                else {
                    negative = false;
                }
            }
        }
    }

    public ArrayList<Cell> getPossibleMoves() {
        return possibleMoves;
    }

    public Cell getCell() {
        return this.cell;
    }
}

