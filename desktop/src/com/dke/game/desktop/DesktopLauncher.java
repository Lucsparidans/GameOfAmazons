package com.dke.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dke.game.View.View;

public class DesktopLauncher {
    private static final int width = 1920;
    private static final int height = 1200;
    public static boolean fullScreen = false;   //Fullscreen is really buggy...
    private static LwjglApplicationConfiguration config;

    public static void main(String[] arg) {
        config = new LwjglApplicationConfiguration();
        config.fullscreen = fullScreen;
        config.title = "Game of Amazons";
        config.resizable = false;
        if(!fullScreen){
            setSize();
        }
        new LwjglApplication(new View(), config);
    }

    private static void setSize() {
        config.width = width;
        config.height = height;
    }
}
