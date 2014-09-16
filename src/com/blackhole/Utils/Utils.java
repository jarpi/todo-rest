package com.blackhole.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

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
	
	public void Utils() {} 
	
	public static Utils getInstance() {
		if (Utils.mInstance == null) {
			Utils.mInstance = new Utils(); 
		}
		return Utils.mInstance; 
	} 

	
	public String ObjectToJSON(Object[] objArr, Class<?> cl) throws Exception {
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("[");
		for (int i=0; i<objArr.length; i++) { 
			Object obj = objArr[i]; 
			String jsonObjStr = this.ObjectToJson(obj, cl); 
			jsonString.append(jsonObjStr); 
			if (i<objArr.length-1) {  
				jsonString.append(","); 
			} 
		} 
		jsonString.append("]");
		return jsonString.toString(); 
	}
	
	public String ObjectToJson(Object obj, Class<?> cl) throws Exception { 
		StringBuilder jsonString = new StringBuilder(); 
		try {
			getClass().getClassLoader().loadClass(cl.getName());
			FieldType[] fieldsModel = this.exploreMethodFields(obj, cl); 
			jsonString.append("{"); 
			int numberOfFieldsProcessed = 0; 
			for (FieldType f : fieldsModel) {  
				if (f.getFieldType() == String[].class) { 
					jsonString.append("\"" + f.getFieldName() + "\":["); 
					String[] values = (String[]) f.getFieldValue(); 
					for (int i=0; i<values.length; i++) { 
						jsonString.append("\"" + values[i] + "\""); 
						if (i<values.length-1) jsonString.append(","); 
					} 
					jsonString.append("]");  
					numberOfFieldsProcessed +=1;
				} else if (f.getFieldType() == Integer[].class) {
					jsonString.append("\"" + f.getFieldName() + "\":["); 
					Integer[] values = (Integer[]) f.getFieldValue(); 
					for (int i=0; i<values.length; i++) { 
						jsonString.append("\"" + values[i] + "\""); 
						if (i<values.length-1) jsonString.append(","); 
					} 
					jsonString.append("]");
					numberOfFieldsProcessed +=1;
				} else if (f.getFieldType() == Integer.class) { 
					jsonString.append("\"" + f.getFieldName() + "\":");
					jsonString.append("\"" + f.getFieldValue() + "\"");
					numberOfFieldsProcessed +=1;
				} else if (f.getFieldType() == int.class) {  
					jsonString.append("\"" + f.getFieldName() + "\":");
					jsonString.append("\"" + f.getFieldValue() + "\"");
					numberOfFieldsProcessed +=1;
				} else if (f.getFieldType() == String.class) {
					jsonString.append("\"" + f.getFieldName() + "\":");
					jsonString.append("\"" + f.getFieldValue() + "\"");
					numberOfFieldsProcessed +=1;
				}  else {
					throw new Exception("Cannot recognize field type: " + f.toString()); 
				} 
				if (numberOfFieldsProcessed < fieldsModel.length) {
					jsonString.append(","); 
				} 
			} 
			jsonString.append("}");
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); 
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return jsonString.toString(); 
	} 
	
	private FieldType[] exploreMethodFields(Object objToExplore, Class<?> classType) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field[] classTypeFields = classType.getDeclaredFields(); 
		ArrayList<FieldType> fieldTypeArr = new ArrayList<FieldType>(); 
		for (Field classTypeField : classTypeFields) { 
			if ((classTypeField.getName().indexOf("this") == -1)) {
				Field objField = objToExplore.getClass().getDeclaredField(classTypeField.getName());
				objField.setAccessible(true); 
				// System.out.println("Field: " + field.getName() + " Type: " + field.getType()); 
				FieldType ft = new FieldType(objField.getName(), objField.getType(), objField.get(objToExplore));
				fieldTypeArr.add(ft); 
			} 
		} 
		return fieldTypeArr.toArray(new FieldType[fieldTypeArr.size()]); 
	} 
} 

