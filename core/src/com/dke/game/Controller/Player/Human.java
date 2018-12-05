package com.dke.game.Controller.Player;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dke.game.Controller.GameLoop;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Piece;
import com.dke.game.Models.GraphicalModels.Amazon2D;
import com.dke.game.Models.GraphicalModels.Board2D;
import com.dke.game.Models.GraphicalModels.UIOverlaySquare;
import com.dke.game.Views.GameView;

import java.util.ArrayList;

public class Human extends Player {
    private int phase = 1;
    private UIOverlaySquare uiOverlaySquare;
    private Amazon2D selectedAmazon;
    private boolean displayOverlay;
    private GameView view;
    private Board2D board2D;
    private Stage Ui;
    private ShapeRenderer shapeRenderer;


    public Human(char side, GameView view, GameLoop gameLoop) {
        super(side);
        this.shapeRenderer = new ShapeRenderer();
        this.view = view;
        this.board2D = view.getBoard2D();
        this.Ui = view.getUi();
    }

    @Override
    public void performTurn() {
        view.setRepeat(false);
                    if (phase == 1) {
                        phaseOne();
                    } else if (phase == 2) {
                        phaseTwo();
                    } else if (phase == 3) {
                        phaseThree();
                        view.setTurnCounter(view.getTurnCounter()+1);
                    }
    }

    private void phaseOne() {

        Cell c = view.getSelectedCell();
        if (c != null) {
            if (c.isOccupied()) {

                Piece content = c.getContent();
                if (content instanceof Amazon2D) {
                    if (((Amazon2D) content).getSide() == this.side) {
                        if (selectedAmazon == null) {
                            selectedAmazon = (Amazon2D) content;        //Amazon is selected
                            displayOverlay = true;
                            phase = 2;
                        } else if (selectedAmazon == content) {
                            Ui.clear();                                 //Toggle visibility of certain amazon
                            //<editor-fold desc="Toggle displayOverlay">
                            if (displayOverlay) {
                                displayOverlay = false;
                                phase = 1;
                            } else {
                                displayOverlay = true;
                                phase = 2;
                            }
                            //</editor-fold>
                        } else {
                            Ui.clear();
                            selectedAmazon = (Amazon2D) content; //Different amazon was selected
                            displayOverlay = true;
                            phase = 2;
                        }

                        selectedAmazon.possibleMoves(board2D);
                        uiOverlaySquare = new UIOverlaySquare(selectedAmazon.getPossibleMoves(), board2D, shapeRenderer);
                        if (displayOverlay) {
                            Ui.addActor(uiOverlaySquare);

                        }
                    }
                }
            }
        }
    }

    private void phaseTwo() {
        Cell c = view.getSelectedCell();
        if (c != null) {

            Piece content = c.getContent();
            if (content != null) {
                phase = 1;
                view.setRepeat(true);

            } else {
                ArrayList<Cell> pm = selectedAmazon.getPossibleMoves();
                for (Cell test : pm) {
                    if (test.getI() == c.getI() && test.getJ() == c.getJ()) {
                        selectedAmazon.move(test);
                        Ui.clear();
                        selectedAmazon.possibleMoves(board2D);
                        uiOverlaySquare = new UIOverlaySquare(selectedAmazon.getPossibleMoves(), board2D, shapeRenderer);
                        Ui.addActor(uiOverlaySquare);
                        phase = 3;
                    }

                }
            }
        }
    }

    private void phaseThree() {
        Cell c = view.getSelectedCell();
        if (c != null) {
            if (!c.isOccupied()) {
                ArrayList<Cell> pm = selectedAmazon.getPossibleMoves();
                for (Cell test : pm) {
                    if (test.getI() == c.getI() && test.getJ() == c.getJ()) {
                        selectedAmazon.shoot(test);
                        Ui.clear();
                        phase = 1;
                    }
                }
            }
        }
    }

    public int getPhase() {
        return phase;
    }
}