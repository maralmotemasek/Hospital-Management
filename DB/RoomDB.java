package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RoomDB extends DB {
    private final String tableName;

    public RoomDB() {
        this.tableName = "room";
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id SERIAL PRIMARY KEY, "
                + "type VARCHAR(100) NOT NULL CHECK (type IN ('Single', 'Double', 'ICU', 'General')), "
                + "roomNumber INTEGER NOT NULL UNIQUE CHECK (roomNumber > 0), "
                + "bedCount INTEGER NOT NULL CHECK (bedCount > 0), "
                + "isAvailable BOOLEAN NOT NULL DEFAULT TRUE, "
                + "price DECIMAL(10,2) NOT NULL CHECK (price >= 0) "
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table '" + tableName + "' is ready.");
        } catch (SQLException e) {
            System.out.println("Error creating table '" + tableName + "': " + e.getMessage());
        }
    }

    public void addRoom(String type, int roomNumber, int bedCount, boolean isAvailable, double price) {
        String query = "INSERT INTO " + tableName + " (type, roomNumber, bedCount, isAvailable , price) VALUES (?, ?, ?, ?,?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set values for the parameters
            preparedStatement.setString(1, type);
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.setInt(3, bedCount);
            preparedStatement.setBoolean(4, isAvailable);
            preparedStatement.setDouble(5, price);

            // Execute the insert
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Room added successfully.");
            } else {
                System.out.println("Failed to add the room.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while trying to add room.");
            e.printStackTrace();
        }
    }


    public ArrayList<Integer> getRoomNumberByType(String type) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE type = ?";
        ArrayList<Integer> roomNumbers = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the type in the prepared statement
            preparedStatement.setString(1, type);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomNumbers.add(resultSet.getInt("roomNumber"));
            }


        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return roomNumbers;
    }

    public ArrayList<Integer> getBedCountByType(String type) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList<Integer> bedCounts = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the speciality in the prepared statement
            preparedStatement.setString(1, type);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bedCounts.add(resultSet.getInt("bedCount"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return bedCounts;
    }

    public ArrayList<Boolean> getStatusByType(String type) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList<Boolean> statuses = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the speciality in the prepared statement
            preparedStatement.setString(1, type);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                statuses.add(resultSet.getBoolean("status"));
            }


        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return statuses;
    }

    public ArrayList<Double> getPriceByType(String type) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList<Double> prices = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the speciality in the prepared statement
            preparedStatement.setString(1, type);

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                prices.add(resultSet.getDouble("price"));
            }


        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return prices;
    }

    public ArrayList<String> getTypeByRoomNumber(int roomNumber) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList<String> types = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                types.add(resultSet.getString("type"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return types;
    }

    public ArrayList<Integer> getBedCountByRoomNumber(int roomNumber) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList<Integer> bedCounts = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bedCounts.add(resultSet.getInt("besCount"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return bedCounts;
    }

    public ArrayList<Boolean> getStatusByRoomNumber(int roomNumber) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList<Boolean> statuses = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                statuses.add(resultSet.getBoolean("status"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return statuses;
    }

    public ArrayList<Double> getPriceByRoomNumber(int roomNumber) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE speciality = ?";
        ArrayList<Double> prices = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                prices.add(resultSet.getDouble("price"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return prices;
    }

    public ArrayList<String> getTypeByBedCount(int bedCount) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE bedCount = ?";
        ArrayList<String> types = new ArrayList<>();


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                types.add(resultSet.getString("type"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return types;
    }


    public ArrayList<Integer> getRoomNumberByBedCount(int bedCount) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE bedCount = ?";
        ArrayList<Integer> bedCounts = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bedCounts.add(resultSet.getInt("roomNumber"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return bedCounts;
    }


    public ArrayList<Double> getPriceByBedCount(int bedCount) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE bedCount = ?";
        ArrayList<Double> prices = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                prices.add(resultSet.getDouble("price"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return prices;
    }


    public ArrayList<Boolean> getStatusByBedCount(int bedCount) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE bedCount = ?";
        ArrayList<Boolean> statuses = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                statuses.add(resultSet.getBoolean("statuses"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return statuses;
    }


    public ArrayList<String> getTypeByStatus(boolean isAvailable) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE status = ?";
        ArrayList<String> types = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                types.add(resultSet.getString("types"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return types;
    }

    public ArrayList<Integer> getRoomNumberByStatus(boolean isAvailable) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE status = ?";
        ArrayList<Integer> roomNumbers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomNumbers.add(resultSet.getInt("roomNumber"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return roomNumbers;
    }

    public ArrayList<Integer> getBedCountByStatus(boolean isAvailable) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE status = ?";
        ArrayList<Integer> bedCounts = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bedCounts.add(resultSet.getInt("bedCount"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return bedCounts;
    }

    public ArrayList<Double> getPriceByStatus(boolean isAvailable) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE status = ?";
        ArrayList<Double> prices = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                prices.add(resultSet.getDouble("price"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return prices;
    }


    public ArrayList<String> getTypeByPrice(Double price) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE price = ?";
        ArrayList<String> types = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                types.add(resultSet.getString("type"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return types;
    }

    public ArrayList<Integer> getRoomNumberByPrice(Double price) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE price = ?";
        ArrayList<Integer> roomNumbers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomNumbers.add(resultSet.getInt("roomNumber"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return roomNumbers;
    }

    public ArrayList<Integer> getBedCountByPrice(Double price) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE price = ?";
        ArrayList<Integer> bedCounts = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bedCounts.add(resultSet.getInt("bedCount"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return bedCounts;
    }

    public ArrayList<Boolean> getStatusByPrice(Double price) {
        // Create the SQL query with a placeholder
        String query = "SELECT * FROM " + tableName + " WHERE price = ?";
        ArrayList<Boolean> statuses = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Execute the query to get results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                statuses.add(resultSet.getBoolean("status"));
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show status.");
            e.printStackTrace();
        }
        return statuses;
    }

    public ArrayList<HashMap<String, Object>> showAll() {
        String query = "SELECT * FROM " + tableName; // Assure that tableName is correct
        ArrayList<HashMap<String, Object>> allRooms = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                HashMap<String, Object> departmentData = new HashMap<>();

                // Use the correct column names based on your actual table definition
                departmentData.put("type", resultSet.getString("type")); // Update column name if it's different
                departmentData.put("roomNumber", resultSet.getInt("roomNumber"));
                departmentData.put("bedCount", resultSet.getInt("bedCount"));
                departmentData.put("isAvailable", resultSet.getBoolean("isAvailable"));
                departmentData.put("price", resultSet.getDouble("price"));
                departmentData.put("id", resultSet.getInt("id"));

                allRooms.add(departmentData);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while trying to show departments.");
            e.printStackTrace();
        }
        return allRooms;
    }

    public void removeRoom(int id) {
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

        public boolean updateRoom ( int id, String type,boolean isAvailable, int roomNumber, int bedCount ,double price){
            // SQL Update query
            String query = "UPDATE room SET type = ?, isAvailable = ?, roomNumber= ? , bedCount = ? , price = ? WHERE id = ?";

            // Use try-with-resources to ensure resources are closed
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                // Set parameters for the query
                preparedStatement.setString(1, type);
                preparedStatement.setBoolean(2, isAvailable);
                preparedStatement.setInt(3, roomNumber);
                preparedStatement.setInt(4, bedCount);
                preparedStatement.setDouble(5, price);
                preparedStatement.setInt(6, id);


                // Execute update and check the number of affected rows
                int rowsAffected = preparedStatement.executeUpdate();

                // If rowsAffected > 0, the update was successful
                if (rowsAffected > 0) {
                    System.out.println("Room updated successfully.");
                    return true;
                } else {
                    System.out.println("No Room found with the provided id.");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while trying to update department.");
                e.printStackTrace();
                return false;
            }

        }
    }



