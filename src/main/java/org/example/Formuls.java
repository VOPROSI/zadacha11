package org.example;

import org.example.db.DbModule;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Formuls {
    private final double side1;
    private final double side2;
    private final double side3;

    public Formuls(double side1, double side2, double side3) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    public double Perimetr() {
        return side1 + side2 + side3;
    }

    public double Square() {
        double p = (side1 + side2 + side3) / 2;
        return Math.sqrt(p * (p - side1) * (p - side2) * (p - side3));
    }

    public boolean isRectangle() {
        double[] sides = {side1, side2, side3};
        java.util.Arrays.sort(sides);
        return Math.pow(sides[2], 2) == Math.pow(sides[0], 2) + Math.pow(sides[1], 2);
    }

    public static void printTriangleById(int id, String tableName) {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (PreparedStatement stmt = DbModule.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("=========================================");
                System.out.printf("ID: %d\n", rs.getInt("id"));
                System.out.printf("Стороны: %.2f, %.2f, %.2f\n",
                        rs.getDouble("side1"), rs.getDouble("side2"), rs.getDouble("side3"));
                System.out.printf("Периметр: %.2f\n", rs.getDouble("perimeter"));
                System.out.printf("Площадь: %.2f\n", rs.getDouble("square"));
                System.out.printf("Прямоугольный: %s\n", rs.getBoolean("is_rectangular") ? "Да" : "Нет");
                System.out.println("=========================================");
            } else {
                System.out.println("Запись с таким ID не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении данных: " + e.getMessage());
        }
    }
}
