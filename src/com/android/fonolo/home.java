package com.android.fonolo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author Craig Gardner
 * Last updated February 2009
 * 
 * This is the java class associated with the home page of the program.
 * This class will do a check on the username and password passed in from 
 * the previous class, and pass those data points on to subsequent classes 
 * (as in the bundle extras command) when the user inputs a search.
 *
 */
public class home extends Activity implements OnClickListener, private_constants{
	TextView output;
	TextView search_text;
	
	//copy into all classes-----------------------------
	String uname = "";
	String passwd = "";
	//end copy------------------------------------------
	
	private storage_get_set mDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
 		
		output = (TextView)this.findViewById(R.id.output);
		search_text = (TextView)this.findViewById(R.id.search_box);
		// Define the Buttons, and setup the listener on them.
		View help_button = this.findViewById(R.id.help_button);
		help_button.setOnClickListener(this);
		View search_button = this.findViewById(R.id.search_button);
		search_button.setOnClickListener(this);
		View favs_button = this.findViewById(R.id.favs_button);
		View settings_button = this.findViewById(R.id.settings_button);
		settings_button.setOnClickListener(this);
		favs_button.setOnClickListener(this);
		mDbHelper = new storage_get_set(this);
		mDbHelper.open();
		
		Cursor c = mDbHelper.fetchLogin();
		startManagingCursor(c);
		int uname_column = c.getColumnIndex(storage_get_set.KEY_UNAME);
        int pass_column = c.getColumnIndex(storage_get_set.KEY_PASS);
		if(!c.equals(null)){
			if(c.getCount() == 0){
				//send the person to the user settings page because we have no info

			}else{
				if(c.moveToFirst()){
					uname = c.getString(uname_column);
					passwd = c.getString(pass_column);
				}
			}
		}
		
//		//copy into all classes into onCreate()----------
//		//each method will retrieve user/pass
//		Bundle extras = getIntent().getExtras();
//		uname = extras.getString("user");
//		passwd = extras.getString("pass");
//		//end copy---------------------------------------
	}
	//Button click handling
	public void onClick(View v) {        
		//sending data to the next class
		Bundle out_extras = new Bundle();		
		out_extras.putString("user", uname);
		out_extras.putString("pass", passwd);
		
		switch (v.getId()){	//checking the case of which button is pressed.	
		case R.id.help_button:// case of pressing the help button.
			Intent i = new Intent(this, help.class);
			//Help message passed to the help page
        	String help_message = "This is the search screen. Here you will input a search for a company." +
        			" Only the first 30 results will be displayed. If you don't find the company you " +
        			"searched for please refine your search.";
        	Bundle extras = new Bundle();
        	extras.putString("content", help_message);
        	i.putExtras(extras);
        	startActivity(i);
        	break;
        	
		case R.id.search_button:// case of pressing the search button.
			// check if the text view is empty, and show error message if so.
			if(search_text.getText().toString().equals("")){
				Intent j = new Intent(this, message.class);
	        	String message = "Please input search";
	        	Bundle extras1 = new Bundle();
	        	extras1.putString("message", message);
	        	j.putExtras(extras1);
	        	startActivity(j);
				break;
			}
			// if the text view is not empty, then copy the content and pass it to list.class.
			else{
				Intent s = new Intent(this, list.class);
				out_extras.putInt("method", SEARCH_METHOD);
				out_extras.putString("search", search_text.getText().toString());
				s.putExtras(out_extras);
				startActivity(s);
				break;
			}
			//more buttons time permitting.
/*		case R.id.list_all_button:
			Intent l = new Intent(this, list.class);
			out_extras.putInt("method", LIST_METHOD);
			l.putExtras(out_extras);
			startActivity(l);
			break;*/
		case R.id.favs_button:
			Intent f = new Intent(this, list.class);
			out_extras.putInt("method", FAVS_METHOD);
			f.putExtras(out_extras);
			startActivity(f);
			break;
		case R.id.settings_button:
			Intent j = new Intent(this, settings.class);
			startActivity(j);
			break;
		
		}		
	}
}