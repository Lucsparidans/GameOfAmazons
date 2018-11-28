package com.dke.game.Models.AI.Sara;

import com.dke.game.Models.DataStructs.Cell;
import com.dke.game.Models.GraphicalModels.Board2D;

import java.util.ArrayList;
import java.util.List;

public class State {
    private Board2D board;
    private int playerNo;
    private int visitCount;
    private double winScore;

    public State() {
        board = new Board2D();
    }

    public State(State state) {
        this.board = new Board2D(state.getBoard());
        this.playerNo = state.getPlayerNo();
        this.visitCount = state.getVisitCount();
        this.winScore = state.getWinScore();
    }

    public State(Board2D board) {
        this.board = new Board2D(board);
    }

    Board2D getBoard() {
        return board;
    }

    void setBoard(Board2D board) {
        this.board = board;
    }

    int getPlayerNo() {
        return playerNo;
    }

    void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    int getOpponent() {
        return 3 - playerNo;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    double getWinScore() {
        return winScore;
    }

    void setWinScore(double winScore) {
        this.winScore = winScore;
    }

    public List<State> getAllPossibleStates() {
        List<State> possibleStates = new ArrayList<>();
        List<Cell> availablePositions = this.board.();
        availablePositions.forEach(p -> {
            State newState = new State(this.board);
            newState.setPlayerNo(3 - this.playerNo);
            newState.getBoard().performMove(newState.getPlayerNo(), p);
            possibleStates.add(newState);
        });
        return possibleStates;
    }

    void incrementVisit() {
        this.visitCount++;
    }

    void addScore(double score) {
        if (this.winScore != Integer.MIN_VALUE)
            this.winScore += score;
    }

    void randomPlay() {
        List<Position> availablePositions = this.board.getEmptyPositions();
        int totalPossibilities = availablePositions.size();
        int selectRandom = (int) (Math.random() * totalPossibilities);
        this.board.performMove(this.playerNo, availablePositions.get(selectRandom));
    }

    void togglePlayer() {
        this.playerNo = 3 - this.playerNo;
    }
