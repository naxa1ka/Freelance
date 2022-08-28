
package com.company.Enigma.Rotor;

public class RotorSettings {
    private final String name;
    private final int rotorPosition;
    private final int ringSettings;

    public RotorSettings(String[] strings) {
        name = strings[0];
        rotorPosition = Integer.parseInt(strings[1]);
        ringSettings = Integer.parseInt(strings[2]);
    }

    public String getName() {
        return name;
    }

    public int getRotorPosition() {
        return rotorPosition;
    }

    public int getRingSettings() {
        return ringSettings;
    }
}
