package test;

import org.testng.annotations.Test;

import pgobjects.LoginPgObj;

import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



import utilities.BaseClass;

public class ToolsDemoTest extends BaseClass {
	final static Logger log = Logger.getLogger(ToolsDemoTest.class.getName());
	
	@BeforeMethod
	public void openBrwsr(){
		commonUtils.getUrl(prop.getProperty("url"));
		log.info("url is :"+prop.getProperty("url"));
		driver.manage().window().maximize();
		try {
			commonUtils.takeSnapShot("HomePage.png") ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Test
	public void loginTest() throws Exception {
		//start of login test
		commonUtils.takeSnapShot("LoginSuccess.png") ;
		String userName = simpleExcelReaderExample.getStrValue("Login_credentials.xlsx",1, "UserName");
		String passWord = simpleExcelReaderExample.getStrValue("Login_credentials.xlsx",1, "Password");
		commonUtils.sendKeys(loginPgObj.userName_txt,userName );
		commonUtils.sendKeys(loginPgObj.password_txt,passWord );
		commonUtils.click(loginPgObj.login_btn);
		commonUtils.assertValue(commonUtils.getCurrentUrl(), "http://store.demoqa.com/products-page/your-account/?login=1",
				"Enterd Credentials are Wrong");
		
			commonUtils.takeSnapShot("LoginSuccess.png") ;
		

	}

		
	
		//switch to window
		public void switchToWindow(){
    	// Store the current window handle
    	String winHandleBefore = driver.getWindowHandle();

    	
    	for(String winHandle : driver.getWindowHandles()){
    	    driver.switchTo().window(winHandle);
    	}
    	//File uploard
    	WebElement fileInput = driver.findElement(By.id("file"));
    	WebElement uploard = driver.findElement(By.id("done"));
    	String path = (String) prop.get("dataFile");
    	log.info(prop.get("dataFile").toString());
    	File file = new File(path);
    	String absolutePath = file.getAbsolutePath();
		fileInput.sendKeys(absolutePath);
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		uploard.click();
    	driver.close();
    	driver.switchTo().window(winHandleBefore);

    }

	@BeforeMethod(alwaysRun = true)
	private void beforeMethod(Method method) {
		System.out.println("Test Method----> " + this.getClass().getSimpleName() + "::" + method.getName());
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod() {
		System.out.println("_________________________________________________");
	}
	
	

}
