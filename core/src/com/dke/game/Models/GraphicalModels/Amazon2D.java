package com.dke.game.Models.GraphicalModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dke.game.Models.DataStructs.Amazon;
import com.dke.game.Models.DataStructs.Cell;

public class Amazon2D extends Amazon {
    private Sprite icon;

    public Amazon2D(char side,Cell cell) {
        super(side, cell);
        icon = new Sprite(new Texture(Gdx.files.internal("Icons/Queens.png")));
        if (side == 'W') {
            icon.setRegion(0, 0, 300, 283);
        } else if (side == 'B') {
            icon.setRegion(301, 0, 300, 283);

        } else {
            System.out.println("nee");
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(icon, location.getX(), location.getY(), Cell.CELL_SIZE, Cell.CELL_SIZE);
    }
}
