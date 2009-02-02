package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.Button;
import android.widget.TextView;

public class fonolo4android extends Activity implements private_constants, OnClickListener {
	TextView output;
	TextView user;
	TextView pass;
	TextView help_content;
	//Button go, help;
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        help_content = (TextView)this.findViewById(R.id.help_content);
        output = (TextView)this.findViewById(R.id.output);
        user = (TextView)this.findViewById(R.id.user_field);
        pass = (TextView)this.findViewById(R.id.pass_field);
//        go = (Button)this.findViewById(R.id.go_button);        
//        go.setOnClickListener(this);
//        help = (Button)this.findViewById(R.id.help_button);
//        help.setOnClickListener(this);
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
		        	output.setText("Please input a valid username and password.");		        	
		        }
		        else{
			        JSONObject json_result;
			        String result = "";
					try {
						json_result = communication.check_member(uname, passwd);
						JSONObject json_resp = json_result.getJSONObject("result");
						JSONObject json_head = json_resp.getJSONObject("head");
						String message = json_head.getString("response_message");
						code = json_head.getInt("response_code");
						//if(code >= 200 && code < 300){
						//	result += Integer.toString(code);
							//JSONObject session = com.call_start("fd1b39133c5f2c749fdab78b012cae2d", "888-619-8622");
							
						//}
						result += message;
											
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
					//gets user to the main screen
			        output.setText(result);
			        if(code >= 200 && code <= 299){
			        	Intent i = new Intent(this, home.class);
			        	Bundle extras = new Bundle();
			        	extras.putString("user", uname);
			        	extras.putString("pass", passwd);		        	
			    		i.putExtras(extras);
			    		startActivity(i);
			        }
		        }
		        break;
    		case R.id.help_button:
    			Intent j = new Intent(this, help.class);
        		startActivity(j);
        		break;
    		}
    	}
}