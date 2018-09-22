package com.dke.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dke.game.Controller.MainLoop;


public class DesktopLauncher {
    private static final int width = 1280;
    private static final int height = 720;
    public static boolean fullScreen = false;   //Fullscreen is really buggy...
    private static LwjglApplicationConfiguration config;

    public static void main(String[] args) {
        config = new LwjglApplicationConfiguration();
        config.title = "Game of Amazons";
        config.resizable = false;
        config.fullscreen = fullScreen;
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
