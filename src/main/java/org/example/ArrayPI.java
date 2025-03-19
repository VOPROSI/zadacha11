package org.example;

import org.example.db.InsertTable;
import java.util.Scanner;


import java.util.Scanner;

public class ArrayPI {
    private double side1;
    private double side2;
    private double side3;

    public ArrayPI() {
        Scanner input = new Scanner(System.in);
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Введите 3 стороны треугольника через пробел: ");
            String line = input.nextLine().trim();
            String[] parts = line.split("\\s+");

            if (parts.length != 3) {
                System.out.println("Ошибка: необходимо ввести ровно 3 числа!");
                continue;
            }

            try {
                this.side1 = Double.parseDouble(parts[0]);
                this.side2 = Double.parseDouble(parts[1]);
                this.side3 = Double.parseDouble(parts[2]);

                if (side1 <= 0 || side2 <= 0 || side3 <= 0) {
                    System.out.println("Ошибка: стороны должны быть положительными числами!");
                    continue;
                }

                if (side1 + side2 > side3 && side1 + side3 > side2 && side2 + side3 > side1) {
                    isValid = true;
                } else {
                    System.out.println("Ошибка: треугольник с такими сторонами невозможен!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректные числа!");
            }
        }
    }

    public void printData(){
        System.out.println("Треугольник принят: a = " + side1 + ", b = " + side2 + ", c = " + side3);
    }

    public void saveToDatabase(String tableName) {
        InsertTable insertTable = new InsertTable();
        Formuls formuls = new Formuls(side1, side2, side3);

        double perimeter = formuls.Perimetr();
        double square = formuls.Square();
        boolean isRectangular = formuls.isRectangle();

        insertTable.insertTriangle(tableName, side1, side2, side3, perimeter, square, isRectangular);
    }
}
