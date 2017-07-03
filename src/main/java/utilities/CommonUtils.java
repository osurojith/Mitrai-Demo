package utilities;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import java.io.File;
import java.util.List;
import java.util.logging.Level;

public class CommonUtils {

	private WebDriver driver;
	WebElement webElement;
	Actions action;
	JavascriptExecutor js = null;
	boolean elementVisible = false;
	List<WebElement> listWebElements;
	LogEntries browserLogs;

	final static Logger log = Logger.getLogger(CommonUtils.class.getName());

	public CommonUtils(WebDriver driver) {
		this.driver = driver;
	}

	// click
	public void click(By element) {
		try {
			webElement = driver.findElement(element);
			webElement.click();
		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
		}
	}

	// click using js
	public void clickUsingJS(By element) {
		webElement = driver.findElement(element);
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", webElement);
	}

	// send keys
	public void sendKeys(By element, String text) {
		webElement = driver.findElement(element);
		webElement.clear();
		webElement.sendKeys(text);
	}

	// is element present
	public boolean isElementPresent(By element) {
		try {
			webElement = driver.findElement(element);
			return webElement.isDisplayed();
		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
			return false;
		}
	}

	// is element displayed
	public boolean isElementDisplayed(By element) {
		try {
			return driver.findElement(element).isDisplayed();
		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
			return false;
		}
	}

	// is element Enabled
	public boolean isElementEnabled(By element) {
		try {
			webElement = driver.findElement(element);
			return webElement.isEnabled();
		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
			return false;
		}
	}

	// Select by value
	public void selectFrmDrpDwn(By element, String value) {
		Select select = new Select(driver.findElement(element));
		select.selectByVisibleText(value);

	}

	// scroll
	public void scrollTo(int till) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,2500)", "");

	}

	// get url
	public void getUrl(String url) {

		driver.get(url);
	}

	// get current url
	public String getCurrentUrl() {

		return driver.getCurrentUrl();
	}

	// get Text
	public String getText(By element) {
		webElement = driver.findElement(element);
		return webElement.getText();
	}

	// assertValues
	public boolean assertValue(Object actual, Object expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
			return true;
		} catch (AssertionError ae) {
			log.info("Assertion Error: " + ae.getMessage());
			return false;
		}
	}

	// isElementVisibleOnPage
	public boolean isElementsVisibleOnPage(By element) {
		listWebElements = driver.findElements(element);
		if (listWebElements.size() > 0) {
			elementVisible = true;
			return elementVisible;
		} else {
			elementVisible = false;
			return elementVisible;
		}
	}

	public int countNumberOfItems(By element) {
		int numberOfItems = driver.findElements(element).size();
		return numberOfItems;
	}

	public static void setupChromeDriver(String driverPath) {
		String ChromProp = "webdriver.chrome.driver";
		System.setProperty(ChromProp, driverPath);

	}

	public Object browserLogs() {
		browserLogs = driver.manage().logs().get("browser");
		return browserLogs.filter(Level.ALL);
	}

	public void takeSnapShot(String fileName) throws Exception {
		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		// Call getScreenshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		// Move image file to new destination
		File DestFile = new File(".//snap_shot//" + fileName);
		// Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);

	}

}