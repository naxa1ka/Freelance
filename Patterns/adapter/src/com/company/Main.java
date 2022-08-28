package com.company;

import java.util.Random;

public class Main {
    public interface Duck {
        public void quack();
        public void fly();
    }

    public static class MallardDuck implements Duck {
        public void quack() {
            System.out.println("Quack");
        }

        public void fly() {
            System.out.println("I'm flying");
        }
    }

    public interface Turkey {
        public void gobble();
        public void fly();
    }
    public static class WildTurkey implements Turkey {
        public void gobble() {
            System.out.println("Gobble gobble");
        }

        public void fly() {
            System.out.println("I'm flying a short distance");
        }
    }


    public static class TurkeyAdapter implements Duck {
        Turkey turkey;

        public TurkeyAdapter(Turkey turkey) {
            this.turkey = turkey;
        }

        public void quack() {
            turkey.gobble();
        }

        public void fly() {
            for(int i=0; i < 5; i++) {
                turkey.fly();
            }
        }
    }

    public static void main(String[] args) {
        MallardDuck duck = new MallardDuck();

        WildTurkey turkey = new WildTurkey();
        Duck turkeyAdapter = new TurkeyAdapter(turkey);

        System.out.println("The Turkey says...");
        turkey.gobble();
        turkey.fly();

        System.out.println("\nThe Duck says...");
        testDuck(duck);

        System.out.println("\nThe TurkeyAdapter says...");
        testDuck(turkeyAdapter);
    }

    static void testDuck(Duck duck) {
        duck.quack();
        duck.fly();
    }
}
