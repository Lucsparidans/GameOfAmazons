package com.dke.game.Models.GraphicalModels;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dke.game.Models.DataStructs.Board;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Coordinate;
import com.dke.game.Models.DataStructs.Square;


public class Board2D extends Board {

    private static ShapeRenderer shapeRenderer;
    private final int X_POS_BOARD = 5;
    private final int Y_POS_BOARD = 5;
    private final int MARGAIN_BACKGROUND = Cell.CELL_SIZE;
    private static BitmapFont font = new BitmapFont();
    private Square[][] boardCoordinates;

    public Board2D(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
        font.setColor(Color.BLACK);
        font.getData().setScale(0.25f);
        boardCoordinates = new Square[super.height][super.width];
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.translate(getX(), getY(), 0);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(X_POS_BOARD * Cell.CELL_SIZE - MARGAIN_BACKGROUND, Y_POS_BOARD * Cell.CELL_SIZE - MARGAIN_BACKGROUND, (Cell.CELL_SIZE * super.width) + (2 * MARGAIN_BACKGROUND), (Cell.CELL_SIZE * super.height) + (2 * MARGAIN_BACKGROUND));

        for (int i = X_POS_BOARD; i < (super.height + X_POS_BOARD); i++) {
            for (int j = Y_POS_BOARD; j < (super.width + Y_POS_BOARD); j++) {
                if (i % 2 != 0) {
                    if (j % 2 != 0) {
                        shapeRenderer.setColor(Color.WHITE);
                        shapeRenderer.rect(i * Cell.CELL_SIZE, j * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);
                        boardCoordinates[i][j] = new Square(new Coordinate(i+Cell.CELL_SIZE,j), //TODO Check coordinates for correctness!
                                new Coordinate(i+Cell.CELL_SIZE,j+Cell.CELL_SIZE),
                                new Coordinate(i,j+Cell.CELL_SIZE),
                                new Coordinate(i,j));
                    } else {
                        shapeRenderer.setColor(Color.BLACK);
                        shapeRenderer.rect(i * Cell.CELL_SIZE, j * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);
                        boardCoordinates[i][j] = new Square(new Coordinate(i+Cell.CELL_SIZE,j),
                                new Coordinate(i+Cell.CELL_SIZE,j+Cell.CELL_SIZE),
                                new Coordinate(i,j+Cell.CELL_SIZE),
                                new Coordinate(i,j));
                    }

                }
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        shapeRenderer.setColor(Color.WHITE);
                        shapeRenderer.rect(i * Cell.CELL_SIZE, j * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);
                        boardCoordinates[i][j] = new Square(new Coordinate(i+Cell.CELL_SIZE,j),
                                new Coordinate(i+Cell.CELL_SIZE,j+Cell.CELL_SIZE),
                                new Coordinate(i,j+Cell.CELL_SIZE),
                                new Coordinate(i,j));
                    } else {
                        shapeRenderer.setColor(Color.BLACK);
                        shapeRenderer.rect(i * Cell.CELL_SIZE, j * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);
                        boardCoordinates[i][j] = new Square(new Coordinate(i+Cell.CELL_SIZE,j),
                                new Coordinate(i+Cell.CELL_SIZE,j+Cell.CELL_SIZE),
                                new Coordinate(i,j+Cell.CELL_SIZE),
                                new Coordinate(i,j));
                    }
                }
            }
        }
        shapeRenderer.end();
        batch.begin();
        font.draw(batch, "A", 60, 20);


    }

}
