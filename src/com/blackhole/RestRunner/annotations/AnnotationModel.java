package com.blackhole.RestRunner.Annotations;

import java.util.ArrayList;
import java.util.List;

// REST annotation model, contains info about annotation state 
public class AnnotationModel { 
	// As we know wich are request verbs, we can create a well-known variable to handle it 
	public enum AnnotationType {
		GET("GET"), 
		POST("POST"),
		PUT("PUT"), 
		DELETE("DELETE"), 
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
	private AnnotationType mType = AnnotationType.NONE; 
	private String mPath=""; 
	// This variable refers to {id} parameter on request, as we don't know wich type of variable will be, it need to declare as 
	// Object type. 
	private List<Object> mParams = new ArrayList<Object>(); 
	private boolean mPathMatch = false; 
	
	public AnnotationModel(){}
	
	public AnnotationModel(AnnotationType type, String path) {
		this.mType = type; 
		this.mPath = path; 
	} 
	
	public AnnotationType getType() { return this.mType; } 
	public String getPath() { return this.mPath; }
	public Object[] getValues() {return this.mParams.toArray(); }
	public void setValues(Object[] values) {for (int i=0; i<values.length;i++) {this.addValue(values[i]);}} 
	public void setType(AnnotationType type) {this.mType = type;}
	public void setPath(String path) {this.mPath = path;}
	public void setPathMatches(boolean match) {this.mPathMatch = match;}
	public void addValue(Object value) {this.mParams.add(value);} 
	public boolean getPathMatches() {return this.mPathMatch;}
}
