package com.dke.game.Models.DataStructs;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Piece extends Actor {
    public Piece(Cell cell) {
        this.location = cell.getBottomLeft();
        cell.occupy(this);
    }

    protected abstract String getID();
    protected Coordinate location;
}
