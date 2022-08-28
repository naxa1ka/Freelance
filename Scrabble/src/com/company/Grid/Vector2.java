package com.company.Grid;

//вспомогательный класс описывающий точку
//чтобы удобно работать с соседями
public class Vector2 {
    private final int x;
    private final int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
