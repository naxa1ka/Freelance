package com.company.Enigma.Rotor;

public interface RotorFactory {
    Rotor Create(String name, int rotorPosition, int ringSetting);
}
