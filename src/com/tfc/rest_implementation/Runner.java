package com.tfc.rest_implementation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Runner { 
	private static ArrayList<String> endPoints = new ArrayList<String>();  
	public Runner(Class <?> annotatedClass, Class <?> annotationName) {
		try { 
			Class<?> c = getClass().getClassLoader().loadClass(annotatedClass.getName()); 
			// Object classObj = (Class<?>) c.newInstance();  
			Method[] methodsClassImpl = annotatedClass.getMethods();  
			for (Method m : methodsClassImpl) { 
				// Get annotations 
				Annotation[] annotationsMethod = m.getAnnotations(); 
				for (Annotation a : annotationsMethod) {
					if (a.annotationType().equals(annotationName)) {  
						try {
							m.invoke(annotatedClass.newInstance());  
						} catch (IllegalArgumentException
								| InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				} 
				// Do anything with methods run... 
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}  
	} 
	
}
