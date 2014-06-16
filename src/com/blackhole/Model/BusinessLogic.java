package com.blackhole.Model;

import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.Path;

public class BusinessLogic {
	public BusinessLogic() {} 
	
	@GET 
	@Path(value="/test")  
	public void testing() {System.out.println("AAA");}
	
	@GET
	@Path(value="/test2")   
	public void testing2() {System.out.println("BBB");} 
	 
}
