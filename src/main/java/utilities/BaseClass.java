package utilities;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.testng.ITestContext;
import org.testng.annotations.*;

import pgobjects.LoginPgObj;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseClass {

	public WebDriver driver = null;
	public CommonUtils commonUtils = null;
	DesiredCapabilities caps = null;
	public Properties prop = null;
	public static long WAITTIME;
	public LoginPgObj loginPgObj;
	
	public ExcelReader excelReader;
	final static Logger log = Logger.getLogger(BaseClass.class.getName());
	public static String localBrowser = "";

	@BeforeClass(alwaysRun = true)
	protected void setUp() throws MalformedURLException {
		caps = new DesiredCapabilities();
		// The below conditions is to run the desktop tests
		if (localBrowser.equals("firefox")) {
			driver = new FirefoxDriver();
			includePageObjects();
		}
		if (localBrowser.equals("chrome")) {
			CommonUtils.setupChromeDriver(prop.get(Params.CHROME_DRIVER_PATH).toString());
			driver = new ChromeDriver();
			includePageObjects();
		}

	}

	public void includePageObjects() {
		loginPgObj = new LoginPgObj(driver);
		commonUtils = new CommonUtils(driver);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.close();
		driver.quit();
	}

	@BeforeSuite(alwaysRun = true)
	public void readConfig(final ITestContext testContext) throws InterruptedException {
		prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("src/main/resources/environment.properties");
			prop.load(input);
			localBrowser = prop.getProperty("localBrowser");
			WAITTIME = Long.parseLong(prop.getProperty("threadSleep"));
			excelReader = new ExcelReader(prop.getProperty(Params.EXCEL_FILE_PATH));
			log.info(localBrowser + "is the local browser selected to run the test");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}