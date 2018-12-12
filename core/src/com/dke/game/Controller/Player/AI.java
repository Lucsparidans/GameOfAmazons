package com.dke.game.Controller.Player;

import com.dke.game.Controller.GameLoop;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.Luc.MoveNode;
import com.dke.game.Models.AI.Luc.MovesTree;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Board2D;

public class AI extends Player {
    private Algorithm algorithm;
    private MoveNode rootNode;
    private GameLoop gameLoop;
    private Board2D board2D;
    private MovesTree tree;
    private Amazon2D[] myAmazons;
    private Amazon2D[] enemyAmazons;

    public AI(char side, Algorithm algorithm, GameLoop gameLoop) {
        super(side);
        this.algorithm = algorithm;
        this.gameLoop = gameLoop;
        this.board2D=gameLoop.getBoard2D();
        enemyAmazons = new Amazon2D[4];
        myAmazons = new Amazon2D[4];
        int counter = 0;
        int cnt = 0;
        for (Amazon2D a : gameLoop.getAmazons()) {
            if (this.side == a.getSide()) {
                myAmazons[counter] = a;
                counter++;
            } else {
                enemyAmazons[cnt] = a;
                cnt++;
            }
        }
    }

    public Amazon2D[] getMyAmazons() {
        return myAmazons;
    }

    public Amazon2D[] getEnemyAmazons() {
        return enemyAmazons;
    }

    public MovesTree getTree() {
        return tree;
    }

    private void move() {
        Move bestMove = algorithm.getBestMove(this,rootNode);
//        Cell moveQTo = bestMove.getQueenTo();
        Cell moveQueenTo = board2D.getBoardCoordinates()[bestMove.getQueenTo().getI()][bestMove.getQueenTo().getJ()];
        Cell arrowTo = board2D.getBoardCoordinates()[bestMove.getArrowTo().getI()][bestMove.getArrowTo().getJ()];
        Amazon2D amazon2D = gameLoop.getAmazons()[bestMove.getQueen().getIndex()];
        amazon2D.move(moveQueenTo);
        amazon2D.shoot(arrowTo);
//        Cell arrowTo = bestMove.getArrowTo();
//        Amazon2D queen = bestMove.getQueen();
//        queen.move(moveQTo);
//        queen.shoot(arrowTo);

    }

    private void updateTree() {
        this.tree = new MovesTree(gameLoop.getAmazons(), gameLoop.getArrows(), this);
        rootNode = this.tree.getRootNode();
    }

    @Override
    public void performTurn() {
        updateTree();
        move();
        gameLoop.getGameView().setTurnCounter(gameLoop.getGameView().getTurnCounter()+1);
    }

    public enum Phase {
        START_PHASE, MID_PHASE, END_PHASE
    }
}
