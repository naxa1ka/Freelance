package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

//список русских слов взят отсюда - https://github.com/danakt/russian-words
//через данный класс проверяется существование какого-либо слова
public class WordsHolder {
    //используем HashSet - т.к. слова у нас уникальны
    //и нам важна производительность
    //а из-за структуры хэш-таблицы
    //поиск будет проводиться за О(1) что хорошо
    private final Set<String> words = new HashSet<>();

    public WordsHolder(String fileName) {
        File file = new File(fileName);

        checkCorrectFile(file);
        initWords(file);
    }

    public boolean isExist(String string){
        //так как слова в нашем текстовом документе нижнего регистра
        //приводим все к данному виду
        //а далее проверяем наличие данный строки в нашем файле
        String preparedString = string.toLowerCase(Locale.ROOT);
        return words.contains(preparedString);
    }

    //проверки корректности файла
    private void checkCorrectFile(File file) {
        if (!file.exists() || file.isDirectory()) { //проверка на существование файла
            throw new IllegalArgumentException("Not correct file path!");
        }
    }

    //считываем и добавляем все строчки которые у нас существуют в файле
    private void initWords(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { //открываем поток
            String line;
            while ((line = reader.readLine()) != null) { //если очередная строчка есть
                words.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
