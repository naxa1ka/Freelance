package com.company;

import java.util.*;

//слова в тексте
//оболочка над листом слов для удобствы работы
public class WordsList {
    private List<String> words = new ArrayList<>();
    private int currentIndex;

    private static final String IGNORING_PATTERN = "[,.\\n\\s]";

    //делим строчку на слова
    public WordsList(String wordStrings, String divider) {
        String[] splitedWords = wordStrings.split(divider);

        words.addAll(Arrays.asList(splitedWords));
    }

    public WordsList(String wordStrings) {
        this(wordStrings, " ");
    }

    //делим список строчек на слова
    public WordsList(List<String> wordStrings, String divider) {
        List<String> allWords = new ArrayList<>();

        for (String wordString : wordStrings) {
            WordsList wordsList = new WordsList(wordString, divider);
            allWords.addAll(wordsList.getWords());
        }

        words = allWords;
    }

    public WordsList(List<String> wordStrings) {
        this(wordStrings, " ");
    }


    //общее количество слов в списке слов
    public int size() {
        return words.size();
    }

    //геттер слов
    public List<String> getWords() {
        return words;
    }

    //проверяем существует ли данное слово в списке наших слов
    //игнорируя / не игнорируя регистр
    //а также некоторые символы
    public boolean isExistWordInList(String word) {
        String checkableWord;
        String checkableWordFromList;

        for (String wordFromList : words) { //проходимся по всем словам из списка
            checkableWord = word; //слово которое мы проверяем
            checkableWordFromList = wordFromList; //слово из списка

            // игнорируем регистр приводим слова к одному и тому же регистру
            checkableWord = checkableWord.toUpperCase(Locale.ROOT);
            checkableWordFromList = checkableWordFromList.toUpperCase(Locale.ROOT);

            //если слова одинаковы при игнорировании символов - значит такое слово существует в списке
            if (isEqualsWithIgnoringChars(checkableWord, checkableWordFromList)) {
                return true;
            }
        }

        //иначе таких слов нет
        return false;
    }

    //равны ли два слова вместе с игнорированием символов
    //вначале мы формируем шаблон regex проходясь по всем игнорируемым символам
    //позже заменяем эти символы в словах на пробелы
    //и сравниваем их
    private boolean isEqualsWithIgnoringChars(String firstWord, String secondWord) {
        firstWord = firstWord.replaceAll(IGNORING_PATTERN, "");
        secondWord = secondWord.replaceAll(IGNORING_PATTERN, "");

        return firstWord.equals(secondWord);
    }

    //есть ли еще у нас слова в листе
    public boolean hasNext() {
        return currentIndex < words.size();
    }

    //выдаем следующее слово из листа
    public String next() {
        String word = words.get(currentIndex);
        currentIndex++;

        return word;
    }

}
