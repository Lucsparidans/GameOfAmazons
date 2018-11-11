package com.dke.game.Models.DataStructs;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Abstract superclass of all actors that can be placed on the board because they have to be able to be saved as an instance of piece
 */
public abstract class Piece extends Actor {
    public Piece(Cell cell) {
        this.location = cell.getBottomLeft();
        cell.occupy(this);
    }

    protected abstract String getID();
    protected Coordinate location;
}
