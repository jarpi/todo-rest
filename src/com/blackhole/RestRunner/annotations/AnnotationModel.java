package com.blackhole.RestRunner.Annotations;

// REST annotation model, contains info about annotation state 
public class AnnotationModel { 
	// As we know wich are request verbs, we can create a well-known variable to handle it 
	public enum AnnotationType {
		GET("GET"), 
		POST("POST"), 
		NONE("NONE"); 
		
		String annotationName = ""; 
		AnnotationType(String value) {
			this.annotationName = value; 
		}
		
		@Override 
		public String toString() {
			return this.annotationName; 
		} 
	} 
	private AnnotationType type = AnnotationType.NONE; 
	private String path;
	// This variable refers to {id} parameter on request, as we don't know wich type of variable will be, it need to declare as 
	// Object type. 
	private Object value; 
	
	public AnnotationModel(){}
	
	public AnnotationModel(AnnotationType type, String path, Object value) {
		this.type = type; 
		this.path = path; 
		this.value = value; 
	} 
	
	public AnnotationType getType() { return this.type; } 
	public String getPath() { return this.path; }
	public Object getValue() {return this.value; }
	public void setType(AnnotationType type) {this.type = type;}
	public void setPath(String path) {this.path = path;}
	public void setValue(Object value) {this.value = value;} 
}
