package com.company;

/**
 * Замечание:
 * Фабрики добавлены в программу для будущего расширения функциональности программы
 * Чтобы добавить другие языки нам не придется изменять существущий текущий код
 * мы добавим новые фабрики для нужного языка
 * и добавим новую настройку в languageSettings
 * таким образом мы не нарушаем принцип OCP
 */

public class Main {

    public static void main(String[] args) {
        //если все правильно работает то файлы input
        //и fromOutput должны совпадать
        //так как мы вначале шифруем input - получаем шифр
        //пересоздаем машину
        //из дешифровуем шифрованный input в fromOutput
        EntryPoint entryPoint = new EntryPoint();
        entryPoint.start(new String[]{"input.txt", "output.txt", "settings.txt"});
        entryPoint.start(new String[]{"output.txt", "fromOutput.txt", "settings.txt"});
    }
}