import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.*;

public class AdminTestCases {
	static WebDriver driver = new FirefoxDriver();
	static String baseUrl = "http://localhost:9080/";
	
	//Before any unit tests run, log into test@example.com account as an administrator
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
	
	//Test that the navigation bar's Profile link redirects to the profile page
	//This is a duplicate test from UserTestCases.java
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
	
	//Test that the navigation bar's Leaderboard link redirects to the leaderboard page
	//This is a duplicate test from UserTestCases.java
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
	
	//Test that the Questions dropdown's Submit New Question link redirects to the appropriate page
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
	
	//Test that the Questions dropdown's Review New Questions link redirects to the appropriate page
	//This is an administrator only page
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
	
	//Test that the Questions dropdown's Review Old Questions link redirects to the appropriate page
	//This is an administrator only page
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
	
	//Test that an administrator can submit a question.
	//It populates the questionText field and the 4 answer fields before selecting the 2nd option as the answer
	//Then it checks that the question has been successfully submitted, which is indicated on a confirmation page
	@Test
	public void testSubmitQuestion(){
		driver.get(baseUrl);
		try{
			driver.findElement(By.id("Questions")).click();
			driver.findElement(By.id("submitNew")).click();
			driver.findElement(By.id("questionText")).clear();
			driver.findElement(By.id("questionText")).sendKeys("What is your favorite color?");
			driver.findElement(By.id("answer1")).clear();
			driver.findElement(By.id("answer1")).sendKeys("this is red");
			driver.findElement(By.id("answer2")).clear();
			driver.findElement(By.id("answer2")).sendKeys("this is blue");
			driver.findElement(By.id("answer3")).clear();
			driver.findElement(By.id("answer3")).sendKeys("this is green");
			driver.findElement(By.id("answer4")).clear();
			driver.findElement(By.id("answer4")).sendKeys("this is yellow");
			driver.findElement(By.id("select2")).click();
			if (!driver.getPageSource().contains("Question Submitted Successfully!")) fail();
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}
	
	//Test that the question submitted by testSubmitQuestion() is shown on the Review New Question page
	//This is only possible with administrator access.
	//This test WILL FAIL on first run if it loses a race condition with testSubmitQuestion.
	//It should pass on any consecutive run of the test suite.
	//It selects Review New Questions from the Questions dropdown in the nav bar and looks for the question text.
	@Test
	public void testReviewQuestion(){
		driver.get(baseUrl);
		try{
			driver.findElement(By.id("Questions")).click();
			driver.findElement(By.id("reviewNew")).click();
			Thread.sleep(1000);
			Object test = ((JavascriptExecutor) driver).executeScript("return document.getElementsByTagName('html')[0].innerHTML;");
			if (!test.toString().contains("What is your favorite color?")) fail();
		} catch (NoSuchElementException nseex) {
			fail();
		} catch (InterruptedException e) {
		}
	}
	
	//Test that the View Database link displays a question in the database.
	//From the homepage it selects the Questions dropdown and clicks on View Database
	//It gives the js a second to run and then searches the page's source for a given question text
	//If that question text was not rendered the test fails
	@Test
	public void testViewDatabase(){
		driver.get(baseUrl);
		try{
			driver.findElement(By.id("Questions")).click();
			driver.findElement(By.id("reviewOld")).click();
			Thread.sleep(1000);
			Object test = ((JavascriptExecutor) driver).executeScript("return document.getElementsByTagName('html')[0].innerHTML;");
			if (!test.toString().contains("Abilify (Aripirazole) can be classified as which of the following types of drugs?")) fail();
		} catch (NoSuchElementException nseex) {
			fail();
		} catch (InterruptedException e) {
		}
	}
}
