package db.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
//			String sql = "DROP TABLE IF EXISTS categories";
//			stmt.executeUpdate(sql);
			
			String sql = "DROP TABLE IF EXISTS history";
			stmt.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS items";
			stmt.executeUpdate(sql);
			
			sql = "DROP TABLE IF EXISTS users";
			stmt.executeUpdate(sql);
			
			// Step 3 Create new tables
//			sql = "CREATE TABLE categories ("
//					+ "item_id VARCHAR(255) NOT NULL,"
//					+ "category VARCHAR(255) NOT NULL,"
//					+ "PRIMARY KEY (item_id, category),"
//					+ "FOREIGN KEY (item_id) REFERENCES items(item_id))";
//			stmt.executeUpdate(sql);

			sql = "CREATE TABLE users ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "password VARCHAR(255) NOT NULL,"
					+ "user_group INT,"
					+ "first_name VARCHAR(255),"
					+ "last_name VARCHAR(255),"
					+ "PRIMARY KEY (user_id))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE items ("
					+ "item_id INT NOT NULL AUTO_INCREMENT,"
					+ "name VARCHAR(255),"
					+ "category VARCHAR(255),"
					+ "price FLOAT,"
					+ "image_url VARCHAR(255),"
					+ "description VARCHAR(255),"
					+ "approve FLOAT DEFAULT 0,"
					+ "seller_id VARCHAR(255),"
					+ "PRIMARY KEY (item_id),"
					+ "FOREIGN KEY (seller_id) REFERENCES users(user_id))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE history ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "item_id INT NOT NULL AUTO_INCREMENT,"
					+ "last_favor_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
					+ "PRIMARY KEY (user_id, item_id),"
					+ "FOREIGN KEY (item_id) REFERENCES items(item_id),"
					+ "FOREIGN KEY (user_id) REFERENCES users(user_id))";
			stmt.executeUpdate(sql);
			
			// Step 4: insert data
			sql = "INSERT INTO users VALUES ("
					+ "'fziyan2', '666666', 1.0, 'Ziyan', 'Feng')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO users VALUES ("
					+ "'younand2', '666666', 1.0, 'Younan', 'Deng')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO users VALUES ("
					+ "'yunanz2', '666666', 1.0, 'Yunan', 'Zhang')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO users VALUES ("
					+ "'dongxin2', '666666', 1.0, 'Dongxin', 'Zhu')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO users VALUES ("
					+ "'accepted', '666666', 1.0, 'cs', '411')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);

			sql = "INSERT INTO items VALUES ("
					+ "'1', 'iPhoneX', 'Electronic device', 1000.0, 'https://cdn.vox-cdn.com/thumbor/4nOocrwrwul2VZax_BdyHyKkDR4=/0x0:2640x1748/1820x1213/filters:focal(1109x663:1531x1085)/cdn.vox-cdn.com/uploads/chorus_image/image/60675421/twarren_iphonesim_1.1533038365.jpg', 'This is an iPhoneX.', 0.0, 'accepted')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ("
					+ " 'iClicker', 'Electronic device', 20.0, 'https://bloximages.newyork1.vip.townnews.com/purdueexponent.org/content/tncms/assets/v3/editorial/d/25/d25f5715-956a-51fb-a6ab-5f9020e08ec8/4ecb2c760e213.image.jpg?resize=584%2C759', 'This is an iClicker', 1.0, 'accepted')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('ipad', 'Electrical device', 299, 'https://i.imgur.com/0WPSkev.jpg', 'Tablet designed by Apple', 1, 'fziyan2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('AJ11', 'Sports', 220, 'https://i.imgur.com/GdTzUgw.jpg', '11th shoes of michael jordan designed by Air Jordan', 1, 'fziyan2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('AJ1', 'Sports', 160, 'https://i.imgur.com/7c8LTsZ.jpg', '1st shoes of Michael Jordan designed by Air Jordan', 1, 'fziyan2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('AJ3', 'Sports', 190, 'https://i.imgur.com/j5SbQJl.jpg', '3rd shoes of Michael Jordan designed by Air Jordan', 1, 'fziyan2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('AJ4', 'Sports', 200, 'https://i.imgur.com/N8YmRnk.jpg', '4th shoes of Michael Jordan designed by Air Jordan', 1, 'fziyan2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('iPhone 7', 'Electrical device', 799, 'https://i.imgur.com/gsNSbar.jpg', '7th iphone designed by Apple', 1, 'fziyan2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('PS4', 'Electrical device', 299, 'https://i.imgur.com/LmWsA3D.jpg', 'Video Gamebox designed by Sony', 1, 'fziyan2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('Switch', 'Electrical device', 299, 'https://i.imgur.com/vRmJiOI.jpg', 'Video Gamebox designed by Nintendo', 1, 'fziyan2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('Sprite', 'Snack', 1, 'https://i.imgur.com/vzqtd5z.jpg', 'Lemon flavor soda drink', 1, 'fziyan2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('Chips', 'Snack', 1, 'https://i.imgur.com/FiWVFZN.jpg', 'Chips produced by Lays', 1, 'fziyan2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('Oreo', 'Snack', 1, 'https://i.imgur.com/dqc8Oxc.jpg', 'Chocolate Cookie', 1, 'fziyan2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('Sekiro', 'Electrical device', 59, 'https://i.imgur.com/5kMKUFR.jpg', 'Games designed by Fromsoftware', 1, 'yunanz2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('XBOX', 'Electrical device', 165, 'https://i.imgur.com/Z4AK6OX.jpg', ' Game platform made by Microsoft', 1, 'yunanz2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('GJacket', 'Clothes', 44, 'https://i.imgur.com/RJdcsrS.jpg', 'Basic jacket of Gym Shark', 1, 'yunanz2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('Ultraboost', 'Sports', 150, 'https://i.imgur.com/gYXQxYD.jpg', 'Jogging shoes made by Adidas', 1, 'yunanz2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('Lamy2000', 'Daily use', 149, 'https://i.imgur.com/BMFdLp1.jpg', 'Classical pen designed by Lamy', 1, 'yunanz2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('DMC5', 'Electrical device', 59, 'https://i.imgur.com/V9dRu7q.png', '5th game of dmc series made by capcom', 1, 'yunanz2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('Campusbook', 'Daily use', 1, 'https://i.imgur.com/QpgLFcT.jpg', 'Kokuyo notebook for students', 1, 'yunanz2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('Sennheiser', 'Electrical device', 220, 'https://i.imgur.com/kFRyDOi.jpg', 'Best headphone ever', 1, 'yunanz2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('ProteinBar', 'Snack', 2, 'https://i.imgur.com/NxRIqk2.jpg', 'Dark choclate protein bar', 1, 'yunanz2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('DrPeper', 'Snack', 2, 'https://i.imgur.com/12jNipA.jpg?1', 'Drink for wise people', 1, 'yunanz2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT INTO items (name, category, price, image_url, description, approve, seller_id) VALUES ('Echo', 'Electrical device', 39, 'https://i.imgur.com/egBuPW7.jpg', 'Voice assistant', 1, 'yunanz2')";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			
			
			//insert into history
			sql = "INSERT INTO history (user_id, item_id) VALUES ("
					+ "'accepted', 1)";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO history (user_id, item_id) VALUES ("
					+ "'accepted', 2)";
			System.out.println("Executing query: " + sql);
			stmt.executeUpdate(sql);

			System.out.println("Import is done successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

