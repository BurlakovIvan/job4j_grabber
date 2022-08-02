package ru.job4j.cache;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.StringJoiner;

public class Emulator {

    private static  final String MENU = new StringJoiner(System.lineSeparator())
            .add("Введи 1, чтобы загрузить содержимое файла в кэш")
            .add("Введи 2, чтобы получить содержимое файла")
            .add("Введи любой символ, чтобы выйти")
            .toString();

    private static boolean validation(String path, String file) {
        boolean rsl = true;
        validationDir(path);
        Path filePath = Paths.get(path + (!path.endsWith("/") ? "/" : "") + file);
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

    private static void validationDir(String path) {
        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            throw new IllegalArgumentException("Указанной директории не существует!");
        }
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("Заданный путь не является директорией");
        }
    }

    private static String cacheFile(String dir, Scanner scanner, DirFileCache dirFileCache) {
        System.out.println("имя файла:");
        String fileName = scanner.nextLine();
        String result = null;
        if (validation(dir, fileName)) {
            result = dirFileCache.get(fileName);
        }
        return result;
    }

    private static void printText(String text) {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        result.add("Содержимое файла:");
        result.add(text);
        System.out.println(result);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Укажите кэшируемую директорию:");
        String dir = scanner.nextLine();
        DirFileCache dirFileCache = new DirFileCache(dir);
        validationDir(dir);
        String index;
        while (true) {
            System.out.println(MENU);
            index = scanner.nextLine();
            if ("1".equals(index)) {
                cacheFile(dir, scanner, dirFileCache);
            } else if ("2".equals(index)) {
                String text = cacheFile(dir, scanner, dirFileCache);
                if (text != null) {
                    printText(text);
                }
            } else {
                System.out.println("Остановка работы");
                break;
            }
        }
    }
}
