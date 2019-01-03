package com.dke.game.Models.AI.Luc.MyAlgo;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Piece;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;




public class ChristmasCarlo{
    final double diagonalMoveBias = 30;
    Cell[][] currentCellMatrix;
    char sideTurn;
    Random randomGenerator;

    char[][] simpleMatrix;
    char[] emptyChar;
    static ArrayList<int[]> directionList;

    int diagonalMoves = 0;
    int nondiagonalMoves = 0;
    double moveLengthsum = 0;
    double moveAmount = 0;

    public ChristmasCarlo(){
        randomGenerator = new Random();
        emptyChar = new char[1];
        directionList = new ArrayList<int[]>();
        initializeDirectionList(directionList);
    }

    public static void initializeDirectionList(ArrayList<int[]> directionList){
        //directionList = new ArrayList<int[]>();
        for(int i = -1; i<2;i++){
            for(int j = -1; j<2; j++){
                if(i!=0||j!=0){
                    int[] newarray = {i, j};
                    directionList.add(newarray);
                    System.out.println(i+"  "+j);
                }
            }
        }
    }

    //Shuffles moves randomly, with a given chance it will reshuffle if the first move is non-diagonal
    public  void biasedShuffle(){
        Collections.shuffle(directionList);
        int[] firstEntry = directionList.get(0);
        if(firstEntry[0]*firstEntry[1]==0){
            int rand = randomGenerator.nextInt(100);
            if(rand<diagonalMoveBias) {
                Collections.shuffle(directionList);
            }
        }

    }

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
        //System.out.println("CURRENT BOARD:");
        //printCharMatrix(currentboard);
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


    //For a certain moved amazon, take all possible shots and add them to the stateList array.
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

    public char[][] getNextRandomState(char whoseTurn, char[][] startState){
        //initializeDirectionList(directionList);
        printCharMatrix(startState);
        System.out.println("WhoseTurn: " + whoseTurn);
        //Collections.shuffle(directionList);
        biasedShuffle();
        char[][] copyboard = copyCharMatrix(startState);
        ArrayList<CarloCoordinate> myAmazons = new ArrayList<CarloCoordinate>();
        for(int i = 0; i< copyboard.length; i++){
            for(int j = 0; j< copyboard[0].length; j++){
                if(copyboard[i][j] == whoseTurn){
                    myAmazons.add(new CarloCoordinate(i,j));
                }
            }
        }

        int coorX = -1;
        int coorY = -1;
        int newCoorX = -1;
        int newCoorY = -1;
        Collections.shuffle(myAmazons);
        boolean WeDidAMove = false;
        for (int index = 0; index<myAmazons.size(); index++){
            CarloCoordinate coor = myAmazons.get(index);
            coorX = coor.getX();
            coorY = coor.getY();

            //Coming for loop calculates if there is even a possible move for the randomly Selected amazon
            boolean possibleMoveForThisAmazon = false;
            for(int i = coorX-1; i< coorX+2; i++){
                for(int j = coorY-1; j<coorY+2; j++){
                    if(i<0||i>copyboard.length-1||j<0||j>copyboard[0].length-1){
                        continue;
                    }
                    if(copyboard[i][j]==emptyChar[0]){
                        possibleMoveForThisAmazon = true;
                        i = coorX+2;
                        j = coorY +2;
                    }
                }
            }

            if(!possibleMoveForThisAmazon&&index == myAmazons.size()-1){
                throw new RuntimeException("No possible moves for "+ whoseTurn);
            }
            if(!possibleMoveForThisAmazon){continue;}

            for(int[] direction: directionList){
                int xDir = direction[0];
                int yDir = direction[1];
                int distanceMulitplier = 1;
                boolean previousMultiplicityWorked = true;
                int distanceThatWorked = 0;
                while(previousMultiplicityWorked){
                    previousMultiplicityWorked = false;
                    int multipliedxDir = xDir*distanceMulitplier;
                    int multipliedyDir = yDir*distanceMulitplier;
                    int xTry = coorX+multipliedxDir;
                    int yTry = coorY+multipliedyDir;
                    if(xTry<0||xTry>copyboard.length-1||yTry<0||yTry>copyboard[0].length-1){

                    }else if(copyboard[xTry][yTry]==emptyChar[0]){
                        distanceThatWorked = distanceMulitplier;
                        previousMultiplicityWorked = true;
                        distanceMulitplier++;
                    }
                }
                int distanceWeUse = 0;
                if(distanceThatWorked>0){
                    if(distanceThatWorked ==1){
                        distanceWeUse = 1;
                    }else{
                        distanceWeUse = ThreadLocalRandom.current().nextInt(1, distanceThatWorked + 1);
                    }
                    copyboard[coorX][coorY] = emptyChar[0];
                    newCoorX = coorX+(direction[0]*distanceWeUse);
                    newCoorY = coorY+(direction[1]*distanceWeUse);
                    copyboard[newCoorX][newCoorY] = whoseTurn;
                    WeDidAMove = true;
                    if (direction[0]*direction[1]==0){
                        nondiagonalMoves++;
                    }else{
                        diagonalMoves++;
                    }
                    System.out.println("DiagonalMoves: "+ diagonalMoves + " |||| Non-diagonalMoves: " +nondiagonalMoves);
                    moveAmount++;
                    moveLengthsum+= distanceWeUse;

                    System.out.println("Avg distance: "+moveLengthsum/moveAmount);
                    break;
                }
            }

            if(WeDidAMove){break;}

        }
        return takeRandomShot(copyboard, newCoorX, newCoorY);
    }

