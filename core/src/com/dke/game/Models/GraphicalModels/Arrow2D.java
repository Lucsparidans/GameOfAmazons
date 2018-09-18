package com.dke.game.Models.GraphicalModels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dke.game.Models.DataStructs.Arrow;

public class Arrow2D extends Arrow {
    public Arrow2D(ShapeRenderer shapeRenderer) {
        super(shapeRenderer);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ShapeRenderer r = new ShapeRenderer();
        r.setColor(Color.BLACK);
        r.begin(ShapeRenderer.ShapeType.Filled);
        r.circle(40,40,20);
        r.end();
    }
}
