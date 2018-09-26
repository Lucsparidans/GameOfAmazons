package com.dke.game.Models.AI;

public class Minimax {

    public Node node;
    boolean maxPlayer = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       //MiniMax();
    }
/*
scellenton of the minmax algorithm, we need to mold it into the game
 */
/*
 */
private double MiniMax(Node aNode, int depth, boolean maxPlayer) {

        double bestValue = (double) Integer.MIN_VALUE;
        double chNodeVal;
        if (depth == 0 || aNode.allMyChildren.isEmpty()) {
            return aNode.getValue();
        }
        //no
        //
        if (maxPlayer) {
            for (int i = 0; i < aNode.allMyChildren.size(); i++) {
                chNodeVal = MiniMax(aNode.allMyChildren.get(i), depth - 1, false);
                bestValue = Math.max(chNodeVal, bestValue);

            }
            return bestValue;
        } else {
            bestValue = Integer.MAX_VALUE;
            for (int i = 0; i < aNode.allMyChildren.size(); i++) {
                chNodeVal = MiniMax(aNode.allMyChildren.get(i), depth - 1, true);
                bestValue = Math.min(chNodeVal, bestValue);

            }
            return bestValue;

        }

    }
}
