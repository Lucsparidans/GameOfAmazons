//package com.dke.game.Models.AI.Sara;
//
//public class MinMax {
//    public TreeNode<Double> node;
//    boolean maxPlayer=true;
//
//    //returns the best score of all possible Board Status
//    public static double MiniMax(TreeNode<Double> aNode, int depth, boolean maxPlayer, double alpha, double beta){
//
//        double chNodeVal;
//        if(depth==0|| aNode.getChildren().isEmpty()){
//            return aNode.getValue();
//        }
//        //no
//        if(maxPlayer){
//            double bestValue= (double)Integer.MIN_VALUE;
//
//
//            for(int i =0; i< aNode.getChildren().size();i++){
//                chNodeVal= MiniMax(aNode.getChildren().get(i),depth-1,false, alpha, beta);
//                bestValue =Math.max( bestValue,chNodeVal);
//                alpha = Math.max(alpha,bestValue);
//                    if(beta<=alpha) break;
//
//            }return bestValue;
//        } else{
//            double bestValue= Integer.MAX_VALUE;
//            for(int i =0; i< aNode.getChildren().size();i++){
//                chNodeVal= MiniMax(aNode.getChildren().get(i),depth-1,true,alpha, beta);
//                bestValue =Math.min( bestValue, chNodeVal);
//                beta= Math.min(beta,bestValue);
//                if(beta<= alpha) break;
//
//
//            }return bestValue;
//
//        }
//
//    }
//}