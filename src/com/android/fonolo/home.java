package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class home extends Activity implements OnClickListener{
	TextView user;
	TextView pass;
	TextView output;
	
	//copy into all classes-----------------------------
	String uname = "";
	String passwd = "";
	communication com = new communication();
	//end copy------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
        user = (TextView)this.findViewById(R.id.user_field);
        pass = (TextView)this.findViewById(R.id.pass_field);
        output = (TextView)this.findViewById(R.id.home_output);
		
		View logout_button = this.findViewById(R.id.logout_button);
		logout_button.setOnClickListener(this);
		View help_button = this.findViewById(R.id.help_button);
		help_button.setOnClickListener(this);
		View search_button = this.findViewById(R.id.search_button);
		search_button.setOnClickListener(this);
		View list_all_button = this.findViewById(R.id.list_all_button);
		list_all_button.setOnClickListener(this);
		View favorites_button = this.findViewById(R.id.favorites_button);
		favorites_button.setOnClickListener(this);
	}

	@Override
/*	public void onClick(View v) {
		switch (v.getId()){
		case R.id.logout_button:
			Intent i = new Intent(this, fonolo4android.class);
			startActivity(i);
			break;
		//more buttons
		}		
	}*/
	public void onClick(View v){

        JSONObject json_result;
        String result = "";
        
        //copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		com.set_member_info(uname, passwd);
		//end copy---------------------------------------
		
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
        output.setText(result);    
	}
}