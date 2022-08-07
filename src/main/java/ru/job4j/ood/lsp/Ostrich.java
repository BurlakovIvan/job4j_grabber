package ru.job4j.ood.lsp;

/*
1. Класс Ostrich нарушает правило
Постусловия (Postconditions) не могут быть ослаблены в подклассе
в методе move
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
