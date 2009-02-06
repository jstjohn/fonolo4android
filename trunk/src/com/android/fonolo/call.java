package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

//edit class to reflect UI
public class call extends Activity implements OnClickListener, private_constants {
	
	//copy into all classes-----------------------------
	String uname = "";
	String passwd = "";
	//end copy------------------------------------------
	TextView output;
	TextView phone;
	String id = "";
	String outMessage;
	
    /** Called when the activity is first created. */

    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.call);
        
        output = (TextView)this.findViewById(R.id.output);
        phone = (TextView)this.findViewById(R.id.my_phone_number);
        
        //copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		//end copy---------------------------------------
		
		id = extras.getString("id");
		String node_name = extras.getString("node_name");
		String company_name = extras.getString("company_name");
		
		View call_button = this.findViewById(R.id.place_call);
        call_button.setOnClickListener(this);
		
	    outMessage = "Company Name: "+company_name+"\n";
		outMessage += "Destination: "+node_name+"\n";
		
		output.setText(outMessage);

    }


	public void onClick(View v) {
		switch (v.getId()){
		case R.id.place_call:
			String raw_phone = phone.getText().toString();
			String first3 = "";
			String next3 = "";
			String final4 = "";
			if(raw_phone.length() == 11){
				first3 = raw_phone.substring(1,4);
				next3 = raw_phone.substring(4,7);
				final4 = raw_phone.substring(7,11);
			}else{
				first3 = raw_phone.substring(0,3);
				next3 = raw_phone.substring(3,6);
				final4 = raw_phone.substring(6,10);
			}
			
			try {
					JSONObject result = communication.check_member_number(uname, passwd, first3+"-"+next3+"-"+final4);
					int response_code = result.getJSONObject("result").getJSONObject("head").getInt("response_code");
					
					if(response_code >= 200 && response_code < 300){
						outMessage += "\nMember phone number valid: "+first3+"-"+next3+"-"+final4;
						output.setText(outMessage);
						JSONObject call_result = communication.call_start(id, first3+"-"+next3+"-"+final4, uname, passwd);
						outMessage += "\n"+call_result.getJSONObject("result").getJSONObject("head").getString("response_message");
						output.setText(outMessage);
					}else{
						outMessage += "\nMember phone number invalid: "+first3+"-"+next3+"-"+final4;
						outMessage += "\nPlease enter the same 10 digit phone number on your fonolo account. ";
						outMessage += "The correct format should be 555 555 5555 (but no spaces). Please enter your correct number and try again.";
						output.setText(outMessage);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
	}
}