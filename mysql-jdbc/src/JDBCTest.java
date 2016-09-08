import java.sql.*;

public class JDBCTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String username = "root";
		String password = "";
		String database = "test";
		String connectionURL = "jdbc:mysql://localhost:3306/" + database;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, username, password);
			statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS exmpl_tbl ");
			statement.executeUpdate("CREATE TABLE exmpl_tbl (id INT, val VARCHAR(100))");
			statement.executeUpdate("INSERT INTO exmpl_tbl VALUES(1, 'Hello')");
			statement.executeUpdate("INSERT INTO exmpl_tbl VALUES(2, 'World')");
			
			rs = statement.executeQuery("SELECT val FROM exmpl_tbl WHERE id=1");
			if(rs.next()){
				System.out.println("Value returned:" + rs.getString(1));
			}

			String val = "World";
			rs = statement.executeQuery("SELECT id FROM exmpl_tbl WHERE val='" + val + "'");
			if(rs.next()){
				System.out.println("ID of " + val + " is " + rs.getInt(1));
			}
			
			rs = statement.executeQuery("SELECT * FROM exmpl_tbl");
			
			while (rs.next()) {
				System.out.println("Value of ID " + rs.getInt("id") + 
						" is " + rs.getString("val"));
			}
			
			int c = statement.executeUpdate("DELETE FROM exmpl_tbl WHERE id=1");
			System.out.println("Deleted " + c + " rows\n");
			statement.executeUpdate("DROP TABLE exmpl_tbl");
			
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

