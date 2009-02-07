package com.android.fonolo;

import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

/**
 * 
 * @author Craig Gardner, John St. John, Abdul Binrasheed
 * Last updated February 2009
 * 
 * This class handles the logic for listing company list page. It
 * utilizes an array of buttons to hold dynamic buttons for the 
 * company names. It then passes along the necessary information 
 * to company.java to list the contents of the company.
 *
 */
//edit class to reflect UI
public class list extends Activity implements Button.OnClickListener, private_constants{
	
	//copy into all classes-----------------------------
	String uname = "";
	String passwd = "";
	//end copy------------------------------------------
	
	String json_temp_string = "";
	private static final int button_number = 30;
	Button[] b = new Button[button_number];
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.list);  
        //TextView output = (TextView)this.findViewById(R.id.output);
        TableLayout tl = (TableLayout)findViewById(R.id.table_buttons);
        
        //copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		//end copy---------------------------------------
		
		
		int method = extras.getInt("method");
		if(method == SEARCH_METHOD){
			String query = extras.getString("search");

			try {
				JSONObject result = communication.company_search(query,uname,passwd);
				LinkedList<String[]> list = parse.parse_comp_search(result);
				json_temp_string = result.toString();
				
				//limit the size to button_number
				int size = 0;
				if (list.size() > button_number){
					size = button_number;
				} else {
					size = list.size();
				}
				
				
				for(int j = 0; j < size; j++){
					b[j] = new Button(this);
					String[] temp = list.get(j);
					// the 0 position in the result is name, the 1 position is company id
			        b[j].setText(temp[0]); 
			        b[j].setTag(temp[1]);
			        //b[j].setWidth(220);
			        b[j].setId(j);
			        b[j].setOnClickListener(this);
			        tl.addView(b[j]);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
    }

	public void onClick(View v) {
		int i = v.getId();
		Bundle out_extras = new Bundle();
		String id = (String)b[i].getTag();
		String company_name = (String)b[i].getText();
		out_extras.putString("user", uname);
		out_extras.putString("pass", passwd);
		Intent s = new Intent(this, company.class);
		out_extras.putString("company_name", company_name);
		out_extras.putString("id", id);
		s.putExtras(out_extras);
		startActivity(s);
		// TODO Auto-generated method stub		
	}
}