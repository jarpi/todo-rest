package com.blackhole.communication;

import com.blackhole.annotations.Path;

public class BusinessLogic {
	public BusinessLogic() {} 
	
	@Path(value="/test", param="aaa")  
	public void testing() {System.out.println("AAA");}
	@Path(value="/test2", param="aaa")   
	public void testing2() {System.out.println("BBB");} 
	 
}
