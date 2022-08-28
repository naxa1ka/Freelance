package com.company.Enigma.Reflector;

import com.company.Enigma.Language.EnglishLanguageProvider;
import com.company.Enigma.Language.LanguageProvider;

public class EnglishReflectorFactory implements ReflectorFactory {
    private final LanguageProvider languageProvider;

    public EnglishReflectorFactory() {
        this.languageProvider = new EnglishLanguageProvider();
    }

    public Reflector Create(char reflectorType) {
        switch (reflectorType) {
            case 'B':
                return new Reflector(languageProvider, "YRUHQSLDPXNGOKMIEBFZCWVJAT");
            case 'C':
                return new Reflector(languageProvider, "FVPJIAOYEDRZXWGCTKUQSBNMHL");
            default:
                return new Reflector(languageProvider, "ZYXWVUTSRQPONMLKJIHGFEDCBA");
        }
    }
}

