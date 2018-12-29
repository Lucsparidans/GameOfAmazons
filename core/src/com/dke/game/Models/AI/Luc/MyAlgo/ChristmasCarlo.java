package com.dke.game.Models.AI.Luc.MyAlgo;

import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Piece;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;

public class ChristmasCarlo{
    Cell[][] currentCellMatrix;
    char sideTurn;

    char[][] simpleMatrix;

    public void startalgo(Cell[][] currentCellMatrix, char sideTurn){
        this.currentCellMatrix = currentCellMatrix;
        this.sideTurn = sideTurn;

        generateSimpleMatrix();

        //TODO, REPLACE 'B' WITH THE ACTUAL SIDE
        ArrayList<char[][]> nextStates = generateNextPossibleStates('B' ,simpleMatrix);
        //printCharMatrix(simpleMatrix);

        //TODO, REPLACE 'W' WITH THE ACTUAL SIDE
        getbestindex('B','W', nextStates);
    }

    public void generateSimpleMatrix(){
        simpleMatrix = new char[currentCellMatrix.length][currentCellMatrix[0].length];
        for(int i = 0; i< simpleMatrix.length; i++){
            for(int j = 0; j<simpleMatrix[0].length; j++){
                Cell currentCell = currentCellMatrix[i][j];
                if(currentCell.isOccupied()){
                    Piece piece = currentCell.getContent();
                    if(piece instanceof Amazon2D){
                        Amazon2D amazon = (Amazon2D) piece;
                        simpleMatrix[i][j] = amazon.getSideChar();
                    }else if(piece instanceof Arrow2D){
                        simpleMatrix[i][j] = 'A';
                    }
                }
            }
        }
    }

    public ArrayList<char[][]> generateNextPossibleStates(char whoseTurn, char[][] currentboard){
        System.out.println("CURRENT BOARD:");
        printCharMatrix(currentboard);
        ArrayList<char[][]> nextStates = new ArrayList<char[][]>();
        ArrayList<CarloCoordinate> moveableAmazons = new ArrayList<CarloCoordinate>();
        //Adds movable amazons to arrayList
        for(int i = 0; i<currentboard.length; i++){
            for (int j = 0; j<currentboard[0].length; j++){
                if(currentboard[i][j]==whoseTurn){
                    moveableAmazons.add(new CarloCoordinate(i,j));
                }
            }
        }

        //Adds possible next gamestates to the board.
        //Iteratively goes each direction, starting from the selected amazon you want to move.
        final char[] blankchar = new char[1];
        int boardWidth = currentboard.length;
        int boardHeight = currentboard[0].length;
        int howManyAmazonsForMe = moveableAmazons.size();
        for(int a= 0;a< howManyAmazonsForMe; a++){
            CarloCoordinate amazonCoor = moveableAmazons.get(a);
            int x = amazonCoor.getX();
            int y = amazonCoor.getY();
            boolean possible;
            possible = true;
            for(int i= -1;i<2; i++){
                for(int j = -1; j<2; j++){
                    int copI = i;
                    int copJ = j;
                    int copX = x;
                    int copY = y;
                    if(i!=0||j!=0){ possible = true;}

                    while(possible){
                        copX+= copI;
                        copY+= copJ;
                        if(copX<boardWidth&&copY<boardHeight&&copX>-1&&copY>-1) {
                            if (currentboard[copX][copY] == blankchar[0]) {
                                char[][] newboard = copyCharMatrix(currentboard);
                                newboard[x][y]=blankchar[0];
                                newboard[copX][copY] = whoseTurn;

                                //System.out.println("Board without arrow:");
                                //printCharMatrix(newboard);
                                //System.out.println("Arrowmoves for previous board:");
                                addArrowShots(nextStates,copX,copY,newboard);

                            }else{
                                possible = false;
                            }
                        }else{
                            possible = false;
                        }
                    }
                }
            }
        }
        //printCharMatrix(nextStates.get(nextStates.size()-2));
        return nextStates;
    }

    public static void addArrowShots(ArrayList<char[][]> stateList, int amazonX, int amazonY, char[][] board) {
        char[] blankchar = new char[1];
        int boardWidth = board.length;
        int boardHeight = board[0].length;
        boolean possible = true;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int copI = i;
                int copJ = j;
                int copX = amazonX;
                int copY = amazonY;
                if (i != 0 || j != 0) {
                    possible = true;
                }

                while (possible) {
                    copX += copI;
                    copY += copJ;
                    if (copX < boardWidth && copY < boardHeight && copX > -1 && copY > -1) {
                        if (board[copX][copY] == blankchar[0]) {
                            char[][] newboard = copyCharMatrix(board);
                            newboard[copX][copY] = 'A';
                            stateList.add(newboard);
                            //printCharMatrix(newboard);
                        } else {
                            possible = false;
                        }
                    } else {
                        possible = false;
                    }

                }
            }
        }
    }

    public double getbestindex(char AIside, char whoseTurn, ArrayList<char[][]> moveList){
        int amountOfMoves = moveList.size();
        double[] scoreList = new double[amountOfMoves];

        for(int i = 0; i<amountOfMoves; i++){
            char[][] startState = moveList.get(i);

        }

        double res = 1;
        return res;
    }

    public double expandRandomly(char AIside, char whoseTurn, char[][] startState){
        char[][]copyBoard = copyCharMatrix(startState);

    }


    public static void printCharMatrix ( char[][] matrix){
        char[] blankchar = new char[1];
        System.out.println("___________________________________");
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(" ");
            System.out.println(" ");

            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(" [");
                System.out.print(matrix[i][j]);
                if(matrix[i][j] == blankchar[0]){
                    System.out.print(" ");
                }
                System.out.print("] ");
            }
        }
    }

    public static char[][] copyCharMatrix(char[][] array) {
        char[][] newArr = new char[array.length][array[0].length];
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[i].length; j++){
                newArr[i][j] = array[i][j];
            }
        }
        return newArr;
    }



}