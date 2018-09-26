package com.dke.game.Models.DataStructs;



import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.Stack;

public abstract class Amazon extends Piece{
    private final char side;    //B for black & W for white.
    private static String idString = "Amazon: ";
    private static int ID = 0;
    private Integer idNumber;
    private int width = 9;
    private int height = 9;
    private Arrow2D arrow;
    private Cell cell;



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




    protected void updateLocation(Coordinate c){
        this.location = c;
    }
    
    public void move(Cell cell){
        this.cell.unOccupy();
        cell.occupy(this);


    }

    public void shoot(Cell cell){
        arrow = new Arrow2D(cell);
        cell.occupy(arrow);
    }

    public boolean endMe(Cell[][] board){
        boolean isolated = false;
        int xPos = this.cell.getI();
        int yPos = this.cell.getJ();
        
        boolean stop = false;
        int[][] checkArray = new int[10][10];
        for (int i = 0; i<10; i++){
            for(int j = 0; j<10; j++){
                if(board[i][j].getContentID().contains("Arrow")){
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
                if (board[xPos][yPos].getContentID().contains("Amazon: 1") || board[xPos][yPos].getContentID().contains("Amazon: 3") || board[xPos][yPos].getContentID().contains("Amazon: 5") || board[xPos][yPos].getContentID().contains("Amazon: 7")) {
                    System.out.println("Found amazon of opposite colour");
                    break;
                }
            }
            if(this.side == 'B'){
                if (board[xPos][yPos].getContentID().contains("Amazon: 0") || board[xPos][yPos].getContentID().contains("Amazon: 2") || board[xPos][yPos].getContentID().contains("Amazon: 4") || board[xPos][yPos].getContentID().contains("Amazon: 6")) {
                    System.out.println("Found amazon of opposite colour");
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
                System.out.println("No amazon of opposite colour found");
                stop = true;
                isolated = true;

            }
            //Recurs
            if(!moveMade && !stop){
                xStack.pop();
                yStack.pop();
                if(xStack.isEmpty()) {
                    System.out.println("No amazon of opposite colour found");
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
                System.out.println(xStack.peek() + "," + yStack.peek());
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        System.out.print(checkArray[j][i] + " ");
                    }
                    System.out.println();
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

    public Cell[][] possibleMoves(Cell[][] board)
    {
        boolean positive = true;
        boolean negative = true;
        for(int i=1; i<10 ; i++)
        {
            if(this.cell.getI()+i > width)
                positive = false;
            if(this.cell.getI()-i < 0)
                negative = false;
            if(positive)
            {
                if(!board[this.cell.getI()+i][this.cell.getJ()].isOccupied())
                    board[this.cell.getI()+i][this.cell.getJ()].setAvailable("true");
                else
                    positive = false;
            }
            if(negative)
            {
                if(!board[this.cell.getI()-i][this.cell.getJ()].isOccupied() )
                    board[this.cell.getI()-i][this.cell.getJ()].setAvailable("true");
                else
                    negative = false;
            }
        }

        positive = true;
        negative = true;

        for(int i=1; i<10 ; i++)
        {
            int a = this.cell.getI()+i;
            int b = this.cell.getI()-i;
            if(this.cell.getJ()+i > height)
                positive = false;
            if(this.cell.getJ()-i < 0)
                negative = false;

            if(positive)
            {
                if(!board[this.cell.getI()][this.cell.getJ()+i].isOccupied())
                    board[this.cell.getI()][this.cell.getJ()+i].setAvailable("true");
                else
                    positive = false;
            }

            if(negative)
            {
                if(!board[this.cell.getI()][this.cell.getJ()-i].isOccupied())
                    board[this.cell.getI()][this.cell.getJ()-i].setAvailable("true");
                else
                    negative = false;
            }


        }

        positive = true;
        negative = true;

        for(int i=1; i<10 ; i++)
        {

            if(this.cell.getI()+i > width)
                positive = false;
            if(this.cell.getI()-i < 0)
                negative = false;
            if(this.cell.getJ()+i > height)
                positive = false;
            if(this.cell.getJ()-i < 0)
                negative = false;

            if(positive)
            {
                if(!board[this.cell.getI()+i][this.cell.getJ()+i].isOccupied())
                    board[this.cell.getI()+i][this.cell.getJ()+i].setAvailable("true");
                else
                    positive = false;
            }
            if(negative)
            {
                if(!board[this.cell.getI()-i][this.cell.getJ()-i].isOccupied())
                    board[this.cell.getI()-i][this.cell.getJ()-i].setAvailable("true");
                else
                    negative = false;
            }


        }

        positive = true;
        negative = true;

        for(int i=1; i<10 ; i++)
        {
            if(this.cell.getI()-i < 0)
                positive = false;
            if(this.cell.getJ()+i > height)
                positive = false;
            if(this.cell.getJ()-i < 0)
                negative = false;
            if(this.cell.getI()+i > width)
                negative = false;

            if(positive)
            {
                if(!board[this.cell.getI()-i][this.cell.getJ()+i].isOccupied())
                    board[this.cell.getI()-i][this.cell.getJ()+i].setAvailable("true");
                else
                    positive = false;
            }

            if(negative)
            {
                if(!board[this.cell.getI()+i][this.cell.getJ()-i].isOccupied())
                    board[this.cell.getI()+i][this.cell.getJ()-i].setAvailable("true");
                else
                    negative = false;
            }
        }

        return board;
    }

}
