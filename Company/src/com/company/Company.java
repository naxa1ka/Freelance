package com.company;

import java.util.ArrayList;

public class Company {
    private final int maxWeight;
    private final float maxCost;
    private final ArrayList<Detail> details;

    public Company(int maxWeight, float maxCost, ArrayList<Detail> details) {
        this(maxWeight, maxCost);
        for (Detail detail : details) {
            AddDetail(detail);
        }
    }

    public Company(int maxWeight, float maxCost) {
        this.maxWeight = maxWeight;
        this.maxCost = maxCost;
        details = new ArrayList<>();
    }

    public void Print() {
        Print(this.details);
    }

    public void DivideDetailsByName(String name){
        ArrayList<Detail> dividedDetails = new ArrayList<>();

        for (Detail detail : details) {
            if (detail.getName().equals(name)){
                dividedDetails.add(detail);
            }
        }
        dividedDetails.sort(new CostDetailComparator());
        Print(dividedDetails);
    }

    public void AddDetail(Detail detail) {
        if (!IsDetailCorrectByWeight(detail)) {
            throw new IllegalArgumentException("The weight of detail " + detail.getName() + " is greater than the maximum!");
        }
        if (!IsDetailCorrectByCost(detail)) {
            throw new IllegalArgumentException("The cost of detail " + detail.getName() + " is greater than the maximum!");
        }
        this.details.add(detail);
    }

    private void Print(ArrayList<Detail> details){
        for (Detail detail : details) {
            System.out.println(detail);
        }
    }

    private boolean IsDetailCorrectByWeight(Detail detail) {
        return detail.getWeight() <= maxWeight;
    }

    private boolean IsDetailCorrectByCost(Detail detail) {
        return detail.getCost() <= maxCost;
    }
}
