package ru.job4j.ood.srp;

import java.io.*;
import java.util.Objects;

public class Animal {
    private String name;
    private int age;
    private boolean isFlying;

    public Animal(String name, int age, boolean isFlying) {
        this.name = name;
        this.age = age;
        this.isFlying = isFlying;
    }

    public void load() {
        try (FileInputStream in = new FileInputStream("name.txt")) {
           var x =  in.read();
           /*
           какая-то логика загрузки
            */
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal animal = (Animal) o;
        return age == animal.age && isFlying == animal.isFlying && Objects.equals(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isFlying);
    }
}
