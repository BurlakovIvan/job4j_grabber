package ru.job4j.ood.srp;

import java.nio.file.Path;

public class OrderStore implements Store {
    @Override
    public int findIdOrder() {
        return 0;
    }

    @Override
    public void saveOrder() {

    }

    public String loadFileMenu(Path menu) {
        LoadFile loadFile = new LoadFile();
        return loadFile.load(menu);
    }
}
