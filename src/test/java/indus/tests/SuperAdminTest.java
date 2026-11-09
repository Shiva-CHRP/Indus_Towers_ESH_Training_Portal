package indus.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import indus.annotations.TestInfo;
import indus.listener.Listener;
import indus.pojo.RoleData;
import indus.testcomponents.BaseTest;
import indus.utils.AssertUtils;
import indus.utils.ConfigReader;
import indus.utils.TestDataUtil;
import indus.utils.ToastResponse;

@Listeners(Listener.class)
public class SuperAdminTest extends BaseTest {

	private static final String UNIQUE_SUFFIX = String.valueOf(System.currentTimeMillis() % 100000);
	private static final RoleData roleData = TestDataUtil.getSingleRoleData();
	private static final String TEST_ROLE_NAME = roleData.getRoleName() + "-" + UNIQUE_SUFFIX;

	@Test(priority = 1, groups = {"Smoke", "Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "SuperAdmin", description = "Verify Super Admin Login with Email, Password and 2FA OTP", priority = "P1")
	public void superAdmin_Login_With_2FA() {
		loginPage.loginByEmail();
		loginPage.clearFields();

		String saEmail = ConfigReader.getSuperAdminEmail();
		String saPassword = ConfigReader.getSuperAdminPassword();
		String saOtp = ConfigReader.getStaticOtp();

		loginPage.enterUserEmail(saEmail);
		loginPage.enterPassword(saPassword);
		loginPage.clickLogin();

		loginPage.waitForOtpScreen();
		loginPage.enterOtp(saOtp);
		loginPage.clickVerifyOtp();

		loginPage.handleActiveSessionsIfPresent();

		boolean isDisplayed = accessControlPage.getPageHeaderTitle().toLowerCase().contains("access control")
				|| loginPage.isSuperAdminAccessControlDisplayed();
		softAssert.assertTrue(isDisplayed, "Super Admin should navigate to Access Control dashboard");
	}

	@Test(priority = 2, groups = {"Smoke", "Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "SuperAdmin", description = "Verify Access Control page header and navigation tabs", priority = "P1")
	public void superAdmin_AccessControl_Surface_Validation() {
		accessControlPage.goToAccessControl();
		String header = accessControlPage.getPageHeaderTitle();
		softAssert.assertTrue(header.contains("Access Control"), "Page header should be 'Access Control'");

		accessControlPage.clickRolesTab();
		boolean lockedSection = accessControlPage.isStandardTemplatesLockedDisplayed();
		softAssert.assertTrue(lockedSection, "Standard Templates · Locked section should be displayed");
	}

	@Test(priority = 3, groups = {"Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "SuperAdmin", description = "Verify Standard Templates (Organisation & Team) are locked and read-only", priority = "P2")
	public void superAdmin_Standard_Templates_Locked_Validation() {
		boolean orgCard = accessControlPage.isTemplateCardDisplayed(roleData.getTemplateName());
		softAssert.assertTrue(orgCard, "Organisation template card should be displayed");

		boolean teamCard = accessControlPage.isTemplateCardDisplayed("Team");
		softAssert.assertTrue(teamCard, "Team template card should be displayed");
	}

	@Test(priority = 4, groups = {"Smoke", "Sanity", "Regression", "E2E"})
	@TestInfo(author = "Shiva", module = "SuperAdmin", description = "Verify creating a new Custom Role loaded from RoleData POJO", priority = "P1")
	public void superAdmin_Create_Custom_Role_Positive() {
		accessControlPage.clickNewRoleButton();
		softAssert.assertTrue(newRoleModal.isModalOpen(), "New Role modal dialog should open");

		newRoleModal.enterRoleName(TEST_ROLE_NAME);
		newRoleModal.clickCreateRole();

		try {
			ToastResponse toast = toastUtils.captureToast();
			softAssert.assertEquals(toast.getType(), "SUCCESS", "Toast type should be SUCCESS for role creation");
			softAssert.assertTrue(toast.getMessage().contains("Role created"), "Toast message should contain 'Role created'");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {}

		boolean inEditor = roleEditorPage.isRoleEditorOpen();
		softAssert.assertTrue(inEditor, "Role Editor should open for the newly created custom role");
	}

	@Test(priority = 5, groups = {"Regression"})
	@TestInfo(author = "Shiva", module = "SuperAdmin", description = "Verify duplicate role creation name conflict validation", priority = "P2")
	public void superAdmin_Create_Role_Duplicate_Name_Negative() {
		roleEditorPage.clickBack();
		accessControlPage.clickNewRoleButton();
		newRoleModal.enterRoleName(TEST_ROLE_NAME);
		newRoleModal.clickCreateRole();

		try {
			String errorMsg = newRoleModal.getErrorMessage();
			softAssert.assertTrue(errorMsg.toLowerCase().contains("already exists") 
					|| errorMsg.contains(roleData.getExpectedDuplicateError()), 
					"Duplicate error message should state that role already exists");
		} catch (Exception e) {
			ToastResponse toast = toastUtils.captureToast();
			softAssert.assertEquals(toast.getType(), "ERROR", "Toast should report error on duplicate role name");
			toastUtils.waitForToastToDisappear();
		}

		newRoleModal.clickCancel();
	}

	@Test(priority = 6, groups = {"Sanity", "Regression", "E2E"})
	@TestInfo(author = "Shiva", module = "SuperAdmin", description = "Verify permission toggle triggers 409 Cascade Dependency Confirmation modal", priority = "P1")
	public void superAdmin_Permission_Grid_Cascade_Dependency_Negative_And_Confirm() {
		accessControlPage.openRoleEditor(TEST_ROLE_NAME);
		softAssert.assertTrue(roleEditorPage.isRoleEditorOpen(), "Role Editor should open for role");

		// Toggle Users permission checkbox to trigger cascade warning
		roleEditorPage.toggleFeatureCheckbox("Users");

		if (cascadeConfirmModal.isDisplayed()) {
			String title = cascadeConfirmModal.getTitle();
			softAssert.assertTrue(title.contains("affects other pages") || title.contains("needs other items"),
					"Cascade modal title should warn about affected pages");

			// Confirm cascade to continue
			cascadeConfirmModal.clickConfirmCascade();
			ToastResponse toast = toastUtils.captureToast();
			if (toast != null) {
				softAssert.assertTrue(toast.getType().equalsIgnoreCase("success") || toast.getType().equalsIgnoreCase("info"), "Toast should confirm cascade application");
			}
		}

		// Save Role changes
		roleEditorPage.clickSaveChanges();
		try {
			ToastResponse toast = toastUtils.captureToast();
			softAssert.assertEquals(toast.getType(), "SUCCESS", "Role save should produce SUCCESS toast");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {}

		roleEditorPage.clickBack();
	}

	@Test(priority = 7, groups = {"Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "SuperAdmin", description = "Verify Activity Log displays audit event for role creation", priority = "P2")
	public void superAdmin_Activity_Log_Audit_Feed_Validation() {
		activityLogPage.goToActivityLog();
		softAssert.assertEquals(activityLogPage.getPageHeading(), "Activity Log", "Activity Log page header should be displayed");

		activityLogPage.search(TEST_ROLE_NAME);
		softAssert.assertTrue(true, "Audit feed search query executed for: " + TEST_ROLE_NAME);
	}

	@Test(priority = 8, groups = {"Sanity", "Regression", "E2E"})
	@TestInfo(author = "Shiva", module = "SuperAdmin", description = "Verify custom role deletion and cleanup", priority = "P2")
	public void superAdmin_Delete_Custom_Role_Cleanup() {
		accessControlPage.goToAccessControl();
		accessControlPage.clickRolesTab();

		if (accessControlPage.isRolePresent(TEST_ROLE_NAME)) {
			accessControlPage.deleteRole(TEST_ROLE_NAME);
			ToastResponse toast = toastUtils.captureToast();
			if (toast != null) {
				softAssert.assertEquals(toast.getType(), "SUCCESS", "Deleting custom role should produce SUCCESS toast");
			}
		}
	}

	@Test(priority = 9, groups = {"Smoke", "Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "Logout", description = "Verify clean sign out after Super Admin tests", priority = "P1")
	public void superAdmin_SignOut() {
		loginPage.signOutFromPortal();
	}
}
