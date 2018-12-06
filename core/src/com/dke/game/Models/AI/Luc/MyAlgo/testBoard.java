package com.dke.game.Models.AI.Luc.MyAlgo;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dke.game.Models.DataStructs.Board;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Piece;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Arrow2D;

import java.util.ArrayList;

public class testBoard {
    /**
     * The graphical information of the board class, this class also contain all the information about the board there is.
     */

    private static ShapeRenderer shapeRenderer;
    private Cell[][] boardCoordinates;
    Amazon2D[] amazons;
    ArrayList<Arrow2D> arrows;

    public testBoard(Amazon2D[] amazons,ArrayList<Arrow2D> arrows) {
        boardCoordinates = new Cell[Board.height][Board.width];
        arrows = new ArrayList<>();

        placePieces(amazons,arrows);
    }
    private void createTestPieces(Amazon2D[] amazons,ArrayList<Arrow2D> arrows){
        int counter = 0;
        for (Amazon2D a:amazons) {
            amazons[counter] = new Amazon2D(a.getSide(),boardCoordinates[a.getCell().getI()][a.getCell().getJ()],true);
        }

        for (Arrow2D a:arrows) {
            this.arrows.add(new Arrow2D(boardCoordinates[a.getCell().getI()][a.getCell().getJ()],true));
        }
    }

    //mark as occupied?????
    public Piece occupy(Piece piece, Cell cell) {
        this.boardCoordinates[cell.getI()][cell.getJ()].occupy(piece);
        return piece;
    }

    public void placePieces(Amazon2D[] amazon2DS, ArrayList<Arrow2D> arrow2DS) {
        for (Amazon2D a : amazon2DS) {
            this.occupy(a, boardCoordinates[a.getCell().getI()][a.getCell().getJ()]);
            a.updateCell(boardCoordinates[a.getCell().getI()][a.getCell().getJ()]);
        }
        for (Arrow2D a : arrow2DS) {
            this.occupy(a, boardCoordinates[a.getCell().getI()][a.getCell().getJ()]);
            a.updateCell(boardCoordinates[a.getCell().getI()][a.getCell().getJ()]);
        }
    }


    public Cell[][] getBoard() {
        return this.boardCoordinates.clone();
    }




}
