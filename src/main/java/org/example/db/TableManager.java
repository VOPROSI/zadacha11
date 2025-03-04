package org.example.db;

import java.sql.*;
import java.util.Scanner;

public class TableManager {

    private final Connection conn;

    public TableManager() {
        this.conn = DbModule.getConnection();
    }

    public void getTables(){

        String query = "SELECT tablename from pg_tables WHERE schemaname = 'public';";

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)
        ){
            System.out.println("Таблицы:");
            while(rs.next()){

                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка SQL показа всех таблиц" + e.getMessage());
        }
    }

    public String createTable() {
        Scanner input = new Scanner(System.in);
        String tableName = "";

        try {
            boolean exit = false;
            String choice;
            while (!exit) {
                System.out.println("Введите КОРРЕКТНОЕ название таблицы, при отсутствии её, она создастся.Для выхода введите '0'");
                choice = input.nextLine();
                if (choice.equals("0")) {
                    exit = true;
                } else if (choice.matches("\\w+")) {
                    tableName = choice;
                    break;
                } else {
                    System.out.println("Ошибка ввода, повторите");
                }

            }
            if (!exit) {
                String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (id SERIAL PRIMARY KEY, values_array INTEGER[]);";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с базой данных: " + e.getMessage());
            tableName = null;
        }
        return tableName;
    }
}
