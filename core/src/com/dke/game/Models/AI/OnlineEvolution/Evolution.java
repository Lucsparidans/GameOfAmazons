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
    private int compGenerations;
    private int compPopSize;
    private Genome[] parentPopulation;
    private int numberCompGens = 1;
    private int popSize;
    private Random rnd;
    private final int genomeLength = 0;
    private Genome best;
    private int threshold;
    private int genCount = 0;
    private int compGenCount = 0;
    private Player player;
    private Player opponent;
    private GameLoop gameLoop;
    private float mutationRate = 0.1f;
    private float crossovers;
    private boolean competitiveCoevolution;
    public static boolean debugPrinting = true;

    /**
     * Constructor
     * @param amazons
     * @param arrows
     */
    public Evolution(Amazon2D[] amazons, ArrayList<Arrow2D> arrows, GameLoop gameLoop, boolean competitiveCoevolution) {
        initializeVariables(amazons, arrows, gameLoop, competitiveCoevolution);
    }
    public Evolution(Amazon2D[] amazons, ArrayList<Arrow2D> arrows, GameLoop gameLoop, boolean competitiveCoevolution,int compGenerations, int compPopSize, Genome[] parentPopulation){
        initializeVariables(amazons,arrows,gameLoop,competitiveCoevolution);
        this.compGenerations = compGenerations;
        this.compPopSize = compPopSize;
        this.parentPopulation = parentPopulation;
    }

    private void updateVariablesPhase(){
        if(GameLoop.PHASE == GameLoop.Phase.BEGIN){
            this.generations = 4;
            this.popSize = 100;
            this.threshold = popSize/2;
            crossovers = popSize /4;
            this.population = new Genome[popSize];
            if(debugPrinting) {
                System.out.println("Begin phase");
            }
        }else if(GameLoop.PHASE == GameLoop.Phase.MIDDLE){
            generations = 5;
            popSize = 500;
            this.threshold = popSize/2;
            crossovers = popSize /4;
            this.population = new Genome[popSize];
            if(debugPrinting) {
                System.out.println("Middle phase");
            }
        }
        //TODO second move already is ending phase
        else if(GameLoop.PHASE == GameLoop.Phase.END){
            generations = 6;
            popSize = 700;
            this.threshold = popSize/2;
            crossovers = popSize /4;
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
    private void initializeVariables(Amazon2D[] amazons, ArrayList<Arrow2D> arrows, GameLoop gameLoop, boolean competitiveCoevolution){

        initialBoard = new TestBoard(amazons,arrows);
        population = new Genome[popSize];
        this.gameLoop = gameLoop;
        this.rnd = new Random();
        this.competitiveCoevolution = competitiveCoevolution;

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

            for (int i = 0; i < popSize * mutationRate; i++) {
                try {

                    population[rnd.nextInt(popSize)].mutate();
                } catch (Action.InvalidActionTypeException e) {
                    e.printStackTrace();
                }
            }
            Genome[] crossover = new Genome[popSize];
            int counter = 0;
            for (int i = 0; i < crossovers; i++) {
                try {
                    crossover[counter] = population[rnd.nextInt(popSize)].crossover(population[rnd.nextInt(popSize)]);
                    if(crossover[counter] != null) {
                        counter++;
                    }
                } catch (Action.InvalidActionTypeException e) {
                    e.printStackTrace();
                }
            }
            if(counter>=1) {
                crossover = removeNullElements(crossover);
                if (Evolution.debugPrinting) {
                    System.out.println("###############################################################");
                    System.out.printf("                     Crossover array:\n");
                    System.out.println("###############################################################");
                    System.out.println();
                    for (int i = 0; i < crossover.length; i++) {
                        System.out.printf("Crossover %d has value: %f\n", i, crossover[i].getEval());
                    }
                }
            }
            Arrays.sort(population);
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
            if(Evolution.debugPrinting) {
                System.out.println("###############################################################");
                System.out.printf("                     Sorted array:\n");
                System.out.println("###############################################################");
                System.out.println();
                for (int i = 0; i < population.length; i++) {
                    System.out.printf("Population %d has value: %f\n", i, population[i].getEval());
                }
            }

            this.best = bestGenome;
        }
        if(this.competitiveCoevolution){
            Genome[] opponentBest = new Genome[numberCompGens];
            Evolution[] ourBest = new Evolution[numberCompGens];
            for (int i = 0; i < numberCompGens; i++) {
                opponentBest[i] = parentPopulation[(parentPopulation.length-1)-i];
            }
            for (int i = 0; i < ourBest.length; i++) {
                TestBoard curOppBoard = opponentBest[i].getCurrentBoard();
                ourBest[i] = new Evolution(curOppBoard.getAmazons(),curOppBoard.getArrows(),this.gameLoop,false,compGenerations,compPopSize,population);
                ourBest[i].initialize(opponent);
            }

        }

        return population;
    }

    private Genome[] removeNullElements(Genome[] g){
        ArrayList<Genome> genomes = new ArrayList<>();
        for (Genome genome :
                g) {
            if(genome!=null){
                genomes.add(genome);
            }
        }
        if(!genomes.isEmpty()){
            Genome[] gens = new Genome[genomes.size()];
            int counter = 0;
            for (Genome genome :
                    genomes) {
                gens[counter] = genome;
                counter++;
            }
            return gens;
        }
        return g;
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
        return this.best.getMove();
    }

    /**
     * When generating a move the second time and further the board needs to be updated
     * @param gameLoop
     */
    private void updateInitialBoard(GameLoop gameLoop){
        this.initialBoard = new TestBoard(gameLoop.getAmazons(),gameLoop.getArrows());
    }

    @Override
    public void initialize(Player p) {
        this.player = p;
        if(p.getSide() == 'W'){
            opponent = gameLoop.getBlack();
        }
        else{
            opponent = gameLoop.getWhite();
        }


    }



    public Genome getBest() {
        return best;
    }
}
