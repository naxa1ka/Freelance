package com.company;

public class Material {
    private final String name;

    public Material(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "'" + name + "'";
    }
}
