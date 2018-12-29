package com.dke.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dke.game.Controller.MainLoop;
import com.dke.game.Models.AI.Luc.MyAlgo.ChristmasCarlo;
import com.dke.game.Models.DataStructs.Cell;

import java.util.ArrayList;


public class DesktopLauncher {

    private static LwjglApplicationConfiguration config;
    //Platform specific launcher for PC
    public static void main(String[] args) {
        config = new LwjglApplicationConfiguration();
        config.title = "Game of Amazons";

        config.resizable = false;
        DisplayMode displayMode = DisplayMode.HIGH_REZ;
        setDisplayMode(config,displayMode);

        new LwjglApplication(new MainLoop(), config);


        //TODO REMOVE TEST THIS:
        {
            ChristmasCarlo TESTCARLO = new ChristmasCarlo();
//            char[][] testCharBoard = new char[10][10];
//            testCharBoard[3][0] = 'W';
//            testCharBoard[6][0] = 'W';
//            testCharBoard[0][3] = 'W';
//            testCharBoard[9][3] = 'W';
//            testCharBoard[0][6] = 'B';
//            testCharBoard[9][6] = 'B';
//            testCharBoard[3][9] = 'B';
//            testCharBoard[6][9] = 'B';

            char[][] testCharBoard = new char[5][5];
            testCharBoard[2][1] = 'W';
            testCharBoard[4][4] = 'B';
//            ArrayList<char[][]> nextstates = TESTCARLO.generateNextPossibleStates('B', testCharBoard);
//            System.out.println("nexstates: " + nextstates.size());
//            for (int i = 0; i < nextstates.size(); i += 50) {
//                ChristmasCarlo.printCharMatrix(nextstates.get(i));
//            }
            TESTCARLO.expandRandomly('B','W',testCharBoard);
        }

    }
    //Multiple display configurations you can choose from by changing the displayMode variable above
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
