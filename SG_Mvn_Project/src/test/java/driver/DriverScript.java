package driver;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import methods.AppDependentMethods;
import methods.AppIndependentMethods;
import methods.Datatable;
import methods.UserModuleMethods;
import reports.ReportUtils;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static Datatable datatable = null;
	public static ReportUtils reports = null;
	public static UserModuleMethods userMethods = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static String strResultLocation = null;
	public static String strScreenshotLocation = null;
	public static String strController = null;
	public static String moduleName = null;
	public static String testCaseID = null;
	
	
	@BeforeSuite
	public void loadClasses()
	{
		try {
			appInd = new AppIndependentMethods();
			appDep = new AppDependentMethods();
			datatable = new Datatable();
			reports = new ReportUtils();
			userMethods = new UserModuleMethods();
			strController = System.getProperty("user.dir")+"\\ExecutionController\\Controller.xlsx";
		}catch(Exception e)
		{
			System.out.println("Exception while executing loadClasses() method. "+e.getMessage());
		}
	}
	
	
	
	@Test
	public void executeTests() {
		String executionStatus = null;
		int pRows = 0;
		int mRows = 0;
		int tcRows = 0;
		Class cls = null;
		Object obj = null;
		Method meth = null;
		try {
			pRows = datatable.getRowNumber(strController, "Project");
			for(int i=1; i<=pRows; i++)
			{
				executionStatus = datatable.getCellData(strController, "Project", "ExecuteProject", i);
				if(executionStatus.equalsIgnoreCase("Yes")) {
					String projectName = datatable.getCellData(strController, "Project", "ProjectName", i);
					
					mRows = datatable.getRowNumber(strController, projectName);
					for(int j=1; j<=mRows; j++)
					{
						executionStatus = datatable.getCellData(strController, projectName, "ExecuteModule", j);
						if(executionStatus.equalsIgnoreCase("Yes")) {
							moduleName = datatable.getCellData(strController, projectName, "ModuleNames", j);
							
							tcRows = datatable.getRowNumber(strController, moduleName);
							for(int k=1; k<=tcRows; k++)
							{
								executionStatus = datatable.getCellData(strController, moduleName, "ExecuteTest", k);
								if(executionStatus.equalsIgnoreCase("Yes"))
								{
									String className = datatable.getCellData(strController, moduleName, "ClassName", k);
									String scriptName = datatable.getCellData(strController, moduleName, "TestScriptName", k);
									testCaseID = datatable.getCellData(strController, moduleName, "TestCaseID", k);
									cls = Class.forName(className);
									obj = cls.newInstance();
									meth = obj.getClass().getMethod(scriptName);
									if(String.valueOf(meth.invoke(obj)).equalsIgnoreCase("True")){
										datatable.setCellData(strController, moduleName, "Status", k, "PASSED");
									}else {
										datatable.setCellData(strController, moduleName, "Status", k, "FAILED");
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e)
		{
			System.out.println("Exception in executeTests() method. "+e.getMessage());
		}
		finally {
			cls = null;
			obj = null;
			meth = null;
		}
	}
	
}

