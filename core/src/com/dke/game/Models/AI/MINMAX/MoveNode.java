package com.dke.game.Models.AI.MINMAX;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Board2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MoveNode {

    private Move data;
    private MoveNode parent;
    private final int DEPTH;
    private List<MoveNode> children = new ArrayList<>();
    private double value;
    private static Random random = new Random(10);

    public MoveNode(Move data, MoveNode parent) {
        this.data = data;
        this.parent = parent;
        this.DEPTH = this.getDepth();
    }


    public MoveNode addChild(MoveNode child) {
        this.children.add(child);
        child.setParent(this);
        return child;
    }


    public void addChildren(List<MoveNode> children) {
        this.children.addAll(children);
        for (MoveNode child : children) {
            child.setParent(this);
        }
    }


    public List<MoveNode> getChildren() {
        return this.children;
    }


    public Move getData() {
        return this.data;
    }


    public void setData(Move data) {
        this.data = data;
    }

    private void setParent(MoveNode parent) {
        // Finish this method
        this.parent = parent;
    }

    public MoveNode getParent() {
        return this.parent;
    }

    public double getValue() {
        return this.value;
    }

    public int getDEPTH() {
        return this.DEPTH;
    }

    /*
    Calculates the depth of a node by creating a reference to the parent of the current node, this node,
     and then replacing the reference to the current parent to the parents parent.
    This is repeated while that is possible.
     */
    private int getDepth() {

        int counter = 0;
        MoveNode cur;

        if (this.getParent() != null) {
            cur = this.getParent();
            counter++;
            while (cur.getParent() != null) {
                counter++;
                cur = cur.parent;
            }
        }
        return counter;
    }
    /*
    Function which is called to calculate the heuristic value of the node.
     */
    public void evaluateNode(AI player, TestBoard testBoard) {
        this.createCurrentState(this,testBoard);
        //testBoard.printBoard();
        this.value = evaluateState(player, testBoard);
        //this.value = evaluateQueens(player, testBoard);
        this.data.setValue(this.value);
        testBoard.resetMoves();
    }

    /*
    sub-memthod to othe  evaluate node method, this returns the value of the heuristic evaluation.
     */
    private double evaluateState(AI playerAI, TestBoard testBoard) {
        // mobility(testBoard, playerAI.getMyAmazons(), playerAI.getEnemyAmazons());
        if(this.data != null) {
            if (this.getData().isPlayerMaximizing(playerAI)) {
                MSP(testBoard,playerAI);
                 // double val = mobility(testBoard, playerAI.getMyAmazons(), playerAI.getEnemyAmazons());
                double val = positioHheuristics(testBoard, playerAI.getMyAmazons(), playerAI.getEnemyAmazons());
                //System.out.println(val);
                return val;
            } else {
                    MSP(testBoard,playerAI);
                //double val = mobility(testBoard, playerAI.getEnemyAmazons(), playerAI.getMyAmazons());
                double val = positioHheuristics(testBoard, playerAI.getEnemyAmazons(), playerAI.getMyAmazons());
                //System.out.println(val);
                return val;
            }

        }
        return  0;
    }

   /* private double evaluateQueens(AI playerAI, TestBoard testBoard) {
        if (this.data != null) {
            if (this.getData().isPlayerMaximizing(playerAI)) {
               // double val = queenCells(testBoard.getBoard());
                return val;
            } else {
                //double val = queenCells(testBoard.getBoard());
                return val;
            }
//            if (this.getData().isPlayerMaximizing(playerAI)) {
//                double val = positioHheuristics(testBoard, playerAI.getMyAmazons(), playerAI.getEnemyAmazons());
//                //System.out.println(val);
//                return val;
//            } else {
//                double val = positioHheuristics(testBoard, playerAI.getEnemyAmazons(), playerAI.getMyAmazons());
//                //System.out.println(val);
//                return val;
//            }
            return random.nextDouble();
        }
        return 0;
    }
    */
    /*
    Because the node only contains a move object which describes the action that takes us from the state in the parent-node to the state in this node.
    Because only the state of the rootnode of the movetree is kept, we backtrack from our node to the root while putting the current node we are referencing into
    a stack. Once the rootnode is reached we can recreate the state we are currently in by executing all moves in the stack
     while the stack still contains a move object.

     */
    public void createCurrentState(MoveNode node, TestBoard testBoard) {
//

        MoveNode cur = node;
        Stack<MoveNode> path = new Stack<>();
        path.push(cur);
        while (cur.getParent() != null) {
            cur = cur.getParent();
            path.push(cur);
        }
        for (MoveNode n : path) {
            if (n.getData() != null) {
                testBoard.executeMove(n.getData());
                //testBoard.printBoard();
            }
        }
    }
    /*
    Heuristics
     */
    //<editor-fold desc="Heuristics">
    //calculating the score for the node
    private double positioHheuristics(TestBoard testBoard, Amazon2D[] ourQueens, Amazon2D[] enemyQueens) {
        double value = 0;

        for (Amazon queen : ourQueens) {

            //score: in range

            ArrayList<Cell> possibleShots = queen.getPossibleMoves();// evaluate all possible shots of that cell(as moves are the same as shots we use moves)
            //all possible shots questioned
            for (Cell arrowCell : possibleShots) {
                try {
                    for (int i = arrowCell.getI() - 1; i <= arrowCell.getI() + 1; i++) {
                        for (int j = arrowCell.getJ() - 1; j <= arrowCell.getJ() + 1; j++) { // going around  and over the potential arrow placement
                            if (i != arrowCell.getI() && j != arrowCell.getJ()) { //avoiding the cell in questioning
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
                Cell ourQueenCell = ourQueen.getCell();
                int i = ourQueenCell.getI();
                int j = ourQueenCell.getJ();
                int[][] territory = ourQueen.countTerritory(testBoard.getBoard()); //basically how many cells are free to use from that position

                value = +territoryToInt(territory);
                //Score"territory" ends
            }

        }
        return value;

    }

    private int territoryToInt(int[][] territory) {
        int count = 0;
        for (int i = 0; i < territory.length; i++) {
            for (int j = 0; j < territory[i].length; j++) {
                if (territory[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    private double shotsHeuristics(TestBoard testBoard, Amazon2D[] ourQueens, Amazon2D[] enemyQueens) {
        double value = 0;
        for (Amazon queen : ourQueens) {

            //score: in range
            queen.possibleMoves(testBoard);
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
        return value;

    }


    // pos : current board position
    // evaluate the territory feature for the board position “pos” and return the result.
    int whiteStonePly;
    int blackStonePly;
   public int MSP(TestBoard testBoard,AI playerAI){


       int  blackSquares = 0;
       int  whiteSquares = 0;
       TestBoard testBoard1 = realDealHeuristics(testBoard, playerAI.getMyAmazons(), playerAI.getEnemyAmazons());
       Cell[][] pos=testBoard1.getBoard();
       for (int i = 0; i<pos.length; i++){
            for(int j=0; j< pos.length;j++){
//all empty squares x do
                if(!pos[i][j].isOccupied()){
                    String moveID = pos[i][j].getMoveID();
                    //whiteStonePly = the minimum number of moves that a white piece needs to arrive at x;
                     whiteStonePly = pos[i][j].getMoveNumWhite();
                    //blackStonePly = the minimum number of moves that a black piece needs to arrive at x;
                    blackStonePly = pos[i][j].getMoveNumBlack();
                }
            }



         if (blackStonePly < whiteStonePly){
            blackSquares = blackSquares + 1;
         }
         else
            whiteSquares = whiteSquares + 1;
    }
       // white’s turn to play
if (playerAI.getSide()=='W') {
    return (whiteSquares -blackSquares);
}else{
        return (blackSquares -whiteSquares);
        }
   }


public TestBoard realDealHeuristics( TestBoard testBoard,Amazon2D[] ourQueens, Amazon2D[] enemyQueens) {
    //make sure the queens and cells we're talking about are connected to the testBoard as we're trying to change it
    //for every queen
    int countMove=0;
    //for us
    for (Amazon Q : ourQueens) {
        //get possible moves
        ArrayList<Cell> possibleMoves = Q.getPossibleMoves();
        //mark every cell in the possible moves list as 1 (so moveNum=1)
        for (Cell C : possibleMoves) {
            //if cell not occupied
            //and if cell not marked
            if (!C.isOccupied()) {
                checkTiles(C, countMove,Q.getSide());
                //queen-like moves
                ArrayList<Cell> possibleMovesOfCell = C.possibleMovesOfCell(testBoard, C, true);
                countMove++;// going 2 ply deep
                //maybe remove tiles marked as 10 from the possible moves ans not possible anymore
                for (Cell cell : possibleMovesOfCell) {
                    checkTiles(cell,countMove,Q.getSide());

                }
                //king-like moves
                ArrayList<Cell> possibleMovesOfCell1 = C.possibleMovesOfCell(testBoard, C, false);
                for (Cell cell : possibleMovesOfCell) {
                    checkTiles(cell,countMove,Q.getSide());

                }


            }

        }
    }
    //for enemy
    for (Amazon Q : enemyQueens) {
        ArrayList<Cell> possibleMoves = Q.getPossibleMoves();
          for (Cell C : possibleMoves) {


                checkTiles(C, countMove,Q.getSide());
                 ArrayList<Cell> possibleMovesOfCellenemy = C.possibleMovesOfCell(testBoard, C, true);
                countMove++;// going 2 ply deep
                 for (Cell cell : possibleMovesOfCellenemy) {
                    checkTiles(cell,countMove,Q.getSide());

                }
                //king-like moves
                ArrayList<Cell> possibleMovesOfCellenemy1 = C.possibleMovesOfCell(testBoard, C, false);
                for (Cell cell : possibleMovesOfCellenemy1) {
                    checkTiles(cell,countMove,Q.getSide());

                }




        }
    }
    return testBoard;
}


            //if cell marked
        //mark all those unmarked cells with adequate moveID
        //







/*
* changes the test board, doesn't return anything as the cells of the testBoard are being changed
* */
    public void checkTiles(Cell C,int countMove, char side ) {
        if (side == 'W') {
            if (C.getMoveID() == null) {
                //can't detect if conflicting the enemy q so moveID changed to string
                C.setMoveID("W");
                C.setMoveNumWhite(countMove + 1);
            }//if cell already marked by enemy,
            // if cell moveID and moveNum of our queen = to cell moveNum of the enemy,
            // then moveNumours and movenumenemy of that cell is 10 for nonexistant.
            else if (C.getMoveID() != "W" && C.getMoveNumBlack() == countMove + 1) {
                C.setMoveNumWhite(10);//for not exist
                C.setMoveNumBlack(10);

            } //if cell already marked by enemy,
            //and if cell moveNumEnemy > our potential moveNumOurs
            //reset movNuOours to the potential one
            else if (C.getMoveID() != "W" && C.getMoveNumBlack() > countMove + 1 || C.getMoveNumBlack() < countMove + 1) {
                C.setMoveNumWhite(countMove + 1);


                //cover the same side conflicting parts
                //if what we have is slower that what we could have, change it
                //if what we have is equal to what we found, do nothing
                //if what we have is faster(takes less moves) than what we found, do nothing
            } else if (C.getMoveID() == "W" && C.getMoveNumWhite() > countMove + 1) {
                C.setMoveNumWhite(countMove + 1);

            } else {
                C.setMoveNumWhite(countMove + 1);
            }
        } else {

            if (C.getMoveID() == null) {
                //can't detect if conflicting the enemy q so moveID changed to string
                C.setMoveID("B");
                C.setMoveNumBlack(countMove + 1);
            }//if cell already marked by enemy,
            // if cell moveID and moveNum of our queen = to cell moveNum of the enemy,
            // then moveNumours and movenumenemy of that cell is 10 for nonexistant.
            else if (C.getMoveID() != "B" && C.getMoveNumWhite() == countMove + 1) {
                C.setMoveNumWhite(10);//for not exist
                C.setMoveNumBlack(10);

            } //if cell already marked by enemy,
            //and if cell moveNumEnemy > our potential moveNumOurs
            //reset movNuOours to the potential one
            else if (C.getMoveID() != "B" && C.getMoveNumWhite() > countMove + 1 || C.getMoveNumWhite() < countMove + 1) {
                C.setMoveNumBlack(countMove + 1);


                //cover the same side conflicting parts
                //if what we have is slower that what we could have, change it
                //if what we have is equal to what we found, do nothing
                //if what we have is faster(takes less moves) than what we found, do nothing
            } else if (C.getMoveID() == "B" && C.getMoveNumBlack() > countMove + 1) {
                C.setMoveNumBlack(countMove + 1);

            } else {
                C.setMoveNumBlack(countMove + 1);
            }
        }
    }

    //</editor-fold>



    /*
    kingsMoves is a method that will decide which move wins most territory according to how the king (in chess) moves
    Cells closest to your amazons are your territory, the delta of before and after must be maximized
    kingsMoves is best used in the beginning
    */
    private double kingsMoves(TestBoard testBoard, Amazon2D[] ourQueens, Amazon2D[] enemyQueens){
        double delta = 0;

        //Call kingsTerritory in Amazon to calculate the territory ratio on the current board.
        //try every move on the testboard and call the kingsTerritory on the testboard.
        //Determine which change is the most positive

        return delta;
    }


    /*
    queensMoves is a method that will decide which move wins the most territory according to how the amazons can move.
    Cells closest to your amazons are your territory, the delta of before and after must be maximized
    queensMoves is best used in the beginning
     */
    // private double queensMoves(TestBoard testBoard, Amazon2D[] ourQueens, Amazon2D[] enemyQueens){

    //}

    public double heuristics(Player p){
        char side = p.getSide();
        double result = 0;
        boolean white = true;

        if(side == 'B'){
            white = false;
        }


        return result;
    }

    public double mobility(TestBoard testBoard, boolean whiteTurn) {
        Amazon2D[] myAmazons = new Amazon2D[4];
        Amazon2D[] enemyAmazons = new Amazon2D[4];
        int w = 0;
        int b = 0;
        if(whiteTurn) {
            for (Amazon2D a :
                    testBoard.getAmazons()) {
                if (a.getSide() == 'W') {
                    myAmazons[w] = a;
                    w++;
                } else {
                    enemyAmazons[b] = a;
                    b++;
                }
            }
        }
        else{
            for (Amazon2D a :
                    testBoard.getAmazons()) {
                if (a.getSide() == 'B') {
                    myAmazons[b] = a;
                    b++;
                } else {
                    enemyAmazons[w] = a;
                    w++;
                }
            }
        }
        return mobility(testBoard.getBoard(), myAmazons,enemyAmazons);
    }

    public double mobility(Cell[][] board, Amazon2D[] ourQueens, Amazon2D[] enemyQueens){
        double ourMob = 0;
        double oppMob = 0;
        double mob = 0;

        for (Amazon2D Q: ourQueens) {

            ArrayList<Cell> moves = Q.getPossibleMoves();
            ourMob = ourMob + moves.size();
        }
        for (Amazon2D Q: enemyQueens) {

            ArrayList<Cell> moves = Q.getPossibleMoves();
            oppMob = oppMob + moves.size();

        }
        mob = ourMob - oppMob;
/*
        System.out.println("white mobility is: " + ourMob);
        System.out.println("Black mobility is: " + oppMob);
        System.out.println("mobility is: " + mob);
*/
        // If mob is positive, white has the advantage, if it's negative black has the advantage.

        return mob;
    }



}
