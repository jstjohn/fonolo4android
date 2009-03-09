package com.android.fonolo4android;

/**
 * 
 * @author Abdul Binrasheed, John St. John
 * Last updated February 2009
 * 
 * This is an implementation of a binary search tree, except to avoid confusion
 * in the application of this data structure to our code we have renamed left and
 * right child to sister and child. This allows us to have a straight forward 
 * data structure in which to store information from the company information
 * phone trees returned by fonolo.
 * 
 * Each node has storage for a sister node, a child node, and all the data from
 * by fonolo for each node as of Version 1.1 build 5. This data may change in
 * subsequent versions and this data structure would have to be updated accordingly.
 *
 */
public class Node {
	private Node sister;
	private Node child;
	private boolean has_sister = false;
	private boolean has_child = false;
	private Boolean status = false;
	private String menu;
	private String id;
	private String type;
	private String title;
	private int depth;
	private String alias;
	
	// copy constructor
	public Node(Node copy){
		status = copy.status;
		menu = copy.menu;
		id = copy.id;
		type = copy.type;
		title = copy.title;
		depth = copy.depth;
		alias = copy.alias;
		sister = copy.sister;
		child = copy.child;
		has_sister = copy.has_sister;
		has_child = copy.has_child;

	}
	
	
	//Constructor with initializing arguments. This could be used in the place of the setter methods.
	public Node(Boolean my_status, String my_menu, String my_id, String my_type, String my_title, int my_depth, String my_alias){
		status = my_status;
		menu = my_menu;
		id = my_id;
		type = my_type;
		title = my_title;
		depth = my_depth;
		alias = my_alias;
	}
	
	//empty constructor class for making dummy nodes and head nodes. 
	//these nodes could be subsequently filled with real data through
	//the setter methods.
	public Node(){}
	
	//returns the nodes sister
	public Node getSister(){
		return sister;
	}
	
	// returns the nodes child
	public Node getChild(){
		return child;
	}
	
	// sets the node's sister if one doesn't already exist
	public void setSister(Node new_sister){
		if(! has_sister){
			has_sister = true;
			sister = new_sister;
		}
	}
	
	// sets the nodes child if one doesn't already exist
	public void setChild(Node new_child){
		if(! has_child){
			has_child = true;
			child = new_child;
		}
	}
	
	// tells the outside world whether or not this node has a sister
	public boolean hasSister(){
		return has_sister;
	}
	
	//tells the outside world whether or not this node has a child
	public boolean hasChild(){
		return has_child;
	}
	
	
	
	//Data getter methods
	
	//returns the status of the node (@see fonolo API)
	public boolean getStatus(){
		return status;
	}
	
	// returns the node's menu (@see fonolo API)
	public String getMenu(){
		return menu;
	}
	
	// returns the node's ID (@see fonolo API)
	public String getId(){
		return id;
	}
	
	// returns the node's type (@see fonolo API)
	public String getType(){
		return type;
	}
	
	// returns the nodes title (@see fonolo API)
	public String getTitle(){
		return title;
	}
	
	// returns the nodes depth (@see fonolo API)
	public int getDepth(){
		return depth;
	}
	
	// returns the nodes alias (@see fonolo API)
	// if none exist returns the string: "null" (@see fonolo API)
	public String getAlias(){
		return alias;
	}
	
	
	//Data Setter methods
	
	// sets the status of the node. Need to convert
	// from the string that they return to the boolean
	// for this method(@see fonolo API)
	public void setStatus(boolean my_status){
		status = my_status;
	}
	
	// sets this nodes menu (@see fonolo API)
	public void setMenu(String my_menu){
		menu = my_menu;
	}
	
	// sets this nodes id(@see fonolo API)
	public void setId(String my_id){
		id = my_id;
	}
	
	// sets this nodes type (@see fonolo API)
	public void setType(String my_type){
		type = my_type;
	}
	
	// sets this nodes title (@see fonolo API)
	public void setTitle(String my_title){
		title = my_title;
	}
	
	// sets this nodes depth (@see fonolo API)
	public void setDepth(int my_depth){
		depth = my_depth;
	}
	
	// sets this nodes alias (@see fonolo API)
	public void setAlias(String my_alias){
		alias = my_alias;
	}


}
