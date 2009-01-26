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
		public JSONObject get_check_member( String username, String password) throws JSONException
		{
			String[] params = new String[2];
			params[0]=username;
			params[1]=password;
	       String json = lib.get_json_contents("check_member", params, false, true);
			return make_json(json);
		} 
		// End check member info function

		public void get_check_member_number( String username, String password, String phone)
		{ 
			String[] params = new String[3];
			params[0]=username;
			params[1]=password;
			params[2]=phone;
	       
			lib.get_json_contents("check_member_number", params, false, false);
		} 
		// End check member number info function

		
		// company search results
		//
		public void get_search_companies(String searchstring)
		{
			String[] params = new String[1];
			params[0]=searchstring;
			
			lib.get_json_contents("company_search", params, false, false);
		}
		// End company search results function

		//
		// lookup a company tree
		//
		public void get_lookup_company( String treeid)
		{
	
		}
		// End company lookup function
		public void get_company_list( String limit, String pageno, String date)
		{ 
			String[] params = new String[3];
			params[0]=limit;
			params[1]=pageno;
			params[2]=date;
	       
			lib.get_json_contents("company_list", params, false, false);
		} 
		
		//throws a JSONException if it is given a bad string as input
		private JSONObject make_json(String input) throws JSONException{	
			JSONObject output = new JSONObject(input);		
			return output;
		}
		}	