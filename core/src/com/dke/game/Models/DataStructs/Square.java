package com.dke.game.Models.DataStructs;

public class Square {
    private Coordinate topLeft, topRight, bottomRight, bottomLeft;

    public Square(Coordinate topLeft, Coordinate topRight, Coordinate bottomRight, Coordinate bottomLeft) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }

    public Coordinate getTopLeft() {
        return topLeft;
    }

    public Coordinate getTopRight() {
        return topRight;
    }

    public Coordinate getBottomRight() {
        return bottomRight;
    }

    public Coordinate getBottomLeft() {
        return bottomLeft;
    }
}
