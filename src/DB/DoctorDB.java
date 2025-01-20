package DB;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class DoctorDB extends DB{
    private final String tableName;

    public DoctorDB() {
        this.tableName = "doctor";
    }

    public void createTableIfNotExists() { // ایحاد table مورد نظر با شزوط مورد نظر خودمان
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id SERIAL PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL CHECK (name ~ '^[A-Za-z]+$'), "
                + "lastName VARCHAR(100) NOT NULL CHECK (lastName ~ '^[A-Za-z]+$'), "
                + "gender CHAR(6) NOT NULL CHECK (gender IN ('Male', 'Female', 'Other')), "
                + "medicalCode INTEGER NOT NULL UNIQUE CHECK (medicalCode > 0), "
                + "speciality VARCHAR(100) NOT NULL CHECK (speciality ~ '^[A-Za-z ]+$') "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table '" + tableName + "' is ready.");
        } catch (SQLException e) {
            System.out.println("Error creating table '" + tableName + "': " + e.getMessage());
        }
    }

    public void addDoctor(String name, String lastName, String gender, int medicalCode , String speciality) {
        String query = "INSERT INTO " + tableName + " (name, lastName, gender, medicalCode, speciality ) VALUES (?, ?, ? ,? ,? )";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setInt(4,medicalCode);
            preparedStatement.setString(5, speciality);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Doctor added successfully.");
            } else {
                System.out.println("Failed to add the Doctor.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while trying to add doctor.");
            e.printStackTrace();
        }
    }


    public ArrayList<String> getLastNameByName(String name) {
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


    public ArrayList<String> getSpecialityByName(String name) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE name = ?";
        ArrayList<String> specialities = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the name in the prepared statement
            preparedStatement.setString(1, name);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                specialities.add(resultSet.getString("special"));
            }


        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show special.");
            e.printStackTrace();
        }
        return specialities;
    }

    public ArrayList<Integer> getMedicalCodeByName(String name) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE name = ?";
        ArrayList<Integer> medicalCodes = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the name in the prepared statement
            preparedStatement.setString(1, name);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                medicalCodes.add(resultSet.getInt("medicalCode"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show medicalCode.");
            e.printStackTrace();
        }
        return medicalCodes;
    }


    public ArrayList<Integer> getMedicalCodeByLastName(String lastName) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE lastName = ?";
        ArrayList<Integer> medicalCodes = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the lastName in the prepared statement
            preparedStatement.setString(1, lastName);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                medicalCodes.add(resultSet.getInt("medicalCode"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show medicalCode.");
            e.printStackTrace();
        }
        return medicalCodes;
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

    public ArrayList<String> getSpecialityByLastName(String lastName) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE lastName = ?";
        ArrayList<String> specialities = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the lastName in the prepared statement
            preparedStatement.setString(1, lastName);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                specialities.add(resultSet.getString("speciality"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show specialities.");
            e.printStackTrace();
        }
        return specialities;
    }

    public ArrayList<Integer> getMedicalCodeByGender(String gender) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE lastName = ?";
        ArrayList<Integer> medicalCodes = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the gender in the prepared statement
            preparedStatement.setString(1, gender);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                medicalCodes.add(resultSet.getInt("medicalCode"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show medicalCode.");
            e.printStackTrace();
        }
        return medicalCodes;
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

    public ArrayList<String> getSpecialityByGender(String gender) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE gender = ?";
        ArrayList<String> specialities = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the gender in the prepared statement
            preparedStatement.setString(1, gender);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                specialities.add(resultSet.getString("specialities"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show speciality.");
            e.printStackTrace();
        }
        return specialities;
    }

    public ArrayList<Integer> getMedicalCodeBySpeciality(String speciality) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList<Integer> medicalCodes = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the speciality in the prepared statement
            preparedStatement.setString(1, speciality);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                medicalCodes.add(resultSet.getInt("medicalCode"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show medicalCode.");
            e.printStackTrace();
        }
        return medicalCodes;
    }



    public ArrayList<String> getNameBySpeciality(String speciality) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList<String> names = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the speciality in the prepared statement
            preparedStatement.setString(1, speciality);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                names.add(resultSet.getString("medicalCode"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show names.");
            e.printStackTrace();
        }
        return names;
    }


    public ArrayList<String> getLastNameBySpeciality(String speciality) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList<String> lastnames = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the speciality in the prepared statement
            preparedStatement.setString(1, speciality);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                lastnames.add(resultSet.getString("lastName"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show last names.");
            e.printStackTrace();
        }
        return lastnames;
    }

    public ArrayList<String> getGenderBySpeciality(String speciality) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList<String> genders = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the speciality in the prepared statement
            preparedStatement.setString(1, speciality);

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


    public ArrayList<String> getGenderByMedicalCode(int medicalCode) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE medicalCode = ?";
        ArrayList<String> genders = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the medicalCode in the prepared statement
            preparedStatement.setInt(1, medicalCode);

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

    public ArrayList<String> getNameByMedical(int medicalCode) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE medicalCode = ?";
        ArrayList<String> names = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the medicalCode in the prepared statement
            preparedStatement.setInt(1, medicalCode);

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

    public ArrayList<String> getLastNameByMedicalCode(int medicalCode) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE medicalCode = ?";
        ArrayList<String> lastNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the medicalCode in the prepared statement
            preparedStatement.setInt(1, medicalCode);

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

    public ArrayList<String> getSpecialityByMedicalCode(int medicalCode) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE medicalCode = ?";
        ArrayList<String> specialities = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the medicalCode in the prepared statement
            preparedStatement.setInt(1, medicalCode);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                specialities.add(resultSet.getString(" specialities"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show  specialities.");
            e.printStackTrace();
        }
        return  specialities;
    }



    public ArrayList<HashMap<String, Object>> showAll() {
        String query = "SELECT * FROM " + tableName; // Assure that tableName is correct
        ArrayList<HashMap<String, Object>> allDoctors = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                HashMap<String, Object> doctorData = new HashMap<>();

                // Use the correct column names based on your actual table definition
                doctorData.put("name", resultSet.getString("name")); // Update column name if it's different
                doctorData.put("lastName", resultSet.getString("lastName"));
                doctorData.put("gender", resultSet.getString("gender"));
                doctorData.put("speciality", resultSet.getString("speciality"));
                doctorData.put("medicalCode", resultSet.getInt("medicalCode"));
                doctorData.put("id", resultSet.getInt("id"));
                allDoctors.add(doctorData);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show doctors.");
            e.printStackTrace();
        }
        return allDoctors;
    }


    public void removeDoctor(int id) {
        // Correctly format the DELETE SQL query with a placeholder
        String query = "DELETE FROM " + tableName + " WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the id parameter
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Doctor deleted successfully.");
            } else {
                System.out.println("Failed to delete the doctor; no doctor found with the provided id.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while trying to delete doctor.");
            e.printStackTrace();
        }
    }

    public boolean updateDoctor ( int id, String name,String lastName, String gender, int medicalCode , String speciality) {
        // SQL Update query
        String query = "UPDATE doctor SET name = ?,  lastName= ? , gender = ? , medicalCode = ?, speciality = ?  WHERE id = ?";

        // Use try-with-resources to ensure resources are closed
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the query
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, gender);
            preparedStatement.setInt(4, medicalCode);
            preparedStatement.setString(5, speciality);
            preparedStatement.setInt(6, id);


            // Execute update and check the number of affected rows
            int rowsAffected = preparedStatement.executeUpdate();

            // If rowsAffected > 0, the update was successful
            if (rowsAffected > 0) {
                System.out.println("Doctor updated successfully.");
                return true;
            } else {
                System.out.println("No Doctor found with the provided id.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while trying to update doctor.");
            e.printStackTrace();
            return false;
        }
    }
}