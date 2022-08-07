package ru.job4j.ood.lsp;

public class Animal {
    protected boolean isFlying;
    protected int age;

    public Animal(boolean isFlying, int age) {
        this.isFlying = isFlying;
        this.age = age;
    }

    public void move() {
        if (isFlying) {
            fly();
        } else {
            walk();
        }
    }

    protected void fly() {

    }

    protected void walk() {

    }
}
