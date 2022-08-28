package com.company;

import java.util.Random;

public class Detail {
    private final int weight;
    private final float cost;
    private final Material material;
    private final String name;

    public Detail(Material material, String name){
        Random random = new Random();
        this.weight = random.nextInt(105);
        this.cost = random.nextFloat();
        this.material = material;
        this.name = name;
    }

    public Detail(int weight, float cost, Material material, String name) {
        this.weight = weight;
        this.cost = cost;
        this.material = material;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public float getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Деталь{" +
                "вес=" + weight +
                ", стоимость=" + cost +
                ", материал=" + material +
                ", название='" + name + '\'' +
                '}';
    }
}

