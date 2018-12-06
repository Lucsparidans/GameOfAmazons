package com.dke.game.Controller.Player;

import com.dke.game.Controller.GameLoop;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.Luc.GameState;
import com.dke.game.Models.AI.Luc.MyAlgo.OptimizedTree;
import com.dke.game.Models.AI.Luc.MyAlgo.State;
import com.dke.game.Models.AI.Luc.Node;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Board2D;

public class AI extends Player {
    private Algorithm algorithm;
    private Node<State> rootNode;
    private GameLoop gameLoop;
    private OptimizedTree tree;
    private Amazon2D[] myAmazons;
    private Amazon2D[] enemyAmazons;

    public AI(char side, Algorithm algorithm, Board2D board2D, GameLoop gameLoop) {
        super(side);
        this.algorithm = algorithm;
        this.gameLoop=gameLoop;
        this.tree = new OptimizedTree(gameLoop.getAmazons(),gameLoop.getArrow(),this);
        enemyAmazons = new Amazon2D[4];
        myAmazons = new Amazon2D[4];
        int counter = 0;
        int cnt=0;
        for (Amazon2D a:gameLoop.getAmazons()) {
            if(this.side==a.getSide()) {
                myAmazons[counter] = a;
                counter++;
            }
            else{
                enemyAmazons[cnt]=a;
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






    private void move() {
        Move bestMove = algorithm.getBestMove(this,rootNode);
        Cell moveQTo = bestMove.getQueenTo();
        Cell arrowTo = bestMove.getArrowTo();
        Amazon2D queen = bestMove.getQueen();
        queen.move(moveQTo);
        queen.shoot(arrowTo);
    }
    private void updateTree(){
        this.tree=new OptimizedTree(gameLoop.getAmazons(),gameLoop.getArrow(),this);
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
