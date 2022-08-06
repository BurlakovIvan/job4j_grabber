package ru.job4j.ood.srp;

/*
Cинглтон не только выполняет свою основную функцию,
но ещё и проверяет, не существует ли уже созданных экземпляров.
 */
public class Singleton {
    private static Singleton instance = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
