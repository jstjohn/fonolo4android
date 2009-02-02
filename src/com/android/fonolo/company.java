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
	
	 /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.company);
    	
    	//copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		//end copy---------------------------------------
        
		
		//Strictly for test purposes!
		String id = extras.getString("id");
		String json = extras.getString("json");
		id += json;
		
        TextView tv = new TextView(this);
        tv.setText(id);
        setContentView(tv);
    }
}