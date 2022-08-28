package com.company;

import java.io.*;

public class Main {

    public enum CoffeeType {
        ESPRESSO,
        AMERICANO,
        CAFFE_LATTE,
        CAPPUCCINO
    }

    public abstract class Coffee {
        public void makeCoffee(){
            // делаем кофе
        }
        public void pourIntoCup(){
            // наливаем в чашку
        }
    }

    public class ItalianStyleAmericano extends Coffee {}
    public class ItalianStyleCappuccino extends Coffee {}
    public class ItalianStyleCaffeLatte extends Coffee {}
    public class ItalianStyleEspresso extends Coffee {}

    public class AmericanStyleAmericano extends Coffee {}
    public class AmericanStyleCappuccino extends Coffee {}
    public class AmericanStyleCaffeLatte extends Coffee {}
    public class AmericanStyleEspresso extends Coffee {}

    public abstract class CoffeeShop {

        public Coffee orderCoffee(CoffeeType type) {
            Coffee coffee = createCoffee(type);

            coffee.makeCoffee();
            coffee.pourIntoCup();

            System.out.println("Вот ваш кофе! Спасибо, приходите еще!");
            return coffee;
        }

        protected abstract Coffee createCoffee(CoffeeType type);
    }

    public class ItalianCoffeeShop extends CoffeeShop {

        @Override
        public Coffee createCoffee (CoffeeType type) {
            Coffee coffee = null;
            switch (type) {
                case AMERICANO:
                    coffee = new ItalianStyleAmericano();
                    break;
                case ESPRESSO:
                    coffee = new ItalianStyleEspresso();
                    break;
                case CAPPUCCINO:
                    coffee = new ItalianStyleCappuccino();
                    break;
                case CAFFE_LATTE:
                    coffee = new ItalianStyleCaffeLatte();
                    break;
            }
            return coffee;
        }
    }

    public class AmericanCoffeeShop extends CoffeeShop {
        @Override
        public Coffee createCoffee(CoffeeType type) {
            Coffee coffee = null;

            switch (type) {
                case AMERICANO:
                    coffee = new AmericanStyleAmericano();
                    break;
                case ESPRESSO:
                    coffee = new AmericanStyleEspresso();
                    break;
                case CAPPUCCINO:
                    coffee = new AmericanStyleCappuccino();
                    break;
                case CAFFE_LATTE:
                    coffee = new AmericanStyleCaffeLatte();
                    break;
            }

            return coffee;
        }
    }

    public void main(String[] args) {
        CoffeeShop italianCoffeeShop = new ItalianCoffeeShop();
        italianCoffeeShop.orderCoffee(CoffeeType.CAFFE_LATTE);

        CoffeeShop americanCoffeeShop = new AmericanCoffeeShop();
        americanCoffeeShop.orderCoffee(CoffeeType.CAFFE_LATTE);
    }

}
