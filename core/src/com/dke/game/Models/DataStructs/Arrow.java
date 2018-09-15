package com.dke.game.Models.DataStructs;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Arrow extends Piece{

    private static int ID = 0;
    private Integer idNumber;
    private static String idString = "Arrow: ";

    public Arrow(ShapeRenderer shapeRenderer) {
        super(shapeRenderer);
        idNumber = ID++;
    }

    @Override
    protected String getID() {
        return idString.concat(idNumber.toString());
    }
}
