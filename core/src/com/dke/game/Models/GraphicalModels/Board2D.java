package com.dke.game.Models.GraphicalModels;


import com.dke.game.Models.DataStructs.Cell;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dke.game.Models.DataStructs.Board;


public class Board2D extends Board {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private final int X_POS_BOARD = 15; //Cannot be 0!!
    private final int Y_POS_BOARD = 10; //Cannot be 0!!
    private final int MARGAIN_BACKGROUND = 0;

    public void draw() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
//        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = X_POS_BOARD; i < (super.height + X_POS_BOARD); i++) {
            for (int j = Y_POS_BOARD; j < (super.width + Y_POS_BOARD); j++) {
                if (i % 2 != 0) {
                    if (j % 2 != 0) {
                        shapeRenderer.setColor(Color.WHITE);
                        shapeRenderer.rect(i * Cell.CELL_SIZE, j * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);
                    }
                    else{
                        shapeRenderer.setColor(Color.BLACK);
                        shapeRenderer.rect(i * Cell.CELL_SIZE, j * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);
                    }

                }
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        shapeRenderer.setColor(Color.WHITE);
                        shapeRenderer.rect(i * Cell.CELL_SIZE, j * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);
                    }
                    else{
                        shapeRenderer.setColor(Color.BLACK);
                        shapeRenderer.rect(i * Cell.CELL_SIZE, j * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);
                    }
                }
            }
        }
        shapeRenderer.rect(X_POS_BOARD,Y_POS_BOARD,(Cell.CELL_SIZE*super.height) + (2*Cell.CELL_SIZE),(Cell.CELL_SIZE*super.width) + (2*Cell.CELL_SIZE));
        shapeRenderer.end();


    }
}
