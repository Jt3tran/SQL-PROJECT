import java.sql.*;

public class DatabaseManager {
    private Connection conn;
    private String url;
    private String user;
    private String password;
    
    public DatabaseManager(String url, String database,String user, String password) {
        this.url = url + "databaseName=" + database; //builds URL w/ current db
        this.user = user;
        this.password = password;
    }
    
    public void connect() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }
    
    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error executing query");
            e.printStackTrace();
        }
        return rs;
    }
    
    public int executeUpdate(String sql) {
        int numRowsAffected = 0;
        try {
            Statement stmt = conn.createStatement();
            numRowsAffected = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error executing update");
            e.printStackTrace();
        }
        return numRowsAffected;
    }
    
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection");
            e.printStackTrace();
        }
    }
}
