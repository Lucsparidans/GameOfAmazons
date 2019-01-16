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
public class Genome implements Comparable{
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
    private final boolean debug = false;

    /**
     * Method that creates the action sequence with the corresponding gameState for this population
     * @param initialBoard
     * @param genomeLength
     */
    public Genome(TestBoard initialBoard, int genomeLength, Player currentPlayer) {
        initializeVariables(initialBoard,currentPlayer);
        if(Evolution.debugPrinting) {
            System.out.printf("The initial board: \n");
            initialBoard.printBoard();
        }
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
            initialState = new GameState(null, null, initialBoard.deepCopy(), true);
        }
        else{
            initialState = new GameState(null,null,initialBoard.deepCopy(),false);
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
            if (amazon != a && a.getSide() == amazon.getSide()) {
                if(a.possibleMoves(board).size()>0){
                    return a;
                }
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

            ArrayList<Cell> possibleMoves = amazon.possibleMoves(board);
            if(possibleMoves.isEmpty()){
                amazon = canMove(amazon,possibleMoves,board);
                possibleMoves = amazon.possibleMoves(board);
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
            if(Evolution.debugPrinting) {
                board.printBoard();
            }

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
    private void crossover(Genome g){
        ArrayList<Action> gActions = g.getActionSequence();
        boolean compatible = true;
        for (int i = 0; i < actionSequence.size()-1; i++) {
            if(gActions.get(i) != actionSequence.get(i)){
                compatible = false;
            }
        }
        int index = actionSequence.size()-1;
        if(compatible){
            actionSequence.add(index,gActions.get(gActions.size()-1));
        }
    }
    //TODO create mutation functionality
    private void mutation(){
        if(rnd.nextBoolean()){
            //mutate move
        }
        else{
            //mutate shot
        }
    }
    //TODO create legality check for new moves produced by mutating and crossing over
    private boolean checkLegal(){
        return false;
    }

    //TODO method that performs the crossover and mutation on this (and some other) genome

    /**
     * return evaluation
     * @return
     */
    public double getEval(){
        return evaluateGenome(gameStates.get(gameStates.size()-1));
    }
    /**
     * Simple heuristic function for testing
     * @param state
     * @return
     */
    private double evaluateGenome(GameState state){
        TestBoard initialBoard = initialState.getBoard();
        TestBoard newBoard = state.getBoard();
        if(debug) {
            System.out.println("Initial board heur:");
            initialBoard.printBoard();
            System.out.println("New board heur:");
            newBoard.printBoard();
        }
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

    public ArrayList<Action> getActionSequence() {
        return actionSequence;
    }

    /**
     * Comparable interface for sorting purposes
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        if(o instanceof Genome) {
            if (this.getEval() > ((Genome) o).getEval()){
                return 1;
            }
            else if(this.getEval() < ((Genome) o).getEval()){
                return -1;
            }
            else return 0;
        }
        return 0;
    }
}
