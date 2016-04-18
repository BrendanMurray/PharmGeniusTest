import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.*;

public class UserTestCases {
	static WebDriver driver = new FirefoxDriver();
	static String baseUrl = "http://localhost:9080/";
	
	//Before any unit tests run, log into test@example.com account as a regular user (!admin)
	@BeforeClass
	public static void beforeAllSetUp() throws Exception{
		driver.get(baseUrl);
		//driver.findElement(By.partialLinkText("Sign In")).click();
		driver.findElement(By.id("signin")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("test@example.com");
		driver.findElement(By.id("submit-login")).click();
	}
	
	//Test that the navigation bar's Profile link redirects to the profile page
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
	
	//This tests the homepage's play button. It validates the GET information of the start quiz form
	@Test
	public void testRunGame(){
		driver.get(baseUrl);
		driver.findElement(By.id("on-play")).click();
		driver.findElement(By.id("startQuiz")).click();
		String URL = driver.getCurrentUrl();
		if (!URL.contains("takeQuiz?category=PHARM+2001&number=5")) fail();
	}
	
	//This is the first test that validates the quiz options.
	//It changes the first dropdown to select 'PHARM 3023' and leaves the second dropdown at '5'
	//Once the start quiz button is selected it validates the GET information in the URL
	@Test
	public void testRunGame3023and5(){
		driver.get(baseUrl);
		driver.findElement(By.id("on-play")).click();
		Select select = new Select(driver.findElement((By.id("category"))));
		select.selectByIndex(1);
		//driver.findElement((By.id("category"))
		driver.findElement(By.id("startQuiz")).click();
		String URL = driver.getCurrentUrl();
		if (!URL.contains("takeQuiz?category=PHARM+3023&number=5")) fail();
	}
	
	//This tests both quiz dropdown options.
	//It changes the first dropdown to 'PHARM 3023' and the second to '10'
	//It then runs the game and validates the GET information in the URL
	@Test
	public void testRunGame3023and10(){
		driver.get(baseUrl);
		driver.findElement(By.id("on-play")).click();
		Select select = new Select(driver.findElement((By.id("category"))));
		select.selectByIndex(1);
		Select select2 = new Select(driver.findElement((By.id("number"))));
		select2.selectByIndex(1);
		driver.findElement(By.id("startQuiz")).click();
		String URL = driver.getCurrentUrl();
		if (!URL.contains("takeQuiz?category=PHARM+3023&number=10")) fail();
	}
	
	//This is tests the 3rd selection in the category dropdown on run game
	@Test
	public void testRunGame3028and10(){
		driver.get(baseUrl);
		driver.findElement(By.id("on-play")).click();
		Select select = new Select(driver.findElement((By.id("category"))));
		select.selectByIndex(2);
		Select select2 = new Select(driver.findElement((By.id("number"))));
		select2.selectByIndex(1);
		driver.findElement(By.id("startQuiz")).click();
		String URL = driver.getCurrentUrl();
		if (!URL.contains("takeQuiz?category=PHARM+3028&number=10")) fail();
	}
	
	//This is tests the 4th selection in the category dropdown on run game
	@Test
	public void testRunGame3040and10(){
		driver.get(baseUrl);
		driver.findElement(By.id("on-play")).click();
		Select select = new Select(driver.findElement((By.id("category"))));
		select.selectByIndex(3);
		Select select2 = new Select(driver.findElement((By.id("number"))));
		select2.selectByIndex(1);
		driver.findElement(By.id("startQuiz")).click();
		String URL = driver.getCurrentUrl();
		if (!URL.contains("takeQuiz?category=PHARM+3040&number=10")) fail();
	}
	
	//This is tests the 5th selection in the category dropdown on run game
	@Test
	public void testRunGame5218and10(){
		driver.get(baseUrl);
		driver.findElement(By.id("on-play")).click();
		Select select = new Select(driver.findElement((By.id("category"))));
		select.selectByIndex(4);
		Select select2 = new Select(driver.findElement((By.id("number"))));
		select2.selectByIndex(1);
		driver.findElement(By.id("startQuiz")).click();
		String URL = driver.getCurrentUrl();
		if (!URL.contains("takeQuiz?category=PHARM+5218&number=10")) fail();
	}
	
	
	//start quiz.
	//submit modal
	//store question text in array and hit an answer x 5
	//check to see if collected question text appears on results page
	//This runs through the default quiz (PHARM 2001, 5 questions) and checks the result page
	//If any of the user answers are not found on the results page, it fails
	@Test
	public void testQuizResults(){
		driver.get(baseUrl);
		driver.findElement(By.id("on-play")).click();
		driver.findElement(By.id("startQuiz")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		driver.findElement(By.id("gobutton")).click();
		String questionText[] = new String[5];
		for (int x = 0 ; x < 5; x++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			questionText[x] = driver.findElement(By.id("answer1")).getText();
			driver.findElement(By.id("answer1")).click();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			driver.findElement(By.id("nextquestionbutton")).click();
		}
		try { Thread.sleep(1000); } catch (InterruptedException e) {}
		Object test = ((JavascriptExecutor) driver).executeScript("return document.getElementsByTagName('html')[0].innerHTML;");
		for (int x = 0; x < 5; x ++){
			if (!test.toString().contains(questionText[x])) fail();
		}
	}

}
