package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {

	public WebDriver driver;
	String inputpath = "./FileInput/Controller.xlsx";
	String outputpath = "./FileOutput/HybridResult.xlsx";
	ExtentReports reports ;
	ExtentTest test;
	String testcases ="MasterTestCases";

	public void starTest() throws Throwable
	{
		String Module_Status="";

		// creating object for excell methods

		ExcelFileUtil xl = new ExcelFileUtil(inputpath);

		// iterate all rows in testcases sheet

		for(int i = 1 ;i<=xl.rowCount(testcases);i++)
		{
			// read cell
			String Execution_status = xl.getCellData(testcases, i, 2);

			if(Execution_status.equalsIgnoreCase("Y"))
			{
				// to store all xl module in one variable

				String TCModule = xl.getCellData(testcases, i, 1);
				// add reports 
				 reports = new ExtentReports( "./target/Reports/"+TCModule+ FunctionLibrary.generateDate()+".html");
				 test = reports.startTest(TCModule);

				// iterate all row in TCModule
				for(int j = 1; j<=xl.rowCount(TCModule);j++)
				{
					// read all cells from tcmodule

					String Description = xl.getCellData(TCModule, j, 0);
					String Object_Type = xl.getCellData(TCModule, j, 1);
					String Locator_Type = xl.getCellData(TCModule, j, 2);
					String Locator_value = xl.getCellData(TCModule, j, 3);
					String Test_Data = xl.getCellData(TCModule, j, 4);

					try
					{
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibrary.startBrowser();
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl(driver);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_value, Test_Data);		
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equals("clickAction"))
						{
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_value);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(driver, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equals("mouseClick"))
						{
							FunctionLibrary.mouseClick(driver);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("categoryTable"))
						{
							FunctionLibrary.categoryTable(driver, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(driver, Locator_Type, Locator_value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("capturestock"))
						{
							FunctionLibrary.capturestock(driver, Locator_Type, Locator_value);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable(driver);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("capturesupplier"))
						{
							FunctionLibrary.capturesupplier(driver, Locator_Type, Locator_value);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable(driver);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("capturecustomer"))
						{
							FunctionLibrary.capturecustomer(driver, Locator_Type, Locator_value);
							test.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("customerTable"))
						{
						    FunctionLibrary.customerTable(driver);
						    test.log(LogStatus.INFO, Description);
						}

						// write pass into TCModule

						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						test.log(LogStatus.PASS,Description);
						Module_Status = "True";

					} catch (Exception e)
					{
						// write fail into TCModule

						xl.setCellData(TCModule, j, 5, "Fail",outputpath);
						test.log(LogStatus.FAIL,Description);
						Module_Status = "Fail";

						System.out.println(e.getMessage());
					}

					if(Module_Status.equalsIgnoreCase("true"))
					{
						// write as pass into testcases

						xl.setCellData(testcases, i, 3, "Pass", outputpath);
					}
					if(Module_Status.equalsIgnoreCase("false"))
					{
						// write as fail into testcases

						xl.setCellData(testcases, i, 3, "Fail", outputpath);
					}
					reports.endTest(test);
					reports.flush();
					
				}

			}


			else
			{
				//write as blocked into Test cases sheet for flag N

				xl.setCellData(testcases, i, 3, "Blocked", outputpath);
			}
		}
	}
}

