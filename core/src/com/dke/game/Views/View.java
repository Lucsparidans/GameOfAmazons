package com.dke.game.Views;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.dke.game.Controller.ViewManager;


public abstract class View extends InputAdapter implements ApplicationListener {
    protected ViewManager viewManager;

    protected View(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    protected void handleInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            //TODO implement a pause state
        }
    }
}
