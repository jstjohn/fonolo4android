package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Abdul Binrasheed
 * Last update February 2009
 * 
 * The communication class has methods that represent every method in the fonolo
 * API. The communication class works by sending the method requests through to 
 * fonolo_library.java which communicates with the fonolo server and sends the 
 * results back to this class. This class then takes the JSON response which is 
 * in string format and parses it into a JSONObject which is what gets returned.
 * JSONObjects provide a very clean and quick way to get information out of JSON
 * strings.
 *
 */

public class communication{

		/**
		 * Check member is the function which takes the user name and the password and check if the
		 * account is exist and activated. 
		 * The function takes the username and the password as strings, and store them into a
		 * string array. the first element is the username, and the second element is the password.
		 * Also, we need to pass the function name as a parameter in therequest.
		 * The return is the "check_member" JSON request that going to be pass through fonolo_library 
		 * to the fonolo DB.
		 */
		public static JSONObject check_member( String username, String password) throws JSONException
		{
			String[] params = new String[2];
			params[0]=username;
			params[1]=password;
	       String json = fonolo_library.run("check_member", params);
			return make_json(json);
		} 
		// End check member info function

		/**
		 * Check member number is the function which takes the user name, the password and the
		 * member phone number, and check if the phone number is the one store under the user account.
		 * The function takes the username, the password and the phone number as strings, and store 
		 * them into a string array. the first element is the username,the second element is the
		 *  password, and the third element is the phone number.
		 *  Also, we need to pass the function name as a parameter in the JSON request.
		 * The return is the "check_member_number" JSON request that going to be pass through 
		 * fonolo_library to the fonolo DB. 
		 */
	
		
		public static JSONObject check_member_number( String username, String password, String phone) throws JSONException
		{ 
			String[] params = new String[3];
			params[0]=username;
			params[1]=password;
			params[2]=phone;
	       
			String json = fonolo_library.run("check_member_number", params);
			return make_json(json);
		} 
		// End check member number info function

		
		// company search results

		/**
		 * Company search is the function which takes a search word, the user name and the password. 
		 * The function takes the the search word, the username, the password as strings, and store 
		 * the search word in a single element string array, and pass the username and the password as 
		 * a parameters along with the array.
		 *  Also, we need to pass the function name as a parameter in the request.
		 * The return is the "company_search" JSON request that going to be pass through fonolo_library 
		 * to the fonolo DB.		
		 */
		
		public static JSONObject company_search(String searchstring, String user, String pass) throws JSONException
		{
			String[] params = new String[1];
			params[0]=searchstring;
			
			String json = fonolo_library.run("company_search", params, user, pass);
			return make_json(json);
		}
		// End company search results function

		//
		// lookup a company 
		/**
		 * Company_details is the function which takes the company id, the user name and the password. 
		 * The function takes the the company id, the username, the password as strings, and store 
		 * the company id in a single element string array, and pass the username and the password as 
		 * a parameters along with the array.
		 *  Also, we need to pass the function name as a parameter in the request.
		 * The return is the "company_details" JSON request that going to be pass through fonolo_library 
		 * to the fonolo DB.		
		 */
		

		public static JSONObject company_details( String treeid, String user, String pass) throws JSONException
		{
			String[] params = new String[1];
			params[0] = treeid;
			String json = fonolo_library.run("company_details", params, user, pass);
			return make_json(json);
		}
		// End company lookup function
		
		/** ""This function is currently not in use, but it's going to be used in the next copy of the software"" 
		
		 * Company list is the function that list all the companies that stored in the fonolo DB.
		 * this function takes the "limit number" which is the number of companies that you want to display 
		 * per page. also, it takes the "page number" which is the number for the page that you are getting
		 * the companies list from. Also, you can pass the date which is the date that you want to list
		 * the companies that added to the DB after this date. The function also takes the username and password. 
		 * The function will store the "limit", the "page number", and the date in a string array.
		 * Then, we will pass the function name, the array, the username, and the password as a parameter in the request.
		 * The return is the "company_list" JSON request that going to be pass through fonolo_library 
		 * to the fonolo DB.		
		 */
		
		public static JSONObject company_list( String limit, String pageno, String date, String user, String pass) throws JSONException
		{ 
			String[] params = new String[3];
			params[0]=limit;
			params[1]=pageno;
			params[2]=date;
	       
			String json = fonolo_library.run("company_list", params, user, pass);
			return make_json(json);
		} 
		
		
		/**
		 * Call start is the function which takes the user name, the password, the user phone number,
		 * and the node id which is the id that has the direct to get to the option from the phone tree.
		 * The function takes the node id and the user phone id as strings, and store them into a
		 * string array. the first element is the node id, and the second is the user phone number.
		 * Then, we pass the function name, the array, the username, and the password as a parameters in the request.
		 * The return is the "caal_start" JSON request that going to be pass through fonolo_library 
		 * to the fonolo to make the call.
		 */
		public static JSONObject call_start(String node_id, String user_phone_number, String user, String pass) throws JSONException{
			String[] params = new String[2];
			params[0] = node_id;
			params[1] = user_phone_number;
			String json = fonolo_library.run("call_start", params, user, pass);
			return make_json(json);
		}
		
		/**
		 * Call Cancel is the function which takes the user name, the password, and the session id which
		 * is the id that can be found in the respond  from call start request.
		 * The function takes the session id and store it in a single element string array. 
		 * Then, we pass the function name, the array, the username, and the password as a parameters in the request.
		 * The return is the "caal_cancel" JSON request that going to be pass through fonolo_library 
		 * to the fonolo to cancel an existing call.
		 */
		public static JSONObject call_cancel(String session_id, String user, String pass) throws JSONException{
			String[] params = new String[1];
			params[0] = session_id;
			String json = fonolo_library.run("call_cancel", params, user, pass);
			return make_json(json);
		}
		

		/**
		 * Call status is the function which takes the user name, the password, and the session id which
		 * is the id that can be found in the respond  from call start request.
		 * The function takes the session id and store it in a single element string array. 
		 * Then, we pass the function name, the array, the username, and the password as a parameters in the request.
		 * The return is the "caal_cancel" JSON request that going to be pass through fonolo_library 
		 * to the fonolo to check the status of an existing call.
		 */
		public static JSONObject call_status(String session_id, String user, String pass) throws JSONException{
			String[] params = new String[1];
			params[0] = session_id;
			String json = fonolo_library.run("call_status", params, user, pass);
			return make_json(json);
		}
		
		//throws a JSONException if it is given a bad string as input
		private static JSONObject make_json(String input) throws JSONException{	
			JSONObject output = new JSONObject(input);		
			return output;
		}
		}	