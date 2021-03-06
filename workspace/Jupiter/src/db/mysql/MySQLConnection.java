package db.mysql;

import db.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entity.Item;
import entity.Item.ItemBuilder;


public class MySQLConnection implements DBConnection{
	private Connection conn;

	public MySQLConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MySQLDBUtil.URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void setFavoriteItems(String userId, List<String> itemIds) {
		if (conn == null) {
			return;
		}
		
		try {
			String sql = "INSERT IGNORE INTO history (user_id, item_id) VALUES (?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			for (String itemId : itemIds) {
				stmt.setString(1, userId);
				stmt.setString(2, itemId);
				stmt.execute();
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void unsetFavoriteItems(String userId, List<String> itemIds) {
		if (conn == null) {
			return;
		}
		
		try {
			String sql = "DELETE FROM history WHERE user_id = ? AND item_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			for (String itemId : itemIds) {
				stmt.setString(1, userId);
				stmt.setString(2, itemId);
				stmt.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Set<String> getFavoriteItemIds(String userId) {
		if (conn == null) {
			return new HashSet<>();
		}
		
		Set<String> favoriteItemIds = new HashSet<>();
		
		try {
			String sql = "SELECT item_id from history where user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String itemId = rs.getString("item_id");
				favoriteItemIds.add(itemId);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		System.out.println(favoriteItemIds);
		return favoriteItemIds;
	}

	@Override
	public Set<Item> getFavoriteItems(String userId) {
		if (conn == null) {
			return new HashSet<>();
		}
		
		Set<Item> favoriteItems = new HashSet<>();
		Set<String> itemIds = getFavoriteItemIds(userId);
		
		try {
			String sql = "SELECT * FROM items WHERE item_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			for (String itemId : itemIds) {
				stmt.setString(1, itemId);
				
				ResultSet rs = stmt.executeQuery();
				
				ItemBuilder builder = new ItemBuilder();
				
				while (rs.next()) {
					builder.setItemId(rs.getString("item_id"));
					builder.setName(rs.getString("name"));
					builder.setCategory(rs.getString("category"));
					builder.setPrice(rs.getDouble("price"));
					builder.setImageUrl(rs.getString("image_url"));
					builder.setDescription(rs.getString("description"));
					builder.setApprove(rs.getDouble("approve"));
					builder.setSellerId(rs.getString("seller_id"));

					favoriteItems.add(builder.build());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return favoriteItems;
	}

	@Override
	public Set<String> getCategories(String itemId) {
		if (conn == null) {
			return null;
		}
		Set<String> categories = new HashSet<>();
		try {
			String sql = "SELECT category from categories WHERE item_id = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, itemId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				categories.add(rs.getString("category"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return categories;
	}

	@Override
	public List<Item> searchItems(String userId) {
		List<Item> items = new ArrayList<>();
		Set<String> itemIds = getFavoriteItemIds(userId);
//		System.out.println(userId);
		try {
			String sql = "SELECT * FROM items WHERE seller_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			for (String itemId : itemIds) {
				stmt.setString(1, userId);
				
				ResultSet rs = stmt.executeQuery();
				
				ItemBuilder builder = new ItemBuilder();
				
				while (rs.next()) {
					builder.setItemId(rs.getString("item_id"));
					builder.setName(rs.getString("name"));
					builder.setCategory(rs.getString("category"));
					builder.setPrice(rs.getDouble("price"));
					builder.setImageUrl(rs.getString("image_url"));
					builder.setDescription(rs.getString("description"));
					builder.setApprove(rs.getDouble("approve"));
					builder.setSellerId(rs.getString("seller_id"));

					items.add(builder.build());
				}
			}
		} catch (SQLException e) {
			System.out.println("Sql request fail");
			e.printStackTrace();
		}
		
		return items;
	}
	
	@Override
	public List<Item> listItems() {
		List<Item> items = new ArrayList<>();
//		System.out.println(userId);
		try {
			String sql = "SELECT * FROM items WHERE approve = 1";
			PreparedStatement stmt = conn.prepareStatement(sql);
				
			ResultSet rs = stmt.executeQuery();
			
			ItemBuilder builder = new ItemBuilder();
			
			while (rs.next()) {
				builder.setItemId(rs.getString("item_id"));
				builder.setName(rs.getString("name"));
				builder.setCategory(rs.getString("category"));
				builder.setPrice(rs.getDouble("price"));
				builder.setImageUrl(rs.getString("image_url"));
				builder.setDescription(rs.getString("description"));
				builder.setApprove(rs.getDouble("approve"));
				builder.setSellerId(rs.getString("seller_id"));

				items.add(builder.build());
			}
		} catch (SQLException e) {
			System.out.println("Sql request fail");
			e.printStackTrace();
		}
		
		return items;
	}

	@Override
	public void saveItem(Item item) {
		if (conn == null) {
			return;
		}
		
		try {
			// SQL injection
			// Example:
			// SELECT * FROM users WHERE username = '<username>' AND password = '<password>';
			//
			// sql = "SELECT * FROM users WHERE username = '" + username + "'
			//       AND password = '" + password + "'"
			//
			// username: aoweifjoawefijwaoeifj
			// password: 123456' OR '1' = '1
			//
			// SELECT * FROM users WHERE username = 'aoweifjoawefijwaoeifj' AND password = '123456' OR '1' = '1'
			String sql = "INSERT IGNORE INTO items VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, item.getItemId());
			stmt.setString(2, item.getName());
			stmt.setString(3, item.getCategory());
			stmt.setDouble(4, item.getPrice());
			stmt.setString(5, item.getImageUrl());
			stmt.setString(6, item.getDescription());
			stmt.setDouble(7, item.getApprove());
			stmt.setString(8, item.getSellerId());
			stmt.execute();
			
//			sql = "INSERT IGNORE INTO categories VALUES (?, ?)";
//			stmt = conn.prepareStatement(sql);
//			for (String category : item.getCategories()) {
//				stmt.setString(1, item.getItemId());
//				stmt.setString(2, category);
//				stmt.execute();
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String getFullname(String userId) {
		if (conn == null) {
			return null;
		}
		String name = "";
		try {
			String sql = "SELECT first_name, last_name from users WHERE user_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				name = String.join(" ", rs.getString("first_name"), rs.getString("last_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	@Override
	public boolean verifyLogin(String userId, String password) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "SELECT user_id from users WHERE user_id = ? and password = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Set<String> getRecommendItemIds(String itemId) {
		if (conn == null) {
			return new HashSet<>();
		}
		
		Set<String> favoriteItemIds = new HashSet<>();
		
		try {
			String sql = "SELECT * from recommend where item_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, itemId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String item = rs.getString("item1");
				favoriteItemIds.add(item);
				
				item = rs.getString("item2");
				favoriteItemIds.add(item);
				
				item = rs.getString("item3");
				favoriteItemIds.add(item);
				
				item = rs.getString("item4");
				favoriteItemIds.add(item);
				
				item = rs.getString("item5");
				favoriteItemIds.add(item);
				
				item = rs.getString("item6");
				favoriteItemIds.add(item);
				
				item = rs.getString("item7");
				favoriteItemIds.add(item);
				
				item = rs.getString("item8");
				favoriteItemIds.add(item);
				
				item = rs.getString("item9");
				favoriteItemIds.add(item);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return favoriteItemIds;
	}
	
	@Override
	public Set<Item> getRecommendations(String userId) {
		if (conn == null) {
			return new HashSet<>();
		}
		
		Set<String> exist = new HashSet<>();
		Set<Item> recommendItems = new HashSet<>();
		Set<String> itemIds = getFavoriteItemIds(userId);
//		System.out.println(itemIds);
		
		try {
			String sql = "SELECT * FROM items WHERE item_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			for (String itemId : itemIds) {
				Set<String> items = getRecommendItemIds(itemId);
//				System.out.println(itemId);
				for(String item : items) {
//					System.out.println(item);
					stmt.setString(1, item);
					
					ResultSet rs = stmt.executeQuery();
					
					ItemBuilder builder = new ItemBuilder();
					
					while (rs.next()) {
						if(exist.contains(rs.getString("item_id"))) {
							continue;
						}
						exist.add(rs.getString("item_id"));
						builder.setItemId(rs.getString("item_id"));
						builder.setName(rs.getString("name"));
						builder.setCategory(rs.getString("category"));
						builder.setPrice(rs.getDouble("price"));
						builder.setImageUrl(rs.getString("image_url"));
						builder.setDescription(rs.getString("description"));
						builder.setApprove(rs.getDouble("approve"));
						builder.setSellerId(rs.getString("seller_id"));

						recommendItems.add(builder.build());
					}
				}
//				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recommendItems;
	}

}

