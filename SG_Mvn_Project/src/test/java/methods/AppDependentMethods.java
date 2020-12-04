package methods;

import org.openqa.selenium.WebDriver;
import driver.DriverScript;
import locators.ObjectLocators;

public class AppDependentMethods extends DriverScript implements ObjectLocators{
	/*********************************************
	 * Method Name	: navigateURL()
	 * Purpose		: to navigate the required URL
	 * Author		: tester1
	 * Parameters	: WebDriver, URL
	 * Return Type	: boolean
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public boolean navigateURL(WebDriver oDriver, String URL)
	{
		try {
			oDriver.navigate().to(URL);
			Thread.sleep(2000);
			
			if(appInd.compareValues(oDriver, oDriver.getTitle(), "actiTIME - Login"))
			{
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Excception", "Exception in the method navigateURL(). "+e.getMessage(), test);
			return false;
		}
	}
	
	
	
	
	/*********************************************
	 * Method Name	: loginToApp()
	 * Purpose		: to login to the actiTime application
	 * Author		: tester1
	 * Parameters	: WebDriver, UserName, Password
	 * Return Type	: boolean
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public boolean loginToApp(WebDriver oDriver, String userName, String password)
	{
		String strStatus = null;
		try {
			test = extent.startTest("loginToApp");
			strStatus+= appInd.setObject(oDriver, obj_User_UserName_Edit, userName);
			strStatus+= appInd.setObject(oDriver, obj_Password_Edit, password);
			strStatus+= appInd.clickObject(oDriver, obj_Login_Btn);
			Thread.sleep(4000);
			
			//For newly created user we need to handle the 'Start Exploring - actiTime' button
			if(appInd.verifyOptionalElement(oDriver, obj_ExploreActiTime_Btn))
			{
				Thread.sleep(2000);
				strStatus+= appInd.clickObject(oDriver, obj_ExploreActiTime_Btn);
				Thread.sleep(4000);
			}
			
			strStatus+= appInd.verifyText(oDriver, obj_TimeTrack_Page, "Text", "Enter Time-Track");
			
			//close the shortcut window
			if(appInd.verifyOptionalElement(oDriver, obj_Shortcut_Window))
			{
				strStatus+= appInd.clickObject(oDriver, obj_Shortcut_Close_Btn);
			}
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to login to actiTime", test);
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "Login to actiTime was successful", test);
				reports.writeResult(oDriver, "screenshot", "login was successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method loginToApp(). "+e.getMessage(), test);
			return false;
		}
	}
	
	
	
	
	
	/*********************************************
	 * Method Name	: logoutFromApp()
	 * Purpose		: to logout from the actiTime application
	 * Author		: tester1
	 * Parameters	: WebDriver
	 * Return Type	: boolean
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public boolean logoutFromApp(WebDriver oDriver)
	{
		String strStatus = null;
		try {
			test = extent.startTest("logoutFromApp");
			strStatus+= appInd.clickObject(oDriver, obj_Logout_Btn);
			Thread.sleep(2000);
			
			strStatus+= appInd.verifyElementExist(oDriver, obj_LoginImage);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to logout from actiTime application", test);
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "Logout from actiTime was successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method logoutFromApp(). "+e.getMessage(), test);
			return false;
		}
	}
}
