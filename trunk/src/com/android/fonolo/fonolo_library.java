package com.android.fonolo;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;





public class fonolo_library implements private_constants{
	private String auth_key = AUTH_KEY; //Stored in the private_constants interface!
	private String response_server = "https://json-rpc.live.fonolo.com"; //provided in fonolo API
	private String version = "0.1";
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
	
	private String make_JSON_req(String method, String[] params){
//make the JSON content reques
		//formulate the params
		String p = "[ ";
		for(int i = 0; i < params.length; i++){		
			p += "\"";
			p += params[i];
			p += '"';
			if(i != params.length -1){
				p += ", ";
			}
		}
		p+=" ]";
		String c = "{ \"version\": \""+version+"\", \"method\": \""+method+"\", \"params\": "+p+" }";
		return c;
	}
	
	
	
	//returns a JSON string from the server
	public String get_json_contents(String method, String[] params, Boolean as_array, Boolean no_login){
		PostMethod postMethod = new PostMethod(response_server); 
		String output = "";
		String request_string = make_JSON_req(method,params);
		postMethod.setRequestHeader("Content-Type", "application/json");
		postMethod.setRequestHeader("X-Fonolo-Auth", auth_key);
		if(no_login == false){
			postMethod.setRequestHeader("X-Fonolo-Username", user);
			postMethod.setRequestHeader("X-Fonolo-Password", pass);
		}
		postMethod.setRequestBody(request_string);
		
		HttpClient client = new HttpClient();
		try {
			output += postMethod.getResponseBodyAsString();
			 
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return output;
		
	}
	
	

}
