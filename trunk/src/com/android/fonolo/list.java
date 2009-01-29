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
		//end copy---------------------------------------
		
		int method = extras.getInt("method");
		if(method == SEARCH_METHOD){
			String query = extras.getString("search");
			String outputres = "";
			String namelist = "";
			try {
				JSONObject result = communication.company_search(query,uname,passwd);
				JSONObject data = result.getJSONObject("result").getJSONObject("data");
				for(int i = 0; i < 3; i++){
					String number = "00"+Integer.toString(i);
					JSONObject company = data.getJSONObject(number);
					String name = company.getString("name");
					namelist += name + "\n";
					
				}
				outputres += namelist;
				outputres += "\n\n\n";
				outputres += result.toString();
				
				//////////////////////////////////
				// TEST CODE, REMOVE!!!
				//////////////////////////////////
//				JSONObject call_stat = communication.call_start("9ee009d3fbff70ac14d81dde0824979e", "000-111-0003", uname, passwd);
//				outputres += "\n\n\n\nCall Request:\n";
//				outputres += call_stat.toString();
//				
				//////////////////////////////////
				// End test code
				//////////////////////////////////
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			outputres += "\n\n\n\n\n\n\n";
			output.setText(outputres);
		}
		
    }

	public void onClick(View v) {
		

		
		// TODO Auto-generated method stub		
	}
}