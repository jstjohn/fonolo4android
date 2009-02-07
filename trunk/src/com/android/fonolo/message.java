package com.android.fonolo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 
 * @author Craig Gardner
 * Last updated February 2009
 * 
 * ***Add description here***
 *
 */
public class message extends Activity{
	TextView message_content;
	String message;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		
		message_content = (TextView)this.findViewById(R.id.message_content);
		
		Bundle extras = getIntent().getExtras();
		message = extras.getString("message");
		
		message_content.setText(message);		
	}
}