package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
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
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.list_layout);
        
        //copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		//end copy---------------------------------------
		
		for(int j = 1; j <= 10; j++){
			Button b = new Button(this);
	        b.setText("Dynamic Button"); 
	        b.setWidth(320);
	        //rl.addView(b);
	        rl.addView(b, j);
		}
		int method = extras.getInt("method");
		if(method == SEARCH_METHOD){
			String query = extras.getString("search");
			String outputres = "";
			String namelist = "";
			try {
				JSONObject result = communication.company_search(query,uname,passwd);
				JSONObject data = result.getJSONObject("result").getJSONObject("data");
				int i = 0;
				String number = "00"+Integer.toString(i);
				while(data.has(number)){
					JSONObject company = data.getJSONObject(number);
					String name = company.getString("name");
					namelist += name + "\n";
					
					//increment i and reset the number to the next in sequence
					//note: this will break if there are more than 999 companies 
					//     in the list. Currently fonolo only returns 3 digits.
					//     If fonolo changes this practice we need to update this.
					i++;
					if(i < 10){
						number = "00"+Integer.toString(i);
					}else if(i < 100){
						number = "0"+Integer.toString(i);
					}else{
						number = Integer.toString(i);
					}
					
				}
				outputres += namelist;
				outputres += "\n\n\n";
				outputres += result.toString();
				
				//////////////////////////////////
				// TEST CODE, REMOVE!!!
				//////////////////////////////////
				//Note that the phone number must be the USER's phone number and match one in their fonolo record
//				JSONObject call_stat = communication.call_start("bc40d2fa90d9746a581d9568f3784b29", "000-000-000", uname, passwd);
//				outputres += "\n\n\n\nCall Request:\n";
//				outputres += call_stat.toString();
				
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