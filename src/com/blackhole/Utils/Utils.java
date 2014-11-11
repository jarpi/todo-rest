package com.blackhole.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.blackhole.App.Context;
import com.blackhole.App.Todos.todo;

public class Utils {
	private class FieldType {
		String mFieldName; 
		Class<?> mFieldType; 
		Object mFieldValue; 
		public FieldType(String fieldName, Class<?> fieldType, Object object) {this.mFieldName = fieldName; this.mFieldType = fieldType; this.mFieldValue = object; } 
		public String getFieldName() { return this.mFieldName; }
		public Class<?> getFieldType() {return this.mFieldType; } 
		public Object getFieldValue() {return this.mFieldValue; } 
		public String toString() {return this.mFieldName + " " + this.mFieldType + " " + this.mFieldValue;} 
	} 
	private static Utils mInstance = null; 
	
	public Utils() {} 
	
	public static Utils getInstance() {
		if (Utils.mInstance == null) {
			Utils.mInstance = new Utils(); 
		}
		return Utils.mInstance; 
	} 

	
	public String ToJSON(Object[] objArr) throws Exception {
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("[");
		for (int i=0; i<objArr.length; i++) { 
			Object obj = objArr[i]; 
			String jsonObjStr = this.ToJSON(obj); 
			jsonString.append(jsonObjStr); 
			if (i<objArr.length-1) {  
				jsonString.append(","); 
			} 
		} 
		jsonString.append("]");
		return jsonString.toString(); 
	} 
	
	public String ToJSON(todo[] objArr) {
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("[");
		for (int i=0; i<objArr.length; i++) { 
			Object obj = objArr[i]; 
			String jsonObjStr = this.ToJSON(obj); 
			jsonString.append(jsonObjStr); 
			if (i<objArr.length-1) {  
				jsonString.append(","); 
			} 
		} 
		jsonString.append("]");
		return jsonString.toString(); 
	}
	
	public String ToJSON(JSONObject jsonObj) {
		StringBuilder jsonString = new StringBuilder(); 
		try {
			getClass().getClassLoader().loadClass(jsonObj.getClass().getName()); 
			FieldType[] fieldsModel = this.getObjectFields(jsonObj); 
			jsonString.append("{"); 
			int numberOfFieldsProcessed = 0; 
			for (FieldType f : fieldsModel) {  
				 // jsonString.append(this.parseField(f)); 
				if(f.getFieldType() == HashMap.class) {
					HashMap<?,?> tempHashMap = (HashMap<?, ?>) f.getFieldValue(); 
					int mapLength = tempHashMap.keySet().toArray().length; 
					int i = 0; 
					for (Entry<?, ?> entry : tempHashMap.entrySet()) {
						i += 1; 
						jsonString.append("\"" + entry.getKey() + "\":"); 
						jsonString.append("\"" + entry.getValue() + "\""); 
						if (i<mapLength) jsonString.append(","); 
					} 
					if (++numberOfFieldsProcessed < fieldsModel.length) {
						jsonString.append(","); 
					} 
				}
			} 
			jsonString.append("}"); 
		} catch (ClassNotFoundException e) {
			jsonString.delete(0, jsonString.length()); 
			jsonString.append("ClassNotFoundException Ocurred");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) { 
			e.printStackTrace();
		} 
		return jsonString.toString(); 
	} 
	
	public String ToJSON(Object obj) { 
		StringBuilder jsonString = new StringBuilder(); 
		try { 
			getClass().getClassLoader().loadClass(obj.getClass().getName());
			FieldType[] fieldsModel = this.getObjectFields(obj);  
			jsonString.append("{"); 
			int numberOfFieldsProcessed = 0; 
			for (FieldType f : fieldsModel) {  
				 jsonString.append(this.parseField(f));  
				if (++numberOfFieldsProcessed < fieldsModel.length) {
					jsonString.append(","); 
				}  
			} 
			jsonString.append("}"); 
		} catch (ClassNotFoundException e) { 
			jsonString.delete(0, jsonString.length()); 
			jsonString.append("ClassNotFoundException Ocurred"); 
			e.printStackTrace(); 
		} catch (IllegalArgumentException e) {
			jsonString.delete(0, jsonString.length()); 
			jsonString.append("IllegalArgumentException Ocurred"); 
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			jsonString.delete(0, jsonString.length()); 
			jsonString.append("IllegalAccessException Ocurred"); 
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			jsonString.delete(0, jsonString.length()); 
			jsonString.append("NoSuchFieldException Ocurred"); 
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			jsonString.delete(0, jsonString.length()); 
			jsonString.append("SecurityException Ocurred"); 
			e.printStackTrace();
		} 
		return jsonString.toString(); 
	} 
	
