package com.dke.game.Models.GraphicalModels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Coordinate;

import java.util.ArrayList;

public class UIOverlaySquare extends Actor {
    private ArrayList<Cell> todo;
    private ShapeRenderer renderer;
    private Cell[][] boardCoordninates;
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

    public void addObject(Cell c){
        todo.add(c);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        renderer.setColor(Color.BLACK);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Cell c:todo) {
            Coordinate center = getCellCenter(c);
            renderer.circle(center.getX(),center.getY(),Cell.CELL_SIZE/20);
        }
        renderer.end();
        batch.begin();
    }
    private Coordinate getCellCenter(Cell cell) {

//        System.out.printf("topLeft x: %d & y: %d\n", boardCoordinates[i][j].getTopLeft().getX(),boardCoordinates[i][j].getTopLeft().getY());
//        System.out.printf("bottomLeft x: %d & y: %d\n", boardCoordinates[i][j].getBottomLeft().getX(),boardCoordinates[i][j].getBottomLeft().getY());
//        System.out.printf("bottomRight x: %d & y: %d\n", boardCoordinates[i][j].getBottomRight().getX(),boardCoordinates[i][j].getBottomRight().getY());
        return new Coordinate(cell.getBottomLeft().getX() + ((cell.getBottomRight().getX() - cell.getBottomLeft().getX()) / 2),
                cell.getBottomLeft().getY() + ((cell.getTopLeft().getY() - cell.getBottomLeft().getY()) / 2));
    }
}
