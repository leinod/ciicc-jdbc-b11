package com.ciicc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/ciicc_db_b11";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {



        Scanner scanner = new Scanner(System.in);


        String quit = "";
        while(!quit.equalsIgnoreCase("quit")){

            short choice = 0;
            System.out.println("Enter your choices");
            System.out.println("1. Insert DATA");

            choice = scanner.nextShort();



            switch(choice){
                case 1 -> registration();
                case 2 -> System.out.println("case 2");
            }



            System.out.println("Type to exit");
            quit = scanner.nextLine();
        }

    }


    public static void registration(){

        Scanner scanner = new Scanner(System.in);
        String firstName;
        String middleName;
        String lastName;


        System.out.println("Insert Data");

        System.out.print("Enter First Name: ");
        firstName = scanner.nextLine();

        System.out.print("Enter Middle Name: ");
        middleName = scanner.nextLine();

        System.out.print("Enter Last Name");
        lastName = scanner.nextLine();

        insertData(firstName,middleName,lastName);


    }

    //
    public static void updateData(int id, String first_name, String middle_name, String lastName ){

        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){

            String query = "UPDATE users SET first_name = ?, middle_name = ?, last_Name = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, first_name);
            statement.setString(2, middle_name);
            statement.setString(3, lastName);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Update Successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteData(int id){



        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD)){

            String query = "DELETE FROM users WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);

            int rowsAffected = statement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Successfully deleted");
            }
            System.out.println("Failed");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void insertData(String firstName, String middleName, String lastName) {
        String query = "INSERT INTO users (first_name, middle_name, last_name) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, middleName);
            statement.setString(3, lastName);
            // Use the statement to execute SQL queries
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchSpecificData(int id) {

        // 1
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            // Use the statement to execute SQL queries
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                int userId = results.getInt("id");
                String firstName = results.getString("first_name");
                String middleName = results.getString("middle_name");
                String lastName = results.getString("last_name");
                System.out.println("ID: " + userId + ", firstName: " + firstName + ", middleName: " +
                        middleName + ", lastName: " + lastName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchData() {
        String query = "SELECT * FROM users";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {
            // statements
            // 1. statement
            // 2. prepared statement
            // 3. callable statement

            Statement statement = connection.createStatement();
            // Use the statement to execute SQL queries
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int id = results.getInt("id");
                String firstName = results.getString("first_name");
                String middleName = results.getString("middle_name");
                String lastName = results.getString("last_name");
                System.out.println("ID: " + id + ", firstName: " + firstName + ", middleName: " +
                        middleName + ", lastName: " + lastName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}