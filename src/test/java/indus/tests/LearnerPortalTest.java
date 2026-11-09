package indus.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import indus.annotations.TestInfo;
import indus.listener.Listener;
import indus.pojo.LearnerData;
import indus.testcomponents.BaseTest;
import indus.utils.ConfigReader;
import indus.utils.TestDataUtil;
import indus.utils.ToastResponse;

@Listeners(Listener.class)
public class LearnerPortalTest extends BaseTest {

	private static final LearnerData learnerData = TestDataUtil.getSingleLearnerData();

	@Test(priority = 1, groups = {"Smoke", "Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "Learner", description = "Verify Mobile Companion App banner and Android APK download link loaded from LearnerData POJO", priority = "P1")
	public void learner_Mobile_Companion_App_Banner_Validation() {
		loginPage.goToLogin();
		softAssert.assertTrue(learnerAccessPage.isMobileAppBannerVisible(), "Mobile companion app banner should be visible on login screen");
		softAssert.assertTrue(learnerAccessPage.isApkDownloadLinkVisible(), "'Download Android APK' button should be present");

		String apkUrl = learnerAccessPage.getApkDownloadUrl();
		softAssert.assertTrue(apkUrl != null && (apkUrl.endsWith(".apk") || apkUrl.contains(learnerData.getApkFileName())), 
				"APK download link must point to a valid .apk file matching: " + learnerData.getApkFileName());
	}

	@Test(priority = 2, groups = {"Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "Learner", description = "Verify web portal login rejection for pure Learner accounts directing them to mobile APK", priority = "P1")
	public void learner_Web_Portal_Access_Rejection() {
		loginPage.goToLogin();
		loginPage.loginByEmail();

		String learnerEmail = ConfigReader.getLearnerEmail();
		String learnerPassword = ConfigReader.getLearnerPassword();

		ToastResponse toast = learnerAccessPage.attemptLearnerLogin(learnerEmail, learnerPassword);
		softAssert.assertTrue("error".equalsIgnoreCase(toast.getType()), "Learner web login should trigger an error toast");

		boolean messageMatched = toast.getMessage().toLowerCase().contains(learnerData.getExpectedRejectionMessage().toLowerCase())
				|| toast.getMessage().toLowerCase().contains("mobile app")
				|| toast.getMessage().toLowerCase().contains("not supported")
				|| toast.getMessage().toLowerCase().contains("apk");
		softAssert.assertTrue(messageMatched, "Toast message should instruct learner to use the mobile APK");

		softAssert.assertTrue(driver.getCurrentUrl().contains("/login"), "Learner must remain on login screen without web portal session");
	}

	@Test(priority = 3, groups = {"Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "Learner", description = "Verify Route Guards block Learner/unauthenticated access to restricted routes from LearnerData POJO", priority = "P1")
	public void learner_Route_Guard_Direct_URL_Interception() {
		if (learnerData.getRestrictedRoutes() != null) {
			for (String route : learnerData.getRestrictedRoutes()) {
				softAssert.assertTrue(learnerAccessPage.verifyRouteRedirectsToLogin(route),
						"Direct access to " + route + " must redirect to /login");
			}
		}
	}
}
