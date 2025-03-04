package org.example;

import org.example.db.InsertTable;

public final class Sort extends ArrayPI {

    public Sort() {
        super();
    }

    public void bubbleSortAscending() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(j, j + 1);
                }
            }
        }
        System.out.println("Массив отсортирован по возрастанию.");
    }

    public void bubbleSortDescending() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] < array[j + 1]) {
                    swap(j, j + 1);
                }
            }
        }
        System.out.println("Массив отсортирован по убыванию.");
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public void saveSortedToDatabase(String tableName) {
        InsertTable insertTable = new InsertTable();
        insertTable.insertNumbers(tableName, array);
    }
}
