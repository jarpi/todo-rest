package com.blackhole.RestRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.blackhole.RestRunner.Annotations.AnnotationModel;
import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.PATH;
import com.blackhole.RestRunner.Annotations.AnnotationModel.AnnotationType;

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
					}  else { 
						if (am.getType().toString() == "") am.setType(AnnotationType.NONE);  
					} 
					// need to get if there are values to parse when request comes ANDALSO how much and type of this parameters 
					// TODO do it recursively 
					if (a.annotationType().equals(PATH.class)) {
						String path; 
						Object value; 
						if (a.toString().indexOf("{")>-1) { 
							// Parameter/s defined ---> Parse request to get params 
							path = a.toString().substring(0,a.toString().indexOf("{")); 
							String values = a.toString().substring(a.toString().indexOf("}"));   
						}
						PATH p = (PATH) a; 
						am.setPath(p.value()); 
					} 
				}  
				try { 
					if (am.getType().toString().equals(methodType) && am.getPath().equals(pathToSearch)) {
						m.invoke(annotatedClass.newInstance()); 
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
