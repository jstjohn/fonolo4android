package com.android.fonolo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

//edit class to reflect UI
public class call extends Activity {
	
	//copy into all classes-----------------------------
	String uname = "";
	String passwd = "";
	//end copy------------------------------------------
	
	
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
		
		String id = extras.getString("id");
		String node_name = extras.getString("node_name");
		String company_name = extras.getString("company_name");
		
		String outMessage = "Placing call to: "+company_name+"\n";
		outMessage += "Destination: "+node_name+"\n";
		outMessage += "Node ID: "+id;
		
		output.setText(outMessage);

    }
}