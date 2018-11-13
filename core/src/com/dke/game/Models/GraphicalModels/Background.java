package com.dke.game.Models.GraphicalModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
    /**
     * The background as an object to be able to dfraw it to the stage as an actor
     */
    private Texture image;
    public Background() {
        image = new Texture(Gdx.files.internal("Images/Background-2.jpg"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }
}
