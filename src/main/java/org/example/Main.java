package org.example;

import org.example.db.ExportToExcel;
import org.example.db.TableManager;
import java.util.Scanner;

public class Main {
    public static String tableName;
    private static boolean tableChosen = false;

    public static void main(String[] args) {

        TableManager tableManager = new TableManager();
        ExportToExcel exportToExcel = new ExportToExcel();

        String choice;
        boolean exit = false;

        try (Scanner input = new Scanner(System.in)) {

            do {
                System.out.println("Возможные действия:");
                System.out.println("1. Вывести все таблицы");
                System.out.println("2. Создать или выбрать таблицу");
                System.out.println("3. Ввести массив и сохранить");
                System.out.println("4. Отсортировать массив и сохранить");
                System.out.println("5. Экспортировать в Excel");
                System.out.println("6. Выйти");

                System.out.print("Выберите действие: ");
                choice = input.nextLine();
                switch (choice) {
                    case "1" -> tableManager.getTables();
                    case "2" -> {
                        try {
                            tableName = tableManager.createTable();
                            if (tableName == null) tableChosen = false;
                            else tableChosen = true;
                        } catch (Exception e) {
                            System.out.println("Ошибка при выборе таблицы " + e.getMessage());
                        }
                    }
                    case "3" -> {
                        if (tableChosen) {
                            ArrayPI arrayPI = new ArrayPI();
                            arrayPI.saveToDatabase(tableName);
                            arrayPI.printArray();
                        } else {
                            System.out.println("Таблица не выбрана, выберите сначала");
                        }
                    }
                    case "4" -> {
                        if (tableChosen) {
                            Sort sortArray = new Sort();
                            System.out.println("Возможные действия:");
                            System.out.println("1. Сортировка по возрастанию");
                            System.out.println("2. Сортировка по убыванию");
                            System.out.println("3. Сортировка по возрастанию и убыванию");
                            while (true) {
                                System.out.print("Выберите действие: ");
                                try {
                                    String choice1 = input.nextLine();
                                    if (choice1.equals("1")) {
                                        sortArray.bubbleSortAscending();
                                        sortArray.saveSortedToDatabase(tableName);
                                        sortArray.printArray();
                                        break;
                                    } else if (choice1.equals("2")) {
                                        sortArray.bubbleSortDescending();
                                        sortArray.saveSortedToDatabase(tableName);
                                        sortArray.printArray();
                                        break;
                                    } else if (choice1.equals("3")) {
                                        sortArray.bubbleSortAscending();
                                        sortArray.saveSortedToDatabase(tableName);
                                        sortArray.printArray();
                                        sortArray.bubbleSortDescending();
                                        sortArray.saveSortedToDatabase(tableName);
                                        sortArray.printArray();
                                        break;
                                    } else {
                                        System.out.println("Ошибка выбора!");
                                        continue;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            System.out.println("Таблица не выбрана, выберите сначала");
                        }
                    }
                    case "5" -> {
                        if (tableChosen) {
                            exportToExcel.exportData(tableName);
                        } else {
                            System.out.println("Таблица не выбрана, выберите сначала");
                        }
                    }
                    case "6" -> exit = true;
                    default -> System.out.println("Ошибка ввода, повторите");
                }
            } while (!exit);
            System.out.println("Завершение программы...");
        }
    }
}
