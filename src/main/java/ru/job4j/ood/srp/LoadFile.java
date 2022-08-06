package ru.job4j.ood.srp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/*
Данный класс помимо считывания данных из файла, фозвращает длину файла
 */
public class LoadFile {

    public long lengthFile(File file) {
        return file.length();
    }

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
