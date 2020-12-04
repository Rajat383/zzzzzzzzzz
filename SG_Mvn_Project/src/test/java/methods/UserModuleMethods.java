package methods;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import driver.DriverScript;
import locators.ObjectLocators;

public class UserModuleMethods extends DriverScript implements ObjectLocators{
	/*********************************************
	 * Method Name	: createUser()
	 * Purpose		: to create the new user in the actiTime application
	 * Author		: tester1
	 * Parameters	: WebDriver
	 * Return Type	: String
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public String createUser(WebDriver oDriver, Map<String, String> objData)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.clickObject(oDriver, obj_Users_Menu);
			Thread.sleep(2000);
			
			strStatus+= appInd.clickObject(oDriver, obj_AddUser_Btn);
			Thread.sleep(2000);
			
			strStatus+= appInd.setObject(oDriver, obj_FirstName_Edit, objData.get("FirstName"));
			strStatus+= appInd.setObject(oDriver, obj_LastName_Edit, objData.get("LastName"));
			strStatus+= appInd.setObject(oDriver, obj_Email_Edit, objData.get("Email"));
			strStatus+= appInd.setObject(oDriver, obj_User_UserName_Edit, objData.get("User_UN"));
			strStatus+= appInd.setObject(oDriver, obj_User_Password_Edit, objData.get("User_PWD"));
			strStatus+= appInd.setObject(oDriver, obj_RetypePassword_Edit, objData.get("User_ReType"));

			strStatus+= appInd.clickObject(oDriver, obj_CreateUser_Btn);
			Thread.sleep(2000);
			
			String userName = objData.get("LastName")+", "+objData.get("FirstName");
			strStatus+= appInd.verifyElementExist(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+userName+"'"+"]"));
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to create the new User in actiTime application", test);
				return null;
			}else {
				reports.writeResult(oDriver, "Pass", "The new user '"+userName+"' has created successful", test);
				return userName;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method createUser(). "+e.getMessage(), test);
			return null;
		}
	}
	
	
	
	
	/*********************************************
	 * Method Name	: deleteUser()
	 * Purpose		: to delete the user in the actiTime application
	 * Author		: tester1
	 * Parameters	: WebDriver, userName
	 * Return Type	: boolean
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public boolean deleteUser(WebDriver oDriver, String userToDelete)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.clickObject(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+userToDelete+"'"+"]"));
			Thread.sleep(2000);
			
			strStatus+= appInd.clickObject(oDriver, obj_DeleteUser_Btn);
			Thread.sleep(2000);
			
			oDriver.switchTo().alert().accept();
			Thread.sleep(2000);
			
			strStatus+= appInd.verifyElementNotExist(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+userToDelete+"'"+"]"));
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to delete the User '"+userToDelete+"' in actiTime application", test);
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "The user '"+userToDelete+"' has deleted successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method deleteUser(). "+e.getMessage(), test);
			return false;
		}
	}
}
