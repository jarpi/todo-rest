package com.blackhole.RestRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.blackhole.RestRunner.AnnotationModel.types;
import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.Path;

public class Runner { 
	// private static ArrayList<String> endPoints = new ArrayList<String>();  
	public Runner(Class<?> annotatedClass, String pathToSearch, String methodType) { 
		try { 
			getClass().getClassLoader().loadClass(annotatedClass.getName()); 
			Method[] methodsClassImpl = annotatedClass.getDeclaredMethods();   
			for (Method m : methodsClassImpl) { 
				// Get annotations 
				Annotation[] annotationsMethod = m.getDeclaredAnnotations(); 
				AnnotationModel am = new AnnotationModel(); 
				for (Annotation a : annotationsMethod) { 
					if (a.annotationType().equals(GET.class)) { 
						am.setType(types.GET); 
					} else if (a.annotationType().equals(POST.class)) {
						am.setType(types.POST); 
					}  else { 
						if (am.getType().toString() == "") am.setType(types.NONE);  
					} 
					if (a.annotationType().equals(Path.class)) {  
						Path p = (Path) a; 
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
