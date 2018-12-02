package com.dke.game.Controller.Player;

import com.dke.game.Controller.GameLoop;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Views.GameView;

public abstract class Player {
    protected char side;
    private Amazon2D[] myAmazons;
    private Amazon2D[] enemyAmazons;

    public Player(char side, GameLoop gameLoop) {
        this.side = side;
        enemyAmazons = new Amazon2D[4];
        myAmazons = new Amazon2D[4];
        int counter = 0;
        int cnt=0;
        for (Amazon2D a:gameLoop.getAmazons()) {
            if(this.side==a.getSide()) {
                myAmazons[counter] = a;
                counter++;
            }
            else{
                enemyAmazons[cnt]=a;
                cnt++;
            }
        }
    }

    public Amazon2D[] getMyAmazons() {
        return myAmazons;
    }

    public Amazon2D[] getEnemyAmazons() {
        return enemyAmazons;
    }

    public abstract void performTurn();

    public char getSide() {
        return side;
    }
}
