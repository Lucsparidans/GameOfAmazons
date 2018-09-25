package com.dke.game.Models.DataStructs;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Piece extends Actor {
    public Piece(Coordinate location) {
        this.location = location;
    }

    protected abstract String getID();
    protected Coordinate location;
}
