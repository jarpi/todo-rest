package com.blackhole.App;

import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.PATH;

public class BusinessLogic {
	public BusinessLogic() {} 
	
	@GET 
	@PATH(value="/test")  
	public void testing() {System.out.println("AAA");}
	
	@GET
	@PATH(value="/test2")   
	public void testing2() {System.out.println("BBB");} 
	
	@POST 
	@PATH(value="/test3")  
	public void testing3() {System.out.println("CCC");} 
}
