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
import android.widget.Button;
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
	TextView phone_call_details;
	String id = "";
	String outMessage;
	Button call_button;
	
	// create a handler to access the sqlite database
	private storage_get_set mDbHelper;
	
	JSONObject call_result;

	int response_code;
	String phone_num;
	
	String status_message = "";

	//code for adding a progress dialog box for user to see status of communication
	ProgressDialog myProgressDialog = null;

	// Need handler for callbacks to the UI thread
	final Handler mHandler = new Handler();

	// Create runnable for posting
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			updateResultsInUi();
		}
	};
	
	final Runnable mUpdatePostCancel = new Runnable() {
		public void run() {
			updatePostCancel();
		}
	};
	
	//do stuff here
	private void updatePostCancel(){
		phone_call_details.setText(status_message);
	}
	protected void startFonoloCallCommunication() {

		// Fire off a thread to do some work that we shouldn't do directly in the UI thread
		Thread t = new Thread() {
			public void run() {
				try {
					//check if the typed number matches the number that stored under the user account. 
					JSONObject result = communication.check_member_number(uname, passwd, phone);
					response_code = result.getJSONObject("result").getJSONObject("head").getInt("response_code");
					// if the typed number is correct, then place the call and display the call placing message.
					if(response_code >= 200 && response_code < 300){
						call_result = communication.call_start(id, phone, uname, passwd);
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
			int call_code = 999;
			
			try {
				call_code = call_result.getJSONObject("result").getJSONObject("head").getInt("response_code");
				status_message = call_result.getJSONObject("result").getJSONObject("head").getString("response_message");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// check the respond from start_call.class
			if(call_code >= 200 && call_code < 300){
				status_message += "\n\nPlease wait for the call from Fonolo.";
			}else{
				status_message += "\n\nAn error occured, this node may be unavailable. Please try your call again later or try a different node in the menu.";
			}
			phone_call_details.setText(status_message);
			//now update information periodically from fonolo;
			
		}					
		//If the typed number is not correct, show error message and asked to retype the number.
		else{
			String outerror = "Member phone number invalid: "+phone;
			outerror += "\nPlease enter the same 10 digit phone number on your fonolo account. ";
			outerror += "The correct format should be 555 555 5555 (but no spaces). Please enter your correct number in the settings page and try again.";
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
		phone_call_details = (TextView)this.findViewById(R.id.phone_call_details);
		
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
		//end copy---------------------------------------
		// setup the needed values.
		id = extras.getString("id");
		String node_name = extras.getString("node_name");
		String company_name = extras.getString("company_name");
		//Create buttons and setup a listener. 
		call_button = (Button) this.findViewById(R.id.place_call); 
		call_button.setOnClickListener(this);
		View help_button = this.findViewById(R.id.help_button);
		help_button.setOnClickListener(this);
		//setup and display the output message to display the company name, and the name of the node you are reaching.
		outMessage = company_name+": ";
		outMessage += node_name+"\n";

		output.setText(outMessage);

	}

	// setup the actions when the buttons were pressed.
	public void onClick(View v) {
		switch (v.getId()){
		case 199:
			call_button.setText("Place Call");
			call_button.setId(R.id.place_call);
			//start progress dialog
			myProgressDialog = ProgressDialog.show(call.this,
                    "Please wait...", "Sending request to end call.", true);
			startFonoloCallCommunication();
			startFonoloCallCancel();
			break;
		case R.id.place_call:// if the place a call pressed, the do the following
			call_button.setText("End Call");
			call_button.setId(199);
			myProgressDialog = ProgressDialog.show(call.this,
                    "Please wait...", "Checking verifying information and sending request.", true);
			startFonoloCallCommunication();
			
			break;
		case R.id.help_button:// show the help window if the pressed button is help. 
			String outmessage = "This is the call screen, after pressing Place Call, " +
					"please wait for a call from fonolo. The call originates from 1(416)366-2500. " +
					"the End Call button that replaces the Place Call button, may be pressed to notify " +
					"fonolo that you are done with your call.";
			Intent j = new Intent(this, help.class); 
			String help_message = outmessage;
			Bundle extras = new Bundle();
			extras.putString("content", help_message);
			j.putExtras(extras);
			startActivity(j);
			break;
		}

	}

	protected void startFonoloCallCancel() {
		// Fire off a thread to do some work that we shouldn't do directly in the UI thread
		Thread t = new Thread() {
			public void run() {
				try {
					//check if the typed number matches the number that stored under the user account. 
					JSONObject result = communication.check_member_number(uname, passwd, phone);
					response_code = result.getJSONObject("result").getJSONObject("head").getInt("response_code");
					// if the typed number is correct, then place the call and display the call placing message.
					if(response_code >= 200 && response_code < 300){
						
						try{
							String session_id = call_result.getJSONObject("result").getJSONObject("data").getString("session_id");
							JSONObject cancel = communication.call_cancel(session_id, uname, passwd);
							String response = cancel.getJSONObject("result").getJSONObject("head").getString("response_message");
							status_message = response;
						}catch(Exception e){
							
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				mHandler.post(mUpdatePostCancel);
				//end progress dialog
				myProgressDialog.dismiss();
			}
		};
		t.start();
		
	}
}
