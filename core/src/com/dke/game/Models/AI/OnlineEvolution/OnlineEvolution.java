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
     * @param player
     */
    public OnlineEvolution(Amazon2D[] amazons, ArrayList<Arrow2D> arrows, Player player) {
        initializeVariables(player, amazons, arrows);
    }

    /**
     * Method to avoid clutter in the constructor
     * @param player
     * @param amazons
     * @param arrows
     */
    private void initializeVariables(Player player, Amazon2D[] amazons, ArrayList<Arrow2D> arrows){
        initialBoard = new TestBoard(amazons,arrows);
        population = new Genome[popSize];
        for (int i = 0; i < popSize; i++) {
            population[i] = new Genome(initialBoard,genomeLength,player);
        }
    }
    @Override
    public Move getBestMove(AI player) {
        return null;
    }
}
