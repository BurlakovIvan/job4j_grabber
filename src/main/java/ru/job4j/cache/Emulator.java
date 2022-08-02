package ru.job4j.cache;

import java.util.Scanner;

public class Emulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Укажите кэшируемую директорию:");
        DirFileCache dirFileCache = new DirFileCache(scanner.nextLine());
        int index;
        do {
            System.out.println("Введи 1, чтобы загрузить содержимое файла в кэш");
            System.out.println("Введи 2, чтобы получить содержимое файла");
            System.out.println("Введи любое число, чтобы выйти");
            index = scanner.nextInt();
            if (index == 1) {
                System.out.println("Введи имя файла, который необходимо добавить в кэш");
                String fileName = scanner.next();
                dirFileCache.load(fileName);
            }
            if (index == 2) {
                System.out.println("Введи имя файле, содержимое которого необходимо получить");
                String fileName = scanner.next();
                System.out.println("Содержимое файла:");
                System.out.println(dirFileCache.get(fileName));
            }
        } while (index > 0 && index < 3);
    }
}
