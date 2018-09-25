package com.dke.game.Models.GraphicalModels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dke.game.Models.DataStructs.Coordinate;
import com.dke.game.Models.DataStructs.Square;

import java.util.ArrayList;

public class UIOverlaySquare extends Actor {
    private ArrayList<Coordinate> todo;
    private ShapeRenderer renderer;
    private Square[][] boardCoordninates;
    public UIOverlaySquare(ArrayList todo, Board2D board, ShapeRenderer renderer) {
        this.todo = todo;
        this.boardCoordninates = board.getBoardCoordinates();
        this.renderer = renderer;

    }

    public UIOverlaySquare(Board2D board2D, ShapeRenderer renderer) {
        this(new ArrayList(),board2D,renderer);

    }

    public UIOverlaySquare(Board2D board2D) {
        this(new ArrayList(),board2D, new ShapeRenderer());
    }

    public void addObject(Coordinate c){
        todo.add(c);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        renderer.setColor(Color.BLACK);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Coordinate c:todo) {
            renderer.circle(c.getX(),c.getY(),5);
        }
        renderer.end();
        batch.begin();
    }
}
