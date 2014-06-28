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
				// Define an annotation model, it handles type of annotation (GET, POST) and relative path (Path) 
				AnnotationModel am = new AnnotationModel(); 
				for (Annotation a : annotationsMethod) { 
					if (a.annotationType().equals(GET.class)) { 
						am.setType(AnnotationType.GET); 
					} else if (a.annotationType().equals(POST.class)) {
						am.setType(AnnotationType.POST); 
					}  else { 
						if (am.getType().toString() == "") am.setType(AnnotationType.NONE);  
					} 
					if (a.annotationType().equals(PATH.class)) {  
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
