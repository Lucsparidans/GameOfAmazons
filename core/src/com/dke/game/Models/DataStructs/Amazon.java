package com.dke.game.Models.DataStructs;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.Stack;

public abstract class Amazon extends Piece{
    private final char side;    //B for black & W for white.
    private static String idString = "Amazon: ";
    private static int ID = 0;
    private Integer idNumber;
    private int posX;
    private int posY;
    private Arrow2D arrow;

    public Amazon(char side, int Xpos, int Ypos , ShapeRenderer shapeRenderer){

        super(shapeRenderer);
        this.side = side;
        this.idNumber = ID++;
        this.posX = Xpos;
        this.posY = Ypos;

    }

    @Override
    protected String getID() {
        return idString.concat(idNumber.toString());
    }
    protected int getposX() {
        return posX;
    }
    protected int getposY() {
        return posY;
    }

    public void move(int newX, int newY, Cell[][] board){
        board[this.getposX()][this.getposY()].unOccupy();
        board[newX][newY].occupy(this);

    }

    public void shoot(int newX, int newY, Cell[][] board){
        arrow = new Arrow2D(shapeRenderer);
        board[newX][newY].occupy(arrow);
    }

    public boolean endMe(Cell[][] board){
        boolean isolated = false;
        int xPos = this.getposX();
        int yPos = this.getposY();
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
}
