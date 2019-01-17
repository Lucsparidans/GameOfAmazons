package com.dke.game.Views;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dke.game.Controller.ViewManager;



public abstract class View extends InputAdapter implements ApplicationListener {


    /**
     * Class that represents what you see on the screen
     */


    protected ViewManager viewManager;

    protected View(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if(viewManager.stackSize()==1){
                Gdx.app.exit();
            }
            else if(viewManager.stackSize()>1) {
                viewManager.pop();
            }
        }
    }
}


