package com.android.fonolo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

//edit class to reflect UI
public class company extends Activity {
	
	//copy into all classes-----------------------------
	String uname = "";
	String passwd = "";
	//end copy------------------------------------------
	TextView output;
	TextView company_name;
	
	 /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.company);
    	
    	output = (TextView)this.findViewById(R.id.output);
    	company_name = (TextView)this.findViewById(R.id.company_name_text);
    	
    	//copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		//end copy---------------------------------------
        
		
		//Strictly for test purposes!
		String id = extras.getString("id");
		String name = extras.getString("company_name");
		String json = extras.getString("json");
		id += json;
		company_name.setText(name);
		output.setText(id);
		
//        TextView tv = new TextView(this);
//        tv.setText(id);
//        setContentView(tv);
    }
}