package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author Craig Gardner, John St. John, Abdul Binrasheed
 * Last updated February 2009
 * 
 * This is the code associated with the logic of the login page of our project.
 * This page is displayed until the user enters a successful fonolo username
 * and password.
 *
 */
public class fonolo4android extends Activity implements private_constants, OnClickListener {
	private storage_get_set mDbHelper;
	
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		mDbHelper = new storage_get_set(this);
		mDbHelper.open();
		Cursor e = mDbHelper.fetchEula();
		Cursor c = mDbHelper.fetchLogin();
		startManagingCursor(c);
		startManagingCursor(e);
		int eula_column = e.getColumnIndex(storage_get_set.KEY_EULA);
		if(e.moveToFirst()){
			int eula = e.getInt(eula_column);
			if (eula == 0){
				//go to EULA page
			}
		}
		if(!c.equals(null)){
			if(c.getCount() == 0){
				//send the person to the user settings page because we have no info
				Intent i = new Intent(this, settings.class);
				startActivity(i);
			}else{
				Intent i = new Intent(this, home.class);
				startActivity(i);
			}
		}else{
			Intent i = new Intent(this, settings.class);
			startActivity(i);
		}
    }
    	   // Setup the action caused by buttons listener 	
    public void onClick(View v){  
    }
}