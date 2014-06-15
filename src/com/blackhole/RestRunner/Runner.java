package com.blackhole.RestRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.blackhole.RestRunner.annotations.Path;

public class Runner { 
	private static ArrayList<String> endPoints = new ArrayList<String>();  
	public Runner(Class<?> annotatedClass, String pathToSearch) {
		try { 
			getClass().getClassLoader().loadClass(annotatedClass.getName()); 
			Method[] methodsClassImpl = annotatedClass.getMethods();  
			for (Method m : methodsClassImpl) { 
				// Get annotations 
				Annotation[] annotationsMethod = m.getDeclaredAnnotations();
				for (Annotation a : annotationsMethod) { 
					if (a.annotationType().equals(Path.class)) {  
						try { 
							Path p = (Path) a; 
							if (p.value().equals(pathToSearch)) {
								m.invoke(annotatedClass.newInstance()); 
							}  
						} catch (IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
						}  
					} 
				} 
				// Do anything with methods run... 
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SecurityException e) {
			e.printStackTrace();
		}  
	} 
	
}
