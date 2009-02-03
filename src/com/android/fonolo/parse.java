package com.android.fonolo;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
			
			head = new Node(status,menu,id,title,type,depth,alias);
			
			
			

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		return head;
		
	}
	
	private void sister_helper(Node sister, JSONObject tree, int level){
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
				
				Node my_node = new Node(status,menu,id,title,type,depth,alias);
				sister.setSister(my_node);
				
				//call recursively for any more sisters
				sister_helper(my_node, tree, level+1);
				
				//call recursively for any children
				child_helper(my_node, tree.getJSONObject(my_sister_level));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private void child_helper(Node parent, JSONObject tree){
		//gets called on parent's first child, who's sister level is 0
		String my_sister_level = "00";
		int level = 0;
		
		
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
				
				Node my_node = new Node(status,menu,id,title,type,depth,alias);
				parent.setChild(my_node);
				
				//call recursively for any more sisters
				sister_helper(my_node, tree, level+1);
				
				//call recursively for any children
				child_helper(my_node, tree.getJSONObject(my_sister_level));
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
