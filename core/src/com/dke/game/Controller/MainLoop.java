package com.dke.game.Controller;
import com.dke.game.Controller.States.*;
import com.dke.game.Models.DataStructs.*;
import com.dke.game.Models.GraphicalModels.*;
import com.dke.game.View.*;



public class MainLoop {

    private View view;
    private StateManager stateManager;

    private Thread graphics;
    private Thread gameLogic;

    public MainLoop(View v){
        this.view = v;
        stateManager.push(new MenuState());
    }


}
