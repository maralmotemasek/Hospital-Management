package DB;

import java.sql.*;

public class Admin extends DB {
    private final String tableName;
    private final String defaultAdminUsername = "admin";

    public Admin() { // constructor
        this.tableName = "admin";
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id SERIAL PRIMARY KEY, "
                + "username VARCHAR(100) NOT NULL UNIQUE, "
                + "password VARCHAR(64) NOT NULL"
                + ");";


// baraye kar ba database va sakhtan table dar pgAdmin.
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {  // شی stmt ساختم برای اجرای دستورات sql
            stmt.execute(sql); // اجرا کردن دستور sql
            System.out.println("Table '" + tableName + "' is ready.");
            ensureAdminExists();
        } catch (SQLException e) {
            System.out.println("Error creating table '" + tableName + "': " + e.getMessage());
        }
    }

    private boolean adminExists() {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // انجام دستورات sql به صورت امن
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) { // اگر  نتایج موجود باشد یا تعداد رکورد ها بیشتر از صقر باشد شرط برقرار است
                return true; // Admin exists
            }
        } catch (SQLException e) {
            System.out.println("Error checking admin existence: " + e.getMessage());
        }
        return false;
    }

    private void deleteAllAdmins() {
        String sql = "DELETE FROM " + tableName; // Removes all admin users
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {  // انجام دستورات sql به صورت امن
            pstmt.executeUpdate(); //  برای ایجاد دستورات sql مثل DELETE
            System.out.println("All previous admins deleted.");
        } catch (SQLException e) {
            System.out.println("Error deleting previous admins: " + e.getMessage());
        }
    }

    private void ensureAdminExists() {
        if (!adminExists()) {
            deleteAllAdmins(); // Ensure no duplicate admin exists
            insertAdmin(defaultAdminUsername, "admin1234");
        }
    }

    public boolean insertAdmin(String username, String password) { // اضافه کردن یک کاربر به عنوان ادمین به database
        if (!isValidPassword(password)) {
            System.out.println("Password must contain at least 8 characters with letters & numbers.");
            return false;
        }

        String sql = "INSERT INTO " + tableName + " (username, password) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("Admin added successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error inserting admin: " + e.getMessage());
            return false;
        }
    }

    private boolean isValidPassword(String password) { // معتبر بودن یا نبودن پسوورد
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public boolean updateAdminInfo(String newUsername, String newPassword) {
        if (!isValidPassword(newPassword)) {
            System.out.println("Password must contain letters & numbers (min 8 chars).");
            return false;
        }

        String sql = "UPDATE " + tableName + " SET username = ?, password = ?"; // اپدیت کردن اطلاعات مدیریت
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // برای اجرای دستورات sql
            pstmt.setString(1, newUsername);
            pstmt.setString(2, newPassword);
            int affectedRows = pstmt.executeUpdate(); // چک کردن سطر های تحت تاثیر قرار گرفته
            if (affectedRows > 0) {
                System.out.println("Admin info updated successfully.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error updating admin info: " + e.getMessage());
        }
        return false;
    }

    public boolean login(String username, String password) {
        String sql = "SELECT password FROM " + tableName + " WHERE username = ?";
        try (Connection conn = connect(); // برای کانکت کردن
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // برای اجرای زبان sql
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String pass = rs.getString("password");
                return pass.equals(password);
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return false;
    }
}