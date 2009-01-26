package com.android.fonolo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class home extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		View logout_button = this.findViewById(R.id.logout_button);
		logout_button.setOnClickListener(this);
		View help_button = this.findViewById(R.id.help_button);
		help_button.setOnClickListener(this);
		View search_button = this.findViewById(R.id.search_button);
		search_button.setOnClickListener(this);
		View list_all_button = this.findViewById(R.id.list_all_button);
		list_all_button.setOnClickListener(this);
		View favorites_button = this.findViewById(R.id.favorites_button);
		favorites_button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.logout_button:
			Intent i = new Intent(this, fonolo4android.class);
			startActivity(i);
			break;
		//more buttons
		}		
	}
}