package com.dke.game.Models.AI;

import com.dke.game.Controller.GameLoop;
import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.AI.Luc.MoveNode;
import com.dke.game.Models.AI.Luc.MovesTree;

import com.dke.game.Models.AI.Luc.MyAlgo.TestBoard;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewSimpleCarlo implements Algorithm {

    public NewSimpleCarlo(GameLoop gameLoop){
        this.loop = gameLoop;
    }

    TestBoard beginb;
    GameLoop loop;
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
        this.beginb = new TestBoard(loop.getAmazons(), loop.getArrows());

        node.createCurrentState(node, beginb);

        Boolean HasEnd = beginb.checkEnd();
        if(HasEnd){
            if(beginb.whiteWon()){
                return 1;
            }
            else return 0;
        }

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
