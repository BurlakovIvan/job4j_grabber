package ru.job4j.ood.ocp;

import java.util.List;

/*
Класс магазин не получится расширить так как метод buy всегда возвращает
определнный тип, можно заменить на дженерик, так же как и поле класса
 */

public class Shop {
    private List<Meat> goods;

    public Meat buy(int indexGoods) {
        return goods.get(indexGoods);
    }
}
