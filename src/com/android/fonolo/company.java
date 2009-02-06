package com.android.fonolo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
	//ScrollView tl;
	//RelativeLayout tl;
	//AbsoluteLayout tl;
	TableLayout tl;
	
	 /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.company);
    	
    	tl = (TableLayout)findViewById(R.id.tab_buttons);
    	//Alternative attempts:
    	//tl = (AbsoluteLayout)findViewById(R.id.tab_buttons);
    	//tl = (RelativeLayout)findViewById(R.id.tab_buttons);
    	//tl = (ScrollView)findViewById(R.id.company_scroll);
    	
    	output = (TextView)this.findViewById(R.id.output);
    	company_name = (TextView)this.findViewById(R.id.company_name_text);
    	
    	//copy into all classes--------------------------
		Bundle extras = getIntent().getExtras();
		uname = extras.getString("user");
		passwd = extras.getString("pass");
		//end copy---------------------------------------

		id = extras.getString("id");
		String name = extras.getString("company_name");
        company_name.setText(name);

		
		
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
			make_button(head,0);	
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    
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
						stars = "***";
					}else{
						//color other way
					}
					String tabs = "";
					for(int i = 0; i < tabbing; i++){
						tabs += "--|";
					}
					tabs += " ";
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