//for SQL
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

//for JFrame
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;


public class App {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:sqlserver://localhost:13001;"; //put ; at the end of url var
        String database = "Northwinds2022TSQLV7"; 
        //hardcoded Northwinds, change within JSONParser try block below
        String user = "sa";
        String password = "PH@123456789";
        String query = "SELECT TOP 10 * FROM Sales.[Order]"; //actual query being used 

              // Create a new DatabaseManager object and connect to the database
              DatabaseManager dbManager = new DatabaseManager(url, database, user, password);
              dbManager.connect();
      
              // Execute the query and retrieve the ResultSet
              ResultSet rs = dbManager.executeQuery(query);
      
              // Create a new JTable to display the results
              JTable table = new JTable(buildTableModel(rs));
      
              // Create a new JFrame to hold the JTable
              JFrame frame = new JFrame("Database Results");
      
              // Create a new JPanel to hold the JTable and add it to the JFrame
              JPanel panel = new JPanel(new BorderLayout());
              panel.add(new JScrollPane(table), BorderLayout.CENTER);
              frame.add(panel);
      
              // Set the size and visibility of the JFrame
              frame.setSize(1500, 500);
              frame.setVisible(true);
      
              // Close the ResultSet and database connection
              try {
                  rs.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
              dbManager.closeConnection();
          }
      
          // Helper method to build a TableModel from a ResultSet
          public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
              ResultSetMetaData metaData = rs.getMetaData();
      
              // Get the number of columns in the ResultSet
              int columnCount = metaData.getColumnCount();
      
              // Create a new DefaultTableModel to hold the data
              DefaultTableModel tableModel = new DefaultTableModel();
      
              // Add the column names to the table model
              for (int i = 1; i <= columnCount; i++) {
                  tableModel.addColumn(metaData.getColumnName(i));
              }
      
              // Add the data rows to the table model
              while (rs.next()) {
                  Object[] row = new Object[columnCount];
                  for (int i = 1; i <= columnCount; i++) {
                      row[i - 1] = rs.getObject(i);
                  }
                  tableModel.addRow(row);
              }
      
              return tableModel;

    }

    //ANSI coloring for header output at terminal
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
}
