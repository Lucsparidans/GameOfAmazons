package com.dke.game.Models.AI.OnlineEvolution;

import com.dke.game.Controller.GameLoop;
import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.MINMAX.TestBoard;
import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Main class for the online evolution algorithm
 */
public class Evolution implements Algorithm {
    private TestBoard initialBoard;
    private Genome[] population;
    private int generations;
    private int popSize;
    private Random rnd;
    private final int genomeLength = 0;
    private Move best;
    private int threshold;
    private int genCount = 0;
    private Player player;
    private GameLoop gameLoop;
    private Thread genThread;
    private float mutationRate = 0.5f;
    private float crossovers = popSize/10;
    public static boolean debugPrinting = true;

    /**
     * Constructor
     * @param amazons
     * @param arrows
     */
    public Evolution(Amazon2D[] amazons, ArrayList<Arrow2D> arrows, GameLoop gameLoop) {
        initializeVariables(amazons, arrows, gameLoop);
    }

    private void updateVariablesPhase(){
        if(GameLoop.PHASE == GameLoop.Phase.BEGIN){
            this.generations = 2;
            this.popSize = 2;
            this.threshold = popSize/2;
            this.population = new Genome[popSize];
            if(debugPrinting) {
                System.out.println("Begin phase");
            }
        }else if(GameLoop.PHASE == GameLoop.Phase.MIDDLE){
            generations = 5;
            popSize = 500;
            this.threshold = popSize/2;
            this.population = new Genome[popSize];
            if(debugPrinting) {
                System.out.println("Middle phase");
            }
        }
        else if(GameLoop.PHASE == GameLoop.Phase.END){
            generations = 6;
            popSize = 700;
            this.threshold = popSize/2;
            this.population = new Genome[popSize];
            if(debugPrinting) {
                System.out.println("End phase");
            }
        }
    }

    /**
     * Method to avoid clutter in the constructor
     * @param amazons
     * @param arrows
     */
    private void initializeVariables(Amazon2D[] amazons, ArrayList<Arrow2D> arrows, GameLoop gameLoop){

        initialBoard = new TestBoard(amazons,arrows);
        population = new Genome[popSize];
        this.gameLoop = gameLoop;
        this.rnd = new Random();


    }

    /**
     * Generates the various generations which all consist of populations with the popSize set at the top
     * @param player
     * @return
     */
    private Genome[] computeGenerations(Player player){
        this.best = null;
        genCount = 0;
        Genome bestGenome = null;
        for(int gen = 0; gen < generations; gen++) {
            if(debugPrinting) {
                printGenCount();
            }
            if (gen > 0) {
                if(popSize == 1){
                    for (int i = 0; i < popSize; i++) {
                        if (Evolution.debugPrinting) {
                            System.out.printf("new Population: %d" +
                                    "\n", i + 1);
                        }
                        population[i] = new Genome(initialBoard.deepCopy(), genomeLength, player);
                    }
                }
                else {
                    for (int i = 0; i < threshold; i++) {
                        if (Evolution.debugPrinting) {
                            System.out.printf("new Population: %d" +
                                    "\n", i + 1);
                        }
                        population[i] = new Genome(initialBoard.deepCopy(), genomeLength, player);
                    }
                }
            } else {
                for (int i = 0; i < popSize; i++) {
                    if(Evolution.debugPrinting) {
                        System.out.printf("Population: %d" +
                                "\n", i + 1);
                    }
                    population[i] = new Genome(initialBoard.deepCopy(), genomeLength, player);
                }
            }

            for (Genome g :
                    population) {
                if (bestGenome == null) {
                    bestGenome = g;
                } else {
                    if (g.getEval() > bestGenome.getEval()) {
                        bestGenome = g;
                    }
                    else if( g.getEval() == bestGenome.getEval()){
                        if(Math.floor(Math.random()*2) ==1){
                            bestGenome = g;
                        }
                    }
                }
            }
            Arrays.sort(population);
            if(debugPrinting) {
                System.out.println("###############################################################");
                System.out.printf("                     Sorted array:\n");
                System.out.println("###############################################################");
                System.out.println();
            }
            for (int i = 0; i < popSize * mutationRate; i++) {
                try {
                    population[rnd.nextInt(popSize)].mutate();
                } catch (Action.InvalidActionTypeException e) {
                    e.printStackTrace();
                }
            }
//                for (int i = 0; i < crossovers; i++) {
//
//                }
            Arrays.sort(population);
            if(population[popSize-1].getEval() > bestGenome.getEval()){
                best = population[popSize-1].getMove();
            }
            if(Evolution.debugPrinting) {
                for (int i = 0; i < population.length; i++) {
                    System.out.println("###############################################################");
                    System.out.printf("                    New Sorted array:\n");
                    System.out.println("###############################################################");
                    System.out.println();
                    System.out.printf("Population %d has value: %f\n", i, population[i].getEval());
                }
            }

            this.best = bestGenome.getMove();
        }

        return population;
    }

    private void printGenCount(){
        System.out.println("###############################################################");
        System.out.printf("                     Generation: %d \n", ++genCount);
        System.out.println("###############################################################");
        System.out.println();


    }
    @Override
    public Move getBestMove(AI player) {
        updateVariablesPhase();
        updateInitialBoard(player.getGameLoop());
        computeGenerations(player);
        return this.best;
    }

    /**
     * When generating a move the second time and further the board needs to be updated
     * @param gameLoop
     */
    private void updateInitialBoard(GameLoop gameLoop){
        this.initialBoard = new TestBoard(gameLoop.getAmazons(),gameLoop.getArrows());
    }

    @Override
    public void initialize(AI p) {
        synchronized (this) {
            this.player = p;
            this.genThread = new Thread(new GenomeGenerator());

        }

    }

    private class GenomeGenerator implements Runnable{
        @Override
        public void run() {

        }
    }


}
