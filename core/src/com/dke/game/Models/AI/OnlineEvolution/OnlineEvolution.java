package com.dke.game.Models.AI.OnlineEvolution;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.MINMAX.TestBoard;
import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;

/**
 * Main class for the online evolution algorithm
 */
public class OnlineEvolution implements Algorithm {
    private TestBoard initialBoard;
    private Genome[] population;
    private int popSize = 100;
    private final int genomeLength = 1;

    /**
     * Constructor
     * @param amazons
     * @param arrows
     */
    public OnlineEvolution(Amazon2D[] amazons, ArrayList<Arrow2D> arrows) {
        initializeVariables(amazons, arrows);

    }

    /**
     * Method to avoid clutter in the constructor
     * @param amazons
     * @param arrows
     */
    private void initializeVariables(Amazon2D[] amazons, ArrayList<Arrow2D> arrows){
        initialBoard = new TestBoard(amazons,arrows);
        population = new Genome[popSize];

    }
    @Override
    public Move getBestMove(AI player) {
        Genome best = null;
        for (int i = 0; i < popSize; i++) {
            System.out.printf("Population: %d" +
                    "\n", i+1);
            population[i] = new Genome(initialBoard,genomeLength,player);
        }
        for (Genome g :
                population) {
            if(best == null){
                best = g;
            }
            else {
                if (g.getEval() > best.getEval()) {
                    best = g;
                }
            }
        }
        return best.getMove();
    }
}
