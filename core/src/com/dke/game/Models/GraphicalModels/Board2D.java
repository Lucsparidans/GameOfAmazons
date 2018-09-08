package com.dke.game.Models.GraphicalModels;


import com.dke.game.Models.DataStructs.Cell;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dke.game.Models.DataStructs.Board;


public class Board2D extends Board {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;


    public void draw() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
//        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        for (int i = 0; i < super.height; i++) {
            for (int j = 0; j < super.width; j++) {
                if (i % 2 != 0) {
                    if (j % 2 != 0) {
                        shapeRenderer.rect(i * Cell.CELL_SIZE, j * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);

                    }

                }
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        shapeRenderer.rect(i * Cell.CELL_SIZE, j * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);

                    }

                }

            }
        }

        shapeRenderer.end();


    }
}
