package com.dev.galagan.historicalgarden;

public class Player {
    private String name = "";
    private int[] score_list = new int[3];

    public Player(String name, int[] score_list) {
        this.name = name;
        this.score_list = score_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getScore_list() {
        return score_list;
    }

    public void setScore_list(int[] score_list) {
        this.score_list = score_list;
    }
}
