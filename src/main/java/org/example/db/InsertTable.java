package org.example.db;

import java.sql.*;

public class InsertTable {
    private final Connection conn;

    public InsertTable() {
        this.conn = DbModule.getConnection();
    }

    public void insertNumbers(String tableName, int[] numbers) {
        final String query = "INSERT INTO " + tableName + " (values_array) VALUES (?);";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            Integer[] integerArray = new Integer[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                integerArray[i] = numbers[i];
            }

            Array sqlArray = conn.createArrayOf("INTEGER", integerArray);
            stmt.setArray(1, sqlArray);
            stmt.executeUpdate();

            System.out.println("Массив чисел успешно сохранен в таблицу '" + tableName + "'.");

        } catch (SQLException e) {
            System.out.println("Ошибка вставки массива в БД: " + e.getMessage());
        }
    }
}
