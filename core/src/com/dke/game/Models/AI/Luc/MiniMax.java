package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Algorithm;

public class MiniMax implements Algorithm {
    private MovesTree movesTree;

    public MiniMax(MovesTree movesTree) {
        this.movesTree = movesTree;
    }

    @Override
    public Move getBestMove(AI player, MoveNode root) {
        MoveNode bestEval = MiniMax(movesTree.getRootNode(), 1, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, player);
        while (bestEval.getParent().getParent() != null) {
            bestEval = bestEval.getParent();
        }
        return bestEval.getData();

    }

    public MoveNode node;
    boolean maxPlayer = true;

    //returns the best score of all possible Board Status
    public MoveNode MiniMax(MoveNode aNode, int depth, boolean maxPlayer, double alpha, double beta, AI player) {

        MoveNode chNodeVal;
        if (depth == 0 || aNode.getChildren().isEmpty()) {

            return aNode;
        }
        //no
        if (maxPlayer) {
            MoveNode bestValue = null;


            for (int i = 0; i < aNode.getChildren().size(); i++) {
                chNodeVal = MiniMax(aNode.getChildren().get(i), depth - 1, false, alpha, beta, player);

//                bestValue =Math.max( bestValue,chNodeVal);
                if (bestValue == null) {
                    aNode.evaluateNode(player, movesTree.getInitialState().getTestBoard());
                    bestValue = aNode;
                } else {
                    aNode.evaluateNode(player, movesTree.getInitialState().getTestBoard());
                    if (bestValue.getValue() < aNode.getValue()) {
                        bestValue = aNode;
                    }
                }
                alpha = Math.max(alpha, bestValue.getValue());
                if (beta <= alpha) break;

            }
            return bestValue;
        } else {
            MoveNode bestValue = null;
            for (int i = 0; i < aNode.getChildren().size(); i++) {
                chNodeVal = MiniMax(aNode.getChildren().get(i), depth - 1, true, alpha, beta, player);
//                bestValue =Math.min( bestValue, chNodeVal);
//                beta= Math.min(beta,bestValue);
                if (bestValue == null) {
                    aNode.evaluateNode(player, movesTree.getInitialState().getTestBoard());
                    bestValue = aNode;
                } else {
                    aNode.evaluateNode(player, movesTree.getInitialState().getTestBoard());
                    if (bestValue.getValue() > aNode.getValue()) {
                        bestValue = aNode;
                    }
                }
                alpha = Math.min(beta, bestValue.getValue());
                if (beta <= alpha) break;


            }
            return bestValue;

        }

    }
}
