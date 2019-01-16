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

/**
 * Main class for the online evolution algorithm
 */
public class Evolution implements Algorithm {
    private TestBoard initialBoard;
    private Genome[] population;
    private int generations = 5;
    private int popSize = 100;
    private final int genomeLength = 0;
    private Move best;
    private final int threshold = popSize/2;
    private int genCount = 0;
    private Player player;
    private GameLoop gameLoop;
    private Thread genThread;
    public static final boolean debugPrinting = false;

    /**
     * Constructor
     * @param amazons
     * @param arrows
     */
    public Evolution(Amazon2D[] amazons, ArrayList<Arrow2D> arrows, GameLoop gameLoop) {
        initializeVariables(amazons, arrows, gameLoop);
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
                for (int i = 0; i < threshold; i++) {
                    if(Evolution.debugPrinting) {
                        System.out.printf("new Population: %d" +
                                "\n", i + 1);
                    }
                    population[i] = new Genome(initialBoard.deepCopy(), genomeLength, player);
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

                for (int i = 0; i < population.length; i++) {
                    System.out.printf("Population %d has value: %f\n",i,population[i].getEval());
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
    }
    @Override
    public Move getBestMove(AI player) {
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