	private FieldType[] getObjectFields(Object objToExplore) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// Field[] classTypeFields = classType.getDeclaredFields();
		Field[] classTypeFields = objToExplore.getClass().getDeclaredFields(); 
		ArrayList<FieldType> fieldTypeArr = new ArrayList<FieldType>(); 
		for (Field classTypeField : classTypeFields) { 
			if ((classTypeField.getName().indexOf("this") == -1) && (classTypeField.getType() != Context.class)) {
				Field objField = objToExplore.getClass().getDeclaredField(classTypeField.getName());
				// When a class is declared private, cannot access it's properties 
				objField.setAccessible(true); 
				// System.out.println("Field: " + field.getName() + " Type: " + field.getType()); 
				FieldType ft = new FieldType(objField.getName(), objField.getType(), objField.get(objToExplore));
				fieldTypeArr.add(ft); 
			} 
		} 
		return fieldTypeArr.toArray(new FieldType[fieldTypeArr.size()]); 
	} 
	
	private String parseField(FieldType f) { 
		StringBuilder jsonString = new StringBuilder(); 
		if (f.getFieldType() == String[].class) { 
			jsonString.append("\"" + f.getFieldName() + "\":["); 
			String[] values = (String[]) f.getFieldValue(); 
			for (int i=0; i<values.length; i++) { 
				jsonString.append("\"" + values[i] + "\""); 
				if (i<values.length-1) jsonString.append(","); 
			} 
			jsonString.append("]");  
		} else if (f.getFieldType() == String[][].class) {
			jsonString.append("\"" + f.getFieldName() + "\":["); 
			String[][] values = (String[][]) f.getFieldValue();
			for (int i=0; i<values.length; i++) {
				jsonString.append("["); 
				String[] valuesInnerArr = (String[]) values[i]; 
				for (int j=0; j<valuesInnerArr.length; j++) { 
					jsonString.append("\"" + valuesInnerArr[j] + "\""); 
					if (j<valuesInnerArr.length-1) jsonString.append(",");
				}
				jsonString.append("]");
				if (i<values.length-1) jsonString.append(","); 
			} 
			jsonString.append("]");  
		} else if(f.getFieldType() == HashMap.class) {
			jsonString.append("\"" + f.getFieldName() + "\":{"); 
			HashMap<?,?> tempHashMap = (HashMap<?, ?>) f.getFieldValue(); 
			int mapLength = tempHashMap.keySet().toArray().length; 
			int i = 0; 
			for (Entry<?, ?> entry : tempHashMap.entrySet()) {
				i += 1; 
				jsonString.append("\"" + entry.getKey() + "\":"); 
				jsonString.append("\"" + entry.getValue() + "\""); 
				if (i<mapLength) jsonString.append(","); 
			} 
			jsonString.append("}"); 
		} else if (f.getFieldType() == Integer[][].class) { 
			jsonString.append("\"" + f.getFieldName() + "\":["); 
			Integer[][] values = (Integer[][]) f.getFieldValue();
			for (int i=0; i<values.length; i++) {
				jsonString.append("["); 
				Integer[] valuesInnerArr = (Integer[]) values[i];
				for (int j=0; j<valuesInnerArr.length; j++) { 
					jsonString.append("\"" + valuesInnerArr[j] + "\""); 
					if (j<valuesInnerArr.length-1) jsonString.append(",");
				} 
				jsonString.append("]"); 
				if (i<values.length-1) jsonString.append(","); 
			} 
			jsonString.append("]");  
		} else if (f.getFieldType() == Integer[].class) {
			jsonString.append("\"" + f.getFieldName() + "\":["); 
			Integer[] values = (Integer[]) f.getFieldValue(); 
			for (int i=0; i<values.length; i++) { 
				jsonString.append("\"" + values[i] + "\""); 
				if (i<values.length-1) jsonString.append(","); 
			} 
			jsonString.append("]");
		} else if (f.getFieldType() == Integer.class) { 
			jsonString.append("\"" + f.getFieldName() + "\":");
			jsonString.append("\"" + f.getFieldValue() + "\"");
		} else if (f.getFieldType() == int.class) {  
			jsonString.append("\"" + f.getFieldName() + "\":");
			jsonString.append("\"" + f.getFieldValue() + "\"");
		} else if (f.getFieldType() == String.class) { 
			jsonString.append("\"" + f.getFieldName() + "\":");
			jsonString.append("\"" + f.getFieldValue() + "\""); 
		}  else if (f.getFieldType() == Boolean.class) {
			jsonString.append("\"" + f.getFieldName() + "\":");
			jsonString.append("\"" + f.getFieldValue() + "\"");
		} else if (f.getFieldType() == boolean.class) {
			jsonString.append("\"" + f.getFieldName() + "\":");
			jsonString.append("\"" + f.getFieldValue() + "\"");
		} else {
			throw new IllegalArgumentException("Cannot recognize field type: " + f.toString()); 
		} 
		return jsonString.toString(); 
	}  
} 

