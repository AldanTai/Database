import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mariadb://0.tcp.jp.ngrok.io:11051/411177012";
    private static final String USER = "411177012";
    private static final String PASSWORD = "411177012";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
