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
 * This is the java class for our help page. It is a dynamic page
 * and provides help information based on where you are in the
 * program when the help button is pressed.
 * 
 */
public class help extends Activity{
	TextView help_content;
	String content;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//start help page
		setContentView(R.layout.help);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,  
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

		
		help_content = (TextView)this.findViewById(R.id.help_content);
		//getting bundled content
		Bundle extras = getIntent().getExtras();
		content = extras.getString("content");
		//displaying bundled content
		help_content.setText(content);
	}
}