package com.dke.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dke.game.Controller.MainLoop;


public class DesktopLauncher {

    private static LwjglApplicationConfiguration config;

    public static void main(String[] args) {
        config = new LwjglApplicationConfiguration();
        config.title = "Game of Amazons";
        config.fullscreen = false;
        config.resizable = false;
        config.width = 800;
        config.height = 600;

        new LwjglApplication(new MainLoop(), config);


    }

}
