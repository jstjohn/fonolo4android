package com.android.fonolo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class help extends Activity{
	TextView help_content;
	String content;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		help_content = (TextView)this.findViewById(R.id.help_content);
		
		Bundle extras = getIntent().getExtras();
		content = extras.getString("content");
		
		help_content.setText(content);
	}
}