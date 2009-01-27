package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

//edit class to reflect UI
public class list extends Activity implements OnClickListener, private_constants{
	
	//copy into all classes-----------------------------
	String uname = "";
	String passwd = "";
	communication com = new communication();
	//end copy------------------------------------------
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);  
        TextView output = (TextView)this.findViewById(R.id.output);
        
        //copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		com.set_member_info(uname, passwd);
		//end copy---------------------------------------
		
		int method = extras.getInt("method");
		if(method == SEARCH_METHOD){
			String query = extras.getString("search");
			String outputres = "";
			try {
				JSONObject result = com.company_search(query);
				outputres += result.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			outputres += "\n\n\n\n\n\n\n";
			output.setText(outputres);
		}
		
    }

	@Override
	public void onClick(View v) {
		

		
		// TODO Auto-generated method stub		
	}
}