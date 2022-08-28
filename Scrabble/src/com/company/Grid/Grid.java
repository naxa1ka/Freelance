package com.company.Grid;

import com.company.Grid.GridStatus.GridStatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//игровая сетка
public class Grid {
    private final int size; //размер поля
    private final char[][] grid; //сетка с символами

    //используем хэш-таблицу по той же причине что и в WordsHolder
    private final Character[] alphabetChars = new Character[]{'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
    private Set<Character> alphabet;
    private final char FREE_FIELD = '.';

    public Grid(int size) {
        this.size = size;
        grid = new char[size][size];

        initAlphabet();
        initGrid();
    }

    //инициализация хэш таблицы алфавита
    private void initAlphabet() {
        alphabet = new HashSet<>(33);
        alphabet.addAll(Arrays.asList(alphabetChars));
    }

    //инициализация поля
    //символ - является меткой о том,
    //что данная клетка свободна
    private void initGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = FREE_FIELD;
            }
        }
    }

    //добавление буквы на сетку
    public GridStatus tryAddLetter(int x, int y, char letter) {
        //проверяем индекс на выход из массива
        if (!isCorrectIndex(x, y)) {
            return GridStatus.INDEX_NOT_CORRECT;
        }

        //проверяем является ли символ который
        //хотят поставить на поле частью алфавита
        if (!alphabet.contains(letter)) {
            return GridStatus.LETTER_NOT_IN_ALPHABET;
        }

        //проверяем стоит ли какой-то другой символ на поле
        if (isFieldBusy(x, y)) {
            return GridStatus.FIELD_IS_BUSY;
        }

        //если сетка непустая - то есть не первый ход
        //нужно проверить соседей
        if (!isGridEmpty() && !isHaveNeighbours(x,y)) {
            return GridStatus.NO_NEIGHBORS;
        }

        return GridStatus.CORRECT;
    }

    public void resetLetter(int x, int y){
        grid[x][y] = FREE_FIELD;
    }

    //устанавливаем символ
    public void setLetter(int x, int y, char letter){
        grid[x][y] = letter;
    }

    //размер сетки
    public int getSize(){
        return size;
    }

    //выдаем символы другим классам таким образом
    //чтобы не нарушать инкапсуляцию
    public char getLetter(int x, int y){
        return grid[x][y];
    }

    //проверка на пустоту сетки
    //используется для второго и следующих ходов
    //слова должны "соприкасаться" друг с другом
    private boolean isGridEmpty() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isFieldBusy(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    //проверяем наличие соседей
    private boolean isHaveNeighbours(int x, int y) {
        Vector2[] neighbours = new Vector2[]{
                new Vector2(x - 1, y),
                new Vector2(x + 1, y),
                new Vector2(x, y - 1),
                new Vector2(x, y + 1)
        };

        //смотрим всех возможных соседей клетки
        for (Vector2 neighbour : neighbours) {
            //если индекс некорректный - переходим к след соседу
            if (!isCorrectIndex(neighbour.getX(), neighbour.getY())) {
                continue;
            }
            //если поле занято каким-то символом - значит сосед есть
            if(isFieldBusy(neighbour.getX(), neighbour.getY())){
                return true;
            }
        }

        return false;
    }

    //проверка на пустоту поля
    public boolean isFieldBusy(int x, int y) {
        return grid[x][y] != FREE_FIELD;
    }

    //для читабельности
    public boolean isFieldFree(int x, int y){
        return grid[x][y] == FREE_FIELD;
    }

    //проверяем индекс на корректность чтобы не выйти за границу массива
    private boolean isCorrectIndex(int x, int y) {
        if (x >= size || y >= size) return false;
        if (x < 0 || y < 0) return false;

        return true;
    }
}

