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
 * @author Craig Gardner, John St. John, Abdul Binrsheed
 * Last Update February 2009
 *
 * This is the class associated with our call page. It contains all the
 * logic associated with that page including placing a call when the button
 * is pushed etc. It also displays some information about the company and
 * node within that company the call is about to be placed to.
 *
 */
//edit class to reflect UI
public class call extends Activity implements OnClickListener, private_constants {

	//copy into all classes-----------------------------
	String uname = "";
	String passwd = "";
	String phone = "";
	//end copy------------------------------------------
	TextView output;
	TextView phone_entry;
	String id = "";
	String outMessage;

	String first3 = "XXX";
	String next3 = "XXX";
	String final4 = "XXXX";
	
	private storage_get_set mDbHelper;
	JSONObject call_result;

	int response_code;
	String raw_phone;
	
	ProgressDialog myProgressDialog = null;

	// Need handler for callbacks to the UI thread
	final Handler mHandler = new Handler();

	// Create runnable for posting
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			updateResultsInUi();
		}
	};
	protected void startLongRunningOperation() {

		// Fire off a thread to do some work that we shouldn't do directly in the UI thread
		Thread t = new Thread() {
			public void run() {
				try {
					//check if the typed number matches the number that stored under the user account. 
					JSONObject result = communication.check_member_number(uname, passwd, first3+"-"+next3+"-"+final4);
					response_code = result.getJSONObject("result").getJSONObject("head").getInt("response_code");
					// if the typed number is correct, then place the call and display the call placing message.
					if(response_code >= 200 && response_code < 300){
						call_result = communication.call_start(id, first3+"-"+next3+"-"+final4, uname, passwd);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mHandler.post(mUpdateResults);
				myProgressDialog.dismiss();
			}
		};
		t.start();
	}

	private void updateResultsInUi() {
		// Back in the UI thread -- update our UI elements based on the data in mResults
		// if the typed number is correct, then place the call and display the call placing message.
		if(response_code >= 200 && response_code < 300){
			outMessage += "\nMember phone number valid: "+first3+"-"+next3+"-"+final4;
			output.setText(outMessage);
			int call_code = 999;
			String outsuccess = "";
			try {
				call_code = call_result.getJSONObject("result").getJSONObject("head").getInt("response_code");
				outsuccess = call_result.getJSONObject("result").getJSONObject("head").getString("response_message");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// check the respond from start_call.class
			if(call_code >= 200 && call_code < 300){
				outsuccess += "\n\nPlease wait for the call from Fonolo.";
			}else{
				outsuccess += "\n\nAn error occured, this node may be unavailable. Please try your call again later or try a different node in the menu.";
			}
			//setup the info to be displayed in popup window. 
			Intent i = new Intent(this, message.class);
			String message = outsuccess;
			Bundle extras = new Bundle();
			extras.putString("message", message);
			i.putExtras(extras);
			startActivity(i);
		}					
		//If the typed number is not correct, show error message and asked to retype the number.
		else{
			String outerror = "Member phone number invalid: "+raw_phone;
			outerror += "\nPlease enter the same 10 digit phone number on your fonolo account. ";
			outerror += "The correct format should be 555 555 5555 (but no spaces). Please enter your correct number and try again.";
			Intent i = new Intent(this, message.class);
			String message = outerror;
			Bundle extras = new Bundle();
			extras.putString("message", message);
			i.putExtras(extras);
			startActivity(i);

		}
	}


	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {     // set up the layout. 
		super.onCreate(savedInstanceState);

		setContentView(R.layout.call);

		output = (TextView)this.findViewById(R.id.output);
		phone_entry = (TextView)this.findViewById(R.id.my_phone_number);
		
		mDbHelper = new storage_get_set(this);
    	mDbHelper.open();
		
		Cursor c = mDbHelper.fetchLogin();
		startManagingCursor(c);
		int uname_column = c.getColumnIndex(storage_get_set.KEY_UNAME);
        int pass_column = c.getColumnIndex(storage_get_set.KEY_PASS);
        int phone_column = c.getColumnIndex(storage_get_set.KEY_PHONE);
		if(!c.equals(null)){
			if(c.getCount() == 0){
				//send the person to the user settings page because we have no info

			}else{
				if(c.moveToFirst()){
					uname = c.getString(uname_column);
					passwd = c.getString(pass_column);
					phone = c.getString(phone_column);
				}
			}
		}

		//copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
//		uname = extras.getString("user");
//		passwd = extras.getString("pass");
		//end copy---------------------------------------
		// setup the needed values.
		id = extras.getString("id");
		String node_name = extras.getString("node_name");
		String company_name = extras.getString("company_name");
		//Create buttons and setup a listener. 
		View call_button = this.findViewById(R.id.place_call); 
		call_button.setOnClickListener(this);
		View help_button = this.findViewById(R.id.help_button);
		help_button.setOnClickListener(this);
		//setup and display the output message to display the company name, and the name of the node you are reaching.
		outMessage = "Company Name: "+company_name+"\n";
		outMessage += "Destination: "+node_name+"\n";

		output.setText(outMessage);

	}

	// setup the actions when the buttons were pressed.
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.place_call:// if the place a call pressed, the do the following.
			raw_phone = phone_entry.getText().toString();// get the typed number.

			// the following regular expression strips out
			// everything that isn't a digit in the person's text field
			raw_phone = raw_phone.replaceAll("\\D", "");


			// store the user phone number in the right format.
			if(raw_phone.length() == 11){//if the user entered the country code"1", ignore it.
				first3 = raw_phone.substring(1,4);
				next3 = raw_phone.substring(4,7);
				final4 = raw_phone.substring(7,11);
			}
			else if (raw_phone.length() == 10){
				first3 = raw_phone.substring(0,3);
				next3 = raw_phone.substring(3,6);
				final4 = raw_phone.substring(6,10);
			}
			
			myProgressDialog = ProgressDialog.show(call.this,
                    "Please wait...", "Checking phone number against fonolo database.", true);
			startLongRunningOperation();
			break;
		case R.id.help_button:// show the help window if the pressed button is help. 
			String outmessage = "This is the call screen. You need the 10 digit phone number that " +
			"was added to your account on the fonolo website. The correct format should be:" +
			"\n555 555 5555 (without spaces). " +
			"Input your phone number into the space provided and wait for a call from fonolo. " +
			"the call originates from 1(416)366-2500";
			Intent j = new Intent(this, help.class); 
			String help_message = outmessage;
			Bundle extras = new Bundle();
			extras.putString("content", help_message);
			j.putExtras(extras);
			startActivity(j);
			break;
		}

	}
}
