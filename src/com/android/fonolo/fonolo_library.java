package com.android.fonolo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONStringer;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.util.Log;




public class fonolo_library implements private_constants{
	private String auth_key = AUTH_KEY; //Stored in the private_constants interface!
	private String response_server = "https://json-rpc.live.fonolo.com/"; //provided in fonolo API
	private String version = "1.1";
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
		String c = " { \"version\" : \""+version+"\", \"method\" : \""+method+"\", \"params\" : "+p+" } ";
		return c;
	}
	
	
	
	//returns a JSON string from the server
	public String get_json_contents(String method, String[] params, Boolean as_array, Boolean no_login){
		String output = "";
		//return an error message
		if(auth_key.equals(null) || auth_key.length() != 32){
			System.err.println("Invalid Auth Key!");
		}
		
		no_login = true;
		
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
		//make the JSON content request
		
		
		String c = make_JSON_req(method,params);
		output += c;
		
		
		StringEntity my_content = new StringEntity(c);
		my_content.setContentType("content");
		
		//my_content.setContentEncoding("UTF-8");
		//my_content.setChunked(true);
		
		//BasicNameValuePair cont = new BasicNameValuePair("content", c);
		//UrlEncodedFormEntity entity = new UrlEncodedFormEntity(cont);
	
		HttpClient httpClient = new DefaultHttpClient();
        HttpPost request = new HttpPost(response_server);
        if(no_login == true){
        	request.setHeader("content-type", "application/json");
        	request.setHeader("X-Fonolo-Auth", auth_key);
        	//request.setHeader("content"," "+c);
        	request.setEntity(my_content);
        	request.setHeader("Content-Length", ""+my_content.getContentLength());
        }else{
        	request.setHeader("content-type", "application/json");
        	request.setHeader("X-Fonolo-Auth", auth_key);
        	request.setHeader("X-Fonolo-Username", user);
        	request.setHeader("X-Fonolo-Password",pass);
        	
        	//request.addHeader("content", my_content);
        	request.setEntity(my_content);
        	//request.setHeader("Content-Length", Long.toString(my_content.getContentLength()));
        }
       // maybe I need to throw content into a local context
       //HttpContext localContext = new BasicHttpContext();
       //localContext.setAttribute(ClientContext., c);
        
        //HttpResponse response = httpClient.execute(request, localContext);
        HttpResponse response = httpClient.execute(request);
        StatusLine status = response.getStatusLine();
        Header[] headers = response.getAllHeaders();
        Header[] reqheaders = request.getAllHeaders();
        HttpEntity entity = response.getEntity();
        //response.setEntity(my_content);
        //response = httpClient.execute(request);

  

        
        if (status.getStatusCode() != HttpStatus.SC_OK) {
        	
        	InputStream is = response.getEntity().getContent();
        	BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                output += line.toString();
            }
            output += "\nContent Length: ";
            output += entity.getContentLength();
            output += "\nMyContent Length: ";
            output += my_content.getContentLength();
            output += "\n it NoWork!";
//            output += "\n Request Body:";
//            InputStream is2 = request.getEntity().getContent();
//            BufferedReader rd2 = new BufferedReader(new InputStreamReader(is));
//            String line2;
//            while ((line2 = rd2.readLine()) != null) {
//                output += line2.toString();
//            }
            output += "\n Request Headers:";
            for(int i = 0; i < reqheaders.length; i++){
            	output += "\n"+reqheaders[i].getName();
            	output += ":" + reqheaders[i].getValue();
            }
            output += "\n Returned Headers: ";
            for(int i = 0; i < headers.length; i++){
            	output += "\n"+headers[i].getName();
            	output += ":" + headers[i].getValue();
            }
            rd.close();
//            rd2.close();
//            is2.close();
        	is.close();
        	
        } else {
        	//connection success!
        	InputStream is = response.getEntity().getContent();
        	BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                output += line.toString();
            }
            output += "\nContent Length: ";
            output += entity.getContentLength();
            output += "\nMyContent Length: ";
            output += my_content.getContentLength();
            output += "\n it Works!";
//            output += "\n Request Body:";
//            InputStream is2 = request.getEntity().getContent();
//            BufferedReader rd2 = new BufferedReader(new InputStreamReader(is));
//            String line2;
//            while ((line2 = rd2.readLine()) != null) {
//                output += line2.toString();
//            }
            output += "\n Request Headers:";
            for(int i = 0; i < reqheaders.length; i++){
            	output += "\n"+reqheaders[i].getName();
            	output += ":" + reqheaders[i].getValue();
            }
            output += "\n Returned Headers: ";
            for(int i = 0; i < headers.length; i++){
            	output += "\n"+headers[i].getName();
            	output += ":" + headers[i].getValue();
            }
            rd.close();
//            rd2.close();
//            is2.close();
        	is.close();
        }
        
	} catch(Exception e){
		e.printStackTrace();
		output += e.toString();
	}
		
		
		return test(method,params);
	}
	
	
	private String test(String method, String[] params){
		String output = "";
		
		try {
			String url = response_server;
//			Object[] p = new Object[3];
//			p[0] = 3;
//			p[1] = 0;
//			p[2] = "2008-12-15";
//            JSONStringer content = new JSONStringer()
//            	.object()
//            		.key("version")
//            		.value("1.1")
//            	.endObject()
//            	.object()
//            		.key("method")
//            		.value("company_list")
//            	.endObject()
//            	.array()
//            		.key("params")
//            		.value(p)
//            	.endArray();
            String request_string = "{\n"+
            		"\t\"version\": \"1.1\",\n"+
            		"\t\"method\": \"check_member\",\n"+
            		"\t\"params\": [\n"+
            		"\t\t\"username\", \"password\"\n"+
            		"\t]\n"+
            		"}";

            output += request_string;

            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("method", "POST");
            connection.setRequestProperty("content-type", "application/json");
            connection.setRequestProperty("X-Fonolo-Auth", auth_key);
            //connection.setRequestProperty("X-Fonolo-Username", user);
            //connection.setRequestProperty("X-Fonolo-Password",pass);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream out = connection.getOutputStream();
            out.write(request_string.getBytes("utf-8"));
            out.close();

            connection.connect();

            InputStream in = connection.getInputStream();
            BufferedReader i = new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = i.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
            }
            in.close();
            output += sb.toString();

//            Map<String, Object> result = null;
//            try {
//                    JSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
//                    result = (Map<String, Object>) reader.read(sb.toString());
//            } catch (Exception e) {
//                    throw new JsonRpcException("cannot decode json", e);
//            }
//            
//            if (result.get("error") != null) {
//                    throw new JsonRpcException(result.get("error").toString());
//            }
//
//            return result.get("result");

    } catch (Exception e) {
            e.printStackTrace();
    }

		return output;
	}

	
	
	

}
