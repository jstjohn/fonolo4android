package com.android.fonolo;

public class Node {
	private Node sister;
	private Node child;
	private boolean has_sister = false;
	private boolean has_child = false;
	private Boolean status;
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
	
	
	//regular constructor, this takes place of setter methods
	public Node(Boolean my_status, String my_menu, String my_id, String my_type, String my_title, int my_depth, String my_alias){
		status = my_status;
		menu = my_menu;
		id = my_id;
		type = my_type;
		title = my_title;
		depth = my_depth;
		alias = my_alias;
	}
	
	//empty constructor class for making head nodes etc.
	public Node(){}
	
	public Node getSister(){
		return sister;
	}
	
	public Node getChild(){
		return child;
	}
	
	public void setSister(Node new_sister){
		if(! has_sister){
			has_sister = true;
			sister = new_sister;
		}
	}
	
	public void setChild(Node new_child){
		if(! has_child){
			has_child = true;
			child = new_child;
		}
	}
	
	public boolean hasSister(){
		return has_sister;
	}
	
	public boolean hasChild(){
		return has_child;
	}
	
	
	
	//Data getter methods
	public boolean getStatus(){
		return status;
	}
	public String getMenu(){
		return menu;
	}
	public String getId(){
		return id;
	}
	public String getType(){
		return type;
	}
	public String getTitle(){
		return title;
	}
	public int getDepth(){
		return depth;
	}
	public String getAlias(){
		return alias;
	}
	
	
	//Data Setter methods
	public void setStatus(boolean my_status){
		status = my_status;
	}
	public void setMenu(String my_menu){
		menu = my_menu;
	}
	public void setId(String my_id){
		id = my_id;
	}
	public void setType(String my_type){
		type = my_type;
	}
	public void setTitle(String my_title){
		title = my_title;
	}
	public void setDepth(int my_depth){
		depth = my_depth;
	}
	public void setAlias(String my_alias){
		alias = my_alias;
	}


}
