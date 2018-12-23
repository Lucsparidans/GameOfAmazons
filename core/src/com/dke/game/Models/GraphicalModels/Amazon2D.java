package com.dke.game.Models.GraphicalModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;

/**
 * Graphical information of the amazon/queen
 */
public class Amazon2D extends Amazon {
    private Sprite icon;
    private boolean test;

    public Amazon2D(char side,Cell cell, boolean test, int index) {
        super(side, cell,index);
        this.test=test;
        if(!test) {
            icon = new Sprite(new Texture(Gdx.files.internal("Icons/Queens.png")));
            if (side == 'W') {
                icon.setRegion(0, 0, 300, 283);
            } else if (side == 'B') {
                icon.setRegion(301, 0, 300, 283);

            } else {
                System.out.println("nee");
            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!test) {
            batch.draw(icon, location.getX(), location.getY(), Cell.CELL_SIZE, Cell.CELL_SIZE);
        }
    }

    public boolean isTest() {
        return test;
    }
}
