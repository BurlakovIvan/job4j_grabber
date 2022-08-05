package ru.job4j.ood.srp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/*
Данный класс помимо считывания данных из файла, также проверяет на валидность,
что является нарушением принципов SRP, это надо сделать по крайней мере хотя бы как отдельный метод,
или в другом классе
 */
public class LoadFile {

    public String load(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Не существует указанного файла!");
        }
        if (Files.isDirectory(path)) {
            throw new IllegalArgumentException("Не является файлом!");
        }
        String out = null;
        try  {
            out = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }
}
