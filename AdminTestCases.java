import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.*;

public class AdminTestCases {
	static WebDriver driver = new FirefoxDriver();
	static String baseUrl = "http://localhost:9080/";
	
	@BeforeClass
	public static void beforeAllSetUp() throws Exception{
		driver.get(baseUrl);
		//driver.findElement(By.partialLinkText("Sign In")).click();
		driver.findElement(By.id("signin")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("test@example.com");
		driver.findElement(By.id("admin")).click();
		driver.findElement(By.id("submit-login")).click();
	}
	
	@Test
	public void testProfileLink(){
		driver.get(baseUrl);
		try{
			driver.findElement(By.id("profile")).click();
			String URL = driver.getCurrentUrl();
			if (!URL.contains("profile")) fail();
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}
	
	@Test
	public void testLeaderboardLink(){
		driver.get(baseUrl);
		try{
			driver.findElement(By.id("leaderboard")).click();
			String URL = driver.getCurrentUrl();
			if (!URL.contains("leaderboard")) fail();
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}
	
	@Test
	public void testSubmitNewLink(){
		driver.get(baseUrl);
		try{
			driver.findElement(By.id("Questions")).click();
			driver.findElement(By.id("submitNew")).click();
			String URL = driver.getCurrentUrl();
			if (!URL.contains("submitNew")) fail();
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}
	
	@Test
	public void testReviewNewLink(){
		driver.get(baseUrl);
		try{
			driver.findElement(By.id("Questions")).click();
			driver.findElement(By.id("reviewNew")).click();
			String URL = driver.getCurrentUrl();
			if (!URL.contains("ReviewNew")) fail();
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}
	
	@Test
	public void testReviewOldLink(){
		driver.get(baseUrl);
		try{
			driver.findElement(By.id("Questions")).click();
			driver.findElement(By.id("reviewOld")).click();
			String URL = driver.getCurrentUrl();
			if (!URL.contains("ReviewOld")) fail();
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}
}
