package com.company.Enigma.Rotor;

import com.company.Enigma.Codable;
import com.company.Enigma.Language.LanguageProvider;

public class Rotor extends Codable {
    private final int[] backwardWiring;

    private final int notchPosition;
    private final int ringSetting;

    //для override в улучшенных версиях ротора
    protected int rotorPosition;

    public Rotor(LanguageProvider languageProvider, String encoding, int rotorPosition, int notchPosition, int ringSetting) {
        super(encoding, languageProvider);

        this.backwardWiring = inverseWiring(forwardWiring);
        this.rotorPosition = rotorPosition;
        this.notchPosition = notchPosition;
        this.ringSetting = ringSetting;
    }

    private int[] inverseWiring(int[] wiring) {
        int[] inverse = new int[wiring.length];

        for (int i = 0; i < wiring.length; i++) {
            int forward = wiring[i];
            inverse[forward] = i;
        }

        return inverse;
    }

    private int encipher(int symbol, int pos, int ring, int[] mapping) {
        int shift = pos - ring;
        int lengthAlphabet = languageProvider.getLengthAlphabet();

        return (mapping[(symbol + shift + lengthAlphabet) % lengthAlphabet] - shift + lengthAlphabet) % lengthAlphabet;
    }

    public int forward(int symbol) {
        return encipher(symbol, rotorPosition, ringSetting, forwardWiring);
    }

    public int backward(int symbol) {
        return encipher(symbol, rotorPosition, ringSetting, backwardWiring);
    }

    /**
     * Позиции метки оборота
     * см - https://en.wikipedia.org/wiki/Enigma_rotor_details#Rotor_wiring_tables
     */
    public boolean isAtNotch() {
        return notchPosition == rotorPosition;
    }

    public void turnover() {
        rotorPosition = (rotorPosition + 1) % languageProvider.getLengthAlphabet();
    }


}