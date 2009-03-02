package com.android.fonolo;


import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Abdul Binrasheed, John St. John
 * Last update February 2009
 * 
 * This class holds the logic for parsing through the company search results,
 * and company information results and returns output that is easy to use
 * in subsequent steps. 
 * 
 */
public class parse {
	
	
	
	//Parses through the results of a company_search() and returns a linkedlist of
    //string arrays holding the company name in pos 0 and the company id in pos 1
    public static LinkedList<String[]> parse_comp_search(JSONObject result_json){
            LinkedList<String[]> result = new LinkedList<String[]>();
            
            try {
                    JSONObject data = result_json.getJSONObject("result").getJSONObject("data");
                    int i = 0;
                    String number = "00"+Integer.toString(i);
                    while(data.has(number)){
                            JSONObject company;
                            company = data.getJSONObject(number);
                            String name = company.getString("name");
                            String id = company.getString("id");
                            String[] temp = new String[2];
                            temp[0] = name;
                            temp[1] = id;
                            result.add(temp);
                            i++;
                            if(i < 10){
                                    number = "00"+Integer.toString(i);
                            }else if(i < 100){
                                    number = "0"+Integer.toString(i);
                            }else{
                                    number = Integer.toString(i);
                            }
                            
                    }
                    } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                    }
                    
                    
            return result;
            
    }

	

	
	
	public static Node parse_comp_info(JSONObject result_json){
		Node head = new Node();
		
		try {
			JSONObject tree = result_json.getJSONObject("result").getJSONObject("data").getJSONObject("tree");
			//Make the head of the tree
			JSONObject node0 = tree.getJSONObject("00").getJSONObject("node");
			String status_string = node0.getString("status");
			boolean status = true;
			if(status_string.equalsIgnoreCase("node_status_error")) status = false;
			String menu = node0.getString("menu");
			String id = node0.getString("id");
			String title = node0.getString("title");
			String type = node0.getString("type");
			int depth = node0.getInt("depth");
			String alias = node0.getString("alias");
			
			head = new Node(status,menu,id,type,title,depth,alias);
			Node sister = tree_builder(tree, 1);
			Node child = tree_builder(tree.getJSONObject("00"),0);
			
			if(sister.getStatus()){
				head.setSister(sister);
			}
			if(child.getStatus()){
				head.setChild(child);
			}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		return head;
		
	}
	
	private static Node tree_builder(JSONObject tree, int level){
		Node my_node = new Node();
		String my_sister_level = "";
		if(level < 10){
			my_sister_level = "0"+Integer.toString(level);
		}else{
			my_sister_level = Integer.toString(level);
		}
		
		if(!tree.has(my_sister_level)){
			//stop! nothing here!
		}else{
			//do things
			try {
				//First fill this node's data
				JSONObject node0 = tree.getJSONObject(my_sister_level).getJSONObject("node");
				String status_string = node0.getString("status");
				boolean status = true;
				if(status_string.equalsIgnoreCase("node_status_error")) status = false;
				String menu = node0.getString("menu");
				String id = node0.getString("id");
				String title = node0.getString("title");
				String type = node0.getString("type");
				int depth = node0.getInt("depth");
				String alias = node0.getString("alias");
				
				my_node = new Node(status,menu,id,type,title,depth,alias);
				
				//call recursively for any more sisters
				Node my_sister = tree_builder(tree, level+1);
				if(my_sister.getStatus()){
					my_node.setSister(my_sister);
				}
				//call recursively for any children
				Node my_child = tree_builder(tree.getJSONObject(my_sister_level),0);
				if(my_child.getStatus()){
					my_node.setChild(my_child);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return my_node;
	}
	
}
