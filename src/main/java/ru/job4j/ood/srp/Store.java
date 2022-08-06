package ru.job4j.ood.srp;

public interface Store {
    /**
     * Поиск id заказа
     * @return id
     */
    int findIdOrder();

    /**
     * Сохранение заказа
     */
    void saveOrder();
}
