package com.dke.game.Controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.dke.game.Controller.States.MenuState;

public class MainLoop extends ApplicationAdapter {
    private StateManager stateManager;
    public static Skin skin;


    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("Skins/cloud-form/skin/cloud-form-ui.json"));
        stateManager = new StateManager();
        stateManager.push(new MenuState(stateManager));
        Gdx.gl.glClearColor(1,1,1,1);
    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateManager.peek().render();
    }

    @Override
    public void dispose() {

    }
}
