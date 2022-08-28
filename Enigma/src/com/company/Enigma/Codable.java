package com.company.Enigma;

import com.company.Enigma.Language.LanguageProvider;

public abstract class Codable {
    protected final LanguageProvider languageProvider;
    protected final int[] forwardWiring;

    public Codable(String encoding, LanguageProvider languageProvider) {
        this.languageProvider = languageProvider;
        forwardWiring = decodeWiring(encoding);
    }

    private int[] decodeWiring(String encoding) {
        char[] charWiring = encoding.toCharArray();
        int[] wiring = new int[charWiring.length];

        for (int i = 0; i < charWiring.length; i++) {
            wiring[i] = charWiring[i] - languageProvider.getFirstSymbolAsciiAlphabet();
        }

        return wiring;
    }
}
