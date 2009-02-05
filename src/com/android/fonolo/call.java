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
	TextView phone;
	String id = "";
	
    /** Called when the activity is first created. */

    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.call);
        
        TextView output = (TextView)this.findViewById(R.id.output);
        
        //copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		//end copy---------------------------------------
		
		id = extras.getString("id");
		String node_name = extras.getString("node_name");
		String company_name = extras.getString("company_name");
		
		String outMessage = "Placing call to: "+company_name+"\n";
		outMessage += "Destination: "+node_name+"\n";
		outMessage += "Node ID: "+id;
		
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
				first3 = raw_phone.substring(1,3);
				next3 = raw_phone.substring(4,6);
				final4 = raw_phone.substring(7,10);
			}else{
				first3 = raw_phone.substring(0,2);
				next3 = raw_phone.substring(3,5);
				final4 = raw_phone.substring(6,9);
			}
			
			try {
					JSONObject result = communication.check_member_number(uname, passwd, first3+"-"+next3+"-"+final4);
					int response_code = result.getJSONObject("result").getJSONObject("head").getInt("response_code");
					
					if(response_code >= 200 && response_code < 300){
						JSONObject call_result = communication.call_start(id, first3+"-"+next3+"-"+final4, uname, passwd);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
	}
}