package com.dke.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dke.game.Controller.MainLoop;
import com.dke.game.Models.DataStructs.Cell;


public class DesktopLauncher {

    private static LwjglApplicationConfiguration config;

    public static void main(String[] args) {
        config = new LwjglApplicationConfiguration();
        config.title = "Game of Amazons";

        config.resizable = false;
        DisplayMode displayMode = DisplayMode.HIGH_REZ;
        setDisplayMode(config,displayMode);

        new LwjglApplication(new MainLoop(), config);


    }
    private static void setDisplayMode(LwjglApplicationConfiguration config,DisplayMode displayMode){
        switch (displayMode) {
            case LOW_REZ:
                config.fullscreen = false;
                config.width = 800;
                config.height = 600;
                Cell.CELL_SIZE = 40;
                break;

            case MED_REZ:
                config.fullscreen = false;
                config.width = 1366;
                config.height = 768;
                Cell.CELL_SIZE = 60;
                break;
            case HIGH_REZ:
                config.fullscreen = true;
                config.width = 1920;
                config.height = 1080;
                Cell.CELL_SIZE = 90;
                break;
        }
    }


    public enum DisplayMode {
        LOW_REZ, MED_REZ, HIGH_REZ
    }
}
