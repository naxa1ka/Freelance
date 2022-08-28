package com.company;

public class Main {

    interface Weapon{
        public void attack();
    }

    static class Axe implements Weapon{
        @Override
        public void attack() {
            System.out.println("Топор");
        }
    }

    class Sword implements Weapon{
        @Override
        public void attack() {
            System.out.println("Меч");
        }
    }

    static class Knife implements Weapon{
        @Override
        public void attack() {
            System.out.println("Нож");
        }
    }

    public abstract class Character{
        Weapon weapon;

        public void attack(){
            weapon.attack();
        }
        public void setWeapon(Weapon weapon){
            this.weapon = weapon;
        }
    }

    public class Queen extends Character{
        public Queen() {
            weapon = new Knife();
        }
    }

    public class Knight extends Character{
        public Knight(){
            weapon = new Axe();
        }
    }

    public  void main(String[] args) {
        Knight knight = new Knight();
        knight.attack();
        knight.setWeapon(new Knife());
        knight.attack();
    }
}
