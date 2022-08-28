package com.company;

public class Player {
    private int score;
    private final String name;

    public Player(String name){
        this.name = name;
    }

    public void addScore(int length) {
        score += length;
    }

    public int getScore(){
        return score;
    }

    public String getName(){
        return name;
    }
}