    public char[][] takeRandomShot(char[][] boardState, int amazonCoorX, int amazonCoorY){
        biasedShuffle();
        for(int[] direction: directionList){
            int xDir = direction[0];
            int yDir = direction[1];
            int distanceMulitplier = 1;
            boolean previousMultiplicityWorked = true;
            int distanceThatWorked = 0;
            while(previousMultiplicityWorked){
                previousMultiplicityWorked = false;
                int multipliedxDir = xDir*distanceMulitplier;
                int multipliedyDir = yDir*distanceMulitplier;
                int xTry = amazonCoorX+multipliedxDir;
                int yTry = amazonCoorY+multipliedyDir;
                if(xTry<0||xTry>boardState.length-1||yTry<0||yTry>boardState[0].length-1){

                }else if(boardState[xTry][yTry]==emptyChar[0]){
                    distanceThatWorked = distanceMulitplier;
                    previousMultiplicityWorked = true;
                    distanceMulitplier++;
                }
            }
            int distanceWeUse = 0;
            if(distanceThatWorked>0){
                if(distanceThatWorked ==1){
                    distanceWeUse = 1;
                }else{
                    distanceWeUse = ThreadLocalRandom.current().nextInt(1, distanceThatWorked + 1);
                }
                boardState[amazonCoorX+(direction[0]*distanceWeUse)][amazonCoorY+(direction[1]*distanceWeUse)] = 'A';
                //WeDidAMove = true;
                break;
            }
        }
        return boardState;
    }

    public double expandRandomly(char AIside, char whoseTurn, char[][] startState){
        char[][]copyBoard = copyCharMatrix(startState);
        printCharMatrix(copyBoard);

        //This is slow!
        ArrayList<char[][]> nextStates = generateNextPossibleStates(whoseTurn, copyBoard);
        //!!

        if(nextStates.size()==0){
            if(AIside == whoseTurn){
                System.out.println(AIside+"Lost");
                return 0;

            }else{
                System.out.println(AIside+"Won");
                return 1;
            }
        }

        int randomindex = randomGenerator.nextInt(nextStates.size());
        return expandRandomly(AIside, switchSide(whoseTurn), nextStates.get(randomindex));

    }

    public void expandRandomlyTestMethod(char whoseTurn, char[][] startState){
        for(int i = 0; i< 200; i++){
            char[][] nextState;
            try {
                nextState = getNextRandomState(whoseTurn, startState);
            }catch(Exception e){
                System.out.println(e.getMessage());
                break;
            }
            //printCharMatrix(startState);
            startState = nextState;
            whoseTurn = switchSide(whoseTurn);
        }
    }


    public static void printCharMatrix ( char[][] matrix){
        char[] blankchar = new char[1];
        System.out.println(" ");
        System.out.println("___________________________________");
        System.out.println(" ");
        System.out.println("___________________________________");
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(" ");
            System.out.println(" ");

            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(" [");
                if(matrix[i][j] == blankchar[0]){
                    System.out.print(" ");
                }else if(matrix[i][j] == 'A'){
                    System.out.print("@");
                }else if(matrix[i][j]=='B'||matrix[i][j]=='W'){
                    System.out.print(ConsoleColors.RED + matrix[i][j] + ConsoleColors.RESET);
                }
                //System.out.print(matrix[i][j]);

                System.out.print("] ");
            }
        }
        System.out.println(" ");
        System.out.println("___________________________________");
        System.out.println(" ");
        System.out.println("___________________________________");
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

    public static char switchSide(char input){
        if(input == 'B'){return 'W';}
        return 'B';
    }



}