package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;

	// method for launch browser

	public static WebDriver startBrowser() throws Throwable 
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Enviroment.properties"));

		if(conpro.getProperty("browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

		}else if(conpro.getProperty("browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();

		}else 
		{
			Reporter.log("Browser value is not matching " , true);

		}

		return driver;	
	}

	public static void openUrl(WebDriver driver)
	{

		driver.get(conpro.getProperty("url"));	

	}
	// method for any webelement wait time
	public static void waitForElement(WebDriver driver ,String Locator_Type,String Locator_value ,String Test_Data)
	{
		//at first  selenium read all the data in string format fom excel 
		// but duration.second takes integer so we convert the data string to integer using Integer.parseInt

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data)));

		if(Locator_Type.equalsIgnoreCase("id"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_value)));
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_value)));
		}	
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_value)));
		}
	}

	// method for text boxes 
	public static void typeAction(WebDriver driver,String Locator_Type,String Locator_value ,String Test_Data) 
	{
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_value)).clear();
			driver.findElement(By.name(Locator_value)).sendKeys(Test_Data);
		}  
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_value)).clear();
			driver.findElement(By.id(Locator_value)).sendKeys(Test_Data);
		}  
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_value)).clear();
			driver.findElement(By.xpath (Locator_value)).sendKeys(Test_Data);
		} 	
	}
	// metod for click action
	public  static void clickAction(WebDriver driver ,String Locator_Type,String Locator_value)
	{ 
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_value)).click();
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_value)).click();
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			//for click action it is use keys.enter
			driver.findElement(By.id(Locator_value)).sendKeys(Keys.ENTER);
		}
	}
	public static void validateTitle(WebDriver driver, String Exp_title)
	{
		String Actual_title = driver.getTitle();

		try {

			Assert.assertEquals(Actual_title, Exp_title , "title is not matching");  

		} catch (Throwable e) 
		{
			System.out.println(e.getMessage());
		}

	}
	public static void closeBrowser(WebDriver driver) 
	{
		driver.quit();
	}
	//   Add /capture date, month ,year and  time in  screen shot 

	public static String generateDate() 
	{
		Date dt = new Date();
		DateFormat  df = new SimpleDateFormat("YYYY-MM-DD hh-mm-ss");
		return df.format(dt);
	}

	// method for mouse action /click
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[starts-with(text(),'Stock Items ')]")));
		ac.perform();
		Thread.sleep(2000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Categories')])[2]")));
		ac.pause(2000).click().perform();

	}
	// method for categorytabel
	public static void categoryTable(WebDriver driver,String Exp_Data) throws Throwable 
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(2000);
		String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
		Reporter.log(Exp_Data +"    "+Act_data , true);
		try {
			Assert.assertEquals(Exp_Data, Act_data, "category name no matching");
		}catch (Throwable e) 
		{
			System.out.println(e.getMessage());
		}		
	}
  // method for drop down or list box
	
	public static void dropDownAction(WebDriver driver, String Locator_Type, String Locator_Value, String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			//convert the string to integer because the select class we take as select by index number so par int convert string to integer 
			
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.id(Locator_Value));
			Select s = new Select(element);
			s.selectByIndex(value);	
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			//convert the string to integer because the select class we take as select by index number so par int convert string to integer 
			
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.xpath(Locator_Value));
			Select s = new Select(element);
			s.selectByIndex(value);	
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			//convert the string to integer because the select class we take as select by index number so par int convert string to integer 
			
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.name(Locator_Value));
			Select s = new Select(element);
			s.selectByIndex(value);	
		}
	}
		
		// method for capturing stock number into note pad
		
	public static void capturestock(WebDriver driver, String Locator_Type , String Locator_Value) throws Throwable 
	{
		String StockNumber = "";
		
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			StockNumber = driver.findElement(By.name(Locator_Value)).getAttribute("value");
			
		}

		if(Locator_Type.equalsIgnoreCase("id"))
		{
			StockNumber = driver.findElement(By.id(Locator_Value)).getAttribute("value");
			
		}

		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			StockNumber = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
			
		}
		
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(StockNumber);
		bw.flush();
		bw.close();
	}
	
	//stock for stock table validation
	
	public static void stockTable(WebDriver driver) throws Throwable
	{
		// read stock number from note pad
		
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(2000);
		String Act_data = driver.findElement(By.xpath("//table[@class ='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data + "    "+Act_data,true);
		try {
		Assert.assertEquals(Exp_Data, Act_data,"stock Number is not matching");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}		
	}
	
	// method for capture supplier number
	
	public static void capturesupplier(WebDriver driver, String Locator_type,String Locator_Value) throws Throwable
	{
		String  supplierNumber ="";
		if(Locator_type.equalsIgnoreCase("name"))
		{
			supplierNumber = driver.findElement(By.name(Locator_Value)).getAttribute("value");
			
		}
		if(Locator_type.equalsIgnoreCase("id"))
		{
			supplierNumber = driver.findElement(By.id(Locator_Value)).getAttribute("value");
			
		}
		
		if(Locator_type.equalsIgnoreCase("xpath"))
		{
			supplierNumber = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
			
		}
		
		FileWriter fw =  new FileWriter("./CaptureData/suppliernumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(supplierNumber);
		bw.flush();
		bw.close();
	}		
		// method for supplier table
	
public static void supplierTable(WebDriver driver) throws Throwable
{
 FileReader fr = new FileReader("./CaptureData/suppliernumber.txt");
 BufferedReader br = new BufferedReader(fr);
 String Exp_Data = br.readLine();
 if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(2000);
	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(Exp_Data +"  "+Act_Data,true);
	try {
		Assert.assertEquals(Exp_Data, Act_Data,"supplier number not matching");
	}catch(Throwable t)
	{
	System.out.println(t.getMessage());	
	}
   }
// method for capture customer number 

public static void capturecustomer(WebDriver driver, String Locator_Type , String Locator_Value) throws Throwable
{
	String CustomerNumber = "";
	
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		CustomerNumber	 = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		
	}

	if(Locator_Type.equalsIgnoreCase("id"))
	{
		CustomerNumber = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		
	}

	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		CustomerNumber = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		
	}
	
	FileWriter fw = new FileWriter("./CaptureData/Customernumber.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(CustomerNumber);
	bw.flush();
	bw.close();
}
// method for customerTable

public static void customerTable(WebDriver driver) throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/Customernumber.txt");
	 BufferedReader br = new BufferedReader(fr);
	 String Exp_Data = br.readLine();
	 if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(2000);
		String Act_Data = driver.findElement(By.xpath("//table[@class ='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Exp_Data +"  "+Act_Data,true);
		try {
			Assert.assertEquals(Exp_Data, Act_Data,"Customer number not matching");
		}catch(Throwable t)
		{
		System.out.println(t.getMessage());	
		}
	   }
}
		





