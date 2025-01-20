package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DB {
    protected String url;
    protected String user;
    protected String password;

    public DB() {
        this.url = "jdbc:postgresql://localhost:5432/Hospital";
        this.user = "postgres";
        this.password = "2005";
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);

        // driveManger مسئول اتصال به پایگاه داده های مخلتلف است
        // getConnection سغی میکند با استفاده از اطلاعاتی ک در اختیارش قرار میدهیم connection نورد نظر را ایحاد نمیاد
    }
}