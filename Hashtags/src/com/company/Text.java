package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Text {
    private final File file; //файл
    private final int amountWordsInText; //количество слов в тексте

    public Text(String filePath) {
        file = new File(filePath);

        amountWordsInText = initAmountWordsInText(); //считаем общее количество слов в тексте
    }

    //количество слов в  тексте
    public int getAmountWordsInText() {
        return amountWordsInText;
    }

    //читаем весь файл
    public List<String> readAllFile() {
        return readFile(false);
    }

    //читаем весь файл
    //добавляя переход на след. строку
    //при считывании очередной строки
    public List<String> readAllFileWithEscapeSymbol() {
        return readFile(true);
    }

    //создаем список строк из файла
    //пока файл не закончился - считываем строчку и добавляем в список
    //если нам нужно учитывать символы перехода на след строчку
    //то добавляем их после чтения очередной строчки
    private List<String> readFile(boolean addEscapeSymbol) {
        List<String> allStrings = new ArrayList<>(); //лист строк

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { //открываем поток
            String line;
            while ((line = reader.readLine()) != null) { //если очередная строчка есть
                if (addEscapeSymbol) {
                    line += "\n";
                }
                allStrings.add(line); //добавляем считанную строчку в лист
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return allStrings;
    }

    //инициализируем количество слов в  тексте
    private int initAmountWordsInText() {
        List<String> allStrings = readAllFile(); //считываем все слова из файла

        //считаем количество слов во всех строках текста
        int counter = 0;
        for (String allString : allStrings) {
            WordsList wordsList = new WordsList(allString);
            counter += wordsList.size();
        }

        return counter;
    }
}
