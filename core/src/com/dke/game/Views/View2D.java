package com.dke.game.Views;


import com.dke.game.Controller.ViewManager;
import com.dke.game.Models.DataStructs.Cell;

public abstract class View2D extends View {
    /**
     * Specialised view class for the game state
     */
    public View2D(ViewManager viewManager) {
        super(viewManager);
    }
    protected abstract Cell getSelectedCell();
}
