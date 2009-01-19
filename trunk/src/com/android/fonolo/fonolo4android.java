package com.android.fonolo;

import android.app.Activity;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        output = (TextView)this.findViewById(R.id.output);
        user = (TextView)this.findViewById(R.id.user);
        pass = (TextView)this.findViewById(R.id.pass);
        go = (Button)this.findViewById(R.id.go_button);
        go.setOnClickListener(this);        
        
        }

        
        //Just for testing purposes because I don't know how to use layouts
    	
    	public void onClick(View v){   
	        //TextView output = new TextView(this);
	        fonolo_library fonolo = new fonolo_library();
	        fonolo.set_member_info(uname,passwd);
	        String[] params = new String[3];
	        params[0] = "3";
	        params[1] = "0";
	        params[2] = "2008-1-1";
	        String result = fonolo.get_json_contents("company_list", params, false, false);
	        result = "Result:" + result;
	        output.setText(result);
	        //setContentView(output);
    	}
    

}