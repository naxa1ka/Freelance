package com.company.Enigma.Reflector;

import com.company.Enigma.Codable;
import com.company.Enigma.Language.LanguageProvider;

//рефлектор по сути в данном случае выступает в роле некоторого шифра цезаря
//на вход мы получаем некоторую последовательность
//и позже она будет выступать как словарь, но мы ограничимся массивом, этого вполне хватит
//то есть первый символу A - будет соотвествовать первый символ из полученной строки "encoding"
public class Reflector  extends Codable {
    public Reflector(LanguageProvider languageProvider, String encoding) {
        super(encoding, languageProvider);

        int lengthAlphabet = languageProvider.getLengthAlphabet();
        if (encoding.length() != lengthAlphabet) {
            throw new IllegalArgumentException("Length encoding must be " + lengthAlphabet + "!");
        }
    }

    public int forward(int symbol) {
        return forwardWiring[symbol];
    }
}


