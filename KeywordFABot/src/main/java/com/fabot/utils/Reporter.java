package com.fabot.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public abstract class Reporter{
	
	public static ExtentHtmlReporter reporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	public String testcaseName, testcaseDec, author, category;
	public static  String excelFileName;
	public static String timeStamp;
	@BeforeSuite
	public void startReport() {
		timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportPath="./reports/report"+timeStamp;
		new File(reportPath+"/images").mkdirs();
		reporter = new ExtentHtmlReporter(reportPath+"/result.html");
		reporter.setAppendExisting(true); 
		extent   = new ExtentReports();
		extent.attachReporter(reporter);
	}
	
    /*@BeforeClass
	public void report() throws IOException {
		test = extent.createTest(testcaseName, testcaseDec);
	    test.assignAuthor(author);
	    test.assignCategory(category);  
	}*/
    
    public abstract long takeSnap();
    
    public void reportStep(String desc, String status,boolean bSnap ) {
    	MediaEntityModelProvider img = null;
		if(bSnap && !status.equalsIgnoreCase("INFO")){

			long snapNumber = 100000L;
			snapNumber = takeSnap();
			try {
				img = MediaEntityBuilder.createScreenCaptureFromPath
						("./../reports/report"+timeStamp+"/images/"+snapNumber+".jpg").build();
			} catch (IOException e) {
				
			}
		}
    	if(status.equalsIgnoreCase("pass")) {
    		test.pass(desc, img);
    	} else if(status.equalsIgnoreCase("fail")) {
    		test.fail(desc, img); 
    	} else if(status.equalsIgnoreCase("INFO")) {
    		test.pass(desc); 
    	}
    }
    public void reportStep(String desc, String status) {
		reportStep(desc, status, true);
	}

    
    @AfterSuite
    public void stopReport() {
    	extent.flush();
    }
}














