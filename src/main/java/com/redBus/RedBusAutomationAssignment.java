package com.redBus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedBusAutomationAssignment {

	public static void main(String[] args) throws InterruptedException {
		ChromeOptions chromeoption = new ChromeOptions();

		chromeoption.addArguments("--start-maximized");

		WebDriver wd = new ChromeDriver(chromeoption);
		WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(20));

		wd.get("https://www.redbus.in");
		By sourceButtonLocator = By.xpath("//div[contains(@class,'srcDestWrapper') and @role='button']");
		WebElement sourceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(sourceButtonLocator));
		sourceButton.click();
		By searchSuggestionSelectionLocator = By.xpath("//div[contains(@class,\"searchSuggestionWrapper\")]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchSuggestionSelectionLocator));

		selectlocation(wd, wait,"nagpur");
		selectlocation(wd,wait,"pune");
		
		 By searchButtonLocator = By.xpath("//button[contains(@class,\"searchButtonWrapper\")]");
		 WebElement searchButton=wd.findElement(searchButtonLocator);
		 searchButton.click();
		 
		 By PrimoBusLocator = By.xpath("//div[contains(text(),'Primo Bus')]");
		 WebElement primoBusFilterElement=wait.until(ExpectedConditions.elementToBeClickable(PrimoBusLocator));
		 primoBusFilterElement.click();
		 
		 Thread.sleep(1000);  //dealy for giving time to load rows
		 
		 By sleeperBusLocator = By.xpath("//div[contains(text(),'SLEEPER ')]");
		 WebElement sleeperFilterElement=wait.until(ExpectedConditions.elementToBeClickable(sleeperBusLocator));
		 sleeperFilterElement.click();
		 
		 By subtitleLocator = By.xpath("//span[contains(@class,'subtitle')]");
		 WebElement subtitleElement=null;
		 if(wait.until(ExpectedConditions.textToBePresentInElementLocated(subtitleLocator,"buses"))) {
		subtitleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(subtitleLocator)); 
		 }
		 System.out.println(subtitleElement.getText());
		 
		 By busesNameLocator = By.xpath(".//div[contains(@class,'travelsName')]");
		  By tuppleWrapperLocator = By.xpath("//li[contains(@class,'tupleWrapper')]");
		  JavascriptExecutor js = (JavascriptExecutor)wd;
		     while(true) {  //Lazy Loading
		    	 List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
		    	 List<WebElement>endOfList=wd.findElements(By.xpath("//span[contains(@class,'endText')]"));
		    	
		    	 if(!endOfList.isEmpty()) {  //exist condition for while loop
		    		break;
		    	}
		    js.executeScript("arguments[0].scrollIntoView({behavior:'smooth'})", rowList.get(rowList.size()-3));
		     }
		     List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
		     for(WebElement row:rowList) {
		    System.out.println(row.findElement(busesNameLocator).getText());
		     } 
		    System.out.println("total no of buses loaded "+ rowList.size());
		    
		    wd.quit();
		     
	}
	
	public static void selectlocation(WebDriver wd, WebDriverWait wait, String LocationData) {
		WebElement searchTextBoxElement = wd.switchTo().activeElement();
		searchTextBoxElement.sendKeys(LocationData);
		By searchCategoryLocator = By.xpath("//div[contains(@class,\"searchSuggestionWrapper\")]/div");
		List<WebElement> searchList = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLocator, 1));
		System.out.println(searchList.size());
		WebElement locationSearchResult = searchList.get(0);
		By locationNameLocator = By.xpath(".//div[contains(@class,\"listHeader\")]");
		List<WebElement> locationList = locationSearchResult.findElements(locationNameLocator);
		System.out.println(locationList.size());
		for (WebElement location : locationList) {
			System.out.println(location.getText());
			if (location.getText().equalsIgnoreCase(LocationData)) {
				location.click();
				break;
			}
		}
	}

}
