package ru.job4j.cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.StringJoiner;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        validationDir(cachingDir);
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        if (validation(cachingDir, key)) {
            StringJoiner out = new StringJoiner(System.lineSeparator());
            try (BufferedReader read = new BufferedReader(new FileReader(this.cachingDir + "\\" + key))) {
                read.lines().forEach(out::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return out.toString();
        }
        return "";
    }

    private boolean validation(String path, String file) {
        boolean rsl = true;
        validationDir(path);
        Path filePath = Paths.get(path + "\\" + file);
        if (!Files.exists(filePath)) {
            System.out.println("Не существует указанного файла!");
            rsl = false;
        }
        if (Files.isDirectory(filePath)) {
            System.out.println("Не является файлом!");
            rsl = false;
        }
        return rsl;
    }

    private void validationDir(String path) {
        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            throw new IllegalArgumentException("Указанной директории не существует!");
        }
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("Заданный путь не является директорией");
        }
    }
}