package pgobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPgObj {

	public WebDriver driver;

	public LoginPgObj(WebDriver driver) {
		this.driver = driver;
	}
	public By userName_txt = By.id("log");
	public By  password_txt = By.id("pwd");
	public By  login_btn = By.id("login");
	
}
