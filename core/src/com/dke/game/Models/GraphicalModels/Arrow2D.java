package com.dke.game.Models.GraphicalModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.dke.game.Models.DataStructs.Arrow;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Coordinate;

public class Arrow2D extends Arrow {
    private Texture icon;

    public Arrow2D(Coordinate location) {
        super(location);
        this.icon = new Texture(Gdx.files.internal("Icons/Arrow.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(icon, location.getX(), location.getY(), Cell.CELL_SIZE,Cell.CELL_SIZE);
    }
}
