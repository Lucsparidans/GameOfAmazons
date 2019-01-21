package com.dke.game.Models.AI.MINMAX;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Models.AI.MINMAX.MyAlgo.State;
import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;

public class SubTree extends MovesTree {
    private MoveNode rootNode;
    private State initialState;
    static int maxDepth = 1;
    private AI player;
    private int nodeCheck = 0;
    private int depth;
    public static ArrayList<MoveNode> currents = new ArrayList<MoveNode>();

    public SubTree(Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS, AI player){
       super(amazon2DS, arrow2DS, player);
        initialState = new State(new TestBoard(amazon2DS, arrow2DS), null, player);
        this.player = player;
        rootNode = new MoveNode(null, null);
        expandNode(rootNode);
    }

    public void expandNode(MoveNode current){
        if (current.getDEPTH() < maxDepth) {
            ArrayList<Move> possibleMoves = getPossibleMoves(current);
            for (Move m : possibleMoves) {
                nodeCheck++;
                //System.out.println("this time");

                System.out.println(nodeCheck + " Nodes created in subtree");
                MoveNode child = new MoveNode(m, current);
                // child.setParent(current); //
                current.addChild(child);
            }
        }
        if (current.getDEPTH() == maxDepth || current.getChildren().size() == 0) {
            return;
        } else {
            MoveNode median = currents.get((currents.size()) / 2);
            for (MoveNode n : current.getChildren()) {
                //System.out.println("expanded");
                expandNode(n);

            }
        }

        }


}
