package com.greatLearning.model;

public class Player {
    private String name;
    private int rank;
    private int totalScore;
    private int lastScore;
    private boolean skipTurn;
    public Player (String playerName){
        this.rank = 0;
        this.totalScore = 0;
        this.lastScore = 0;
        this.name = playerName;
        this.skipTurn = false;

    }

    public boolean isSkipTurn() {
        return skipTurn;
    }

    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getLastScore() {
        return lastScore;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
