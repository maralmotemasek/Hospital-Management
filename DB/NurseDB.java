package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class NurseDB extends DB{
    private final String tableName;

    public NurseDB() {
        this.tableName = "nurse";
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id SERIAL PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL CHECK (name ~ '^[A-Za-z]+$'), "
                + "lastName VARCHAR(100) NOT NULL CHECK (lastName ~ '^[A-Za-z]+$'), "
                + "gender CHAR(6) NOT NULL CHECK (gender IN ('Male', 'Female', 'Other')), "
                + "nationalCode VARCHAR(20) NOT NULL UNIQUE CHECK (nationalCode ~ '^[0-9]+$') "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table '" + tableName + "' is ready.");
        } catch (SQLException e) {
            System.out.println("Error creating table '" + tableName + "': " + e.getMessage());
        }
    }

    public void addNurse(String name, String lastName, String gender, int nationalCode ) {
        String query = "INSERT INTO " + tableName + " (name, lastName, gender, nationalCode) VALUES (?, ?, ? ,?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set values for the parameters
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setInt(4,nationalCode);


            // Execute the insert
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Nurse added successfully.");
            } else {
                System.out.println("Failed to add the Nurse.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while trying to add nurse.");
            e.printStackTrace();
        }
    }


    public ArrayList<String> getLastNameByName(String name) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE name = ?";
        ArrayList<String> lastNames = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the speciality in the prepared statement
            preparedStatement.setString(1, name);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                lastNames.add(resultSet.getString("lastName"));
            }


        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show lastName.");
            e.printStackTrace();
        }
        return lastNames;
    }

    public ArrayList<String> getGenderByName(String name) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE name = ?";
        ArrayList<String> genders = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the name in the prepared statement
            preparedStatement.setString(1, name);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                genders.add(resultSet.getString("gender"));
            }


        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show gender.");
            e.printStackTrace();
        }
        return genders;
    }


    public ArrayList<Integer> getNationalCodeByName(String name) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE name = ?";
        ArrayList<Integer> nationalCodes = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the name in the prepared statement
            preparedStatement.setString(1, name);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                nationalCodes.add(resultSet.getInt("medicalCode"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show medicalCode.");
            e.printStackTrace();
        }
        return nationalCodes;
    }


    public ArrayList<Integer> getNationalCodeByLastName(String lastName) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE lastName = ?";
        ArrayList<Integer> nationalCode = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the lastName in the prepared statement
            preparedStatement.setString(1, lastName);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                nationalCode.add(resultSet.getInt("medicalCode"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show medicalCode.");
            e.printStackTrace();
        }
        return nationalCode;
    }

    public ArrayList<String> getNameByLastName(String lastName) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE lastName = ?";
        ArrayList<String> names = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the lastName in the prepared statement
            preparedStatement.setString(1, lastName);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                names.add(resultSet.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show name.");
            e.printStackTrace();
        }
        return names;
    }

    public ArrayList<String> getGenderByLastName(String lastName) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE lastName = ?";
        ArrayList<String> genders = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the lastName in the prepared statement
            preparedStatement.setString(1, lastName);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                genders.add(resultSet.getString("gender"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show gender.");
            e.printStackTrace();
        }
        return genders;
    }

    public ArrayList<Integer> getNationalCodeByGender(String gender) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE lastName = ?";
        ArrayList<Integer> nationalCode = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the gender in the prepared statement
            preparedStatement.setString(1, gender);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                nationalCode.add(resultSet.getInt("medicalCode"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show medicalCode.");
            e.printStackTrace();
        }
        return nationalCode;
    }

    public ArrayList<String> getNameByGender(String gender) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE gender = ?";
        ArrayList<String> names = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the gender in the prepared statement
            preparedStatement.setString(1, gender);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                names.add(resultSet.getString("names"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show Name.");
            e.printStackTrace();
        }
        return names;
    }

    public ArrayList<String> getLastNameByGender(String gender) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE gender = ?";
        ArrayList<String> lastNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the gender in the prepared statement
            preparedStatement.setString(1, gender);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                lastNames.add(resultSet.getString("lastNames"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show lastName.");
            e.printStackTrace();
        }
        return lastNames;
    }

    public ArrayList<String> getGenderByNationalCode(int nationalCode) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE nationalCode = ?";
        ArrayList<String> genders = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the nationalCode in the prepared statement
            preparedStatement.setInt(1, nationalCode);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                genders.add(resultSet.getString("gender"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show gender.");
            e.printStackTrace();
        }
        return genders;
    }

    public ArrayList<String> getNameByNationalCode(int nationalCode) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE nationalCode = ?";
        ArrayList<String> names = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the nationalCode in the prepared statement
            preparedStatement.setInt(1, nationalCode);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                names.add(resultSet.getString("names"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show names.");
            e.printStackTrace();
        }
        return names;
    }

    public ArrayList<String> getLastNameByNational(int nationalCode) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE nationalCode = ?";
        ArrayList<String> lastNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the nationalCode in the prepared statement
            preparedStatement.setInt(1, nationalCode);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                lastNames.add(resultSet.getString("lastNames"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show lastNames.");
            e.printStackTrace();
        }
        return lastNames;
    }


    public ArrayList<HashMap<String, Object>> showAll() {
        String query = "SELECT * FROM " + tableName; // Assure that tableName is correct
        ArrayList<HashMap<String, Object>> allNurses = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                HashMap<String, Object> nurseData = new HashMap<>();

                // Use the correct column names based on your actual table definition
                nurseData.put("name", resultSet.getString("name")); // Update column name if it's different
                nurseData.put("lastName", resultSet.getString("lastName"));
                nurseData.put("gender", resultSet.getString("gender"));
                nurseData.put("nationalCode", resultSet.getInt("nationalCode"));
                nurseData.put("id", resultSet.getInt("id"));
                allNurses.add(nurseData);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show doctors.");
            e.printStackTrace();
        }
        return allNurses;
    }


    public void removeNurse(int id) {
        // Correctly format the DELETE SQL query with a placeholder
        String query = "DELETE FROM " + tableName + " WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the id parameter
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Nurse deleted successfully.");
            } else {
                System.out.println("Failed to delete the nurse; no nurse found with the provided id.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while trying to delete nurse.");
            e.printStackTrace();
        }
    }

    public boolean updateNurse( int id, String name,String lastName, String gender, int nationalCode) {
        // SQL Update query
        String query = "UPDATE nurse SET name = ?,  lastName= ? , gender = ? , nationalCode = ? WHERE id = ?";

        // Use try-with-resources to ensure resources are closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the query
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setInt(4, nationalCode);
            preparedStatement.setInt(5, id);


            // Execute update and check the number of affected rows
            int rowsAffected = preparedStatement.executeUpdate();

            // If rowsAffected > 0, the update was successful
            if (rowsAffected > 0) {
                System.out.println("Nurse updated successfully.");
                return true;
            } else {
                System.out.println("No Nurse found with the provided id.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while trying to update nurse.");
            e.printStackTrace();
            return false;
        }

    }

}
