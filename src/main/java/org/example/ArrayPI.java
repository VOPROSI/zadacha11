package org.example;

import org.example.db.InsertTable;
import java.util.Scanner;

public class ArrayPI {
    protected int[] array;

    public ArrayPI() {
        Scanner input = new Scanner(System.in);

        int size = 0;
        boolean exit = false;
        while (!exit) {
            System.out.print("Введите количество чисел для ввода: ");
            try{
                size = Integer.parseInt(input.nextLine());
                if (size <= 0) {
                    System.out.println("Вы ввели отрицательное число или 0, попробуйте снова!");
                    exit = false;
                }
                else exit = true;
            }
            catch (NumberFormatException e) {
                System.out.println("Вы ввели некорректное число!");
            }
        }

        array = new int[size];
        System.out.println("Введите " + size + " целых чисел:");

        for (int i = 0; i < size; i++) {
            while (true) {
                if (input.hasNextInt()) {
                    array[i] = input.nextInt();
                    break;
                } else {
                    System.out.println("Ошибка: Введите целое число.");
                    input.next();
                }
            }
        }
    }

    public void printArray() {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public void saveToDatabase(String tableName) {
        InsertTable insertTable = new InsertTable();
        insertTable.insertNumbers(tableName, array);
    }
}
