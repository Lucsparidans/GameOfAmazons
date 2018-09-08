package com.dke.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dke.game.Controller.MainLoop;


public class DesktopLauncher {
    private static final int width = 800;
    private static final int height = 600;
    public static boolean fullScreen = false;   //Fullscreen is really buggy...
    private static LwjglApplicationConfiguration config;

    public static void main(String[] args) {
        config = new LwjglApplicationConfiguration();
        config.fullscreen = fullScreen;
        config.title = "Game of Amazons";
        config.resizable = false;
        if(!fullScreen){
            setSize();
        }
        new LwjglApplication(new MainLoop(), config);
    }

    private static void setSize() {
        config.width = width;
        config.height = height;
    }
}
