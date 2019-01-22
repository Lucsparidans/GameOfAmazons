package com.dke.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dke.game.Controller.GameLoop;
import com.dke.game.Controller.MainLoop;
import com.dke.game.Models.AI.Luc.MINMAX.ChristmasCarlo;
import com.dke.game.Models.DataStructs.Cell;

import javax.swing.*;
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
//        {
//            ChristmasCarlo TESTCARLO = new ChristmasCarlo('B',10,500,true);
//            char[][] testCharBoard = new char[10][10];
//            testCharBoard[3][0] = 'W';
//            testCharBoard[6][0] = 'W';
//            testCharBoard[0][3] = 'W';
//            testCharBoard[9][3] = 'W';
//            testCharBoard[0][6] = 'B';
//            testCharBoard[9][6] = 'B';
//            testCharBoard[3][9] = 'B';
//            testCharBoard[6][9] = 'B';
//
//            char[][] tinyBoard = new char[5][5];
//            tinyBoard[2][1] = 'W';
//            tinyBoard[4][4] = 'B';
//
//            long startTime = System.currentTimeMillis();
//            //TESTCARLO.startalgoWithCharArray(testCharBoard, 'B');
//            double amountofloops = 25;
//            double sum = 0;
//            for(int i = 0; i< amountofloops; i++) {
//                sum += TESTCARLO.expandRandomVSAI('B', 'B', testCharBoard);
//                System.out.println(i);
//            }
//            long endTime = System.currentTimeMillis();
//            System.out.println("That took " + (endTime - startTime) + " milliseconds");
//
//            System.out.println("avg wins: "+ sum/amountofloops);
//
////            testCharBoard[3][3] = 'B';
////            testCharBoard[5][5] = 'W';
//
////            long startTime = System.currentTimeMillis();
//
////            TESTCARLO.printCharMatrix(testCharBoard);
////            double sum = 0;
////            int tries = 500000;
////            for(int i = 0; i<tries; i++) {
////                sum+=TESTCARLO.expandRandomlyTestMethod('B', 'W', testCharBoard);
////            }
////            System.out.println(sum/tries);
////            long endTime = System.currentTimeMillis();
////            double t = (double) tries;
////
////            System.out.println("That took " + (endTime - startTime) + " milliseconds");
////            System.out.println("That took " + (endTime/t - startTime/t) + " milliseconds per expansion");
//
//            //char[][] randomnext = TESTCARLO.getNextRandomState('W', testCharBoard);
//            //TESTCARLO.printCharMatrix(randomnext);
//
//
//////            ArrayList<char[][]> nextstates = TESTCARLO.generateNextPossibleStates('B', testCharBoard);
//////            System.out.println("nexstates: " + nextstates.size());
//////            for (int i = 0; i < nextstates.size(); i += 50) {
//////                ChristmasCarlo.printCharMatrix(nextstates.get(i));
//////            }
////            TESTCARLO.expandRandomly('B','W',testCharBoard);
//        }

    }
    //Multiple display configurations you can choose from by changing the displayMode variable above
    public static void setDisplayMode(LwjglApplicationConfiguration config,DisplayMode displayMode){
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
