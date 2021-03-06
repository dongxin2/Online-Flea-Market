package entity;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Item {
	private String itemId;
	private String name;
	private String category;
	private double price;
	private String imageUrl;
	private String description;
	private double approve;
	private String sellerId;
	
	public String getItemId() {
		return itemId;
	}
	public String getName() {
		return name;
	}
	public String getCategory() {
		return category;
	}
	public double getPrice() {
		return price;
	}
	public String getDescription() {
		return description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public double getApprove() {
		return approve;
	}
	public String getSellerId() {
		return sellerId;
	}
	
//	To convert an Item object a JSONObject instance 
//	because in our application, frontend code only understand JSON.
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("item_id", itemId);
			obj.put("name", name);
			obj.put("category", category);
			obj.put("price", price);			
			obj.put("image_url", imageUrl);
			obj.put("description", description);
			obj.put("approve", approve);
			obj.put("seller_id", sellerId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * This is a builder pattern in Java.
	 */
	private Item(ItemBuilder builder) {
		this.itemId = builder.itemId;
		this.name = builder.name;
		this.category = builder.category;
		this.price = builder.price;
		this.imageUrl = builder.imageUrl;
		this.description = builder.description;
		this.approve = builder.approve;
		this.sellerId = builder.sellerId;
	}
	
//	To use Item item = new ItemBuilder().setItemId().setName().set....build();
	public static class ItemBuilder {
		private String itemId;
		private String name;
		private String category;
		private double price;
		private String imageUrl;
		private String description;
		private double approve;
		private String sellerId;
		
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setCategory(String category) {
			this.category = category;
		}
		
		public void setPrice(double price) {
			this.price = price;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public void setApprove(double approve) {
			this.approve = approve;
		}
		
		public void setSellerId(String sellerId) {
			this.sellerId = sellerId;
		}
		
		public Item build() {
			return new Item(this);
		}
	}


}

