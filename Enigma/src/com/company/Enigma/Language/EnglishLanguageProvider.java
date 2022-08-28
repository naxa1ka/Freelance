package com.company.Enigma.Language;

//так как настройки языка используются несколькими классами
//в разных местах мы их сохранили в одном месте, чтобы эти данные не размазывались по всему коду, т.е. инкапсулировали
//а так как мы работает с интерфейсами, а не с конкретными классами
//то расширить поведение программы(добавить язык) будет достаточно просто

public class EnglishLanguageProvider implements LanguageProvider {
    @Override
    public int getFirstSymbolAsciiAlphabet() {
        return 'A';
    }

    @Override
    public int getLengthAlphabet() {
        return 26;
    }
}
