package com.company.Grid;

//отделим логику и отображения
public class GridView {
    private final Grid grid;

    public GridView(Grid grid) {
        this.grid = grid;
    }

    public void draw() {
        drawHeader();

        for (int i = 0; i < grid.getSize(); i++) {
            System.out.print(i + " ");
            for (int j = 0; j < grid.getSize(); j++) {
                char symbol = grid.getLetter(i, j);
                boolean isEmpty = grid.isFieldFree(i, j);

                String output;

                if (isEmpty) { //если ячейка пустая выводим пустоту
                    output = "." + " ";
                } else {
                    output = symbol + " ";
                }

                System.out.print(output);
            }
            //переходим на след строчку
            System.out.println();
        }
    }

    private void drawHeader() {
        StringBuilder stringBuilder = new StringBuilder("/ ");
        for (int i = 0; i < grid.getSize(); i++) {
            stringBuilder.append(i).append(" ");
        }
        System.out.println(stringBuilder);
    }
}
