package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Hospitalization extends DB {
    private final String tableName;

    public Hospitalization() {
        this.tableName = "hospitalization";
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id SERIAL PRIMARY KEY, "
                + "patient_id VARCHAR(20) NOT NULL, "
                + "room_id INTEGER NOT NULL, "
                + "doctor_id INTEGER NOT NULL, "
                + "date DATE NOT NULL, "
                + "FOREIGN KEY (patient_id) REFERENCES patient(national_code) ON DELETE CASCADE, "
                + "FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE, "
                + "FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table '" + tableName + "' is ready.");
        } catch (SQLException e) {
            System.out.println("Error creating table '" + tableName + "': " + e.getMessage());
        }
    }

    public void addHospitalization(String patientId, int roomId, int doctorId, String date) {
        String sql = "INSERT INTO " + tableName + " (patient_id, room_id, doctor_id, date) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patientId);
            pstmt.setInt(2, roomId);
            pstmt.setInt(3, doctorId);
            pstmt.setDate(4, Date.valueOf(date));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("New hospitalization record added successfully.");
            } else {
                System.out.println("Failed to add hospitalization record.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding hospitalization record: " + e.getMessage());
        }
    }

    public ArrayList<HashMap<String, Object>> showAll() {
        String query = "SELECT h.id, h.date, p.first_name, p.last_name, r.roomNumber, d.name AS doctor_name, d.lastName AS doctor_lastName " +
                "FROM hospitalization h " +
                "JOIN patient p ON h.patient_id = p.national_code " +
                "JOIN room r ON h.room_id = r.id " +
                "JOIN doctor d ON h.doctor_id = d.id";

        ArrayList<HashMap<String, Object>> allHospitalizations = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                HashMap<String, Object> hospitalizationData = new HashMap<>();

                hospitalizationData.put("id", resultSet.getInt("id"));
                hospitalizationData.put("date", resultSet.getDate("date"));
                hospitalizationData.put("patientName", resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                hospitalizationData.put("roomNumber", resultSet.getInt("roomNumber"));
                hospitalizationData.put("doctorName", resultSet.getString("doctor_name") + " " + resultSet.getString("doctor_lastName"));

                allHospitalizations.add(hospitalizationData);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving hospitalizations: " + e.getMessage());
        }
        return allHospitalizations;
    }

    public void removeById(int id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Hospitalization record with ID " + id + " removed.");
            } else {
                System.out.println("No hospitalization record found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error removing hospitalization record: " + e.getMessage());
        }
    }

    public void updateById(int id, String patientId, int roomId, int doctorId, String date) {
        String sql = "UPDATE " + tableName + " SET patient_id = ?, room_id = ?, doctor_id = ?, date = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patientId);
            pstmt.setInt(2, roomId);
            pstmt.setInt(3, doctorId);
            pstmt.setDate(4, Date.valueOf(date));
            pstmt.setInt(5, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Hospitalization record with ID " + id + " updated.");
            } else {
                System.out.println("No hospitalization record found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error updating hospitalization record: " + e.getMessage());
        }
    }
    public ArrayList<HashMap<String, String>> getPatients() {
        String query = "SELECT national_code, first_name, last_name FROM patient";
        ArrayList<HashMap<String, String>> patients = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                HashMap<String, String> patient = new HashMap<>();
                patient.put("id", rs.getString("national_code"));
                patient.put("name", rs.getString("first_name") + " " + rs.getString("last_name"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving patients: " + e.getMessage());
        }
        return patients;
    }

    public ArrayList<HashMap<String, String>> getRooms() {
        String query = "SELECT id, roomNumber FROM room";
        ArrayList<HashMap<String, String>> rooms = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                HashMap<String, String> room = new HashMap<>();
                room.put("id", String.valueOf(rs.getInt("id")));
                room.put("name", "Room " + rs.getInt("roomNumber"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rooms: " + e.getMessage());
        }
        return rooms;
    }

    public ArrayList<HashMap<String, String>> getDoctors() {
        String query = "SELECT id, name, lastName FROM doctor";
        ArrayList<HashMap<String, String>> doctors = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                HashMap<String, String> doctor = new HashMap<>();
                doctor.put("id", String.valueOf(rs.getInt("id")));
                doctor.put("name", rs.getString("name") + " " + rs.getString("lastName"));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving doctors: " + e.getMessage());
        }
        return doctors;
    }

}