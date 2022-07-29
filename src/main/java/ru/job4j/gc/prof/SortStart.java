package ru.job4j.gc.prof;

import java.util.Random;
import java.util.Scanner;

public class SortStart {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RandomArray randomArray = new RandomArray(new Random());
        int in;
        do {
            System.out.println("Введи число");
            in = scanner.nextInt();
            Sort insertSort;
            if (in == 1) {
                randomArray = new RandomArray(new Random());
                randomArray.insert(250000);
            }
            if (in == 2) {
                System.out.println("2.Сортировка пузырьком");
                insertSort = new BubbleSort();
                insertSort.sort(randomArray);
            }
            if (in == 3) {
                System.out.println("3. Сортировка вставками");
                insertSort = new InsertSort();
                insertSort.sort(randomArray);
            }
            if (in == 4) {
                System.out.println("4. Сортировка слиянием");
                insertSort = new MergeSort();
                insertSort.sort(randomArray);
            }
        } while (in != 5);
    }
}
