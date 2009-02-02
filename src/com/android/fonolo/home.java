package com.android.fonolo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class home extends Activity implements OnClickListener, private_constants{
	TextView output;
	TextView search_text;
	
	//copy into all classes-----------------------------
	String uname = "";
	String passwd = "";
	//end copy------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
 		
		output = (TextView)this.findViewById(R.id.output);
		search_text = (TextView)this.findViewById(R.id.search_box);
		
		//View logout_button = this.findViewById(R.id.logout_button);
		//logout_button.setOnClickListener(this);
		View help_button = this.findViewById(R.id.help_button);
		help_button.setOnClickListener(this);
		View search_button = this.findViewById(R.id.search_button);
		search_button.setOnClickListener(this);
		//View list_all_button = this.findViewById(R.id.list_all_button);
		//list_all_button.setOnClickListener(this);
		View favorites_button = this.findViewById(R.id.favorites_button);
		favorites_button.setOnClickListener(this);
		
		//copy into all classes into onCreate()----------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		//end copy---------------------------------------
	}

	public void onClick(View v) {        
		
		Bundle out_extras = new Bundle();		
		out_extras.putString("user", uname);
		out_extras.putString("pass", passwd);
		
		switch (v.getId()){
		case R.id.help_button:
			Intent h = new Intent(this, help.class);
			startActivity(h);
			break;
		case R.id.search_button:
			if(search_text.getText().toString().equals("")){
				output.setText("Please search for something");
				break;
			}
			else{
				Intent s = new Intent(this, list.class);
				out_extras.putInt("method", SEARCH_METHOD);
				out_extras.putString("search", search_text.getText().toString());
				s.putExtras(out_extras);
				startActivity(s);
				break;
			}
/*		case R.id.list_all_button:
			Intent l = new Intent(this, list.class);
			out_extras.putInt("method", LIST_METHOD);
			l.putExtras(out_extras);
			startActivity(l);
			break;*/
		case R.id.favorites_button:
			Intent f = new Intent(this, list.class);
			out_extras.putInt("method", FAVS_METHOD);
			f.putExtras(out_extras);
			startActivity(f);
			break;
		//more buttons
		}		
	}
}