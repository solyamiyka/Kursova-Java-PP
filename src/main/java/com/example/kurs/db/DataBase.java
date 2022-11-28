package com.example.kurs.db;

import com.example.kurs.taxes.Income;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    static final String DB_URL = "jdbc:mysql://localhost:3306/taxes";
    static final String USER = "solyamiya";
    static final String PASS = "11Solya@01";

    public static ArrayList<Income> getTaxesDB() {
        ArrayList<Income> taxes = new ArrayList<>();
        Income newTax;
        String QUERY = "SELECT nameOftaxes, sizeOfIncome, percentageOfTax, sizeOfTax FROM taxes";

        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY)
        ) {
            while (rs.next()) {
                System.out.print("Name: " + rs.getString("nameOftaxes"));
                System.out.print(",Size of income: " + rs.getDouble("sizeOfIncome"));
                System.out.print(", Size of Tax: " + rs.getDouble("sizeOfTax"));
                System.out.println(", Percentage of Tax: " + rs.getDouble("percentageOfTax"));

                newTax = new Income();
                newTax.setNameOfIncome(rs.getString("nameOftaxes"));
                newTax.setSizeOfIncome(rs.getDouble("sizeOfIncome"));
                newTax.setSizeOfTax(rs.getDouble("sizeOfTax"));
                newTax.setPercentageOfTax(rs.getDouble("percentageOfTax"));

                taxes.add(newTax);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taxes;
    }
    public static void writeTaxesInDB(List<Income> taxes) {
        String QUERY_CLEAR = "TRUNCATE TABLE taxes";
        String QUERY_INSERT;

        try(
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(QUERY_CLEAR);
            for (Income tax : taxes) {
                QUERY_INSERT = "INSERT INTO taxes VALUES ('" + tax.getNameOfIncome() + "', " + tax.getSizeOfIncome() + ", " + tax.getPercentageOfTax() + ", " + tax.getSizeOfTax() + ")";
                stmt.executeUpdate(QUERY_INSERT);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
