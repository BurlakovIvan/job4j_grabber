package ru.job4j.ood.lsp;

/*
1. Класс Ostrich нарушает правило
Предусловия (Preconditions) не могут быть усилены в подклассе
в методе transfer
 */
public class MicroAccount extends Account {

    public MicroAccount(float money) {
        super(money);
    }

    @Override
    public void transfer() {
        if (money <= 0) {
            throw new IllegalArgumentException("Нет денег!");
        }
        if (money >= 10000) {
            throw new IllegalArgumentException("Слишком много денег!");
        }
        if (!permission) {
            throw new IllegalArgumentException("Не доступно!");
        }
    }
}
