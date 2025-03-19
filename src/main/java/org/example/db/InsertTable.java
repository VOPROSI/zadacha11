package org.example.db;

import java.sql.*;

public class InsertTable {
    private final Connection conn;

    public InsertTable() {
        this.conn = DbModule.getConnection();
    }

    public void insertTriangle(String tableName, double side1, double side2, double side3, double perimeter, double square, boolean isRectangular) {
        final String query = "INSERT INTO " + tableName + " (side1, side2, side3, perimeter, square, is_rectangular) VALUES (?, ?, ?, ?, ?, ?);";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, side1);
            stmt.setDouble(2, side2);
            stmt.setDouble(3, side3);
            stmt.setDouble(4, perimeter);
            stmt.setDouble(5, square);
            stmt.setBoolean(6, isRectangular);

            stmt.executeUpdate();

            System.out.println("Данные о треугольнике успешно сохранены в таблицу '" + tableName + "'.");

        } catch (SQLException e) {
            System.out.println("Ошибка вставки данных в БД: " + e.getMessage());
        }
    }

    public void insertFactorial(String tableName, int number, long evenFactorial, long oddFactorial) {
        final String query = "INSERT INTO " + tableName + " (number, even_factorial, odd_factorial) VALUES (?, ?, ?);";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, number);
            stmt.setLong(2, evenFactorial);
            stmt.setLong(3, oddFactorial);
            stmt.executeUpdate();

            System.out.println("Факториалы для числа " + number + " успешно сохранены в таблицу '" + tableName + "'.");

        } catch (SQLException e) {
            System.out.println("Ошибка вставки данных в БД: " + e.getMessage());
        }
    }

}

