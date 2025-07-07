import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url="jdbc:mysql://localhost:3306/your_db_name";
    private static final String user="username";
    private static final String pass="add your password";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,user,pass);
    }
}
