package com.company;

public class Main {
    public static void main(String[] args) {
        Company company = new Company(1000, 1000);
        company.AddDetail(new Detail(new Material("сталь"), "шайба"));
        company.AddDetail(new Detail(new Material("сталь1"), "шайба"));
        company.AddDetail(new Detail(new Material("сталь2"), "шайба"));
        company.AddDetail(new Detail(new Material("сталь3"), "шайба"));
        company.AddDetail(new Detail(new Material("сталь4"), "шайба"));
        company.AddDetail(new Detail(new Material("пластмасса1"), "ручка"));
        company.AddDetail(new Detail(new Material("пластмасса2"), "ручка"));
        company.AddDetail(new Detail(new Material("пластмасса3"), "ручка"));
        company.Print();
        System.out.println("----");
        company.DivideDetailsByName("шайба");
        System.out.println("----");
        company.DivideDetailsByName("ручка");
    }
}

