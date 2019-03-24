package db.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class MySQLTableCreation {
	// Run this as Java application to reset db schema.
	public static void main(String[] args) {
		try {
			// This is java.sql.Connection. Not com.mysql.jdbc.Connection.
			Connection conn = null;

			// Step 1 Connect to MySQL.
			try {
				System.out.println("Connecting to " + MySQLDBUtil.URL);
				Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();
				conn = DriverManager.getConnection(MySQLDBUtil.URL);
			} catch (SQLException e) {
				e.printStackTrace();
			}


			if (conn == null) {
				return;
			}
			
			// Step 2 Drop tables in case they exist.
			Statement stmt = conn.createStatement();
			String sql = "DROP TABLE IF EXISTS categories";
			stmt.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS history";
			stmt.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS items";
			stmt.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS users";
			stmt.executeUpdate(sql);
			
			// Step 3 Create new tables
			sql = "CREATE TABLE items ("
					+ "item_id VARCHAR(255) NOT NULL,"
					+ "name VARCHAR(255),"
					+ "category VARCHAR(255),"
					+ "price FLOAT,"
					+ "image_url VARCHAR(255),"
					+ "description VARCHAR(255),"
					+ "approve FLOAT,"
					+ "PRIMARY KEY (item_id))";
//					+ "FOREIGN KEY (category) REFERENCES categories(category))";
			stmt.executeUpdate(sql);
			
//			sql = "CREATE TABLE categories ("
//					+ "item_id VARCHAR(255) NOT NULL,"
//					+ "category VARCHAR(255) NOT NULL,"
//					+ "PRIMARY KEY (item_id, category),"
//					+ "FOREIGN KEY (item_id) REFERENCES items(item_id))";
//			stmt.executeUpdate(sql);

			sql = "CREATE TABLE users ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "password VARCHAR(255) NOT NULL,"
					+ "user_group FLOAT,"
					+ "first_name VARCHAR(255),"
					+ "last_name VARCHAR(255),"
					+ "PRIMARY KEY (user_id))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE history ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "item_id VARCHAR(255) NOT NULL,"
					+ "last_favor_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
					+ "PRIMARY KEY (user_id, item_id),"
					+ "FOREIGN KEY (item_id) REFERENCES items(item_id),"
					+ "FOREIGN KEY (user_id) REFERENCES users(user_id))";
			stmt.executeUpdate(sql);
			
			// Step 4: insert data
			sql = "INSERT INTO users VALUES ("
					+ "'1111', '3229c1097c00d497a0fd282d586be050', 1.0, 'John', 'Smith')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO users VALUES ("
					+ "'accepted', '666666', 1.0, 'cs', '411')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);

			sql = "INSERT INTO items VALUES ("
					+ "'1', 'iPhoneX', 'electronic device', 1000.0, 'https://cdn.vox-cdn.com/thumbor/4nOocrwrwul2VZax_BdyHyKkDR4=/0x0:2640x1748/1820x1213/filters:focal(1109x663:1531x1085)/cdn.vox-cdn.com/uploads/chorus_image/image/60675421/twarren_iphonesim_1.1533038365.jpg', 'This is an iPhoneX.', 0.0)";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO items VALUES ("
					+ "'2', 'iClicker', 'electronic device', 20.0, 'https://bloximages.newyork1.vip.townnews.com/purdueexponent.org/content/tncms/assets/v3/editorial/d/25/d25f5715-956a-51fb-a6ab-5f9020e08ec8/4ecb2c760e213.image.jpg?resize=584%2C759', 'This is an iClicker', 1.0)";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);

			System.out.println("Import is done successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

