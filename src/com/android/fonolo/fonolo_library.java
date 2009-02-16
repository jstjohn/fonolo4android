package com.android.fonolo;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 
 * @author John St. John
 * Last updated February 2009
 * 
 * This class provides the lowest level communication with
 * the fonolo servers. This class formats the JSON request
 * string as required in version 1.1 build 5 of fonolo's API.
 * Since the fonolo API is still in beta as of my writing this,
 * it is likely that this code will eventually have to be
 * changed slightly to reflect updated specifications.
 * 
 */
public class fonolo_library extends Thread implements private_constants{
	private static String auth_key = AUTH_KEY; //Stored in the private_constants interface!
	private static String response_server = "https://json-rpc.live.fonolo.com"; //provided in fonolo API
	private static String version = "0.1";

	
	/**
	 * Note, we wrote this method to manually format JSON strings due to fonolo's
	 * requested strings varied slightly from the format output by Java's JSONObject
	 * 
	 * Makes a JSON string as formated in the examples provided in version 1.1
	 * build 5 of the fonolo API. This method may have to be updated as fonolo's
	 * specifications change
	 * 
	 * This method takes a String which is the name of the method you would like
	 * to execute on fonolo's servers. The next parameter is a string array of the
	 * arguments supplied to the method.
	 * 
	 * @param method
	 * @param params
	 * @return The custom formated JSON request string for Fonolo
	 * 
	 */
	private static String make_JSON_req(String method, String[] params){
		//make the JSON content request
		//formulate the params that passed from communication class.
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
	
	/**
	 * @see This method is to be used in requests to fonolo that require a user name
	 * and password to work properly. The methods that require this are outlined in 
	 * the fonolo API.
	 * 
	 * @param method- The name of the method you would like to execute on fonolo's servers
	 * @param params- The arguments that you would like to pass to the method
	 * @param user- The user name of the client
	 * @param pass- The password of the client
	 * @return The string returned by fonolo which follows JSON specifications. This
	 * string may be passed as an argument to a JSONObject constructor and as of version 1.1 build 5
	 * the JSONObject is able to parse the string properly into a JSON Object.
	 */
	@SuppressWarnings("deprecation")
	public static String run(String method, String[] params, String user, String pass){
		PostMethod postMethod = new PostMethod(response_server); 
		String output = "";
		String request_string = make_JSON_req(method,params);
		/**
		 * As of version 1.1 build 5 of the fonolo API, fonolo requires the following
		 * HTML headers to be set in all requests to their server that require user
		 * authentication.
		 */
		postMethod.setRequestHeader("Content-Type", "application/json");
		postMethod.setRequestHeader("X-Fonolo-Auth", auth_key);
		postMethod.setRequestHeader("X-Fonolo-Username", user);
		postMethod.setRequestHeader("X-Fonolo-Password", pass);
		
		/**
		 * The following deprecated method is the only one I found that
		 * successfully puts the JSON string as the body in an HTML post
		 * request in such a way that fonolo gets the message and responds.
		 * 
		 * This deprecated method is probably not ideal but it is what works 
		 * as of version 1.1 build 5 of the fonolo API.
		 * 
		 * @deprecated
		 */
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
	/**
	 * This method is analogous to the one above, but it is to be used to 
	 * invoke fonolo methods that do not require the username and password set
	 * in the header fields. See the fonolo API for more information on
	 * which methods do not require username and password headers.
	 * @param method
	 * @param params
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String run(String method, String[] params){
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
