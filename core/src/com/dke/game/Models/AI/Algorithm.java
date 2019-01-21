package com.dke.game.Models.AI;

import com.dke.game.Controller.Player.AI;
import com.dke.game.Controller.Player.Player;
import com.dke.game.Models.DataStructs.Move;


public interface Algorithm {
   Move getBestMove(AI player);
   void initialize(Player p);

}
