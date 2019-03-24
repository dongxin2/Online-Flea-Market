package rpc;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class RpcHelper {
	public static void writeJsonObject(HttpServletResponse response, JSONObject obj) {
		try {
			response.setContentType("application/json ");
			PrintWriter out= response.getWriter();
			response.addHeader("Access-Control-Allow-Origin", "*");
			out.print(obj);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeJsonArray(HttpServletResponse response, JSONArray array) {
		try {
			response.setContentType("application/json ");
			PrintWriter out= response.getWriter();
			response.addHeader("Access-Control-Allow-Origin", "*");
			out.print(array);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
