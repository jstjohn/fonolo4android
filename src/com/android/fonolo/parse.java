package com.android.fonolo;

import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

public class parse {
	
	
	
	//Parses through the results of a company_search() and returns a linkedlist of
	//string arrays holding the company name in pos 0 and the company id in pos 1
	public static LinkedList<String[]> parse_comp_search(JSONObject data){
		
		LinkedList<String[]> result = new LinkedList<String[]>();
		int i = 0;
		String number = "00"+Integer.toString(i);
		while(data.has(number)){
			JSONObject company;
			try {
				company = data.getJSONObject(number);
				String name = company.getString("name");
				String id = company.getString("id");
				String[] temp = new String[2];
				temp[0] = name;
				temp[1] = id;
				result.add(temp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//increment i and reset the number to the next in sequence
			//note: this will break if there are more than 999 companies 
			//     in the list. Currently fonolo only returns 3 digits.
			//     If fonolo changes this practice we need to update this.
			i++;
			if(i < 10){
				number = "00"+Integer.toString(i);
			}else if(i < 100){
				number = "0"+Integer.toString(i);
			}else{
				number = Integer.toString(i);
			}
			
		}
		return result;
		
	}

}
