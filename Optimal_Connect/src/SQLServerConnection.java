import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnection {
    public static Connection connect() throws DBException {

        try {
            // Register JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Create a new connection
            return DriverManager.getConnection(
                    Config.getDbUrl(),
                    Config.getDbUsername(),
                    Config.getDbPassword()
            );

        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e.getMessage());
        }
    }
}
