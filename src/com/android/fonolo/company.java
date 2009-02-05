package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

//edit class to reflect UI
public class company extends Activity implements Button.OnClickListener, private_constants {
	
	private static final int button_number = 30;
	Button[] b = new Button[button_number];
	//increment this global button count throughout the recursive method
	//to make sure we don't go over button_number and get a null pointer exception
	private int button_count = 0;
	
	private String id = "";
	
	//copy into all classes-----------------------------
	String uname = "";
	String passwd = "";
	//end copy------------------------------------------
	TextView output;
	TextView company_name;	
	AbsoluteLayout tl;
	//TableLayout tl;
	
	 /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.company);
    	
    	//tl = (TableLayout)findViewById(R.id.tab_buttons);
    	tl = (AbsoluteLayout)findViewById(R.id.tab_buttons);
    	
    	output = (TextView)this.findViewById(R.id.output);
    	company_name = (TextView)this.findViewById(R.id.company_name_text);
    	
    	//copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		//end copy---------------------------------------
        
		
		//Strictly for test purposes!
		id = extras.getString("id");
		String name = extras.getString("company_name");
		//company_name.setText(name);
		
		
		
		/**
		 * Nodes hold all of the company tree information. Here is how they work.
		 * Each node has sisters and children, they also have methods to check
		 * whether or not their sisters or children actually exist. They also have
		 * getter methods for grabbing out all the node information provided by fonolo
		 * Getting the information out of the nodes could be done with a recursive loop
		 */
		
		try {
			JSONObject info = communication.company_details(id, uname, passwd);
			Node head = parse.parse_comp_info(info);
			//just call it a child because it is at the beginning
			//id += head.getChild().getTitle();
			make_child_button(head,0);	
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//        TextView tv = new TextView(this);
//        tv.setText(id);
//        setContentView(tv);
		//output.setText(id);
    }
    
    private void make_child_button(Node me, int tabbing){
    //	if(!me.equals(null)){
	    	//tells you the name of this node in the tree
			String node_name = me.getTitle();
			//this tells you if it is an agent or an IVR tree
			String node_type = me.getType();
			//tells you if this node is works, if it doesn't, forget it
			boolean active = me.getStatus();	
			//use this id to place a call to this node
			String headNodeid = me.getId();
			// the message of this node, (ex "for more information press...")
			String message = me.getMenu();
			//the depth of children below this node
			int depth = me.getDepth();
			//this node's alternative name if it has one
			String alias = me.getAlias();
			
			if(active){//if the node is not active, do not search deeper!
				/**
				 * Insert code here to make the button!
				 * for testing purposes I will just add text to the id, which gets displayed
				 */
				if(button_count < button_number){
					b[button_count] = new Button(this);
					//String[] temp = list.get(button_count);
					// the 0 position in the result is name, the 1 position is company id
			        b[button_count].setText(node_name); 
			        b[button_count].setTag(headNodeid);
			        b[button_count].setWidth(200);
			        b[button_count].setId(button_count);
			        b[button_count].setOnClickListener(this);
			        b[button_count].offsetTopAndBottom(button_count*b[button_count].getHeight());
			        b[button_count].offsetLeftAndRight(tabbing*5);
			        tl.addView(b[button_count]);
			        button_count++;
				}
//				/////////////////////////////////////////
//				// BEGIN TEST CODE!!!!!
//				/////////////////////////////////////////
//				id += "\n";
//				//make tabs
//				for(int i = 0; i < tabbing; i++){
//					id += "\t";
//				}
//				//print the node_name
//				id += node_name;
//				
//				/////////////////////////////////////////
//				// END TEST CODE!!!!!
//				/////////////////////////////////////////
				
				//first do all children, then do all siblings
				if(me.hasChild()){
					make_child_button(me.getChild(),tabbing+1);
				}
				//then do all sisters
				if(me.hasSister()){
					make_sister_button(me.getSister(),tabbing);
				}
			//}
    	}
		
		
    }
    private void make_sister_button(Node me, int tabbing){
    	//if(!me.equals(null)){
	    	//tells you the name of this node in the tree
			String node_name = me.getTitle();
			//this tells you if it is an agent or an IVR tree
			String node_type = me.getType();
			//tells you if this node is works, if it doesn't, forget it
			boolean active = me.getStatus();	
			//use this id to place a call to this node
			String headNodeid = me.getId();
			// the message of this node, (ex "for more information press...")
			String message = me.getMenu();
			//the depth of children below this node
			int depth = me.getDepth();
			//this node's alternative name if it has one
			String alias = me.getAlias();
			
			if(active){//if the node is not active, do not search deeper!
				/**
				 * Insert code here to make the button!
				 * for testing purposes I will just add text to the id, which gets displayed
				 */
				if(button_count < button_number){
					b[button_count] = new Button(this);
					//String[] temp = list.get(button_count);
					// the 0 position in the result is name, the 1 position is company id
			        b[button_count].setText(node_name); 
			        b[button_count].setTag(headNodeid);
			        b[button_count].setWidth(200);
			        b[button_count].setId(button_count);
			        b[button_count].setOnClickListener(this);
			        b[button_count].offsetTopAndBottom(button_count*b[button_count].getHeight());
			        b[button_count].offsetLeftAndRight(tabbing*5);
			        tl.addView(b[button_count]);
			        button_count++;
				}
//				/////////////////////////////////////////
//				// BEGIN TEST CODE!!!!!
//				/////////////////////////////////////////
//				id += "\n";
//				//make tabs
//				for(int i = 0; i < tabbing; i++){
//					id += "\t";
//				}
//				//print the node_name
//				id += node_name;
//				
//				/////////////////////////////////////////
//				// END TEST CODE!!!!!
//				/////////////////////////////////////////
				
				//first do all children, then do all siblings
				if(me.hasChild()){
					make_child_button(me.getChild(),tabbing+1);
				}
				//then do all sisters
				if(me.hasSister()){
					make_sister_button(me.getSister(),tabbing);
				}
			}
    	//}
    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
    
}