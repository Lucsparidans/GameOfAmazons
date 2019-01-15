package com.dke.game.Models.AI.OnlineEvolution;

import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.MINMAX.TestBoard;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

/**
 * This is meant as a sequence of gameStates and actions to allow for opponent modeling in this algorithm, the genome in this case was
 * interpreted to be what usually a population would be.
 */
public class Genome {
    private HashMap actionPair;
    private ArrayList<GameState> gameStates;
    private ArrayList<Action> actionSequence;
    private double moveWeight = 0.8;
    private GameState initialState;
    private Random rnd;
    private Amazon2D[] amazons;
    private ArrayList<Amazon2D> whiteAmazons;
    private ArrayList<Amazon2D> blackAmazons;
    private double eval = 0;

    /**
     * Method that creates the action sequence with the corresponding gameState for this population
     * @param initialBoard
     * @param genomeLength
     */
    public Genome(TestBoard initialBoard, int genomeLength, Player currentPlayer) {
        initializeVariables(initialBoard,currentPlayer);
        System.out.printf("The initial board: \n");
        initialBoard.printBoard();
//        TestBoard test = initialBoard.deepCopy();
//        test.getAmazons()[0].move(test.getBoard()[3][3]);
//        test.getAmazons()[0].shoot(test.getBoard()[3][4]);
//        System.out.println("The initial board after copying and altering the copy");
//        initialBoard.printBoard();
//        System.out.println("The altered copy of the initialboard");
//        test.printBoard();
        generateGenome(initialBoard, genomeLength, currentPlayer);
    }

    /**
     * Initializes all fields here to avoid clutter in the constructor
     * @param initialBoard
     */
    private void initializeVariables(TestBoard initialBoard, Player currentPlayer){
        rnd = new Random();
        actionPair = new HashMap();
        gameStates = new ArrayList<>();
        whiteAmazons = new ArrayList<>();
        blackAmazons = new ArrayList<>();
        actionSequence = new ArrayList<>();
        amazons = initialBoard.getAmazons();
        for (Amazon2D amazon :
                amazons) {
            if (amazon.getSide() == 'W') {
                whiteAmazons.add(amazon);
            }
            else if(amazon.getSide() == 'B'){
                blackAmazons.add(amazon);
            }
        }
        if(currentPlayer.getSide()=='W') {
            initialState = new GameState(null, null, initialBoard, true);
        }
        else{
            initialState = new GameState(null,null,initialBoard,false);
        }
    }

    /**
     * Generate action-state sequence by randomly selecting a move at each level of the tree
     * @param board
     * @param genomeLength represents the wanted length of the action-state sequence
     * @param currentPlayer
     */
    private void generateGenome(TestBoard board, int genomeLength, Player currentPlayer){
        char currentSide = currentPlayer.getSide();
        while(genomeLength >= 0) {
            generateMoves(board,currentSide);
            System.out.println("Current shared board:");
            board.printBoard();

            //Toggle side and reduce depth to be generated
            if(currentSide=='W'){
                currentSide='B';
            }
            else if(currentSide=='B'){
                currentSide='W';
            }
            genomeLength--;
        }
    }

    /**
     * Method to create every node from one specific situation in the game
     * @param testBoard
     * @param side
     */
    private TestBoard generateMoves(TestBoard testBoard, char side) {
        if(side=='W'){
            Amazon2D a = whiteAmazons.get(rnd.nextInt(whiteAmazons.size()));
            return generateMoves(a,testBoard,true);
        }
        else if(side=='B'){
            Amazon2D a = blackAmazons.get(rnd.nextInt(blackAmazons.size()));
            return generateMoves(a,testBoard,false);
        }
        return testBoard;

    }

