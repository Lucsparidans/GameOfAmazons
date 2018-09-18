package com.dke.game.Models.DataStructs;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Piece extends Actor {
    public Piece(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    protected abstract String getID();
    protected ShapeRenderer shapeRenderer;
}
