package org.example.db;

import java.sql.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExportToExcel {

    final private static String URL = "jdbc:postgresql://localhost:5432/amigo";
    final private static String USER = "postgres";
    final private static String PASSWORD = "12345";

    protected static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения");
            System.exit(228);
        }
    }

    public void exportData(String tableName) {

        if (conn == null) {
            System.out.println("Нет соединения с БД.");
            return;
        }

        String sql = "SELECT * FROM " + tableName;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet(tableName);
            Row headerRow = sheet.createRow(0);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    row.createCell(i - 1).setCellValue(rs.getString(i));
                }
            }

            try (FileOutputStream fos = new FileOutputStream(tableName + ".xlsx")) {
                workbook.write(fos);
            }
            System.out.println("Экспорт выполнен: " + tableName + ".xlsx.");
        } catch (SQLException e) {
            System.out.println("Ошибка SQL: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }
}