package indus.testcomponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import indus.pageobjects.admin.Circle;
import indus.pageobjects.admin.Cluster;
import indus.pageobjects.admin.Department;
import indus.pageobjects.admin.Designation;
import indus.pageobjects.admin.District;
import indus.pageobjects.admin.LoginPage;
import indus.pageobjects.admin.National;
import indus.pageobjects.admin.Site;
import indus.pageobjects.admin.TowerTypes;
import indus.reports.ExtentReportNG;
import indus.reports.ExtentTestManager;
import indus.utils.ConfigReader;
import indus.utils.ToastResponse;
import indus.utils.ToastUtils;
import indus.utils.WebDriverFactory;

public class BaseTest {

	protected static ExtentReports extent = ExtentReportNG.getInstance();
	protected SoftAssert softAssert;
	public WebDriver driver;
	public ToastUtils toastUtils;
	protected ExtentTest test;
	
	public LoginPage loginPage;
	public Department department;
	public Designation designation;
	public TowerTypes towerTypes;
	public National national;
	public Circle circle;
	public Cluster cluster;
	public District district;
	public Site site;
	WebDriverFactory factory;
	@BeforeClass(alwaysRun = true)
	public void setup() throws IOException {
		factory = new WebDriverFactory();
		extent = ExtentReportNG.getInstance();
		//test = extent.createTest("");
		//ExtentTestManager.setTest(test);
		driver = factory.initializeDriver();
		driver.get(ConfigReader.getUrl());	
		initializePageObjects();
	}
	
	public void initializePageObjects() {
		loginPage = new LoginPage(driver);
		department = new Department(driver);
		designation = new Designation(driver);
		towerTypes = new TowerTypes(driver);
		national = new National(driver);
		circle = new Circle(driver);
		cluster = new Cluster(driver);
		district = new District(driver);
		site = new Site(driver);
	}
	public void assertToast(ToastResponse toast, String expectedMessage, String expectedType) {

		Assert.assertEquals(toast.getMessage(), expectedMessage);
		Assert.assertEquals(toast.getType(), expectedType);
	}
	
	@BeforeMethod(alwaysRun = true)
	public void setUp() {
	
		toastUtils = new ToastUtils(driver);
		softAssert = new SoftAssert();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownSuite() {
		if (driver != null) {
			WebDriverFactory.quitDriver();
		}
		ExtentTestManager.unload();
		extent.flush();
	}
	
}
