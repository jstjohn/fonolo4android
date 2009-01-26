package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class fonolo4android extends Activity implements private_constants, OnClickListener {
	TextView output;
	TextView user;
	TextView pass;
	Button go;
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        output = (TextView)this.findViewById(R.id.output);
        user = (TextView)this.findViewById(R.id.user_field);
        pass = (TextView)this.findViewById(R.id.pass_field);
        go = (Button)this.findViewById(R.id.go_button);
        go.setOnClickListener(this);        
        }
    	    	
    	public void onClick(View v){
    		String uname = user.getText().toString(); 
    		String passwd = pass.getText().toString();
    		
	        if((uname.equals(""))||(passwd.equals(""))){
	        	output.setText("Please input a valid username and password.");
	        }
	        else{
	        	communication com = new communication();
	        	com.set_member_info(uname, passwd);
		        JSONObject json_result;
		        String result = "";
				try {
					json_result = com.check_member(uname, passwd);
					JSONObject json_resp = json_result.getJSONObject("result");
					JSONObject json_head = json_resp.getJSONObject("head");
					String message = json_head.getString("response_message");
					result += message;
										
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//this is ugly but it works
				//gets user to the main screen
		        output.setText(result);
		        if(output.length() == 16){
		        	Intent i = new Intent(this, home.class);
		    		startActivity(i);
		        }
	        }
    	}
}