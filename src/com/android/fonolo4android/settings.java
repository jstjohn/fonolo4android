package com.android.fonolo4android;

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
public class settings extends Activity implements private_constants, OnClickListener {
	TextView output;
	TextView user;
	TextView pass;
	TextView phone;
	//set up progress dialog
	ProgressDialog myProgressDialog = null;
	//set up database
	private storage_get_set mDbHelper;
	String first3 = "XXX";
	String next3 = "XXX";
	String final4 = "XXXX";
	
	// Need handler for callbacks to the UI thread
    final Handler mHandler = new Handler();
    int code;
    String result;
    String glob_uname;
    String glob_passwd;
    String glob_phone;

    // Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
        }	
    };
    private void updateResultsInUi() {
    	//gets user to the main screen
        if(code >= 200 && code <= 299){
        	Intent i = new Intent(this, home.class);
        	try{
        		mDbHelper.createLogin(glob_uname, glob_passwd, glob_phone);
        	}catch(Exception e){
        		mDbHelper.updateLogin(glob_uname, glob_passwd, glob_phone);
        	}
    		startActivity(i);
        }
        // if the account does not exists, show error message.
        else{
        	Intent i = new Intent(this, message.class);
        	String message = result;
        	Bundle extras = new Bundle();
        	extras.putString("message", message);
        	i.putExtras(extras);
        	startActivity(i);
        }
			
	}
    protected void startLongRunningOperation(final String uname, final String passwd, final String phone_num) {    	
        // Fire off a thread to do some work that we shouldn't do directly in the UI thread
        Thread t = new Thread() {
            public void run() {
            	/*
		         * send the typed info to check member function, and take the user to the home screen
		         * if the account is valid.
		         */
		        JSONObject json_result;
		        glob_uname = uname;
		        glob_passwd = passwd;
		        glob_phone = phone_num;
				try {
					json_result = communication.check_member_number(uname, passwd, phone_num);
					JSONObject json_resp = json_result.getJSONObject("result");
					JSONObject json_head = json_resp.getJSONObject("head");
					String message = json_head.getString("response_message");
					code = json_head.getInt("response_code");
					if(message.equals("")){
						message = "You have no internet connection!";
					}
					result = message;
										
				}
				catch (JSONException e) {
					//empty catch block
				}
				mHandler.post(mUpdateResults);
				myProgressDialog.dismiss(); 
	        }
        };
        t.start();
    }
	
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.settings);
        /*
         * Store the information from the UI into the variables.
         */
        output = (TextView)this.findViewById(R2.id.output);
        user = (TextView)this.findViewById(R2.id.user_field);
        pass = (TextView)this.findViewById(R2.id.pass_field);
        phone = (TextView)this.findViewById(R2.id.number_field);
        
        //set up database
		mDbHelper = new storage_get_set(this);
		mDbHelper.open();
		
		//get login information from database
		Cursor c = mDbHelper.fetchLogin();
		startManagingCursor(c);
		int uname_column = c.getColumnIndex(storage_get_set.KEY_UNAME);
        int pass_column = c.getColumnIndex(storage_get_set.KEY_PASS);
        int phone_column = c.getColumnIndex(storage_get_set.KEY_PHONE);
		if(!c.equals(null)){
			if(c.getCount() == 0){
				//do nothing, the person needs to enter this information
			}else{
				if(c.moveToFirst()){
					user.setText(c.getString(uname_column));
					pass.setText(c.getString(pass_column));
					phone.setText(c.getString(phone_column));
					//add phone part here too
				}
			}
		}
        
        //Create buttons
        View go_button = this.findViewById(R2.id.go_button);
        go_button.setOnClickListener(this);
        View help_button = this.findViewById(R2.id.help_button);
        help_button.setOnClickListener(this);
        View clear_button = this.findViewById(R2.id.clear_button);
        clear_button.setOnClickListener(this);
        View license_button = this.findViewById(R2.id.view_eula_button);
        license_button.setOnClickListener(this);
        }
    	// Setup the action caused by buttons listener 	
    	public void onClick(View v){  
    		switch (v.getId()){
    		case R2.id.go_button:
	    		String uname = user.getText().toString(); 
	    		String passwd = pass.getText().toString();
	    		String raw_phone = phone.getText().toString();

				// the following regular expression strips out
				// everything that isn't a digit in the person's text field
				raw_phone = raw_phone.replaceAll("\\D", "");
				String phone_num = "";

				// store the user phone number in the right format.
				if(raw_phone.length() == 11){//if the user entered the country code"1", ignore it.
					first3 = raw_phone.substring(1,4);
					next3 = raw_phone.substring(4,7);
					final4 = raw_phone.substring(7,11);
					phone_num = first3+"-"+next3+"-"+final4;
				}
				else if (raw_phone.length() == 10){
					first3 = raw_phone.substring(0,3);
					next3 = raw_phone.substring(3,6);
					final4 = raw_phone.substring(6,10);
					phone_num = first3+"-"+next3+"-"+final4;
				} else {
					phone_num = raw_phone;
				}				
	    		
	    		//Check if the the username, password fields are empty
		        if((uname.equals(""))||(passwd.equals(""))||(phone_num.equals(""))){		        	
		        	Intent i = new Intent(this, message.class);
		        	String message = "Please input a valid username, password and the " +
		        			"phone number registered with fonolo, in the format: XXX-XXX-XXXX";
		        	Bundle extras = new Bundle();
		        	extras.putString("message", message);
		        	i.putExtras(extras);
		        	startActivity(i);
		        }
		        /*
		         * send the typed info to check member function, and take the user to the home screen
		         * if the account is valid.
		         */
		        else{
		        	//setting up progress dialog
		        	myProgressDialog = ProgressDialog.show(settings.this,     
                        "Please wait...", "Confirming username and password with fonolo.", true); 
			        startLongRunningOperation(uname,passwd,phone_num);			        
		        }
		        break;
    		case R2.id.help_button:// case for when help button is pressed.
    			Intent i = new Intent(this, help.class);
            	String help_message = "On this screen you will input your username, password " +
            			"and phone number that you set up on the fonolo website (www.fonolo.com) " +
            			"Click save to save your login information. Click clear to clear this " +
            			"information from the database.";
            	Bundle extras = new Bundle();
            	extras.putString("content", help_message);
            	i.putExtras(extras);
            	startActivity(i);
            	break;
    		case R2.id.clear_button:// case for when clear button is pressed
    			user.setText("");
    			pass.setText("");
    			phone.setText("");
    			mDbHelper.deleteLogin();
    			break;
    		case R2.id.view_eula_button:
    			Intent e = new Intent(this, eula.class);
    			startActivity(e);
    			break;
    		}
    	}
}