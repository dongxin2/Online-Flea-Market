package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = (request.getParameter("user_id"));
		String password = request.getParameter("password");
		// Term can be empty or null.
		String term = request.getParameter("term");
		DBConnection connection = DBConnectionFactory.getConnection();
		
		//for log in page
		if(password != null) {
			JSONObject obj = new JSONObject();
			if(connection.verifyLogin(userId, password)) {
				String fullname = connection.getFullname(userId);
				try {
					obj.put("fullname", fullname);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			RpcHelper.writeJsonObject(response, obj);
			return;
		}
		
		//For search items, one is for list all, the other is for users
		List<JSONObject> list = new ArrayList<>();
		if(term.contains("all")) {	//List All
			List<Item> items = connection.listItems();
			
			Set<String> favorite = connection.getFavoriteItemIds(userId);
			connection.close();
			try {
				for (Item item : items) {
					// Add a thin version of item object
					JSONObject obj = item.toJSONObject();
					// Check if this is a favorite one.
					// This field is required by frontend to correctly display favorite items.
					obj.put("favorite", favorite.contains(item.getItemId()));

					list.add(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else {
			List<Item> items = connection.searchItems(userId);
			
			Set<String> favorite = connection.getFavoriteItemIds(userId);
			connection.close();
			
			try {
				for (Item item : items) {
					// Add a thin version of item object
					JSONObject obj = item.toJSONObject();
					// Check if this is a favorite one.
					// This field is required by frontend to correctly display favorite items.
					obj.put("favorite", favorite.contains(item.getItemId()));

					list.add(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		JSONArray array = new JSONArray(list);
		RpcHelper.writeJsonArray(response, array);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
