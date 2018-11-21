package com.dke.game.Models.AI;

import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Piece;
import com.dke.game.Models.GraphicalModels.Board2D;


import java.util.ArrayList;
import java.util.List;

public class TreeNode<Double> {
     Board2D board2D;
    TreeNode root;
    private Piece piece;
    private ArrayList<Amazon> enemyQueens;
    private ArrayList<Amazon> ourQueens;
    private Cell[][] board;
    private Cell cell;
    private List<TreeNode<Double>> children = new ArrayList<TreeNode<Double>>(); //connections
    private double value;
    private String gameState = null;
    private int coordinateI;
    private int coordinateJ;
    private Cell[][] boardCoordinates;

    //constructor
    public TreeNode( int i, int j) {
        //coordinates of chosen queen
        this.coordinateI=i;
        this.coordinateJ=j;

    }
    //root
    public void setRoot(TreeNode root){
        this.root=root;
    }
    //Board2D
    public Board2D getBoard2D() {
       return board2D;
    }
    public void setBoard2D(Board2D board2D) {
       this.board2D = board2D;
    }
    //Board
    public Cell[][] getBoard() {
       return board;
    }
    public void setBoard(Cell[][] board) {
      this.board = board;
    }
    //value
    public void setValue(double value){
        this.value=value;
    }
    public double getValue() {
        return value;
    }
    //ourQ
    public void addQueen(Amazon amazon){
        this.ourQueens.add(amazon);
    }
    public ArrayList<Amazon> getOurQueens() {
        return ourQueens;
    }
    public void setOurQueens(ArrayList<Amazon> ourQueens) {
        this.ourQueens = ourQueens;
    }
    //enemyQ
    public void addEnemyQueen(Amazon amazon) {
        this.enemyQueens.add(amazon);
    }
    public void setEnemyQueens(ArrayList<Amazon> enemyQueens) {
        this.enemyQueens = enemyQueens;
    }

    public ArrayList<Amazon> getEnemyQueens() {
        return enemyQueens;
    }

    /**
     * @return children
     */
    public List<TreeNode<Double>> getChildren() {
        return children;
    }
    public void setChild(TreeNode<Double> children) {
        this.children.add(children);
    }
    //height of tree from that node
    public int getHeight(TreeNode<Double> root) {
        if (root == null) {
            return 0;
        }
        int h = 0;

        for (TreeNode<Double> n : root.getChildren()) {
            h = Math.max(h, getHeight(n));
        }
        return h + 1;
    }

    /*public void setAllQueens(ArrayList<Amazon> allQueens) {
        this.ourQueens = allQueens;
    }*/

    //calculating the score for the node
    public void positioHheuristics() {


        for (Amazon queen : ourQueens) {

            //score: in range

            ArrayList<Cell> possibleShots = queen.getPossibleMoves();// evaluate all possible shots of that cell(as moves are the same as shots we use moves)
            //all possible shots questioned
            for (Cell arrowCell : possibleShots) {
                try {
                    for (int i = arrowCell.getI() - 1; i <= arrowCell.getI() + 1; i++) {
                        for (int j = arrowCell.getJ() - 1; j <= arrowCell.getJ() + 1; j++) { // going around  and over the potential arrow placement
                                if(i!=arrowCell.getI() && j!=arrowCell.getJ()){ //avoiding the cell in questioning
                            for (Amazon enemyQueen : enemyQueens) {
                                //need list of enemy queens

                                if (i == enemyQueen.getX() && j == enemyQueen.getY()) {
                                    value = +1;
                                }
                            }
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException ex) {
                    continue;
                }
            }//Score "in range" ends
            //
            for (Amazon ourQueen : ourQueens) {
                //score: territory
                Cell ourQueenCell = queen.getCell();
                int i = ourQueenCell.getI();
                int j = ourQueenCell.getJ();
                int[][] territory = queen.countTerritory(boardCoordinates); //basically how many cells are free to use from that position
                //WARNING: due to no coordinates, can't put the location of the queen in question.
                value = + territoryToInt(territory);
                //Score"territory" ends
            }

        }

    }

    private int territoryToInt(int[][] territory){
        int count = 0;
        for (int i = 0; i < territory.length; i++) {
            for (int j = 0; j < territory[i].length; j++) {
                if(territory[i][j] == 1){
                    count++;
                }
            }
        }
        return count;
    }

    public void shotsHeuristics() {

        for (Amazon queen : ourQueens) {

            //score: in range

            ArrayList<Cell> possibleShots = queen.getPossibleMoves();// evaluate all possible shots of that cell(as moves are the same as shots we use moves)
            //all possible shots questioned
            for (Cell arrowCell : possibleShots) {
                try {
                    for (int i = arrowCell.getI() - 1; i <= arrowCell.getI() + 1; i++) {
                        for (int j = arrowCell.getJ() - 1; j <= arrowCell.getJ() + 1; j++) { // going around  and over the potential arrow placement

                            for (Amazon enemyQueen : enemyQueens) {
                                //need list of enemy queens

                                if (i == enemyQueen.getX() && j == enemyQueen.getY()) {
                                    value = +1;
                                }
                            }
                        }

                    }
                } catch (IndexOutOfBoundsException ex) {
                    continue;
                }
            }//Score "in range" ends


        }

    }

    // might be needed in the future
    public String boardToString(Cell[][] board){
        for(int i = 0;i<10; i++){
            for(int j =0; j<10;j++ ){
                String contentID = board[i][j].getContentID();
                gameState=gameState+contentID;

            }
        }
        return gameState;
    }



}
