package com.dke.game.Controller.Player;

import com.dke.game.Controller.GameLoop;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.Luc.GameState;
import com.dke.game.Models.AI.Luc.Node;
import com.dke.game.Models.AI.Luc.Tree;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Board2D;

public class AI extends Player {
    private Algorithm algorithm;
    private Board2D board2D;
    private Node<GameState> rootNode;
    private GameLoop gameLoop;
    private Tree tree;

    public AI(char side, Algorithm algorithm, Board2D board2D, GameLoop gameLoop) {
        super(side, gameLoop);
        this.algorithm = algorithm;
        this.board2D = board2D;
        this.gameLoop=gameLoop;
        this.tree = new Tree(gameLoop.getAmazons(),gameLoop.getArrow(),this);





    }

    private void move() {
        Move bestMove = algorithm.getBestMove(this,rootNode);
        Cell moveQTo = bestMove.getQueenTo();
        Cell arrowTo = bestMove.getArrowTo();
        Amazon2D queen = bestMove.getQueen();
        queen.move(moveQTo);
        queen.shoot(arrowTo);
    }
    private void updateTree(){
        this.tree=new Tree(gameLoop.getAmazons(),gameLoop.getArrow(),this);
        rootNode=tree.getRootNode();
    }

    @Override
    public void performTurn() {
        updateTree();
        move();
    }

    public enum Phase{
        START_PHASE, MID_PHASE, END_PHASE
    }
}
