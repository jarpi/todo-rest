package com.blackhole.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.blackhole.App.Context;

public class ObjectToJsonTest {
	public class TestContainer {
		private String[] testArr = {"ttt","ttt","ttt"};
		private String[] testArr2 = {"aaa","aaa","aaa"}; 
		private String[] testArr3 = {"bbb","bbb","bbb"}; 
		private Integer[] testIntArr = {1,1,1};
		private Integer[] testIntArr2 = {2,2,2,2,2}; 
		private Integer[] testIntArr3 = {3,3,3,3,3,3,3}; 
		private String[][] testArrList = {testArr,testArr2,testArr3};
		private String[][] testArrList2 = {testArr,testArr2,testArr3};
		private Integer[][] testIntArrList = {testIntArr,testIntArr2,testIntArr3}; // TODO object to json must convert array of arrays
		// private String[][][] testAAA = {testArrList,testArrList2};    
		// Constructor 
		public TestContainer(){}  
	}
	
	@Test
	public void test() { 
		String expectedValue = "{\"testArr\":[\"ttt\",\"ttt\",\"ttt\"],\"testArr2\":[\"aaa\",\"aaa\",\"aaa\"],\"testArr3\":[\"bbb\",\"bbb\",\"bbb\"],\"testIntArr\":[\"1\",\"1\",\"1\"],\"testIntArr2\":[\"2\",\"2\",\"2\",\"2\",\"2\"],\"testIntArr3\":[\"3\",\"3\",\"3\",\"3\",\"3\",\"3\",\"3\"],\"testArrList\":[[\"ttt\",\"ttt\",\"ttt\"],[\"aaa\",\"aaa\",\"aaa\"],[\"bbb\",\"bbb\",\"bbb\"]],\"testArrList2\":[[\"ttt\",\"ttt\",\"ttt\"],[\"aaa\",\"aaa\",\"aaa\"],[\"bbb\",\"bbb\",\"bbb\"]],\"testIntArrList\":[[\"1\",\"1\",\"1\"],[\"2\",\"2\",\"2\",\"2\",\"2\"],[\"3\",\"3\",\"3\",\"3\",\"3\",\"3\",\"3\"]]}";
		TestContainer tc = new TestContainer(); 
		Context c = new Context(); 
		String result = c.mObjUtilsInstance.ToJSON(tc); 
		assertEquals(result, expectedValue); 
	} 
} 
