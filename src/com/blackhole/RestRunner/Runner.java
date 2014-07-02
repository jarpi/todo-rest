package com.blackhole.RestRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.blackhole.RestRunner.Annotations.AnnotationModel;
import com.blackhole.RestRunner.Annotations.DELETE;
import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.PATH;
import com.blackhole.RestRunner.Annotations.AnnotationModel.AnnotationType;
import com.blackhole.RestRunner.Annotations.PUT;

public class Runner { 
	// private static ArrayList<String> endPoints = new ArrayList<String>();  
	// Change this class: 
	// - Create a new  method that will run annotation methods defined in vars 
	// - Create a new  method that analyzes methods in a class and keep in an array 
	// - ... 
	public Runner(Class<?> annotatedClass, String pathToSearch, String methodType) { 
		try { 
			getClass().getClassLoader().loadClass(annotatedClass.getName()); 
			Method[] methodsClassImpl = annotatedClass.getDeclaredMethods();   
			for (Method m : methodsClassImpl) {  
				// Get annotations by method 
				Annotation[] annotationsMethod = m.getDeclaredAnnotations();
				// Define an annotation model, it handles verb request (GET, POST) and relative path (Path) 
				// It creates an annotationModel object that it's more useful as once created and filled, it'll be possible 
				// to manage and organize rest requests 
				AnnotationModel am = new AnnotationModel(); 
				for (Annotation a : annotationsMethod) { 
					// get method verb 
					if (a.annotationType().equals(GET.class)) { 
						am.setType(AnnotationType.GET); 
					} else if (a.annotationType().equals(POST.class)) {
						am.setType(AnnotationType.POST); 
					} else if (a.annotationType().equals(PUT.class)) {
						am.setType(AnnotationType.PUT); 
					} else if (a.annotationType().equals(DELETE.class)) {
						am.setType(AnnotationType.DELETE); 
					}  else { 
						if (am.getType().toString() == "") am.setType(AnnotationType.NONE);  
					} 
					// get params  
					// TODO multi params 
					// TODO do it recursively 
					if (a.annotationType().equals(PATH.class)) { 
						Annotation[][] paramAnnotations = m.getParameterAnnotations();  
						if (paramAnnotations != null && paramAnnotations.length>0) { 
							PATH p = (PATH) a; 
							am.setPath(p.value());
							String requestFields[] = pathToSearch.split("/"); 
							String annotationPathFields[] = p.value().split("/");  
							if (annotationPathFields.length==requestFields.length) { 
								// Length of params matches 
								// Parameter/s defined ---> Parse request to get params 
								/* for (Annotation[] paramAnnotation : paramAnnotations) {
								} */ 
								// Extract arguments to send to method invocation 
								boolean pathsMatches = true; 
								for (int i=0;i<annotationPathFields.length;i++) { 
									String annotationPathField = annotationPathFields[i]; 
									String requestPathField = requestFields[i]; 
									if (annotationPathField.indexOf("{")>-1) {
										am.addValue(requestPathField);  
									} else {
										if (!annotationPathField.equals(requestPathField)) pathsMatches = false; 
									}  
								}
								if (pathsMatches) am.setPathMatches(true); 
							}  
						}  else { 
							PATH p = (PATH) a; 
							am.setPath(p.value()); 
							if (am.getPath().equals(pathToSearch)) {
								am.setPathMatches(true); 
							} 
						} 
					} 
				}  
				try { 
					if (am.getType().toString().equals(methodType) && am.getPathMatches()) {
						if (am.getValues() != null) {
							m.invoke(annotatedClass.newInstance(), am.getValues()); 	
						} else { 
							m.invoke(annotatedClass.newInstance());
						} 
						break; 
					}  
				} catch (IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
				// Do anything with methods run... 
			} 
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SecurityException e) {
			e.printStackTrace();
		}  
	} 
}
