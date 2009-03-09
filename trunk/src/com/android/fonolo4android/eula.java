package com.android.fonolo4android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author Craig Gardner, John St. John
 * Last updated February 2009
 * 
 * This is the java class for our eula page. this page contains
 * the End User License Agreement.
 */
public class eula extends Activity implements OnClickListener{
	TextView eula_content;
	String message;	
	private storage_get_set mDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eula);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,  
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		View eula_button = this.findViewById(R.id.accept_eula_button);
		eula_button.setOnClickListener(this);
		mDbHelper = new storage_get_set(this);
		mDbHelper.open();
		
		message = "By accepting you agree not to hold the developers responsible for damages " +
				"caused while using this software. Additionally, as the developers are not affiliated with " +
				"fonolo, you agree that said developers are not responsible for fonolo's actions.";
		
		eula_content = (TextView)this.findViewById(R.id.eula_content);
		eula_content.setText(message);
		//getting bundled content
		//Bundle extras = getIntent().getExtras();
		//content = extras.getString("content");
		//displaying bundled content
		//eula_content.setText(content);
	}

	public void onClick(View arg0) {
		switch (arg0.getId()){
		case R.id.accept_eula_button:
			mDbHelper.setEulaTrue();
			Intent i = new Intent(this,settings.class);
			startActivity(i);
			break;
		}
			
		
	}
}