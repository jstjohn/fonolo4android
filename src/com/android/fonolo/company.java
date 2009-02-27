package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * 
 * @author Craig Gardner, John St. John
 * Last edited February 2009
 * 
 * This is the class associated with the company information page.
 * This class handles the logic of the page, the on click listeners,
 * dynamically generating buttons for each of the company nodes, and
 * passing the information along to the call page when a button is
 * clicked. 
 *
 */
//edit class to reflect UI
public class company extends Activity implements Button.OnClickListener, private_constants {
	
	
	/**
	 * The following static final variable may be adjusted to change the maximum
	 * number of buttons to display within this company information page. In our
	 * experience, 30 is more than we have seen in a phone tree. If you want to 
	 * make extra sure that all buttons are displayed you could make this number
	 * larger. 
	 */
	private static final int button_number = 30;
	Button[] b = new Button[button_number];
	Node head;
	final Handler mHandler = new Handler();
	
	ProgressDialog myProgressDialog = null;
	
	/**
	 * The recursive definitions in this code are fairly fragile. If the following
	 * button_count is not incremented, and if the programmer fails to check that 
	 * the button count is equal to the button number, there will surely be an over
	 * flow exception error. If that is the case, this may be a good place to check.
	 */
	private int button_count = 0;
	
	private String id = "";
	private String name = "";
	
	//copy into all classes-----------------------------
	String uname = "";
	String passwd = "";
	//end copy------------------------------------------
	TextView output;
	TextView company_name;
	TableLayout tl;
	
	private storage_get_set mDbHelper;
	
	
    protected void startLongRunningOperation() {

        // Fire off a thread to do some work that we shouldn't do directly in the UI thread
        Thread t = new Thread() {
            public void run() {
        		/**
        		 * Nodes hold all of the company tree information. Here is how they work.
        		 * Each node has sisters and children, they also have methods to check
        		 * whether or not their sisters or children actually exist. They also have
        		 * getter methods for grabbing out all the node information provided by fonolo
        		 * Getting the information out of the nodes could be done with a recursive loop
        		 */
        		//call company details function, and store the respond info.also initinal 
        		try {
        			JSONObject info = communication.company_details(id, uname, passwd);
        			head = parse.parse_comp_info(info);  				
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
	
    // Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            make_button(head,0);
        }
    };
	
	
	 /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.company);
    	View new_search_button = this.findViewById(R.id.new_search_button);
    	View favs_button = this.findViewById(R.id.favs_button);
    	favs_button.setOnClickListener(this);
    	favs_button.setId(911);
    	new_search_button.setOnClickListener(this);
    	new_search_button.setId(611);
    	View help_button = this.findViewById(R.id.help_button);
    	help_button.setOnClickListener(this);
    	help_button.setId(411);
    	mDbHelper = new storage_get_set(this);
//    	try{
    		mDbHelper.open();
//    	}catch(SQLException e){
//    		Intent i = new Intent(this, message.class);
//        	String message = e.getMessage();
//        	Bundle extras = new Bundle();
//        	extras.putString("message", message);
//        	i.putExtras(extras);
//        	startActivity(i);
//    	}
    	
    	tl = (TableLayout)findViewById(R.id.tab_buttons);
    	
    	// store the data from the UI into the variables.
    	output = (TextView)this.findViewById(R.id.output);
    	company_name = (TextView)this.findViewById(R.id.company_name_text);
    	
    	//copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		//end copy---------------------------------------

		id = extras.getString("id");
		name = extras.getString("company_name");
        company_name.setText(name);
        
        myProgressDialog = ProgressDialog.show(company.this,
                "Please wait...", "Populating the company tree.", true);
        startLongRunningOperation();
    }
    
