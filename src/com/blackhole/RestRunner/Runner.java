package com.blackhole.RestRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.AmbientLight;

import com.blackhole.App.AppFacade;
import com.blackhole.App.Context;
import com.blackhole.RestRunner.Annotations.AnnotationModel;
import com.blackhole.RestRunner.Annotations.DELETE;
import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.PATH;
import com.blackhole.RestRunner.Annotations.AnnotationModel.AnnotationType;
import com.blackhole.RestRunner.Annotations.PUT;
import com.sun.accessibility.internal.resources.accessibility;

public class Runner { 
	// private static ArrayList<String> endPoints = new ArrayList<String>();  
	// Change this class: 
	// - Create a new  method that will run annotation methods defined in vars 
	// - Create a new  method that analyzes methods in a class and keep in an array 
	// - ... 
	
	private Class<?> annotatedClassToExplore = null; 
	private String pathToSearch = null; 
	private String methodType = null; 
	
	public  Runner(Class<?> annotatedClass, String pathToSearch, String methodType) { 
		this.annotatedClassToExplore = annotatedClass; 
		this.pathToSearch = pathToSearch; 
		this.methodType = methodType; 
		try {
			getClass().getClassLoader().loadClass(annotatedClass.getName()); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); 
		}
	} 
	
	public Object exploreAnnotatedClass() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		AnnotationModel am = null; 
		Method[] methodsClassImpl = this.annotatedClassToExplore.getDeclaredMethods();    
		Object result = null; 
		for (Method m : methodsClassImpl) {  
			am = this.getModelFromAnnotations(m);  
			m.toString(); 
			if (am != null) {
				if (am.getPathMatches() && am.getValues().length == 0) { 
					// Run non parameters method method 
					result = m.invoke(this.annotatedClassToExplore.newInstance()); 
				} else if (am.getPathMatches() && am.getValues().length > 0 ) {
					// Run method with parameters 
					// ... 
					result = m.invoke(this.annotatedClassToExplore.newInstance(),am.getValues());
				} 
			} 
		}  
		return result; 
	}
	
	private AnnotationType getAnnotationHttpVerb(Annotation annotation) { 
		AnnotationType atResult = null; 
		if (annotation.annotationType().equals(GET.class)) { 
			atResult = AnnotationType.GET; 
		} else if (annotation.annotationType().equals(POST.class)) {
			atResult = AnnotationType.POST; 
		} else if (annotation.annotationType().equals(PUT.class)) {
			atResult = AnnotationType.PUT; 
		} else if (annotation.annotationType().equals(DELETE.class)) {
			atResult = AnnotationType.DELETE; 
		}
		return atResult;   
	}
	
	private String getAnnotationPath(Annotation annotation) { 
		// Method has path annotation without parameters 
		PATH p = (PATH) annotation; 
		return p.value(); 
	} 
	
	private Object[] getAnnotationParameters(Method m, Annotation a) { 
		Annotation[][] paramAnnotations = m.getParameterAnnotations();  
		List<Object> paramValuesAnnotationsParsed = new ArrayList<Object>(); 
		
		if (paramAnnotations != null && paramAnnotations.length>0) { 
			// Method has parameters in his annotations 
			String methodAnnotationPathParameterFields[] =  ((PATH) a).value().split("/");  
			String pathToSearchParameters[] = this.pathToSearch.split("/"); 
			
			if (methodAnnotationPathParameterFields.length == pathToSearchParameters.length) {
				// Parameters matches 
				for (int i=0; i<methodAnnotationPathParameterFields.length;i++) {
					String annotationField = methodAnnotationPathParameterFields[i]; 
					String pathField = pathToSearchParameters[i]; 
					if (annotationField.indexOf("{")>-1) { 
						// Value on path it's a param 
						paramValuesAnnotationsParsed.add(pathField); 
					} else {
						// Value it's a single string, check that matches 
						if (!annotationField.equals(pathField)) { 
							paramValuesAnnotationsParsed.clear(); 
							break; 
						}
					} 
				}
			}
		} 
		return paramValuesAnnotationsParsed.toArray();   
	} 
	
	private boolean checkPathMatches(Annotation a) { 
		PATH annotationPath = (PATH) a;
		String[] annotationPathArray = ((PATH) a).value().split("/"); 
		String[] pathToSearchFields = this.pathToSearch.split("/"); 
		if (annotationPathArray.length == pathToSearchFields.length && annotationPath.value().indexOf("{")==-1) { 
			for (int i=0; i<pathToSearchFields.length;i++) {
				if (!annotationPathArray[i].equals(pathToSearchFields[i])) {
					return false;
				} 
			}  
			return true; 
		} else if (annotationPathArray.length == pathToSearchFields.length && annotationPath.value().indexOf("{")>-1) { 
			for (int i=0; i<pathToSearchFields.length;i++) { 
				// Check value is not null or empty 
				if (annotationPathArray[i].toString().indexOf("{")==-1 && !annotationPathArray[i].equals(pathToSearchFields[i])) {
					return false; 
				} 
			}
			return true; 
		} 
		return false; 
	} 
	
	private AnnotationModel getModelFromAnnotations(Method m) { 
		// Create a model to handle easily each annotation 
		AnnotationModel modelResult = new AnnotationModel(); 
		Annotation[] methodAnnotations = m.getDeclaredAnnotations();
		// 2 Types of annotations: 
		// 	- HTTP verb annotation - which type of request must be  
		// 	- PATH annotation - which path must consume 
		for (Annotation annotation : methodAnnotations) { 
			if (annotation.annotationType().equals(GET.class) || 
				annotation.annotationType().equals(POST.class) || 
				annotation.annotationType().equals(PUT.class) || 
				annotation.annotationType().equals(DELETE.class)) 
			{ 
				modelResult.setType(this.getAnnotationHttpVerb(annotation)); 
			} else if (annotation.annotationType().equals(PATH.class)) 
			{ 
				modelResult.setPath(this.getAnnotationPath(annotation)); 
				Object[] parameters = this.getAnnotationParameters(m, annotation); 
				modelResult.setValues(parameters); 
				modelResult.setPathMatches(this.checkPathMatches(annotation));  
			} 
		} 
		return modelResult; 
	} 
} 
