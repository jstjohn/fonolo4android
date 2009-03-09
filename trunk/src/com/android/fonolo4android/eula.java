package com.android.fonolo4android;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		Button eula_button = (Button)this.findViewById(R.id.accept_eula_button);
		eula_button.setOnClickListener(this);
		mDbHelper = new storage_get_set(this);
		mDbHelper.open();
		Cursor eula = mDbHelper.fetchEula();
		int eula_column = eula.getColumnIndex(storage_get_set.KEY_EULA);
		if(eula.moveToFirst()){
			int eula_val = eula.getInt(eula_column);
			if(eula_val == 0){
				//base case, leave code as is
			}else{
				//Eula has been accepted so we need to change the button from accept to ok
				eula_button.setText("OK");
				eula_button.setId(211);
			}
		}
		message = "By accepting you agree not to hold the developers responsible for " +
				"damages caused while using this software. Additionally, as the developers " +
				"are not affiliated with fonolo, you agree that said developers are not " +
				"responsible for fonolo's actions.";
		
		//for inserting the eula content into the page
		eula_content = (TextView)this.findViewById(R.id.eula_content);
		eula_content.setText(message);
	}

	public void onClick(View arg0) {
		switch (arg0.getId()){
		case 211:
			Intent q = new Intent(this, fonolo4android.class);
			startActivity(q);
			break;
		case R.id.accept_eula_button:
			mDbHelper.setEulaTrue();
			Intent i = new Intent(this,settings.class);
			startActivity(i);
			break;
		}
	}
}