    /**
     * 
     * @param me - the current node to be analyzed, the method is called recursively
     * 				on children and siblings. 
     * @param tabbing - the amount of indenting that this node should have. When
     * 				making a child node, increase the indenting, other sister nodes
     * 				should just get the same indenting. The first node should start
     * 				with the value zero.
     */
    private void make_button(Node me, int tabbing){
	    	//tells you the name of this node in the tree
			String node_name = me.getTitle();
			//this tells you if it is an agent or an IVR tree
			String node_type = me.getType();
			//tells you if this node is works, if it doesn't, forget it
			boolean active = me.getStatus();	
			//use this id to place a call to this node
			String headNodeid = me.getId();
			
			/**
			 * The following 6 lines of commented out code is currently not needed, however it represents
			 * everything that is returned by fonolo as of version 1.1 build 5
			 * of their API. I am leaving these variables here in case they are
			 * needed in future builds
			 */
			// the message of this node, (ex "for more information press...")
//			String message = me.getMenu();
//			//the depth of children below this node
//			int depth = me.getDepth();
//			//this node's alternative name if it has one
//			String alias = me.getAlias();
			
			if(active){//if the node is not active, do not search deeper!
				if(button_count < button_number){
					b[button_count] = new Button(this);
					String stars = "";
					if(node_type.equalsIgnoreCase("NODE_TYPE_AGENT")){
						//color for agent
						stars = " (Human)";
					}else{
						//color other way
					}
					String tabs = "";
					for(int i = 0; i < tabbing; i++){
						tabs += "   ";
					}
					tabs += "|";
					String[] info = new String[2];
					info[0] = node_name;
					info[1] = headNodeid;
			        b[button_count].setText(tabs + node_name + stars); 
			        b[button_count].setTag(info);		        
			        b[button_count].setId(button_count);
			        b[button_count].setOnClickListener(this);			        
			        //b[button_count].offsetTopAndBottom(button_count*b[button_count].getHeight());
			        //b[button_count].offsetLeftAndRight(tabbing*5);
			        tl.addView(b[button_count]);
			        //b[button_count].setWidth(200);
			        //b[button_count].setLayoutParams(new LayoutParams(200,LayoutParams.WRAP_CONTENT));
			        b[button_count].setGravity(3);
			        button_count++;
				}
				
				//first do all children, then do all siblings
				// always increase the tabbing by one when making a child
				if(me.hasChild()){
					make_button(me.getChild(),tabbing+1);
				}
				//then do all sisters
				if(me.hasSister()){
					make_button(me.getSister(),tabbing);
				}
    	}
				
    }


	public void onClick(View v) {
		int i = v.getId();
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
			String outmessage = "This is the tree listing. Here you will notice different characters. " +
					"Spaces before the description represent the depth of the phone tree. The (Human) tells you that the node gets you to a human\n" +
					"Example:\n     |Other inquiries (Human)\n represents a sub node (because of its spaces) that connects you to an agent. " +
					"Other inquiries is the title of the directory. ";
			Intent j = new Intent(this, help.class); 
			String help_message = outmessage;
			Bundle extras = new Bundle();
			extras.putString("content", help_message);
			j.putExtras(extras);
			startActivity(j);
		}
		
		//add to favorites
		else if(i == 911){
			if(mDbHelper.createFavorites(id, name) == -1){}
			else{		
				Intent q = new Intent(this, message.class);
	        	String message = "Failed to add the company to your favorites";
	        	Bundle extras = new Bundle();
	        	extras.putString("message", message);
	        	q.putExtras(extras);
	        	startActivity(q);
			}
		}
		
		//else go to make call page
		else{			
			/**
			 * The button tag currently holds a string array with 2 values
			 * The first value is the name of the node to be called and the
			 * second value is the id of the node.
			 */
			String[] info = (String[])b[i].getTag();
			String id = info[1];
			String node_name = info[0];
			out_extras.putString("user", uname);
			out_extras.putString("pass", passwd);
			Intent s = new Intent(this, call.class);
			out_extras.putString("node_name", node_name);
			out_extras.putString("company_name", company_name.getText().toString());
			out_extras.putString("id", id);
			s.putExtras(out_extras);
			startActivity(s);	
		}
	}
    
}