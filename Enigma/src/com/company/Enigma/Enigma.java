package com.company.Enigma;

import com.company.Enigma.Language.LanguageProvider;
import com.company.Enigma.Reflector.Reflector;
import com.company.Enigma.Reflector.ReflectorFactory;
import com.company.Enigma.Rotor.Rotor;
import com.company.Enigma.Rotor.RotorFactory;
import com.company.Enigma.Rotor.RotorSettings;

public class Enigma {
    private Rotor leftRotor;
    private Rotor middleRotor;
    private Rotor rightRotor;

    private Reflector reflector;

    private RotorFactory rotorFactory;
    private ReflectorFactory reflectorFactory;
    private LanguageProvider languageProvider;

    private void rotate() {
        if (middleRotor.isAtNotch()) {
            middleRotor.turnover();
            leftRotor.turnover();
        } else if (rightRotor.isAtNotch()) {
            middleRotor.turnover();
        }

        rightRotor.turnover();
    }

    private int encrypt(int symbol) {
        rotate();

        int c1 = rightRotor.forward(symbol);
        int c2 = middleRotor.forward(c1);
        int c3 = leftRotor.forward(c2);

        int c4 = reflector.forward(c3);

        int c5 = leftRotor.backward(c4);
        int c6 = middleRotor.backward(c5);
        int c7 = rightRotor.backward(c6);

        return c7;
    }

    public char encrypt(char symbol) {
        int firstSymbolAsciiAlphabet = languageProvider.getFirstSymbolAsciiAlphabet();
        return (char) (encrypt((int)symbol - firstSymbolAsciiAlphabet) + firstSymbolAsciiAlphabet);
    }

    public String encrypt(String string) {
        char[] chars = string.toCharArray();
        char[] encrypt = encrypt(chars);
        return new String(encrypt);
    }

    public char[] encrypt(char[] string) {
        char[] output = new char[string.length];

        for (int i = 0; i < string.length; i++) {
            output[i] = encrypt(string[i]);
        }

        return output;
    }

    public static Builder newBuilder() {
        return new Enigma().new Builder();
    }

    //так как у класса достаточно большая инициализация принято решение использовать Builder
    //для более лаконичного интерфейса использования класса
    public class Builder {

        private Builder() {
        }

        public Builder setLeftRotor(RotorSettings rotorSettings) {
            Enigma.this.leftRotor = setRotor(rotorSettings);

            return this;
        }

        public Builder setLeftRotor(String name, int rotorPosition, int ringSettings) {
            Enigma.this.leftRotor = setRotor(name, rotorPosition, ringSettings);

            return this;
        }

        public Builder setMiddleRotor(RotorSettings rotorSettings) {
            Enigma.this.middleRotor = setRotor(rotorSettings);

            return this;
        }

        public Builder setMiddleRotor(String name, int rotorPosition, int ringSettings) {
            Enigma.this.middleRotor = setRotor(name, rotorPosition, ringSettings);

            return this;
        }

        public Builder setRightRotor(RotorSettings rotorSettings) {
            Enigma.this.rightRotor = setRotor(rotorSettings);

            return this;
        }

        public Builder setRightRotor(String name, int rotorPosition, int ringSettings) {
            Enigma.this.rightRotor = setRotor(name, rotorPosition, ringSettings);

            return this;
        }

        private Rotor setRotor(RotorSettings rotorSettings) {
            return setRotor(rotorSettings.getName(), rotorSettings.getRotorPosition(), rotorSettings.getRingSettings());
        }

        private Rotor setRotor(String name, int rotorPosition, int ringSettings) {
            return rotorFactory.Create(name, rotorPosition, ringSettings);
        }

        public Builder setReflector(String encoding) {
            Enigma.this.reflector = new Reflector(languageProvider, encoding);

            return this;
        }


        public Builder setReflector(char reflectorType) {
            Enigma.this.reflector = reflectorFactory.Create(reflectorType);

            return this;
        }

        public Builder setRotorFactory(RotorFactory rotorFactory) {
            Enigma.this.rotorFactory = rotorFactory;

            return this;
        }

        public Builder setReflectorFactory(ReflectorFactory reflectorFactory) {
            Enigma.this.reflectorFactory = reflectorFactory;

            return this;
        }

        public Builder setLanguageProvider(LanguageProvider languageProvider) {
            Enigma.this.languageProvider = languageProvider;

            return this;
        }

        public Enigma build() {
            return Enigma.this;
        }


    }

}