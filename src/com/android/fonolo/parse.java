package com.android.fonolo;

import java.util.LinkedList;

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
	
	
	public static LinkedList<String[]> parse_comp_info(JSONObject result_json){
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

}
