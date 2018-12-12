package com.dke.game.Controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.dke.game.Views.MenuView;

/**
 * It is required to have this class by libGdX and because this class is technically also kinda a view, it made it only render other view and not itself
 * Rendering the other view is done by rendering the top view in the viewManager stack
 */

public class MainLoop extends ApplicationAdapter {
    private ViewManager viewManager;
    public static Skin skin;


    @Override //Method that initialises the whole program
    public void create() {

        skin = new Skin(Gdx.files.internal("Skins/cloud-form/skin/cloud-form-ui.json"));
        viewManager = new ViewManager();
        //viewManager.push(new ScoreView(viewManager,0,80));
        //gameLoop = new GameLoop(viewManager);
        //viewManager.push(new GameView(viewManager));
        viewManager.push(new MenuView(viewManager));
        Gdx.gl.glClearColor(1, 1, 1, 1);
    }

    @Override //Renders the top view from viewManager and handles quitting when esc is pressed
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewManager.peek().render();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }


    @Override //Getting rid of memory heavy objects
    public void dispose() {
        skin.dispose();
    }
}
