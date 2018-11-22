package com.dke.game.Models.DataStructs;

import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;

public class Pieces {
    private Amazon2D[] amazon2DArrayList;
    private ArrayList<Arrow2D> arrow2DArrayList;

    public Pieces(Amazon2D[] amazon2DArrayList, ArrayList<Arrow2D> arrow2DArrayList) {
        this.amazon2DArrayList = amazon2DArrayList;
        this.arrow2DArrayList = arrow2DArrayList;
    }

    public Amazon2D[] getAmazon2DArrayList() {
        return amazon2DArrayList;
    }

    public ArrayList<Arrow2D> getArrow2DArrayList() {
        return arrow2DArrayList;
    }
}
