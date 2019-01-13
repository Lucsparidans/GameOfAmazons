package com.dke.game.Models.AI.OnlineEvolution;

import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.MINMAX.TestBoard;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;

import java.util.ArrayList;
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
    private void generateMoves(TestBoard testBoard, char side) {
        if(side=='W'){
            Amazon2D a = whiteAmazons.get(rnd.nextInt(whiteAmazons.size()));
            generateMoves(a,testBoard,true);
        }
        else if(side=='B'){
            Amazon2D a = blackAmazons.get(rnd.nextInt(blackAmazons.size()));
            generateMoves(a,testBoard,false);
        }

    }

    /**
     * Method to avoid duplicate code
     * @param a
     * @param board
     */
    private void generateMoves(Amazon2D a, TestBoard board, boolean whiteMove){
        try {
            TestBoard testBoard = board.deepCopy();
            ArrayList<Cell> possibleMoves = a.possibleMoves(testBoard);
            Cell moveCell = possibleMoves.get(rnd.nextInt(possibleMoves.size()));
            a.move(moveCell);
            Action moveAction = new Action(Action.ActionType.MOVE, moveCell);
            actionSequence.add(moveAction);
            if (gameStates.isEmpty()) {
                gameStates.add(new GameState(moveAction, null, testBoard,whiteMove));
            } else {
                gameStates.add(new GameState(moveAction, gameStates.get(gameStates.size() - 1), testBoard,whiteMove));
            }
            testBoard = testBoard.deepCopy();
            a.possibleMoves(testBoard);
            Cell shootCell = possibleMoves.get(rnd.nextInt(possibleMoves.size()));
            a.shoot(shootCell);
            Action shootAction = new Action(Action.ActionType.SHOT, shootCell);
            actionSequence.add(shootAction);
            gameStates.add(new GameState(shootAction, gameStates.get(gameStates.size() - 1), testBoard,whiteMove));
            actionPair.put(moveAction,shootAction);
            actionPair.put(shootAction,moveAction);
        }
        catch(Action.InvalidActionTypeException e){
            System.out.println("Invalid action type?!");
        }
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
            return moveWeight*(newMovW-prevMovW)+(1-moveWeight)*(newMovB-prevMovB);
        }
        else{
            return (1-moveWeight)*(newMovW-prevMovW)+moveWeight*(newMovB-prevMovB);
        }
    }
}
