package com.android.fonolo;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class fonolo_library implements private_constants{
	private static String auth_key = AUTH_KEY; //Stored in the private_constants interface!
	private static String response_server = "https://json-rpc.live.fonolo.com"; //provided in fonolo API
	private static String version = "0.1";

	
	
	private static String make_JSON_req(String method, String[] params){
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
	
	
	public static String get_json_contents(String method, String[] params, String user, String pass){
		PostMethod postMethod = new PostMethod(response_server); 
		String output = "";
		String request_string = make_JSON_req(method,params);
		postMethod.setRequestHeader("Content-Type", "application/json");
		postMethod.setRequestHeader("X-Fonolo-Auth", auth_key);
		postMethod.setRequestHeader("X-Fonolo-Username", user);
		postMethod.setRequestHeader("X-Fonolo-Password", pass);
		postMethod.setRequestBody(request_string);
		
		
		HttpClient client = new HttpClient();
		try {
			client.executeMethod(postMethod);
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
	//returns a JSON string from the server
	public static String get_json_contents(String method, String[] params){
		PostMethod postMethod = new PostMethod(response_server); 
		String output = "";
		String request_string = make_JSON_req(method,params);
		postMethod.setRequestHeader("Content-Type", "application/json");
		postMethod.setRequestHeader("X-Fonolo-Auth", auth_key);
		postMethod.setRequestBody(request_string);
		
		HttpClient client = new HttpClient();
		try {
			client.executeMethod(postMethod);
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
