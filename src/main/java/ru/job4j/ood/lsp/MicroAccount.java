package ru.job4j.ood.lsp;

/*
1. Класс MicroAccount нарушает правило в методе transfer:
Предусловия (Preconditions) не могут быть усилены в подклассе
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
    }
}
