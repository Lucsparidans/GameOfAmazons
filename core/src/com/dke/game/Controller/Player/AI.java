package com.dke.game.Controller.Player;

import com.dke.game.Controller.GameLoop;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.Luc.Tree;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Board2D;

public class AI extends Player {
    private Algorithm algorithm;
    private Board2D board2D;

    private GameLoop gameLoop;
    private Tree tree;

    public AI(char side, Algorithm algorithm, Board2D board2D, GameLoop gameLoop) {
        super(side, gameLoop);
        this.algorithm = algorithm;
        this.board2D = board2D;
        this.gameLoop=gameLoop;
        //TODO fix
        if(this.side=='W'){
            this.tree = new Tree(board2D,gameLoop.getAmazons(),gameLoop.getArrow(),this);
        }
        else if(this.side=='B'){
            this.tree = new Tree(board2D,gameLoop.getAmazons(),gameLoop.getArrow(),this);
        }
        else{
            System.out.println("Something went wrong");
        }



    }

    private void move() {
        Move bestMove = algorithm.getBestMove();
        Cell moveQTo = bestMove.getQueenTo();
        Cell arrowTo = bestMove.getArrowTo();
        Amazon2D queen = bestMove.getQueen();
        queen.move(moveQTo);
        queen.shoot(arrowTo);
    }

    @Override
    public void performTurn() {
        move();
    }

    public enum Phase{
        START_PHASE, MID_PHASE, END_PHASE
    }
}
