package com.fabot.keyword.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.fabot.selenium.api.base.SeleniumBase;

public class KeyWordEngine extends SeleniumBase{

	//public WebDriver driver;
	public Properties prop;

	public static Workbook book;
	public static Sheet sheet;

	//public Base base;
	public WebElement element;

	public final String SCENARIO_SHEET_PATH = "./src/main/java/com/fabot/keyword/scenarios/connect_scenarios.xlsx";

	public void startExecution(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}

		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
		int k = 0;
		
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			try {
				
				if(sheet.getRow(i + 1).getCell(k).toString().trim().equals("Y"))
				{
				String locatorType = sheet.getRow(i + 1).getCell(k + 3).toString().trim();
				String locatorValue = sheet.getRow(i + 1).getCell(k + 4).toString().trim();
				String action = sheet.getRow(i + 1).getCell(k + 5).toString().trim();
				String value = sheet.getRow(i + 1).getCell(k + 6).toString().trim();
				
				
				if(sheet.getRow(i + 1).getCell(k + 1).toString().trim().equals("Y"))
				{
					testcaseName = sheet.getRow(i + 1).getCell(k + 7).toString().trim();
					testcaseDec = sheet.getRow(i + 1).getCell(k + 8).toString().trim();
					author = sheet.getRow(i + 1).getCell(k + 9).toString().trim();
					category = sheet.getRow(i + 1).getCell(k + 10).toString().trim();
					test = extent.createTest(testcaseName, testcaseDec);
				    test.assignAuthor(author);
				    test.assignCategory(category);  
				}

				switch (action) {
				
				case "open browser":
					prop = init_properties();
					if (value.isEmpty() || value.equals("NA")) openBrowser(prop.getProperty("browser"));
					else openBrowser(value);
					break;

				case "enter url":
					if (value.isEmpty() || value.equals("NA")) driver.get(prop.getProperty("url"));
					else driver.get(value);
					break;

				case "quit":
					driver.quit();
					break;
				
				default:
					break;
				}

				switch (locatorType) {
				case "id":
					element = driver.findElement(By.id(locatorValue));
					performAction(action, value);
					locatorType = null;
					break;

				case "name":
					element = driver.findElement(By.name(locatorValue));
					performAction(action, value);
					locatorType = null;
					break;

				case "xpath":
					element = driver.findElement(By.xpath(locatorValue));
					performAction(action, value);
					locatorType = null;
					break;

				case "cssSelector":
					element = driver.findElement(By.cssSelector(locatorValue));
					performAction(action, value);
					locatorType = null;
					break;

				case "className":
					element = driver.findElement(By.className(locatorValue));
					performAction(action, value);
					locatorType = null;
					break;

				case "linkText":
					element = driver.findElement(By.linkText(locatorValue));
					element.click();
					locatorType = null;
					break;

				case "partialLinkText":
					element = driver.findElement(By.partialLinkText(locatorValue));
					element.click();
					locatorType = null;
					break;

				default:
					break;
				}

			}}
			
			catch (Exception e) {
				System.out.println("end exception");
				e.printStackTrace();
			}

		}

	}
	
	public void performAction(String action,String value) 
	{
		if (action.equalsIgnoreCase("sendkeys")) clearAndType(element, value);
		else if (action.equalsIgnoreCase("click")) click(element);
		else if (action.equalsIgnoreCase("isDisplayed")) verifyDisplayed(element);
		else if (action.equalsIgnoreCase("getText")) getElementText(element);
		else if (action.equalsIgnoreCase("verifyText")) verifyExactText(element, value);
	}
	
}

