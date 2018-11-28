package com.dke.game.Controller.Player;

import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.Luc.MiniMax;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Board2D;

public class AI extends Player {
    private Algorithm algorithm;
    private Board2D board2D;

    public AI(char side, Algorithm algorithm, Board2D board2D) {
        super(side);
        this.algorithm = algorithm;
        this.board2D = board2D;
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
