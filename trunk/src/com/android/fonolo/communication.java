package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;

public class communication {
	private fonolo_library lib;
	public communication() {
		lib = new fonolo_library();
	}
	public void set_member_info(String uname, String passwd){
		lib.set_member_info(uname, passwd);
	}
		//make it into a JSONObject
		public JSONObject check_member( String username, String password) throws JSONException
		{
			String[] params = new String[2];
			params[0]=username;
			params[1]=password;
	       String json = lib.get_json_contents("check_member", params, false, true);
			return make_json(json);
		} 
		// End check member info function

		public JSONObject check_member_number( String username, String password, String phone) throws JSONException
		{ 
			String[] params = new String[3];
			params[0]=username;
			params[1]=password;
			params[2]=phone;
	       
			String json = lib.get_json_contents("check_member_number", params, false, true);
			return make_json(json);
		} 
		// End check member number info function

		
		// company search results
		//
		public JSONObject company_search(String searchstring) throws JSONException
		{
			String[] params = new String[1];
			params[0]=searchstring;
			
			String json = lib.get_json_contents("company_search", params, false, false);
			return make_json(json);
		}
		// End company search results function

		//
		// lookup a company tree
		//
		public JSONObject company_details( String treeid) throws JSONException
		{
			String[] params = new String[1];
			params[1] = treeid;
			String json = lib.get_json_contents("company_details", params, true, false);
			return make_json(json);
		}
		// End company lookup function
		public JSONObject company_list( String limit, String pageno, String date) throws JSONException
		{ 
			String[] params = new String[3];
			params[0]=limit;
			params[1]=pageno;
			params[2]=date;
	       
			String json = lib.get_json_contents("company_list", params, false, false);
			return make_json(json);
		} 
		
		public JSONObject call_start(String node_id, String company_phone_number) throws JSONException{
			String[] params = new String[2];
			params[0] = node_id;
			params[1] = company_phone_number;
			String json = lib.get_json_contents("call_start", params, false, false);
			return make_json(json);
		}
		
		public JSONObject call_cancel(String session_id) throws JSONException{
			String[] params = new String[1];
			params[0] = session_id;
			String json = lib.get_json_contents("call_cancel", params, false, false);
			return make_json(json);
		}
		
		public JSONObject call_status(String session_id) throws JSONException{
			String[] params = new String[1];
			params[0] = session_id;
			String json = lib.get_json_contents("call_status", params, false, false);
			return make_json(json);
		}
		
		//throws a JSONException if it is given a bad string as input
		private JSONObject make_json(String input) throws JSONException{	
			JSONObject output = new JSONObject(input);		
			return output;
		}
		}	