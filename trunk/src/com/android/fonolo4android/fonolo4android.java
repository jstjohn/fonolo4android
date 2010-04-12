package com.android.fonolo4android;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

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
public class fonolo4android extends Activity {
	private storage_get_set mDbHelper;
	
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.main);
        try{
        	//set up database
			mDbHelper = new storage_get_set(this);
			mDbHelper.open();
			Cursor e = mDbHelper.fetchEula();
			Cursor c = mDbHelper.fetchLogin();
			startManagingCursor(c);
			startManagingCursor(e);
			int eula_column = e.getColumnIndex(storage_get_set.KEY_EULA);
			if(e.moveToFirst()){
				//eula unseen or not accepted
				int eula = e.getInt(eula_column);
				if (eula == 0){//go to EULA page					
					Intent i = new Intent(this, eula.class);
					startActivity(i);
				}
				else if(!c.equals(null)){
					if(c.getCount() == 0){
						//send the person to the user settings page because we have no info
						Intent i = new Intent(this, settings.class);
						startActivity(i);
					}
					else{
						//go to the home page because we have info
						Intent i = new Intent(this, home.class);
						startActivity(i);
					}
				}			
			}
			else{//eula accepted				
				Intent i = new Intent(this, settings.class);
				startActivity(i);
			}
		}
        catch(Exception e3){//catch errors
			Intent i = new Intent(this, message.class);
			Bundle extras = new Bundle();
			String message = e3.getMessage();
			extras.putString("message", message);
			i.putExtras(extras);
			startActivity(i);
		}
    }
}