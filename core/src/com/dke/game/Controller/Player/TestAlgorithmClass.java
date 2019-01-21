package com.dke.game.Controller.Player;

import com.dke.game.Models.AI.Algorithm;
import com.dke.game.Models.AI.MINMAX.TestBoard;

public class TestAlgorithmClass {
    public static void compete(Algorithm a1, Algorithm a2, int loops){
        //TODO: Initialize testboard, also change Algorithm interface so monteCarlo doesnt pull from gameloop but the right place.

        double sum= 0;
        for(int i = 0; i< loops; i++){

        }
        double score = sum/(double)loops;
        System.out.println(a1.getClass().getSimpleName() + " won " + score*100 + "% of the time against " + a2.getClass().getSimpleName() +" after "+ loops +" loops!");
    }

    public int Playout(TestBoard board, Algorithm a1, Algorithm a2, AI p1, AI p2, boolean a1Turn){
        if(a1Turn){

        }

        return 1;
    }
}
