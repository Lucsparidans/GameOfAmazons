package com.dke.game.Controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.dke.game.Views.GameView;


public class MainLoop extends ApplicationAdapter {
    private ViewManager viewManager;
    public static Skin skin;


    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("Skins/cloud-form/skin/cloud-form-ui.json"));
        viewManager = new ViewManager();

        viewManager.push(new GameView(viewManager));
        //viewManager.push(new MenuView(viewManager));
        Gdx.gl.glClearColor(1, 1, 1, 1);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            viewManager.peek().render();

    }


    @Override
    public void dispose() {
        skin.dispose();
    }
}
