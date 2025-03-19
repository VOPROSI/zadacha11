package org.example;

import org.example.db.DbModule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FactorialCalculator {

    public static long evenFactorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i += 2) {
            result *= i;
        }
        return result;
    }

    public static long oddFactorial(int n) {
        long result = 1;
        for (int i = 1; i <= n; i += 2) {
            result *= i;
        }
        return result;
    }

    public static int validateInput(Scanner scanner) {
        int num;
        while (true) {
            System.out.print("Введите положительное целое число: ");
            String input = scanner.nextLine().trim(); // Читаем строку и убираем пробелы

            try {
                num = Integer.parseInt(input); // Пробуем преобразовать в int
                if (num > 0) {
                    return num; // Если число положительное, возвращаем его
                } else {
                    System.out.println("Ошибка: Число должно быть больше 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите корректное целое число!");
            }
        }
    }

    public static void printFactById(int id, String tableName) {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (PreparedStatement stmt = DbModule.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("=========================================");
                System.out.printf("ID: %d\n", rs.getInt("id"));
                System.out.printf("Число: %d\n", rs.getInt("number"));

                long evenFactorial = rs.getLong("even_factorial");
                if (!rs.wasNull()) {
                    System.out.printf("Четный факториал: %d\n", evenFactorial);
                } else {
                    System.out.println("Четный факториал: отсутствует");
                }

                long oddFactorial = rs.getLong("odd_factorial");
                if (!rs.wasNull()) {
                    System.out.printf("Нечетный факториал: %d\n", oddFactorial);
                } else {
                    System.out.println("Нечетный факториал: отсутствует");
                }

                System.out.println("=========================================");
            } else {
                System.out.println("Запись с таким ID не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении данных: " + e.getMessage());
        }
    }
}
