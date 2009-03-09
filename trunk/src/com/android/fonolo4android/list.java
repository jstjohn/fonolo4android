package com.android.fonolo4android;

import java.util.LinkedList;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
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
    
    ProgressDialog myProgressDialog = null;
    
    LinkedList<String[]> list;
    final Handler mHandler = new Handler();
    private static final int button_number = 30;// define a variable that holds the max number of records.  
    Button[] b = new Button[button_number];// define an array of type button.
    
    private storage_get_set mDbHelper;        
    
    // Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
        }
    };
    protected void updateResultsInUi() {
        // choose table layout to display the company list.
        TableLayout tl = (TableLayout)findViewById(R.id.table_buttons);
        if (list.size() == 0){
            Intent j = new Intent(this, message.class);
            String message = "Your search yielded no results.\n" +
                            "Please refine your search.";
            Bundle extras1 = new Bundle();
            extras1.putString("message", message);
            j.putExtras(extras1);
            startActivity(j);
            }
            else{
            //limit the size to button_number effectively limiting buttons to 30
                int size = 0;
                if (list.size() > button_number){
                    size = button_number;
                } 
                else {
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
            }                
        }
    
    protected void startLongRunningOperation(final String query) {

        // Fire off a thread to do some work that we shouldn't do directly in the UI thread
        Thread t = new Thread() {
            public void run() {
                try {// send the request, and save the respond                	
                    JSONObject result = communication.company_search(query,uname,passwd);
                    list = parse.parse_comp_search(result);// save the result as a string linked list.
                } 
                catch (JSONException e) {
                	e.printStackTrace();
                }                
                mHandler.post(mUpdateResults);
                myProgressDialog.dismiss();
            }
        };
        t.start();
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.list); 
        
        //button variables set up
        View new_search_button = this.findViewById(R.id.new_search_button);
        new_search_button.setOnClickListener(this);
        new_search_button.setId(611);
        View help_button = this.findViewById(R.id.help_button);
        help_button.setOnClickListener(this);
        help_button.setId(411);
        
        //database set up
        mDbHelper = new storage_get_set(this);
        mDbHelper.open();
        Cursor c = mDbHelper.fetchLogin();
        startManagingCursor(c);
        int uname_column = c.getColumnIndex(storage_get_set.KEY_UNAME);
        int pass_column = c.getColumnIndex(storage_get_set.KEY_PASS);
        if(!c.equals(null)){
            if(c.getCount() == 0){
                    //send the person to the user settings page because we have no info
            	Intent s = new Intent(this, settings.class);
            	startActivity(s);
            }
            else{                        	
                if(c.moveToFirst()){
                    uname = c.getString(uname_column);
                    passwd = c.getString(pass_column);
                }
            }
        }         
        //copy into all classes--------------------------
        Bundle extras = getIntent().getExtras();
        //end copy---------------------------------------                
        
        int method = extras.getInt("method");
        if(method == SEARCH_METHOD/* defined in private_cons..*/){
                String query = extras.getString("search");
                //set up progress dialog
                myProgressDialog = ProgressDialog.show(list.this,                		
                		"Please wait...", "Getting search results.", true);
                startLongRunningOperation(query);
        }
        else if(method == FAVS_METHOD){
                fillData();
        }                
    }
// Setup the listener for the company list buttons.
        public void onClick(View v) {
            int i = v.getId(); // get the id of the button that was pressed.
            Bundle out_extras = new Bundle();
            //if user wants to refine search
            if(i == 611){
                out_extras.putString("user", uname);
                out_extras.putString("pass", passwd);
                Intent h = new Intent(this, home.class);
                h.putExtras(out_extras);
                startActivity(h);
            }
            else if(i == 411){
                String outmessage = "This is the search listing. If you see the company you " +
                                "searched for in the list, select it to see the phone tree for that " +
                                "company. If your search is not listed, please refine your search. ";
                Intent j = new Intent(this, help.class); 
                String help_message = outmessage;
                Bundle extras = new Bundle();
                extras.putString("content", help_message);
                j.putExtras(extras);
                startActivity(j);
            }
            //else get tree info
            else{
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
        
        /**
         * this method should get called when the screen needs to be populated
         * by favorites from the database
         */
        private void fillData() {
        // Get all of the notes from the database and create the item list
                try{
        
        Cursor c = mDbHelper.fetchAllFavorites();
        // choose table layout to display the company list.
        TableLayout tl = (TableLayout)findViewById(R.id.table_buttons);
        startManagingCursor(c);
        if(!c.equals(null)){
            if (c.getCount() == 0){  
            	//text to message window
                Intent j = new Intent(this, message.class);
                String message = "You have no favorites.\n\n To add a favorite to this list, " +
                		"first search for the company you want to add from the previous page, " +
                		"then click on the add button at the top of the company information " +
                		"page to add it to this list. Click the remove button on that same " +
                		"page to remove the company from this list.";
                Bundle extras1 = new Bundle();
                extras1.putString("message", message);
                j.putExtras(extras1);
                startActivity(j);
                }
            int id_column = c.getColumnIndex(storage_get_set.KEY_ID);
            int name_column = c.getColumnIndex(storage_get_set.KEY_NAME);
            if(c.moveToFirst()){
                int i = 0;
                do{
                    String id = c.getString(id_column);
                    String name = c.getString(name_column);
                    //setting up buttons with company information
                    b[i] = new Button(this);
                    b[i].setText(name);
                    b[i].setTag(id);
                    b[i].setId(i);
                    b[i].setOnClickListener(this);
                    tl.addView(b[i]);                    
                    i++;
                    if(i == button_number) break;
                }while(c.moveToNext());
            }
        }
        }catch(SQLException e){
                Intent i = new Intent(this, message.class);
                String message = e.getMessage();
                Bundle extras = new Bundle();
                extras.putString("message", message);
                i.putExtras(extras);
                startActivity(i);
        }
    }
}
