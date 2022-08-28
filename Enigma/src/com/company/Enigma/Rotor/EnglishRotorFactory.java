package com.company.Enigma.Rotor;

import com.company.Enigma.Language.EnglishLanguageProvider;
import com.company.Enigma.Language.LanguageProvider;

public class EnglishRotorFactory implements RotorFactory {
    private final LanguageProvider languageProvider;

    public EnglishRotorFactory() {
        this.languageProvider = new EnglishLanguageProvider();
    }

    @Override
    public Rotor Create(String name, int rotorPosition, int ringSetting) {
        switch (name) {
            case "I":
                return new Rotor(languageProvider, "EKMFLGDQVZNTOWYHXUSPAIBRCJ", rotorPosition, 16, ringSetting);
            case "II":
                return new Rotor(languageProvider, "AJDKSIRUXBLHWTMCQGZNPYFVOE", rotorPosition, 4, ringSetting);
            case "III":
                return new Rotor(languageProvider, "BDFHJLCPRTXVZNYEIWGAKMUSQO", rotorPosition, 21, ringSetting);
            case "IV":
                return new Rotor(languageProvider, "ESOVPZJAYQUIRHXLNFTGKDCMWB", rotorPosition, 9, ringSetting);
            case "V":
                return new Rotor(languageProvider, "VZBRGITYUPSDNHLXAWMJQOFECK", rotorPosition, 25, ringSetting);
            case "VI":
                return new Rotor(languageProvider, "JPGVOUMFYQBENHZRDKASXLICTW", rotorPosition, 0, ringSetting) {
                    @Override
                    public boolean isAtNotch() {
                        return this.rotorPosition == 12 || this.rotorPosition == 25;
                    }
                };
            case "VII":
                return new Rotor(languageProvider, "NZJHGRCXMYSWBOUFAIVLPEKQDT", rotorPosition, 0, ringSetting) {
                    @Override
                    public boolean isAtNotch() {
                        return this.rotorPosition == 12 || this.rotorPosition == 25;
                    }
                };
            case "VIII":
                return new Rotor(languageProvider, "FKQHTLXOCBJSPDZRAMEWNIUYGV", rotorPosition, 0, ringSetting) {
                    @Override
                    public boolean isAtNotch() {
                        return this.rotorPosition == 12 || this.rotorPosition == 25;
                    }
                };
            default:
                return new Rotor(languageProvider, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", rotorPosition, 0, ringSetting);
        }
    }
}
