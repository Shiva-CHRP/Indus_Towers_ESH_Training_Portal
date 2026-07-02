package indus.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import indus.annotations.TestInfo;
import indus.listener.Listener;
import indus.testcomponents.BaseTest;
import indus.utils.AssertUtils;
import indus.utils.ConfigReader;
import indus.utils.TestDataUtil;

@Listeners(Listener.class)
public class LoginTestValidation extends BaseTest{

	String username = ConfigReader.getUsername();
	String password = ConfigReader.getPassword();
	@Test
	@TestInfo(author = "Shiva",module="Login", description = "Verify login using valid email and password", priority="Critical")
	public void verify_Login_With_Email_Validation() throws InterruptedException {
		loginPage.loginByEmail();
		loginPage.enterUserEmail(TestDataUtil.getData("loginValidation.validLogin.useremail"));
		
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.validLogin.password"));
		
		loginPage.clickLogin();
		
		String actualText = loginPage.adminOverallCompliance();
		AssertUtils.assertEquals(actualText,"OVERALL COMPLIANCE","Overview Loaded Successfully");
		loginPage.clickProfile();
		loginPage.clickSignOut();
		loginPage.clickConfirmSignOut();
		Thread.sleep(3000);
	}
}
