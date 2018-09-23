package com.dke.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dke.game.Controller.MainLoop;


public class DesktopLauncher {
    //    private static final int width = Gdx.graphics.getWidth();
//    private static final int height = Gdx.graphics.getHeight();
    private static LwjglApplicationConfiguration config;

    public static void main(String[] args) {
        config = new LwjglApplicationConfiguration();
        config.title = "Game of Amazons";
        config.fullscreen = true;
        config.resizable = false;
        config.width = 1920;
        config.height = 1080;

        new LwjglApplication(new MainLoop(), config);


    }

}
