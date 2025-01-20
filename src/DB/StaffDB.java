package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class StaffDB extends DB {
    private final String tableName;

    public StaffDB() {
        this.tableName = "staff";
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id SERIAL PRIMARY KEY, "
                + "first_name VARCHAR(100) NOT NULL, "
                + "last_name VARCHAR(100) NOT NULL, "
                + "gender CHAR(6) NOT NULL CHECK (gender IN ('Male', 'Female', 'Other')), "
                + "position VARCHAR(250)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table '" + tableName + "' is ready.");
        } catch (SQLException e) {
            System.out.println("Error creating table '" + tableName + "': " + e.getMessage());
        }
    }

    public void addStaff(String firstName, String lastName, String gender, String position) {
        String sql = "INSERT INTO " + tableName + " (first_name, last_name, gender, position) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, gender);
            pstmt.setString(4, position);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("New staff record added successfully.");
            } else {
                System.out.println("Failed to add staff record.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding staff record: " + e.getMessage());
        }
    }

    public ArrayList<HashMap<String, Object>> showAll() {
        String query = "SELECT * FROM " + tableName;
        ArrayList<HashMap<String, Object>> allStaff = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                HashMap<String, Object> staffData = new HashMap<>();
                staffData.put("id", resultSet.getInt("id"));
                staffData.put("firstName", resultSet.getString("first_name"));
                staffData.put("lastName", resultSet.getString("last_name"));
                staffData.put("gender", resultSet.getString("gender"));
                staffData.put("position", resultSet.getString("position"));

                allStaff.add(staffData);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to retrieve staff.");
            e.printStackTrace();
        }
        return allStaff;
    }

    public void removeById(int id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Staff with ID " + id + " removed.");
            } else {
                System.out.println("No staff found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error removing staff: " + e.getMessage());
        }
    }

    public void updateById(int id, String firstName, String lastName, String gender, String position) {
        String sql = "UPDATE " + tableName + " SET first_name = ?, last_name = ?, gender = ?, position = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, gender);
            pstmt.setString(4, position);
            pstmt.setInt(5, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Staff with ID " + id + " updated successfully.");
            } else {
                System.out.println("No staff found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error updating staff: " + e.getMessage());
        }
    }
}
