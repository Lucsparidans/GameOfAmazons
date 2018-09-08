package com.dke.game.Controller.States;

import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class State {
   protected abstract Stage createStage();
   protected abstract void updateView();
   public abstract void dispose();
}