    /**
     * Finds a random amazon which can move
     * @param amazon
     * @param possibleMoves
     * @param board
     * @return
     */
    private Amazon2D canMove(Amazon2D amazon, ArrayList<Cell> possibleMoves, TestBoard board){
        for (Amazon2D a :
                amazons) {
            if (amazon != a && a.getSide() == amazon.getSide() && a.possibleMoves(board).size()!=0) {
                amazon = a;
                return amazon;
            }
        }
        return null;
    }

    /**
     * Method to avoid duplicate code
     * @param amazon
     * @param board
     */
    private TestBoard generateMoves(Amazon2D amazon, TestBoard board, boolean whiteMove){
        try {
            Cell[][] boardCoordinates = board.getBoard();
            ArrayList<Cell> possibleMoves = amazon.possibleMoves(board);
            if(possibleMoves.isEmpty()){
                amazon = canMove(amazon,possibleMoves,board);
            }
            Cell moveCell = possibleMoves.get(rnd.nextInt(possibleMoves.size()));
            amazon.move(moveCell);
            Action moveAction = new Action(Action.ActionType.MOVE, moveCell,amazon);
            actionSequence.add(moveAction);
            if (gameStates.isEmpty()) {
                gameStates.add(new GameState(moveAction, null, board.deepCopy(),whiteMove));
            } else {
                gameStates.add(new GameState(moveAction, gameStates.get(gameStates.size() - 1), board.deepCopy(),whiteMove));
            }
            //board.printBoard();
            possibleMoves = amazon.possibleMoves(board);
            if(possibleMoves.isEmpty()){
                amazon = canMove(amazon,possibleMoves,board);
            }
            Cell shootCell = possibleMoves.get(rnd.nextInt(possibleMoves.size()));
            amazon.shoot(board.getBoard()[shootCell.getI()][shootCell.getJ()]);
            Action shootAction = new Action(Action.ActionType.SHOT, shootCell,amazon);
            actionSequence.add(shootAction);
            gameStates.add(new GameState(shootAction, gameStates.get(gameStates.size() - 1), board.deepCopy(),whiteMove));
            board.printBoard();
            actionPair.put(moveAction,shootAction);
            actionPair.put(shootAction,moveAction);
            return board;
        }
        catch(Action.InvalidActionTypeException e){
            System.out.println("Invalid action type?!");
        }
        return board;
    }
    // TODO create crossover functionality
    private void crossover(){

    }
    //TODO create mutation functionality
    private void mutation(){

    }
    //TODO create legality check for new moves produced by mutating and crossing over
    private boolean checkLegal(){
        return false;
    }

    /**
     * return evaluation
     * @return
     */
    public double getEval(){
        return evaluateGenome(gameStates.get(gameStates.size()-1));
    }
    /**
     * Simple heuristic function foro testing
     * @param state
     * @return
     */
    private double evaluateGenome(GameState state){
        TestBoard initialBoard = state.getParentBoard();
        TestBoard newBoard = state.getBoard();
        int prevMovW = 0;
        int prevMovB = 0;
        int newMovW = 0;
        int newMovB = 0;
        for (Amazon2D a :
                initialBoard.getAmazons()) {
            if (a.getSide() == 'W') {
                prevMovW += a.possibleMoves(initialBoard).size();
            } else if (a.getSide() == 'B') {
                prevMovB += a.possibleMoves(initialBoard).size();
            }
        }
        for (Amazon2D a :
                newBoard.getAmazons()) {
            if (a.getSide() == 'W') {
                newMovW += a.possibleMoves(newBoard).size();
            } else if (a.getSide() == 'B') {
                newMovB += a.possibleMoves(newBoard).size();
            }

        }
        if(state.isWhiteMove()) {
            return moveWeight*(newMovW-prevMovW)-(1-moveWeight)*(newMovB-prevMovB);
        }
        else{
            return moveWeight*(newMovB-prevMovB)-(1-moveWeight)*(newMovW-prevMovW);
        }
    }
    public Move getMove(){
        return new Move(actionSequence.get(0).getAmazon(),actionSequence.get(0).getDestination(),actionSequence.get(1).getDestination());
    }


}
