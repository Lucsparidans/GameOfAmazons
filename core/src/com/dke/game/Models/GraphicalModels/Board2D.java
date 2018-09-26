package com.dke.game.Models.GraphicalModels;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dke.game.Models.DataStructs.Board;
import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.DataStructs.Coordinate;


public class Board2D extends Board {

    private static ShapeRenderer shapeRenderer;
    private final int X_POS_BOARD = (Gdx.graphics.getWidth() / 2) - (this.calcBoardWidth() / 2);
    private final int Y_POS_BOARD = (Gdx.graphics.getHeight() / 2) - (this.calcBoardHeight() / 2);
    //private final int MARGAIN_BACKGROUND = Cell.CELL_SIZE;
    private static BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));
    private Cell[][] boardCoordinates;
    //private final float CAP_HEIGHT = font.getData().capHeight;


    public Board2D(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
        super.createBoard();
        font.setColor(Color.BLACK);
        font.getData().setScale(1);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        boardCoordinates = new Cell[super.height][super.width];
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        shapeRenderer.translate(getX(), getY(), 0);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


//        shapeRenderer.setColor(Color.BLACK);
//        shapeRenderer.rect(X_POS_BOARD - MARGAIN_BACKGROUND, Y_POS_BOARD - MARGAIN_BACKGROUND, (Cell.CELL_SIZE * super.width) + (2 * MARGAIN_BACKGROUND), (Cell.CELL_SIZE * super.height) + (2 * MARGAIN_BACKGROUND));

        for (int i = X_POS_BOARD; i < (super.height * Cell.CELL_SIZE + X_POS_BOARD); i += Cell.CELL_SIZE) {
            for (int j = Y_POS_BOARD; j < (super.width * Cell.CELL_SIZE + Y_POS_BOARD); j += Cell.CELL_SIZE) {
                if (i / Cell.CELL_SIZE % 2 != 0) {
                    if (j / Cell.CELL_SIZE % 2 != 0) {
                        shapeRenderer.setColor(Color.BROWN);
                        shapeRenderer.rect(i, j, Cell.CELL_SIZE, Cell.CELL_SIZE);
                        if(boardCoordinates[(i - X_POS_BOARD) / Cell.CELL_SIZE][(j - Y_POS_BOARD) / Cell.CELL_SIZE] == null) {
                            boardCoordinates[(i - X_POS_BOARD) / Cell.CELL_SIZE][(j - Y_POS_BOARD) / Cell.CELL_SIZE] =
                                    new Cell(
                                            new Coordinate(i, j + Cell.CELL_SIZE),
                                            new Coordinate(i + Cell.CELL_SIZE, j + Cell.CELL_SIZE),
                                            new Coordinate(i + Cell.CELL_SIZE, j),
                                            new Coordinate(i, j), (i - X_POS_BOARD) / Cell.CELL_SIZE, (j - Y_POS_BOARD) / Cell.CELL_SIZE);
                        }
                    } else {
                        shapeRenderer.setColor(Color.valueOf("#FFF8DC"));
                        shapeRenderer.rect(i, j, Cell.CELL_SIZE, Cell.CELL_SIZE);
                        if(boardCoordinates[(i - X_POS_BOARD) / Cell.CELL_SIZE][(j - Y_POS_BOARD) / Cell.CELL_SIZE] == null) {
                            boardCoordinates[(i - X_POS_BOARD) / Cell.CELL_SIZE][(j - Y_POS_BOARD) / Cell.CELL_SIZE] =
                                    new Cell(
                                            new Coordinate(i, j + Cell.CELL_SIZE),
                                            new Coordinate(i + Cell.CELL_SIZE, j + Cell.CELL_SIZE),
                                            new Coordinate(i + Cell.CELL_SIZE, j),
                                            new Coordinate(i, j), (i - X_POS_BOARD) / Cell.CELL_SIZE, (j - Y_POS_BOARD) / Cell.CELL_SIZE);
                        }
                    }

                }
                if (i / Cell.CELL_SIZE % 2 == 0) {
                    if (j / Cell.CELL_SIZE % 2 == 0) {
                        shapeRenderer.setColor(Color.BROWN);
                        shapeRenderer.rect(i, j, Cell.CELL_SIZE, Cell.CELL_SIZE);
                        if(boardCoordinates[(i - X_POS_BOARD) / Cell.CELL_SIZE][(j - Y_POS_BOARD) / Cell.CELL_SIZE] == null) {
                            boardCoordinates[(i - X_POS_BOARD) / Cell.CELL_SIZE][(j - Y_POS_BOARD) / Cell.CELL_SIZE] =
                                    new Cell(
                                            new Coordinate(i, j + Cell.CELL_SIZE),
                                            new Coordinate(i + Cell.CELL_SIZE, j + Cell.CELL_SIZE),
                                            new Coordinate(i + Cell.CELL_SIZE, j),
                                            new Coordinate(i, j), (i - X_POS_BOARD) / Cell.CELL_SIZE, (j - Y_POS_BOARD) / Cell.CELL_SIZE);
                        }
                    } else {
                        shapeRenderer.setColor(Color.valueOf("#FFF8DC"));
                        shapeRenderer.rect(i, j, Cell.CELL_SIZE, Cell.CELL_SIZE);
                        if (boardCoordinates[(i - X_POS_BOARD) / Cell.CELL_SIZE][(j - Y_POS_BOARD) / Cell.CELL_SIZE] == null) {
                            boardCoordinates[(i - X_POS_BOARD) / Cell.CELL_SIZE][(j - Y_POS_BOARD) / Cell.CELL_SIZE] =
                                    new Cell(
                                            new Coordinate(i, j + Cell.CELL_SIZE),
                                            new Coordinate(i + Cell.CELL_SIZE, j + Cell.CELL_SIZE),
                                            new Coordinate(i + Cell.CELL_SIZE, j),
                                            new Coordinate(i, j), (i - X_POS_BOARD) / Cell.CELL_SIZE, (j - Y_POS_BOARD) / Cell.CELL_SIZE);
                        }
                    }
                }
            }
        }
        shapeRenderer.end();
        batch.begin();
        GlyphLayout layout = new GlyphLayout();
        //TODO location of letter should be equal to for example bottomLeft.getY() + (Cell.CELL_SIZE/2) - layout.width to perfectly center all the letters
        char ch = 'A';
        for (int i = 0; i < boardCoordinates.length; i++) {
            for (int j = 0; j < boardCoordinates[i].length; j++) {
                if (j == 0) {
                    Coordinate c = boardCoordinates[i][j].getBottomLeft();
                    layout.setText(font, Character.toString(ch));
                    if(ch == 'I') {
                        font.draw(batch, layout, c.getX() + layout.width, c.getY() - (font.getLineHeight()/10));
                    }
                    else{
                        font.draw(batch, layout, c.getX() + (Cell.CELL_SIZE / 10), c.getY() - (font.getLineHeight() / 10));

                    }
                    ch++;
                }
                if (i == 0) {
                    Coordinate c = boardCoordinates[i][j].getTopLeft();
                    layout.setText(font, Integer.toString(j + 1));
                    font.draw(batch, layout, c.getX() - layout.width, c.getY() - (font.getLineHeight() / 10));
                }
            }
        }


    }

    public Cell[][] getBoardCoordinates() {
        return boardCoordinates.clone();
    }

    private int calcBoardWidth() {
        return super.width * Cell.CELL_SIZE;
    }
    public Cell[][] getBoard(){
        return super.board.clone();
    }

    private int calcBoardHeight() {
        return super.height * Cell.CELL_SIZE;
    }
}
