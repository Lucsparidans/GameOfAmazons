package com.dke.game.Models.AI.Luc.MyAlgo;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Amazon2D;

import java.util.ArrayList;

public class State {
    private TestBoard testBoard;
    private Move move;
    private Player player;

    public State(TestBoard testBoard, Move move, Player player) {
        this.player = player;
        this.testBoard = testBoard;
        this.move = move;
    }

    public TestBoard getTestBoard() {
        return testBoard;
    }

    public Move getMove() {
        return move;
    }

    public Player getPlayer() {
        return player;
    }
    public TestBoard executeMove() {
        Amazon2D amazon2D = move.getQueen();
        Cell queenTo = move.getQueenTo();
        Cell arrowTo = move.getArrowTo();
        amazon2D.move(queenTo);
        amazon2D.shoot(arrowTo);
        return testBoard;
    }


}
