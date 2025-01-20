package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PatientDB extends DB {
    private final String tableName;

    public PatientDB() {
        this.tableName = "patient";
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "national_code VARCHAR(20) PRIMARY KEY, "
                + "first_name VARCHAR(100) NOT NULL CHECK (first_name ~ '^[A-Za-z]+$'), "
                + "last_name VARCHAR(100) NOT NULL CHECK (last_name ~ '^[A-Za-z]+$'), "
                + "gender CHAR(6) NOT NULL CHECK (gender IN ('Male', 'Female', 'Other')), "
                + "age INTEGER NOT NULL CHECK (age >= 0), "
                + "illness VARCHAR(200) NOT NULL, "
                + "insurance VARCHAR(100), "
                + "is_pregnant BOOLEAN CHECK (gender = 'Female' OR is_pregnant IS NULL) "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table '" + tableName + "' is ready.");
        } catch (SQLException e) {
            System.out.println("Error creating table '" + tableName + "': " + e.getMessage());
        }
    }

    public void addPatient(String nationalCode, String firstName, String lastName, String gender, int age, String illness, String insurance, Boolean isPregnant) {
        String sql = "INSERT INTO " + tableName + " (national_code, first_name, last_name, gender, age, illness, insurance, is_pregnant) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nationalCode);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, gender);
            pstmt.setInt(5, age);
            pstmt.setString(6, illness);
            pstmt.setString(7, insurance);

            // Ensure is_pregnant is only set for female patients
            if ("Female".equalsIgnoreCase(gender)) {
                pstmt.setObject(8, isPregnant);
            } else {
                pstmt.setNull(8, Types.BOOLEAN);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("New patient record added successfully.");
            } else {
                System.out.println("Failed to add patient record.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding patient record: " + e.getMessage());
        }
    }

    public ArrayList<HashMap<String, Object>> showAll() {
        String query = "SELECT * FROM " + tableName;
        ArrayList<HashMap<String, Object>> allPatients = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                HashMap<String, Object> patientData = new HashMap<>();

                patientData.put("nationalCode", resultSet.getString("national_code"));
                patientData.put("firstName", resultSet.getString("first_name"));
                patientData.put("lastName", resultSet.getString("last_name"));
                patientData.put("gender", resultSet.getString("gender"));
                patientData.put("age", resultSet.getInt("age"));
                patientData.put("illness", resultSet.getString("illness"));
                patientData.put("insurance", resultSet.getString("insurance"));
                patientData.put("isPregnant", resultSet.getObject("is_pregnant"));

                allPatients.add(patientData);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to retrieve patients.");
            e.printStackTrace();
        }
        return allPatients;
    }

    public void removeByNationalCode(String nationalCode) {
        String sql = "DELETE FROM " + tableName + " WHERE national_code = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nationalCode);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient with National Code " + nationalCode + " removed.");
            } else {
                System.out.println("No patient found with National Code " + nationalCode);
            }
        } catch (SQLException e) {
            System.out.println("Error removing patient: " + e.getMessage());
        }
    }

    public void updateByNationalCode(String nationalCode, String firstName, String lastName, String gender, int age, String illness, String insurance, Boolean isPregnant) {
        String sql = "UPDATE " + tableName + " SET first_name = ?, last_name = ?, gender = ?, age = ?, illness = ?, insurance = ?, is_pregnant = ? WHERE national_code = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, gender);
            pstmt.setInt(4, age);
            pstmt.setString(5, illness);
            pstmt.setString(6, insurance);

            // Ensure is_pregnant is only set for female patients
            if ("Female".equalsIgnoreCase(gender)) {
                pstmt.setObject(7, isPregnant);
            } else {
                pstmt.setNull(7, Types.BOOLEAN);
            }

            pstmt.setString(8, nationalCode);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient with National Code " + nationalCode + " updated.");
            } else {
                System.out.println("No patient found with National Code " + nationalCode);
            }
        } catch (SQLException e) {
            System.out.println("Error updating patient: " + e.getMessage());
        }
    }
}
