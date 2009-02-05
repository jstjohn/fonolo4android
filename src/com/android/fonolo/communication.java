package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;

public class communication {

		//make it into a JSONObject
		public static JSONObject check_member( String username, String password) throws JSONException
		{
			String[] params = new String[2];
			params[0]=username;
			params[1]=password;
	       String json = fonolo_library.get_json_contents("check_member", params);
			return make_json(json);
		} 
		// End check member info function

		public static JSONObject check_member_number( String username, String password, String phone) throws JSONException
		{ 
			String[] params = new String[3];
			params[0]=username;
			params[1]=password;
			params[2]=phone;
	       
			String json = fonolo_library.get_json_contents("check_member_number", params);
			return make_json(json);
		} 
		// End check member number info function

		
		// company search results
		//
		public static JSONObject company_search(String searchstring, String user, String pass) throws JSONException
		{
			String[] params = new String[1];
			params[0]=searchstring;
			
			String json = fonolo_library.get_json_contents("company_search", params, user, pass);
			return make_json(json);
		}
		// End company search results function

		//
		// lookup a company tree
		//
		public static JSONObject company_details( String treeid, String user, String pass) throws JSONException
		{
			String[] params = new String[1];
			params[0] = treeid;
			String json = fonolo_library.get_json_contents("company_details", params, user, pass);
			return make_json(json);
		}
		// End company lookup function
		public static JSONObject company_list( String limit, String pageno, String date, String user, String pass) throws JSONException
		{ 
			String[] params = new String[3];
			params[0]=limit;
			params[1]=pageno;
			params[2]=date;
	       
			String json = fonolo_library.get_json_contents("company_list", params, user, pass);
			return make_json(json);
		} 
		
		public static JSONObject call_start(String node_id, String user_phone_number, String user, String pass) throws JSONException{
			String[] params = new String[2];
			params[0] = node_id;
			params[1] = user_phone_number;
			String json = fonolo_library.get_json_contents("call_start", params, user, pass);
			return make_json(json);
		}
		
		public static JSONObject call_cancel(String session_id, String user, String pass) throws JSONException{
			String[] params = new String[1];
			params[0] = session_id;
			String json = fonolo_library.get_json_contents("call_cancel", params, user, pass);
			return make_json(json);
		}
		
		public static JSONObject call_status(String session_id, String user, String pass) throws JSONException{
			String[] params = new String[1];
			params[0] = session_id;
			String json = fonolo_library.get_json_contents("call_status", params, user, pass);
			return make_json(json);
		}
		
		//throws a JSONException if it is given a bad string as input
		private static JSONObject make_json(String input) throws JSONException{	
			JSONObject output = new JSONObject(input);		
			return output;
		}
		}	