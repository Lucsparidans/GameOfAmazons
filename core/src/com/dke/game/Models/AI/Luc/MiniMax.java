package com.dke.game.Models.AI.Luc;

import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Algorithm;

public class MiniMax implements Algorithm {
    private static Node<GameState> bestEval;
    private static boolean max;

    @Override
    public Move getBestMove(Player player, Node<GameState> root) {
        if (player.getSide() == 'W') {
            this.MiniMax(root, 5, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            max=true;
        } else {
            this.MiniMax(root, 5, false, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            max=false;
        }
        return bestEval.getData().getMove();
    }


    //returns the best score of all possible Board Status
    public static double MiniMax(Node<GameState> aNode, int depth, boolean maxPlayer, double alpha, double beta) {

        double chNodeVal;
        if (depth == 0 || aNode.getChildren().isEmpty()) {
            double eval = aNode.getData().evaluateState();
            if(max){
                if(eval>bestEval.getData().evaluateState()){
                    bestEval=aNode;
                }
            }
            else{
                if(eval<bestEval.getData().evaluateState()){
                    bestEval=aNode;
                }
            }
            return aNode.getData().evaluateState();

        }
        //no
        if (maxPlayer) {
            double bestValue = Double.NEGATIVE_INFINITY;


            for (int i = 0; i < aNode.getChildren().size(); i++) {
                chNodeVal = MiniMax(aNode.getChildren().get(i), depth - 1, false, alpha, beta);
                bestValue = Math.max(bestValue, chNodeVal);
                alpha = Math.max(alpha, bestValue);
                if (beta <= alpha) break;

            }
            return bestValue;
        } else {
            double bestValue = Double.POSITIVE_INFINITY;
            for (int i = 0; i < aNode.getChildren().size(); i++) {
                chNodeVal = MiniMax(aNode.getChildren().get(i), depth - 1, true, alpha, beta);
                bestValue = Math.min(bestValue, chNodeVal);
                beta = Math.min(beta, bestValue);
                if (beta <= alpha) break;


            }
            return bestValue;

        }

    }
}
