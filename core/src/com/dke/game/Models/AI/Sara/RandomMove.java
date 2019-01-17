package com.dke.game.Models.AI.Sara;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Luc.*;
import com.dke.game.Models.AI.*;
import com.dke.game.Models.AI.Luc.MyAlgo.State;
import com.dke.game.Models.AI.Luc.MyAlgo.TestBoard;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;
import java.util.Random;

public class RandomMove implements Algorithm{
    //private GameState randomState;
    private MovesTree tree;
    private State aState;
    AI player;


    public void RandomMove(State someState, Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS, AI player) {
        aState = new State(new TestBoard(amazon2DS, arrow2DS), null, player);//TEST BOARD
        this.player = player;
    }


    private void generateMoves(TestBoard testBoard, ArrayList<Move> moves, char side) {
        Random r = new Random();

        ArrayList<Amazon2D> queens=null;
        for (Amazon2D Q:testBoard.getAmazons()) {
            if (Q.getSide() == side) {
                queens.add(Q);
            }
        }
            // pic a random queen
            Amazon2D randomQ = queens.get(r.nextInt(queens.size()));
            //generate all her moves
            randomQ.possibleMoves(testBoard);
            ArrayList<Cell> possibleMoves = randomQ.getPossibleMoves();
            //pic a random move
             Cell randomMove = possibleMoves.get(r.nextInt(possibleMoves.size()));
             // move it
            randomQ.move(randomMove);
            randomQ.possibleMoves(testBoard);
            //from that move generate all possible shots
            ArrayList<Cell> shootMoves = randomQ.getPossibleMoves();
            // pic a random shot
            Cell randomShot = shootMoves.get(r.nextInt(shootMoves.size()));
            //save all in the move array
            //what is move array?
            moves.add(new Move(randomQ, randomMove, randomShot));




            }
    //algorithm interface

    @Override
    public Move getBestMove(AI player, MoveNode root) {
        return null;
    }
}








