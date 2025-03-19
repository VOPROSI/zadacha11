package org.example;

import org.example.db.ExportToExcel;
import org.example.db.InsertTable;
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
        boolean exit_prog = false;
        int mode;

        try (Scanner input = new Scanner(System.in)) {
            do {
                System.out.println("Выбор режима работы:");
                System.out.println("1. Рассчет треугольника");
                System.out.println("2. Расчет факториала");

                choice = input.nextLine();

                if (choice.equals("1")) {
                    System.out.println("Вы выбрали расчет треугольника");
                    mode = 1;
                    exit_prog = true;
                } else if (choice.equals("2")) {
                    System.out.println("Вы выбрали расчет факториала");
                    mode = 2;
                    exit_prog = true;
                } else {
                    System.out.println("Ошибка! Введите 1 или 2");
                }

            } while (!exit_prog);

            do {
                System.out.println("Возможные действия:");
                System.out.println("1. Вывести все таблицы");
                System.out.println("2. Создать или выбрать таблицу");
                System.out.println("3. Ввести стороны треугольника и сохранить");
                System.out.println("4. Вывод результатов вычислений по id");
                System.out.println("5. Экспортировать в Excel");
                System.out.println("6. Вычислить четный факториал числа");
                System.out.println("7. Вычислить нечетный факториал числа");
                System.out.println("8. Создать или выбрать таблицу для факториалов");
                System.out.println("9. Выйти");

                System.out.print("Выберите действие: ");
                choice = input.nextLine();
                switch (choice) {
                    case "1" -> tableManager.getTables();
                    case "2" -> {
                        try {
                            tableName = tableManager.createTableTreangle();
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
                            arrayPI.printData();
                        } else {
                            System.out.println("Таблица не выбрана, выберите сначала");
                        }
                    }
                    case "4" -> {
                        if (tableChosen) {
                            System.out.print("Введите id записи: ");
                            try {
                                int id = Integer.parseInt(input.nextLine());
                                Formuls.printTriangleById(id, tableName);
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка: id должен быть числом.");
                            }
                        } else {
                            System.out.println("Таблица не выбрана, выберите сначала.");
                        }
                    }
                    case "5" -> {
                        if (tableChosen) {
                            exportToExcel.exportData(tableName);
                        } else {
                            System.out.println("Таблица не выбрана, выберите сначала");
                        }
                    }
                    case "6" -> {
                        int number = FactorialCalculator.validateInput(input);
                        long evenFactorial = FactorialCalculator.evenFactorial(number);

                        // Сохранение в базу данных
                        InsertTable insertTable = new InsertTable();
                        insertTable.insertFactorial(tableName, number, evenFactorial, 0); // Сохраняем только четный факториал

                        System.out.println("Четный факториал " + number + " = " + evenFactorial);
                    }

                    case "7" -> {
                        int number = FactorialCalculator.validateInput(input);
                        long oddFactorial = FactorialCalculator.oddFactorial(number);

                        // Сохранение в базу данных
                        InsertTable insertTable = new InsertTable();
                        insertTable.insertFactorial(tableName, number, 0, oddFactorial); // Сохраняем только нечетный факториал

                        System.out.println("Нечетный факториал " + number + " = " + oddFactorial);
                    }

                    case "8" -> {
                        try {
                            tableName = tableManager.createFactorialTable();
                            tableChosen = tableName != null;
                        } catch (Exception e) {
                            System.out.println("Ошибка при выборе таблицы: " + e.getMessage());
                        }
                    }

                    case "9" -> exit = true;
                    default -> System.out.println("Ошибка ввода, повторите");
                }
            } while (!exit);
            System.out.println("Завершение программы...");
        }
    }
}
