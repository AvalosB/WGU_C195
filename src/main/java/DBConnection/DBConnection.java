package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    private static PreparedStatement preparedStatement;

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//192.168.0.208:3306/client_schedule";
    //private static final String ipAddress = "//127.0.0.1:3306/client_schedule";

    private static final String jdbcURL = protocol + vendorName + ipAddress + "?connectionTimeZone=SERVER";

    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    public static Connection conn = null;

    private static final String username = "Brian";
    private static final String password = "PIEsandwich!@23";

    /**
     * Start the databsae connection.
     * @return conn
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Good maybe");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return conn;

    }

    /**
     * Method to get the current connection.
     * @return current connection.
     */
    public static Connection getConnection() {

        return conn;
    }

    /**
     * Close the connection.
     */
    public static void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.getMessage(); 
        }
    }

    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sqlStatement);
    }

    public static PreparedStatement getPreparedStatement() {

        return preparedStatement;
    }

}