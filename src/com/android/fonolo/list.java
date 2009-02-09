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
	private static final int button_number = 30;// define a variable that holds the max number of records.  
	Button[] b = new Button[button_number];// define an array of type button.
	
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.list);  
        TableLayout tl = (TableLayout)findViewById(R.id.table_buttons);// choose table layout to display the company list.
        
        //copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		//end copy---------------------------------------
		
		
		int method = extras.getInt("method");
		if(method == SEARCH_METHOD/* defined in private_cons..*/){
			String query = extras.getString("search");

			try {
				JSONObject result = communication.company_search(query,uname,passwd);// send the request, and save the respond
				LinkedList<String[]> list = parse.parse_comp_search(result);// save the result as a string linked list.
				json_temp_string = result.toString();
				
				//limit the size to button_number effectively limiting buttons to 30
				int size = 0;
				if (list.size() > button_number){
					size = button_number;
				} else {
					size = list.size();
				}
				
				// create the buttons dynamically.
				for(int j = 0; j < size; j++){
					b[j] = new Button(this);
					String[] temp = list.get(j);// reset the array, and store the company name and id. 
					// the 0 position in the result is name, the 1 position is company id
			        b[j].setText(temp[0]);
			        b[j].setTag(temp[1]);
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
// Setup the listener for the company list buttons.
	public void onClick(View v) {
		int i = v.getId(); // get the id of the button that was pressed.
		Bundle out_extras = new Bundle();
		String id = (String)b[i].getTag();
		String company_name = (String)b[i].getText();
		out_extras.putString("user", uname);
		out_extras.putString("pass", passwd);
		Intent s = new Intent(this, company.class);// choose the next page to be displayed.
		out_extras.putString("company_name", company_name);
		out_extras.putString("id", id);
		s.putExtras(out_extras);// pass the company info to the company.class.
		startActivity(s);
	}
}