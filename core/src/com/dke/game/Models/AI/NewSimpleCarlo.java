package com.dke.game.Models.AI;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.AI.Luc.MoveNode;
import com.dke.game.Models.AI.Luc.MovesTree;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewSimpleCarlo implements Algorithm {

    MovesTree tree;
    final int branchfac = 5;
    @Override
    public Move getBestMove(AI aiplayer, MoveNode root) {
        Move BestMove = null;

        // construct tree
        tree= aiplayer.getTree();

        //get childrenlv1 of root node
        List<MoveNode> childrenList = root.getChildren();
        if( childrenList.size()==0){
            tree.expandNode(root);
            childrenList = root.getChildren();
        }

        //expand on these children randomly, n times for each child
        double[] childrenScores = new double[childrenList.size()];
        for(int i = 0; i<childrenList.size(); i++){
            double sum = 0;
            for (int j = 0; j< branchfac; j++){
                sum+= expandRandomly(childrenList.get(i));
            }
            double avg = sum/branchfac;
            childrenScores[i] = avg;
        }
        int highestScoreIndex = getMaxIndex(childrenScores);

        MoveNode BestMoveNode = childrenList.get(highestScoreIndex);
        BestMove = BestMoveNode.getData();

        return BestMove;
    }

    public double expandRandomly(MoveNode node){
        //createcurrentstate -> checkend
        //if endstate return 0/1


        //get random child
        List<MoveNode> children = node.getChildren();
        if(children.size() ==0){
            tree.expandNode(node);
            children = node.getChildren();
        }
        Random rand = new Random();
        MoveNode randomChild = children.get(rand.nextInt(children.size()));

        return expandRandomly(randomChild);
        }

    public int getMaxIndex(double[] array){
        int maxIndex = 0;
        for(int i = 1; i<array.length; i++){
            if(array[i]>array[maxIndex]){
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
