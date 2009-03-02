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
public class eula extends Activity{
	TextView eula_content;
	String content;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eula);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,  
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

		
		eula_content = (TextView)this.findViewById(R.id.eula_content);
		//getting bundled content
		Bundle extras = getIntent().getExtras();
		content = extras.getString("content");
		//displaying bundled content
		eula_content.setText(content);
	}
}