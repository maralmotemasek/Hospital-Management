package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Connection;

public class DepartmentDB extends DB {
    private final String tableName;

    public DepartmentDB() {
        this.tableName = "department";
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id SERIAL PRIMARY KEY, "
                + "speciality VARCHAR(100) NOT NULL CHECK (speciality ~ '^[A-Za-z ]+$'), "
                + "departmentNumber INTEGER UNIQUE CHECK (departmentNumber > 0), "
                + "isAvailable BOOLEAN NOT NULL DEFAULT TRUE "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql); // اجرای دستور sql که همان ساختن table است
            System.out.println("Table '" + tableName + "' is ready.");
        } catch (SQLException e) {
            System.out.println("Error creating table '" + tableName + "': " + e.getMessage());
        }
    }

    public void addDepartment(String speciality, int departmentNumber, boolean isAvailable) {
        String query = "INSERT INTO " + tableName + " (speciality, departmentNumber, isAvailable) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password); // اتصال به پایگاه داده
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, speciality);
            preparedStatement.setInt(2, departmentNumber);
            preparedStatement.setBoolean(3, isAvailable);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Department added successfully.");
            } else {
                System.out.println("Failed to add the department.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while trying to add department.");
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getDepartmentNumberBySpeciality(String speciality) {
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList <Integer> departmentNumbers = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                departmentNumbers.add(resultSet.getInt("departmentNumber")) ;
            }


        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return departmentNumbers;
    }

    public ArrayList<Boolean> getStatusBySpeciality (String speciality) {
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList <Boolean> statuses = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                statuses.add(resultSet.getBoolean("isAvailable")) ;
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return statuses;
    }

    public ArrayList<String> getSpecialityByDepartmentNumber (int departmentNumber){
        String query = "SELECT * FROM " + tableName + " WHERE departmentNumber = " + departmentNumber;
        ArrayList <String> specialities = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {


            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Access to the table '" + tableName + "' is granted.");
                specialities.add(resultSet.getString("speciality")) ;
            } else {
                System.out.println("No results found for the specialty: " + departmentNumber);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return specialities;
    }

    public ArrayList<Boolean> getStatusByDepartmentNumber (int departmentNumber){
        String query = "SELECT * FROM " + tableName + " WHERE departmentNumber = " + departmentNumber;
        ArrayList <Boolean> statuses = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                statuses.add( resultSet.getBoolean("isAvailable"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return statuses;
    }

    public ArrayList<String> getSpecialityByStatus (boolean isAvailable){
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE isAvailable = ";
        ArrayList <String> specialities = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                specialities.add(resultSet.getString("speciality"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return specialities;
    }

    public ArrayList<Integer> getDepartmentNumberByStatus (boolean isAvailable) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE isAvailable = ?";
        ArrayList <Integer> departmentNumbers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the isAvailable parameter as a boolean, which may be stored as 0 or 1 in the database
            preparedStatement.setBoolean(1, isAvailable);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                departmentNumbers.add( resultSet.getInt("departmentNumber")); // Assuming this is the correct field
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return departmentNumbers;
    }

    public ArrayList<HashMap<String, Object>> showAll() {
        String query = "SELECT * FROM " + tableName; // Assure that tableName is correct
        ArrayList<HashMap<String, Object>> allDepartments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                HashMap<String, Object> departmentData = new HashMap<>();

                // Use the correct column names based on your actual table definition
                departmentData.put("name", resultSet.getString("speciality")); // Update column name if it's different
                departmentData.put("departmentNumber", resultSet.getInt("departmentNumber"));
                departmentData.put("isAvailable", resultSet.getBoolean("isAvailable"));
                departmentData.put("id", resultSet.getInt("id"));

                allDepartments.add(departmentData);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return allDepartments;
    }

    public void removeDepartment(int id) {
        // Correctly format the DELETE SQL query with a placeholder
        String query = "DELETE FROM " + tableName + " WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the id parameter
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Department deleted successfully.");
            } else {
                System.out.println("Failed to delete the department; no department found with the provided id.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while trying to delete department.");
            e.printStackTrace();
        }

    }

        public boolean updateDepartment(int id, String speciaity, boolean isAvailable , int departmentNumber) {
            // SQL Update query
            String query = "UPDATE department SET speciality = ?, isAvailable = ?, departmentNumber= ? WHERE id = ?";

            // Use try-with-resources to ensure resources are closed
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                // Set parameters for the query
                preparedStatement.setString(1, speciaity);
                preparedStatement.setBoolean(2, isAvailable);
                preparedStatement.setInt(3, departmentNumber);
                preparedStatement.setInt(4, id);


                // Execute update and check the number of affected rows
                int rowsAffected = preparedStatement.executeUpdate();

                // If rowsAffected > 0, the update was successful
                if (rowsAffected > 0) {
                    System.out.println("Department updated successfully.");
                    return true;
                } else {
                    System.out.println("No department found with the provided id.");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while trying to update department.");
                e.printStackTrace();
                return false;
            }
    }
}