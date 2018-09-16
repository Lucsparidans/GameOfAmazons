package com.dke.game.Models.DataStructs;

public  class Board {
    protected final int height = 10;
    protected final int width = 10;
    private Cell[][] board = new Cell[height][width];
    private   Amazon queen;
    protected Arrow wall;


    // Default set of amazons at the start of the game:

    public void SetAmazonsForStart(){


       board[3][0].occupy(queen = new Amazon('B') {
                });
         board[6][0].occupy(queen = new Amazon('W') {
                });
           board[0][3].occupy(queen = new Amazon('B') {
           });
           board[9][3].occupy(queen = new Amazon('W') {

           });
           board[9][6].occupy(queen = new Amazon('W') {
           });
           board[0][6].occupy(queen = new Amazon('B') {
           });
           board[3][9].occupy(queen = new Amazon('B') {
           });
           board[6][9].occupy(queen = new Amazon('W') {
           });
       }






    }

