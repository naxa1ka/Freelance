package com.company;

import java.util.Comparator;

public class CostDetailComparator implements Comparator<Detail> {
    @Override
    public int compare(Detail o1, Detail o2) {
        return Float.compare(o1.getCost(), o2.getCost());
    }
}
