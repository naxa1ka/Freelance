package com.company.Grid;

import com.company.Grid.GridStatus.GridStatus;
import com.company.Player;
import com.company.WordsHolder;

import java.util.*;

//чтобы не мешать кашу в классе Grid
//логику работу сетки со словами выделим в отдельный класс
public class WordGridHandler {
    private final Grid grid; //сетка
    private final WordsHolder wordsHolder; //проверка слова в списке слов

    private final List<String> composedWords = new ArrayList<>(); //составленные слова

    public WordGridHandler(Grid grid, WordsHolder wordsHolder) {
        this.grid = grid;
        this.wordsHolder = wordsHolder;
    }

    public GridStatus tryAddLetter(int x, int y, char letter, Player player) {
        //подготавливаем букву
        char preparedLetter = Character.toLowerCase(letter);
        //ставим букву на сетку
        GridStatus gridStatus = grid.tryAddLetter(x, y, preparedLetter);
        //если все хорошо - продолжаем работать
        //иначе возвращаем ошибку
        if (gridStatus != GridStatus.CORRECT) {
            return gridStatus;
        }

        grid.setLetter(x, y, preparedLetter);

        //составляем слова по всем осям
        String [] composedWords =  initComposeWords(x, y);

        GridStatus [] gridStatuses = initGridStatuses(player, composedWords);

        for (GridStatus status : gridStatuses) {
            if (status == GridStatus.WORD_IS_COMPOSED){
                return GridStatus.WORD_IS_COMPOSED;
            }
        }

        return GridStatus.CORRECT;
    }

    private String[] initComposeWords(int x, int y) {
        String[] composedWords = new String[4];

        composedWords[0] = wordHorizontalToRightBuilder(x, y);
        composedWords[1] = wordHorizontalToLeftBuilder(x, y);
        composedWords[2] = wordVerticalUpBuilder(x, y);
        composedWords[3] = wordVerticalDownBuilder(x, y);

        return composedWords;
    }

    //смотрим что у нас с этим словом
    private GridStatus[] initGridStatuses(Player player, String[] composedWords) {
        GridStatus[] gridStatus = new GridStatus[4];

        for (int i = 0; i < gridStatus.length; i++) {
            gridStatus[i] = tryComposeWord(player, composedWords[i]);
        }

        return gridStatus;
    }


    //пробуем составить слвоо
    private GridStatus tryComposeWord(Player player, String word) {
        if (isWordExist(word)) {
            //если слово уже составлено очков не добавляем
            if (isWordCurrentlyComposed(word)) {
                return null;
            }

            System.out.println("составлено слово: " + word);
            composedWords.add(word);
            player.addScore(word.length());

            return GridStatus.WORD_IS_COMPOSED;
        }

        return null;
    }


    //формируем слово слева направо
    private String wordHorizontalToRightBuilder(int x, int y) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = y; i < grid.getSize(); i++) {
            if (buildLetterImpossible(stringBuilder, x, i)) break;
        }
        return stringBuilder.reverse().toString();
    }

    //формируем слово cправо налево
    private String wordHorizontalToLeftBuilder(int x, int y) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = y; i >= 0; i--) {
            if (buildLetterImpossible(stringBuilder, x, i)) break;
        }
        return stringBuilder.reverse().toString();
    }

    //формируем слово сверху вниз
    private String wordVerticalDownBuilder(int x, int y) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = x; i >= 0; i--) {
            if (buildLetterImpossible(stringBuilder, i, y)) break;
        }

        return stringBuilder.reverse().toString();
    }

    private String wordVerticalUpBuilder(int x, int y) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = x; i < grid.getSize(); i++) {
            if (buildLetterImpossible(stringBuilder, i, y)) break;
        }

        return stringBuilder.reverse().toString();
    }


    //пробуем установить букву если это возможно
    private boolean buildLetterImpossible(StringBuilder stringBuilder, int x, int y) {
        //если ячейка пуста заканчиваем формирование слова
        if (grid.isFieldFree(x, y)) {
            return true;
        }

        stringBuilder.append(grid.getLetter(x,y));
        return false;
    }

    private boolean isWordExist(String string) {
        if (string.length() == 1) return false;
        return wordsHolder.isExist(string);
    }

    private boolean isWordCurrentlyComposed(String string) {
        return composedWords.contains(string);
    }
}
