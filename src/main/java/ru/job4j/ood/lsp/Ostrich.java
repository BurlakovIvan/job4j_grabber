package ru.job4j.ood.lsp;

/*
1. Класс Ostrich нарушает правило в методе move:
Постусловия (Postconditions) не могут быть ослаблены в подклассев
 */
public class Ostrich extends Animal {

    public Ostrich(boolean isFlying, int age) {
        super(isFlying, age);
    }

    @Override
    public void move() {
        fly();
    }

}
