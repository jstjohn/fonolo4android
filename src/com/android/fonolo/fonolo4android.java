package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author Craig Gardner, John St. John, Abdul Binrasheed
 * Last updated February 2009
 * 
 * This is the code associated with the logic of the login page of our project.
 * This page is displayed until the user enters a successful fonolo username
 * and password. The username and password will be bundled up on this page and 
 * forwarded to subsequent classes.
 *
 */
public class fonolo4android extends Activity implements private_constants, OnClickListener {
	TextView output;
	TextView user;
	TextView pass;
	
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        output = (TextView)this.findViewById(R.id.output);
        user = (TextView)this.findViewById(R.id.user_field);
        pass = (TextView)this.findViewById(R.id.pass_field);

        View go_button = this.findViewById(R.id.go_button);
        go_button.setOnClickListener(this);
        View help_button = this.findViewById(R.id.help_button);
        help_button.setOnClickListener(this);
        }
    	    	
    	public void onClick(View v){
    		switch (v.getId()){
    		case R.id.go_button:
	    		String uname = user.getText().toString(); 
	    		String passwd = pass.getText().toString();
	    		int code = 0;
	    		
		        if((uname.equals(""))||(passwd.equals(""))){
		        	//output to message class
		        	Intent i = new Intent(this, message.class);
		        	String message = "Please input a valid username and password";
		        	Bundle extras = new Bundle();
		        	extras.putString("message", message);
		        	i.putExtras(extras);
		        	startActivity(i);
		        }
		        else{
			        JSONObject json_result;
			        String result = "";
					try {
						//checking for valid user/pass
						json_result = communication.check_member(uname, passwd);
						JSONObject json_resp = json_result.getJSONObject("result");
						JSONObject json_head = json_resp.getJSONObject("head");
						String message = json_head.getString("response_message");
						code = json_head.getInt("response_code");
						result += message;
											
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
					//gets user to the main screen
			        //output.setText(result);
			        if(code >= 200 && code <= 299){
			        	//if valid user
			        	Intent i = new Intent(this, home.class);
			        	//bundling up data to pass to future classes
			        	Bundle extras = new Bundle();
			        	//each method will bundle the user/pass like this
			        	extras.putString("user", uname);
			        	extras.putString("pass", passwd);		        	
			    		i.putExtras(extras);
			    		startActivity(i);
			        }
			        else{
			        	//else error
			        	Intent i = new Intent(this, message.class);
			        	String message = result;
			        	Bundle extras = new Bundle();
			        	extras.putString("message", message);
			        	i.putExtras(extras);
			        	startActivity(i);
			        }
		        }
		        break;
    		case R.id.help_button:
    			Intent i = new Intent(this, help.class);
    			//bundled message for help class
            	String help_message = "On this screen you will input your username and password " +
            			"that you set up on the fonolo website(www.fonolo.com)";
            	Bundle extras = new Bundle();
            	extras.putString("content", help_message);
            	i.putExtras(extras);
            	startActivity(i);
            	break;
    		}
    	}
}