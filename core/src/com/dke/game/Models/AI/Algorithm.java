package com.dke.game.Models.AI;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.AI.Luc.Move;
import com.dke.game.Models.AI.Luc.MoveNode;


public interface Algorithm {
   Move getBestMove(AI player, MoveNode root);
}
