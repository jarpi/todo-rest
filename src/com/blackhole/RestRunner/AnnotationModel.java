package com.blackhole.RestRunner;

public class AnnotationModel {
	public enum AnnotationType {
		GET("GET"), 
		POST("POST"), 
		NONE("NONE"); 
		
		String annotationName; 
		AnnotationType(String value) {
			this.annotationName = value; 
		}
		
		@Override 
		public String toString() {
			return this.annotationName; 
		} 
	} 
	private AnnotationType type; 
	private String path; 
	
	public AnnotationModel(){}
	
	public AnnotationModel(AnnotationType type, String path) {
		this.type = type; 
		this.path = path; 
	} 
	
	public AnnotationType getType() { return this.type; } 
	public String getPath() { return this.path; } 
	public void setType(AnnotationType type) {this.type = type;}
	public void setPath(String path) {this.path = path;}
	
}
