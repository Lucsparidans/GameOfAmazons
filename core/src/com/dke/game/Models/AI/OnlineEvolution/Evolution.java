package com.dke.game.Models.AI.OnlineEvolution;

import com.dke.game.Controller.GameLoop;
import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.MINMAX.TestBoard;
import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import com.dke.game.Views.OptionsView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Main class for the online evolution algorithm
 */
public class Evolution implements Algorithm, Comparable {
    private TestBoard initialBoard;
    private Genome[] population;
    private int generations;
    private int compGenerations;
    private int compPopSize;
    private Genome[] parentPopulation;
    private int numberCompGens = 5;
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
    private boolean debug = false;
    public static boolean debugPrinting = true;

    /**
     * Constructor
     * @param amazons
     * @param arrows
     */
    public Evolution(Amazon2D[] amazons, ArrayList<Arrow2D> arrows, GameLoop gameLoop, boolean competitiveCoevolution) {
        initializeVariables(amazons, arrows, gameLoop, competitiveCoevolution);
    }
    public Evolution(Amazon2D[] amazons, ArrayList<Arrow2D> arrows, GameLoop gameLoop, boolean competitiveCoevolution,int Generations, int PopSize, Genome[] parentPopulation){
        initializeVariables(amazons,arrows,gameLoop,competitiveCoevolution);
        //Below initializing the variables for the coevolution because values are different from the updateVariablesPhase() method.
        this.generations = Generations;
        this.popSize = PopSize;
        this.population = new Genome[popSize];
        this.threshold = popSize / 2;
        this.crossovers = popSize / 4;
        this.parentPopulation = parentPopulation;
    }

    private void updateVariablesPhase() {
        if (GameLoop.PHASE == GameLoop.Phase.BEGIN) {
            this.generations = 5;                           //Number of generation for the evolution
            this.popSize = 100;                               //Number of elements in each population in this evolution
            this.threshold = popSize / 2;                   //Number of elements to be selected for redo for the next generation
            crossovers = popSize / 4;                       //Number of crossovers
            this.compPopSize = 100;                           //Number of elements in the coevolution populations
            this.compGenerations = 2;                       //Number of generations generated in the coevolution
            this.population = new Genome[popSize];
        } else if (GameLoop.PHASE == GameLoop.Phase.MIDDLE) {
            generations = 5;
            popSize = 500;
            this.threshold = popSize / 2;
            crossovers = popSize / 4;
            this.compPopSize = 2;
            this.compGenerations = 1;
            this.population = new Genome[popSize];
        } else if (GameLoop.PHASE == GameLoop.Phase.END) {
            generations = 6;
            popSize = 700;
            this.threshold = popSize / 2;
            crossovers = popSize / 4;
            this.compPopSize = 2;
            this.compGenerations = 1;
            this.population = new Genome[popSize];
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

                //<editor-fold desc="Crossover and mutation">
//                                for (int i = 0; i < popSize * mutationRate; i++) {
//                    try {
//
//                        population[rnd.nextInt(popSize)].mutate();
//                    } catch (Action.InvalidActionTypeException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Genome[] crossover = new Genome[popSize];
//                int counter = 0;
//                for (int i = 0; i < crossovers; i++) {
//                    try {
//                        crossover[counter] = population[rnd.nextInt(popSize)].crossover(population[rnd.nextInt(popSize)]);
//                        if(crossover[counter] != null) {
//                            counter++;
//                        }
//                    } catch (Action.InvalidActionTypeException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(counter>=1) {
//                    crossover = removeNullElements(crossover);
//                    if (Evolution.debugPrinting) {
//                        System.out.println("###############################################################");
//                        System.out.printf("                     Crossover array:\n");
//                        System.out.println("###############################################################");
//                        System.out.println();
//                        for (int i = 0; i < crossover.length; i++) {
//                            System.out.printf("Crossover %d has value: %f\n", i, crossover[i].getEval());
//                        }
//                    }
//                }
                //</editor-fold>

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
                if(Evolution.debugPrinting && debug) {
                    System.out.println("###############################################################");
                    System.out.printf("                     Sorted array:\n");
                    System.out.println("###############################################################");
                    System.out.println();
                    for (int i = 0; i < population.length; i++) {
                        System.out.printf("Population %d has value: %f\n", i+1, population[i].getEval());
                    }
                    System.out.println();
                }

                this.best = bestGenome;
            }
            if(this.competitiveCoevolution){
                Genome[] ourBest = new Genome[numberCompGens];
                Evolution[] opponentBest = new Evolution[numberCompGens];
                for (int i = 0; i < numberCompGens; i++) {
                    ourBest[i] = population[(population.length-1)-i];
                }
                for (int i = 0; i < ourBest.length; i++) {
                    TestBoard curOppBoard = ourBest[i].getCurrentBoard();
                    if(debugPrinting) {
                        System.out.println("---------------------------------------------------------------");
                        System.out.printf("                     Board for coevolution:\n");
                        curOppBoard.printBoard();
                        System.out.println("---------------------------------------------------------------");
                        System.out.println();
                    }
                    opponentBest[i] = new Evolution(curOppBoard.getAmazons(),curOppBoard.getArrows(),this.gameLoop,false,compGenerations,compPopSize,population);
                    opponentBest[i].initialize(opponent);
                    opponentBest[i].computeBestMove();
                }
                double[] finalValues = new double[numberCompGens];
                double bestEval = Double.NEGATIVE_INFINITY;
                for (int i = 0; i < finalValues.length; i++) {
                    finalValues[i] = ourBest[i].getEval() - opponentBest[i].getBest().getEval();
                    if(debug) {
                        System.out.printf("Index: %d gives =>\n" +
                                "Ourbest.value: %f\n" +
                                " OpponentBest.value: %f\n",i,ourBest[i].getEval(),opponentBest[i].getBest().getEval());
                        System.out.printf("Final values: %f\n", finalValues[i]);
                    }
                    if(finalValues[i] > bestEval){
                        this.best = ourBest[i];
                        bestEval = finalValues[i];
                    }
                }
                if(debug){
                    System.out.printf("The best evaluation found has value: %f\n",bestEval);
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
            if(OptionsView.TESTING){
                debugPrinting = false;
                Evolution[] evolutions = new Evolution[OptionsView.TEST_ITERATIONS];
                System.out.println("----------Test evoltions----------");
                for (int i = 0; i < OptionsView.TEST_ITERATIONS; i++) {
                    evolutions[i] = new Evolution(initialBoard.getAmazons(), initialBoard.getArrows(), this.gameLoop, competitiveCoevolution, generations, popSize,null);

                    evolutions[i].initialize(player);
                    evolutions[i].computeBestMove();

                }
                Arrays.sort(evolutions);
                for (Evolution e :
                        evolutions) {
                    System.out.printf("Evolution has best.value: %f\n",e.getBest().getEval());
                }
            }
            return this.best.getMove();
        }

        private void computeBestMove(){
            computeGenerations(this.player);
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
            //System.out.println("Player: " + player.getClass().getSimpleName());
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

    @Override
    public int compareTo(Object o) {
            if(o instanceof Evolution) {
                Evolution e = (Evolution)o;
                if (this.getBest().getEval() > e.getBest().getEval()){
                    return 1;
                }
                else if(this.getBest().getEval() == e.getBest().getEval()){
                    return 0;
                }else if(this.getBest().getEval() < e.getBest().getEval()){
                    return -1;
                }
            }
            return 0;
    }
}
