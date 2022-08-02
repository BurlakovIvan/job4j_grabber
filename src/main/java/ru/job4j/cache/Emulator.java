package ru.job4j.cache;

import java.util.Scanner;

public class Emulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Укажите кэшируемую директорию:");
        DirFileCache dirFileCache = new DirFileCache(scanner.nextLine());
        String index;
        while (true) {
            System.out.println("Введи 1, чтобы загрузить содержимое файла в кэш");
            System.out.println("Введи 2, чтобы получить содержимое файла");
            System.out.println("Введи любой символ, чтобы выйти");
            index = scanner.nextLine();
            if ("1".equals(index)) {
                System.out.println("Введи имя файла, который необходимо добавить в кэш");
                String fileName = scanner.nextLine();
                dirFileCache.load(fileName);
            } else if ("2".equals(index)) {
                System.out.println("Введи имя файле, содержимое которого необходимо получить");
                String fileName = scanner.nextLine();
                System.out.println("Содержимое файла:");
                System.out.println(dirFileCache.get(fileName));
            } else {
                break;
            }
        }
    }
}
