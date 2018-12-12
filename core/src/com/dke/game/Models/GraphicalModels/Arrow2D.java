package com.dke.game.Models.GraphicalModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.dke.game.Models.DataStructs.Arrow;
import com.dke.game.Models.DataStructs.Cell;

public class Arrow2D extends Arrow {
    private boolean test;
    /**
     * Graphical information of the arrows
     */
    private Texture icon;


    public Arrow2D(Cell cell,boolean test) {
        super(cell);
        this.test = test;
        if(!test) {
            this.icon = new Texture(Gdx.files.internal("Icons/Arrow.png"));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!test) {
            batch.draw(icon, location.getX(), location.getY(), Cell.CELL_SIZE, Cell.CELL_SIZE);
        }
    }
    public Cell getCell(){
        return super.cell;
    }

}
