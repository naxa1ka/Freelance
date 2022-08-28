package com.company;

import com.company.Enigma.Enigma;
import com.company.Enigma.Language.EnglishLanguageProvider;
import com.company.Enigma.Reflector.EnglishReflectorFactory;
import com.company.Enigma.Rotor.EnglishRotorFactory;
import com.company.Enigma.Rotor.RotorSettings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EntryPoint {
    public void start(String[] args){
        if (args.length != 3) { //проверка длины массивы аргументов
            throw new IllegalArgumentException("args length must be 3!");
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String settingsFile = args[2];

        String[] settings = readSettingsFile(settingsFile);
        Enigma enigma = parseSettings(settings);
        write(enigma, inputFile, outputFile);
    }

    private Enigma parseSettings(String[] settings) {
        Enigma.Builder builder = Enigma.newBuilder();

        String languageVersion = settings[0].toUpperCase();
        if (languageVersion.equals("EN")) {
            builder.setLanguageProvider(new EnglishLanguageProvider());
            builder.setReflectorFactory(new EnglishReflectorFactory());
            builder.setRotorFactory(new EnglishRotorFactory());
        } else {
            throw new IllegalArgumentException("Unexecpted language!");
        }

        for (int i = 1; i <= 3; i++) {
            checkCorrectRotorSettings(settings[i].split(" "));
        }

        builder.setLeftRotor(new RotorSettings(settings[1].split(" ")))
                .setMiddleRotor(new RotorSettings(settings[2].split(" ")))
                .setRightRotor(new RotorSettings(settings[3].split(" ")));

        String settingReflector = settings[4];
        if (settingReflector.length() == 1) {
            builder.setReflector(settingReflector.charAt(0));
        } else {
            builder.setReflector(settingReflector);
        }

        return builder.build();
    }

    private void checkCorrectRotorSettings(String[] strings) {
        if (strings.length != 3) {
            throw new IllegalArgumentException("Length arguments rotor settings must be 3!");
        }

        for (int i = 0; i < 3; i++) {
            if (strings[i].isEmpty()) {
                throw new IllegalArgumentException("Argument must be not empty!");
            }
        }

        if (!isNumber(strings[1]) || !isNumber(strings[2])) {
            throw new IllegalArgumentException("Rotor position and ringSettings must be a digit!");
        }
    }

    private boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }

    private String[] readSettingsFile(String settingsFile) {
        String[] parseResults = new String[6];

        try {
            FileReader fr = new FileReader(settingsFile);

            BufferedReader reader = new BufferedReader(fr);

            for (int i = 0; i < 5; i++) {
                String line = reader.readLine();

                if (line == null || line.isEmpty()) {
                    throw new IllegalArgumentException("Setting string must be not empty!");
                }

                parseResults[i] = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parseResults;
    }

    private void write(Enigma enigma, String inputFile, String outputFile) {
        try {
            FileReader fr = new FileReader(inputFile);
            FileWriter writer = new FileWriter(outputFile);

            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            while (line != null) {
                line = line.toUpperCase();
                String encrypt = enigma.encrypt(line);
                writer.write(encrypt + "\n");
                line = reader.readLine();
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
