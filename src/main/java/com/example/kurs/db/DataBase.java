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
    static final String PASS = "**********";

    public static ArrayList<Income> getTaxesDB() {
        ArrayList<Income> taxes = new ArrayList<>();
        Income newTax;

        String QUERY = "SELECT incomeInfo.incomeName, incomeInfo.incomeSize, taxesInfo.percentage, taxesInfo.taxSize\n" +
                "FROM incomeInfo\n" +
                "INNER JOIN taxesInfo ON taxesInfo.income_id = incomeInfo.incomeId ;";

        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY)
        ) {
            while (rs.next()) {
                System.out.print("Name: " + rs.getString("incomeName"));
                System.out.print(",Size of income: " + rs.getDouble("incomeSize"));
                System.out.print(", Size of Tax: " + rs.getDouble("taxSize"));
                System.out.println(", Percentage of Tax: " + rs.getDouble("percentage"));

                newTax = new Income();
                newTax.setNameOfIncome(rs.getString("incomeName"));
                newTax.setSizeOfIncome(rs.getDouble("incomeSize"));
                newTax.setSizeOfTax(rs.getDouble("taxSize"));
                newTax.setPercentageOfTax(rs.getDouble("percentage"));

                taxes.add(newTax);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taxes;
    }
    public static void writeTaxesInDB(List<Income> taxes) {
        String QUERY_CLEAR1 = "TRUNCATE TABLE taxesInfo;";
        String QUERY_CLEAR2 =  "TRUNCATE TABLE incomeInfo;";

        String QUERY_INSERT1, QUERY_INSERT2;

        try(
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(QUERY_CLEAR1);
            stmt.executeUpdate(QUERY_CLEAR2);

            int id = 1;
            for (Income tax : taxes) {
                QUERY_INSERT1 = "INSERT INTO taxesInfo VALUES (" + id + "," + tax.getSizeOfTax() + ", " + tax.getPercentageOfTax() + ", " + id +  ");\n";

                QUERY_INSERT2 ="INSERT INTO incomeInfo VALUES (" + id + ",'" + tax.getNameOfIncome() + "', " + tax.getSizeOfIncome() + ")";

                stmt.executeUpdate(QUERY_INSERT1);
                stmt.executeUpdate(QUERY_INSERT2);
                id++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
