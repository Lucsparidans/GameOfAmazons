package com.dke.game.Models.AI.OnlineEvolution;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.dke.game.Controller.GameLoop;
import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.MINMAX.TestBoard;
import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;
import org.lwjgl.opengl.GLContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Main class for the online evolution algorithm
 */
public class OnlineEvolution implements Algorithm {
    private TestBoard initialBoard;
    private Genome[] population;
    private int generations = 2;
    private int popSize = 100;
    private final int genomeLength = 0;
    private Move best;
    private Player player;
    private Runnable r;
    private Thread genThread;
    private GameLoop gameLoop;

    /**
     * Constructor
     * @param amazons
     * @param arrows
     */
    public OnlineEvolution(Amazon2D[] amazons, ArrayList<Arrow2D> arrows, GameLoop gameLoop) {
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

    private Genome[] computeGenerations(Player player){
        Genome best = null;
        for (int i = 0; i < popSize; i++) {
            System.out.printf("Population: %d" +
                    "\n", i+1);
            population[i] = new Genome(initialBoard.deepCopy(),genomeLength,player);
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
        Collections.sort(Arrays.asList(population), new GenomeComparator());
        this.best = best.getMove();
        return population;
    }
    @Override
    public Move getBestMove(AI player) {
        updateIntialBoard(player.getGameLoop());
        computeGenerations(player);
        return this.best;
    }

    private void updateIntialBoard(GameLoop gameLoop){
        this.initialBoard = new TestBoard(gameLoop.getAmazons(),gameLoop.getArrows());
    }

    @Override
    public void initialize(AI p) {
        synchronized (this) {
            this.player = p;


        }

    }

}
