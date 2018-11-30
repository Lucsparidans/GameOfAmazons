package com.dke.game.Models.AI;

import com.badlogic.gdx.Game;
import com.dke.game.Models.AI.Luc.Node;
import com.dke.game.Models.AI.Luc.Tree;
import com.dke.game.Models.DataStructs.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleCarlo {
    Tree tree;
    final int branchfac = 10;

    public SimpleCarlo(Tree tree){
        this.tree = tree;
    }
    public Node<GameState> startAlg(Node<GameState> startnode){
        tree.expandNode(startnode);
        List<Node<GameState>> childrenList = startnode.getChildren();
        double[] childrenScores = new double[childrenList.size()];
        for(int i = 0; i<childrenList.size(); i++){
            double sum = 0;
            for (int j = 0; j< branchfac; j++){
                sum+= expand(childrenList.get(i));
            }
            double avg = sum/branchfac;
            childrenScores[i] = avg;
        }
        int highestScoreIndex = getMaxIndex(childrenScores);
        Node<GameState> bestNode = childrenList.get(highestScoreIndex);
        return bestNode;
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

    public double expand (Node<GameState> gamenode){
        //TODO: if game is finished{ return 1 if black return 0 if white wins}
        ArrayList<GameState> posmoves = tree.getPossibleStates(gamenode);
        Random rand = new Random();
        GameState randomNextState = posmoves.get(rand.nextInt(posmoves.size()));
        Node<GameState> nextNode = new Node<GameState>(randomNextState, gamenode);
        return expand(nextNode);
    }

}
