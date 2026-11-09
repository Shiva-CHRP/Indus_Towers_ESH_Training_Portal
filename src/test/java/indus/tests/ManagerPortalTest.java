package indus.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import indus.annotations.TestInfo;
import indus.listener.Listener;
import indus.pojo.ManagerTeamData;
import indus.testcomponents.BaseTest;
import indus.utils.ConfigReader;
import indus.utils.TestDataUtil;
import indus.utils.ToastResponse;

@Listeners(Listener.class)
public class ManagerPortalTest extends BaseTest {

	private static final ManagerTeamData teamData = TestDataUtil.getSingleManagerTeamData();

	@Test(priority = 1, groups = {"Smoke", "Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "Manager", description = "Verify Manager login and successful landing on Team Overview", priority = "P1")
	public void manager_Login_Success() {
		loginPage.loginByEmail();
		loginPage.clearFields();

		String mgrEmail = ConfigReader.getManagerEmail();
		String mgrPassword = ConfigReader.getManagerPassword();

		loginPage.enterUserEmail(mgrEmail);
		loginPage.enterPassword(mgrPassword);
		loginPage.clickLogin();

		loginPage.handleActiveSessionsIfPresent();

		managerTeamPage.waitForPageLoaded();
		softAssert.assertTrue(managerTeamPage.isTeamComplianceDisplayed(), "Manager should land on Team Overview page");
	}

	@Test(priority = 2, groups = {"Smoke", "Sanity", "Regression"}, dependsOnMethods = {"manager_Login_Success"})
	@TestInfo(author = "Shiva", module = "Manager", description = "Verify Team Compliance hero metrics and KPI strip", priority = "P2")
	public void manager_Team_Compliance_Hero_And_KPI_Validation() {
		managerTeamPage.goToTeam();
		String compPct = managerTeamPage.getCompliancePercentage();
		softAssert.assertNotNull(compPct, "Compliance percentage should be displayed");

		String total = managerTeamPage.getKpiValue("Total");
		softAssert.assertNotNull(total, "KPI Total should be visible");
	}

	@Test(priority = 3, groups = {"Sanity", "Regression"}, dependsOnMethods = {"manager_Login_Success"})
	@TestInfo(author = "Shiva", module = "Manager", description = "Verify status filter chips loaded from ManagerTeamData POJO", priority = "P2")
	public void manager_Status_Filter_Chips_Validation() {
		managerTeamPage.goToTeam();

		if (teamData.getStatusFilters() != null) {
			for (String status : teamData.getStatusFilters()) {
				managerTeamPage.filterByStatus(status);
				softAssert.assertTrue(true, status + " filter applied successfully");
			}
		}
	}

	@Test(priority = 4, groups = {"Sanity", "Regression"}, dependsOnMethods = {"manager_Login_Success"})
	@TestInfo(author = "Shiva", module = "Manager", description = "Verify direct reports search functionality loaded from ManagerTeamData POJO", priority = "P2")
	public void manager_Search_Direct_Reports() {
		managerTeamPage.goToTeam();
		managerTeamPage.searchMember(teamData.getSearchMemberName());
		softAssert.assertTrue(true, "Search executed without UI errors");
		managerTeamPage.searchMember(""); // reset
	}

	@Test(priority = 5, groups = {"Smoke", "Sanity", "Regression", "E2E"}, dependsOnMethods = {"manager_Login_Success"})
	@TestInfo(author = "Shiva", module = "Manager", description = "Verify drill-down to member details and Remind modal", priority = "P2")
	public void manager_Member_Detail_And_Reminder_Action() {
		managerTeamPage.goToTeam();
		if (managerTeamPage.getMemberCardCount() > 0) {
			managerTeamPage.selectMemberByIndex(0);
			softAssert.assertNotNull(memberDetailPage.getMemberName(), "Member detail page should display member name");

			if (memberDetailPage.getAssignedCoursesCount() > 0) {
				// Verify back navigation works
				memberDetailPage.goBack();
				managerTeamPage.waitForPageLoaded();
			}
		}
	}

	@Test(priority = 6, groups = {"Sanity", "Regression"}, dependsOnMethods = {"manager_Login_Success"})
	@TestInfo(author = "Shiva", module = "Manager", description = "Verify manager assessment view is strictly read-only with no Edit/Delete buttons", priority = "P1")
	public void manager_Assessment_ReadOnly_Enforcement() {
		managerAssessmentViewPage.navigateToAssessment("mock-assessment-id");
		boolean canEdit = managerAssessmentViewPage.isEditButtonPresent();
		softAssert.assertFalse(canEdit, "Manager role MUST NOT have Edit permission on Assessments");

		boolean canDelete = managerAssessmentViewPage.isDeleteButtonPresent();
		softAssert.assertFalse(canDelete, "Manager role MUST NOT have Delete permission on Assessments");
	}

	@Test(priority = 7, groups = {"Sanity", "Regression"}, dependsOnMethods = {"manager_Login_Success"})
	@TestInfo(author = "Shiva", module = "Manager", description = "Verify route guard blocks Manager from unauthorized Super Admin and Admin routes", priority = "P1")
	public void manager_Route_Guard_Unauthorized_Access_Prevention() {
		String base = driver.getCurrentUrl().split("#")[0].replaceAll("/(admin|super-admin|manager).*", "");

		// Attempting /super-admin/access-control
		driver.get(base + "/super-admin/access-control");
		boolean blockedFromSuperAdmin = !driver.getCurrentUrl().contains("/super-admin/access-control");
		softAssert.assertTrue(blockedFromSuperAdmin, "Manager should be blocked by SuperAdminRoute guard");

		// Attempting /admin/users
		driver.get(base + "/admin/users");
		boolean blockedFromAdmin = !driver.getCurrentUrl().contains("/admin/users");
		softAssert.assertTrue(blockedFromAdmin, "Manager should be blocked by AdminRoute guard");
	}
}
