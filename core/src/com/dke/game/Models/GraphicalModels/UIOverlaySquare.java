package com.dke.game.Models.GraphicalModels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Stack;

public class UIOverlaySquare extends Actor {
    public UIOverlaySquare(Stack stack) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.setColor(Color.OLIVE);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(20,20,2);
        renderer.end();
        batch.begin();
    }
}
