package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

//Copyright 2008 fonolo. All Rights Reserved.
//
// +---------------------------------------------------------------------------+
// | fonolo Java API client                                                     |
// +---------------------------------------------------------------------------+
// | Copyright (c) 2008 fonolo.                                                |
// | All rights reserved.                                                      |
// |                                                                           |
// | Redistribution and use in source and binary forms, with or without        |
// | modification, are permitted provided that the following conditions        |
// | are met:                                                                  |
// |                                                                           |
// | 1. Redistributions of source code must retain the above copyright         |
// |    notice, this list of conditions and the following disclaimer.          |
// | 2. Redistributions in binary form must reproduce the above copyright      |
// |    notice, this list of conditions and the following disclaimer in the    |
// |    documentation and/or other materials provided with the distribution.   |
// |                                                                           |
// | THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR      |
// | IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES |
// | OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.   |
// | IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,          |
// | INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT  |
// | NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, |
// | DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY     |
// | THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT       |
// | (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF  |
// | THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.         |
// +---------------------------------------------------------------------------+


public class fonolo_library implements private_constants{
	private String auth_key = AUTH_KEY; //Stored in the private_constants interface!
	private String response_server = "https://json-rpc.live.fonolo.com/"; //provided in fonolo API
	private String version = "0.01";
	private String user = "";
	private String pass = "";
	
	//empty constructor class. Probably not needed for this implementation
	public void fonolo_library(){
		
	}
	
	//public setter function for the user name and password
	public void set_member_info(String uname, String passwd){
		user = uname;
		pass = passwd;
	}
	
	//returns a JSON string from the server
	public String get_json_contents(String method, String params, Boolean as_array, Boolean no_login){
		String response = "";
		//return an error message
		if(auth_key.equals(null) || auth_key.length() != 32){
			System.err.println("Invalid Auth Key!");
		}
		

//		JSONObject content = new JSONObject();
//		
//		//JSONObjects throw JSONExceptions so we need to handle that stuff
//		try {
//			content.put("version", version);
//			content.put("method", method);
//			content.put("params", params);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		JSONObject options = new JSONObject();
//		JSONObject http = new JSONObject();
//		if(no_login == true){
//			try {
//				http.put("method", "POST");
//				http.put("header", "Content-Type: application/json");
//				http.put("X-Fonolo-AUTH", auth_key);
//				http.put("content", content);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}else{
//			try {
//				http.put("method", "POST");
//				http.put("header", "Content-Type: application/json");
//				http.put("X-Fonolo-Auth", auth_key);
//				http.put("X-Fonolo-Username", user);
//				http.put("X-Fonolo-Password", pass);
//				http.put("content", content);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		//now we throw the http information into the options JSON array
//		try {
//			options.put("http", http);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		/**
		 * We need to use something like the following example code.
		 * Also we don't want a JSON request. we want an HTTP request, the response will be in JSON.
		 * and part of the http request will also be in JSON.
		 *  try {
        // Construct data
        String data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
        data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");
    
        // Send data
        URL url = new URL("http://hostname:80/cgi");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();
    
        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            // Process line...
        }
        wr.close();
        rd.close();
    } catch (Exception e) {
    }
		 */
	try{
		
	} catch(Exception e){
		
	}
		
		
		return response;
	}
	
	
	

}
