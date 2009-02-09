package com.android.fonolo;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 
 * @author Craig Gardner
 * Last updated February 2009
 * 
 * This is the java class for our message page used for error messages.
 * An error message will be bundled in one class and passed to this class
 * for display.
 *
 */
public class message extends Activity{
	TextView message_content;
	String message;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,  
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		message_content = (TextView)this.findViewById(R.id.message_content);
		//getting bundled content
		Bundle extras = getIntent().getExtras();
		message = extras.getString("message");
		//displaying bundled content
		message_content.setText(message);		
	}
}