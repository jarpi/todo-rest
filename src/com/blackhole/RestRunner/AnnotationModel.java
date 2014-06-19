package com.blackhole.RestRunner;

public class AnnotationModel {
	public enum types {
		GET("GET"), 
		POST("POST"), 
		NONE("NONE"); 
		
		String value; 
		types(String value) {
			this.value = value; 
		}
		
		@Override 
		public String toString() {
			return this.value; 
		} 
	} 
	private types type; 
	private String path; 
	
	public AnnotationModel(){}
	
	public AnnotationModel(types type, String path) {
		this.type = type; 
		this.path = path; 
	} 
	
	public types getType() { return this.type; } 
	public String getPath() { return this.path; } 
	public void setType(types type) {this.type = type;}
	public void setPath(String path) {this.path = path;}
	
}
