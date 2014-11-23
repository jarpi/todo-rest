package com.blackhole.Utils;

import java.util.HashMap;

public class JSONObject { 
	private HashMap<String,Object> list = new HashMap<String,Object>(); 
	public JSONObject(String key, Object value){list.put(key, value);}    
} 

