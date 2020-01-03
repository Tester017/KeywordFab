package com.fabot.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fabot.keyword.engine.KeyWordEngine;

public class LoginTest extends KeyWordEngine{
	
	
	@Test
	public void loginTest(){
		startExecution("login");
	}
	
	
	
	

}

