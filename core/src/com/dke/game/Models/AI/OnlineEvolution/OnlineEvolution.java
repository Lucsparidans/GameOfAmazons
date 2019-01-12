package com.dke.game.Models.AI.OnlineEvolution;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.MINMAX.MoveNode;
import com.dke.game.Models.AI.MINMAX.TestBoard;
import com.dke.game.Models.DataStructs.Move;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;

public class OnlineEvolution implements Algorithm {
    private GameState initialState;
    private TestBoard initialBoard;
    public OnlineEvolution(Amazon2D[] amazons, ArrayList<Arrow2D> arrows) {
        initialBoard = new TestBoard(amazons,arrows);
        initialState = new GameState(null,null,null, initialBoard);
    }
    @Override
    public Move getBestMove(AI player) {
        return null;
    }
}
