package com.dke.game.Models.AI;

import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Luc.GameState;
import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.AI.Luc.MyAlgo.State;
import com.dke.game.Models.AI.Luc.Node;
import com.dke.game.Models.GraphicalModels.Board2D;

public interface Algorithm {
   Move getBestMove(Player player, Node<Move>root);
}
