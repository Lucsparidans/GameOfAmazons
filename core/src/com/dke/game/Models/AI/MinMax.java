package com.dke.game.Models.AI;

public class MinMax {
    public TreeNode<Double> node;
    boolean maxPlayer=true;

    //returns the best score of all possible Board Status'
    public double MiniMax(TreeNode<Double> aNode,int depth, boolean maxPlayer){

        double chNodeVal;
        if((depth == 0) || aNode.getChildren().isEmpty()){
            return aNode.getValue();
        }
        //no
        if(maxPlayer){
            double bestValue= (double)Integer.MIN_VALUE;


            for(int i =0; i< aNode.getChildren().size();i++){
                chNodeVal= MiniMax(aNode.getChildren().get(i),depth-1,false);
                bestValue =Math.max( bestValue,chNodeVal);

            }return bestValue;
        } else{
            double bestValue= Integer.MAX_VALUE;
            for(int i =0; i< aNode.getChildren().size();i++){
                chNodeVal= MiniMax(aNode.getChildren().get(i),depth-1,true);
                bestValue =Math.min( bestValue, chNodeVal);

            }return bestValue;

        }

    }
}
