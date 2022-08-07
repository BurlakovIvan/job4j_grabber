package ru.job4j.ood.lsp;

public class Account {
        protected float money;

        public Account(float money) {
            this.money = money;
        }

        public void transfer() {
            if (money <= 0) {
                throw new IllegalArgumentException("Нет денег!");
            }
        }
}
