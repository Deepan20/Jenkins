package TestScripts;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import Pages.AmazonPages;
import Pages.GooglePages;
import Pages.MakeMyTrip;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	WebDriver driver;
	AmazonPages ap;
	MakeMyTrip mmt;
	GooglePages gp;
	Actions a;
	String parentwindow;
	String childwindow;
	@BeforeMethod
	@Parameters("Browser")
	public void Setup(String Browser) {
		System.out.println("Parameter value is "+Browser);
		if(Browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}
		else if(Browser.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver();
		}
		ap=new AmazonPages(driver);
		mmt=new MakeMyTrip(driver);
		gp=new GooglePages(driver);
		driver.manage().window().maximize();
		a=new Actions(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	@AfterMethod
	public void tearDown() {
	   // driver.close();
		driver.quit();
	}
	
	public void captureScreenShot(WebDriver driver,String tName) throws IOException {
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		File target=new File(System.getProperty("user.dir")+"\\Screenshot\\"+tName+".png");
		FileUtils.copyFile(source, target);
	}
}
