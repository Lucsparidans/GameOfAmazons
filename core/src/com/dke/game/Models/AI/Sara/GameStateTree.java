package com.dke.game.Models.AI.Sara;

import com.dke.game.Controller.GameLoop;
import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Piece;
import com.dke.game.Models.GraphicalModels.Board2D;

import java.util.ArrayList;
import java.util.List;

public class GameStateTree {
    boolean onlyMove;
    private TreeNode<Double> stateRoot;
    private Amazon queen;
    private Piece piece;
    private ArrayList<Cell> possibleMoves;
    private Board2D board2D;
    private char chosenSide;// B for black W for white
    //constructor
    public GameStateTree(Amazon queen,char chosenSide) {
        GameLoop gameLoop= new GameLoop();
        this.board2D = gameLoop.getBoard2D();
        this.queen = queen; //initial queen
        this.chosenSide=chosenSide;

    }
/*basically need the state of the board per queen's move
    queens possible moves
    each move individually stored in a node with other queen's locations
    need to save which cell is occupied*/
    public Board2D makeTree(){
      // need to put queens in the stateRoot
        Cell[][] boardsLocations = board2D.getBoard();
        for(int i=0;i<boardsLocations.length;i++) {
            for (int j = 0; j < boardsLocations.length; j++) { // going through the board
                if (boardsLocations[i][j].isOccupied()) { //if location occupied
                    Piece content = boardsLocations[i][j].getContent(); //save the content
                    String contentID = boardsLocations[i][j].getContentID(); //save the information
                    for (Integer k = 1; k < 5; k++) {
                        if (contentID.matches((k.toString() + chosenSide))) { // if matches our chosen queen side information

                            //add the piece, cause can't turn it into an amazon
                            stateRoot.addQueen((Amazon) content);
                        }else{
                            stateRoot.addEnemyQueen((Amazon) content);}
                    }
                }
            }
        }//now we have all the queens in their lists!

        for (Amazon queen:stateRoot.getOurQueens()) {// for every queen of ours
            char side = queen.getSide();

                queen.possibleMoves(board2D); //according to this situation, calculate us our possible moves
                possibleMoves = queen.getPossibleMoves();//get possible moves

                stateRoot.setBoard(boardsLocations); // save the location of pieces into the stateRoot node
                stateRoot.setBoard2D(board2D);// saving the board state in root
                for (Cell child : possibleMoves) { //for every one of those potential cells to place the queen
                    TreeNode node = new TreeNode(child.getI(), child.getJ());// we make a node with the coordinates of the cell
                    node.setOurQueens(stateRoot.getOurQueens());
                    node.setEnemyQueens(stateRoot.getEnemyQueens());

                    //1 unoccupy the last position
                    Cell cellQ = queen.getCell();//not sufficient?????????????????????????????????????????????????
                    cellQ.unOccupy();

                    //2 upgrade it to this 'potential move'
                    queen.move(child);
                    board2D.occupy(piece, child);

                    //TODO: fill in the lists of our queens and enemy queens again, from the stateRoot, but updated
                    // we have moved the queen and deleted her from the previous post
                    // but is the board gonna be up to date with it...? cause it should

                     node.setRoot(stateRoot);// set that it came from this initial state(Root)
                    node.getHeight(stateRoot);// we get height of the tree from stateRoot, should be 2


                    node.setBoard(boardsLocations); //we save locations in that node

                    node.setBoard2D(board2D);//saving the whole board state
                    //mystery: is the initial position of the queen deleted?
                    node.positioHheuristics();
                    double value = node.getValue();


                }
            
            double data = MinMax.MiniMax(stateRoot, 2, true,Integer.MIN_VALUE,Integer.MAX_VALUE);//calling the minmax to tell us which one is the best

           List<TreeNode<Double>> children = stateRoot.getChildren();
            for (TreeNode child : children) {//search for the node with this value
                    if(child.getValue()==data){
                        return child.getBoard2D();
                    }   }
        }

        // i don't remember what this was about
      // for(Cell cell: possibleMoves){

        //   queen.move(cell); //we move the queen in a cell
          // stateRoot.addQueen(queen);//add queen to the state
           //add position of others to the state

       //}


        return null;
    }
}
