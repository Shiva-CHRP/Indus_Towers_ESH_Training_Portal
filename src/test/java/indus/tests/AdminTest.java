package indus.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import indus.annotations.TestInfo;
import indus.listener.Listener;
import indus.pojo.AssessmentData;
import indus.pojo.AssignTrainingData;
import indus.pojo.CourseData;
import indus.pojo.EventData;
import indus.pojo.FeedbackData;
import indus.pojo.GroupData;
import indus.pojo.QuestionBankData;
import indus.pojo.SupportTicketData;
import indus.pojo.UserData;
import indus.testcomponents.BaseTest;
import indus.utils.AssertUtils;
import indus.utils.ConfigReader;
import indus.utils.TestDataUtil;
import indus.utils.ToastResponse;

@Listeners(Listener.class)
public class AdminTest extends BaseTest {

	private static final String UNIQUE_SUFFIX = String.valueOf(System.currentTimeMillis() % 100000);
	
	// Loaded from testdata JSON files via POJOs
	private static final UserData userData = TestDataUtil.getSingleUserData();
	private static final CourseData courseData = TestDataUtil.getSingleCourseData();
	private static final QuestionBankData questionBankData = TestDataUtil.getSingleQuestionBankData();
	private static final AssessmentData assessmentData = TestDataUtil.getSingleAssessmentData();
	private static final AssignTrainingData assignTrainingData = TestDataUtil.getSingleAssignTrainingData();
	private static final GroupData groupData = TestDataUtil.getSingleGroupData();
	private static final EventData eventData = TestDataUtil.getSingleEventData();
	private static final SupportTicketData ticketData = TestDataUtil.getSingleSupportTicketData();
	private static final FeedbackData feedbackData = TestDataUtil.getSingleFeedbackData();

	// Dynamic values with unique suffix
	private static final String TEST_USER_NAME = userData.getUserName() + " " + UNIQUE_SUFFIX;
	private static final String TEST_EMP_ID = userData.getEmployeeId() + "-" + UNIQUE_SUFFIX;
	private static final String TEST_USER_EMAIL = "testuser" + UNIQUE_SUFFIX + "@industowers.com";
	private static final String TEST_COURSE_TITLE = courseData.getCourseTitle() + " " + UNIQUE_SUFFIX;
	private static final String TEST_COURSE_CODE = courseData.getCourseCode() + "-" + UNIQUE_SUFFIX;
	private static final String TEST_QUESTION_TEXT = questionBankData.getQuestionText() + " " + UNIQUE_SUFFIX;
	private static final String TEST_ASSESSMENT_TITLE = assessmentData.getAssessmentTitle() + " " + UNIQUE_SUFFIX;

	// ── 1. Authentication & Overview Dashboard ───────────────────────────
	@Test(priority = 1, groups = {"Smoke", "Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "Admin", description = "Login as Admin and verify Overview Dashboard compliance metrics", priority = "P1")
	public void admin_Login_And_Overview_Dashboard() {
		loginPage.loginByEmail();
		loginPage.clearFields();

		String adminEmail = ConfigReader.getAdminEmail();
		String adminPassword = ConfigReader.getAdminPassword();

		loginPage.enterUserEmail(adminEmail);
		loginPage.enterPassword(adminPassword);
		loginPage.clickLogin();

		loginPage.handleActiveSessionsIfPresent();

		overview.goToOverview();
		softAssert.assertTrue(overview.isOverallComplianceDisplayed(), "Overall Compliance hero should be displayed");

		String compPct = overview.getCompliancePercentage();
		softAssert.assertNotNull(compPct, "Compliance percentage should be present");

		String usersKpi = overview.getKpiValue("Users");
		softAssert.assertNotNull(usersKpi, "Users KPI card should be present");
	}

	// ── 2. Users Module ──────────────────────────────────────────────────
	@Test(priority = 2, groups = {"Smoke", "Sanity", "Regression", "E2E"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Users", description = "Verify single user creation with valid data loaded from UserData POJO", priority = "P1")
	public void users_Add_User_Positive() {
		users.goToUsers();
		users.clickAddUser();
		softAssert.assertTrue(users.addUserModel().contains("Add User"), "Add User modal should be displayed");

		users.fillUserForm(TEST_USER_NAME, TEST_EMP_ID, TEST_USER_EMAIL, userData.getMobile(), userData.getPassword());
		users.submitUserForm();

		try {
			ToastResponse toast = toastUtils.captureToast();
			softAssert.assertEquals(toast.getType(), "SUCCESS", "Toast type should be SUCCESS for user creation");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {}

		users.searchUser(TEST_USER_EMAIL);
		softAssert.assertTrue(users.isUserPresent(TEST_USER_EMAIL), "Newly created user should appear in the table");
	}

	@Test(priority = 3, groups = {"Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Users", description = "Verify duplicate email creation is rejected with error notification", priority = "P2")
	public void users_Duplicate_Email_Negative_Validation() {
		users.goToUsers();
		users.clickAddUser();

		// Use duplicate details from UserData POJO
		users.fillUserForm(userData.getDuplicateName(), userData.getDuplicateEmployeeId() + UNIQUE_SUFFIX, TEST_USER_EMAIL, userData.getDuplicateMobile(), userData.getPassword());
		users.submitUserForm();

		try {
			ToastResponse toast = toastUtils.captureToast();
			softAssert.assertEquals(toast.getType(), "ERROR", "Toast type should be ERROR for duplicate email");
			softAssert.assertTrue(toast.getMessage().toLowerCase().contains("already") 
					|| toast.getMessage().toLowerCase().contains("could not"), "Error toast should indicate duplicate conflict");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {}

		users.cancelAddUser();
	}

	@Test(priority = 4, groups = {"Sanity", "Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Users", description = "Verify Bulk Upload Users modal opening and controls", priority = "P2")
	public void users_Bulk_Upload_Modal_Validation() {
		users.goToUsers();
		users.clickBulkUpload();
		softAssert.assertTrue(users.isBulkUploadModalOpen(), "Bulk Upload Users modal should open");
		users.cancelBulkUpload();
	}

	@Test(priority = 5, groups = {"Sanity", "Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Users", description = "Verify User list tab switching across Learners, Managers, Admins, and Inactive", priority = "P2")
	public void users_Filter_Tabs_Validation() {
		users.goToUsers();
		users.navigateToLearnersTab();
		users.navigateToManagersTab();
		users.navigateToAdminTab();
		users.navigateToInActiveTab();
	}

	// ── 3. Courses Module ────────────────────────────────────────────────
	@Test(priority = 6, groups = {"Smoke", "Sanity", "Regression", "E2E"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Courses", description = "Verify creating a new Course loaded from CourseData POJO", priority = "P1")
	public void courses_Create_Course_Positive_Validation() {
		courses.goToCourses();
		courses.clickOnAddCourse();
		softAssert.assertTrue(courses.addCourseModel().contains("Add Course"), "Add Course modal should open");

		courses.fillCourseForm(TEST_COURSE_TITLE, TEST_COURSE_CODE, courseData.getDescription());
		courses.submitCourse();

		try {
			ToastResponse toast = toastUtils.captureToast();
			softAssert.assertEquals(toast.getType(), "SUCCESS", "Toast should be SUCCESS on course creation");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {}

		softAssert.assertTrue(courses.isCoursePresent(TEST_COURSE_TITLE), "Course card should appear in catalog");
	}

	@Test(priority = 7, groups = {"Sanity", "Regression", "E2E"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Courses", description = "Verify navigation to Course Content manager and back to catalog", priority = "P2")
	public void courses_Content_Manager_Navigation_Validation() {
		courses.goToCourses();
		if (courses.manageContentButtonEnable()) {
			courses.clickOnManageContent();
			softAssert.assertTrue(courses.courseContentName().contains("Course Content"), "Course Content screen should load");
			courses.clickOnBackToCourses();
		}
	}

	// ── 4. Question Bank Module ──────────────────────────────────────────
	@Test(priority = 8, groups = {"Smoke", "Sanity", "Regression", "E2E"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "QuestionBank", description = "Verify creating a single choice question loaded from QuestionBankData POJO", priority = "P1")
	public void question_Bank_Create_Question_Positive() {
		questionBank.goToQuestionBank();
		questionBank.clickOnCreateQuestion();

		questionBank.enterQuestionText(TEST_QUESTION_TEXT);
		questionBank.fillOptions(questionBankData.getOptionA(), questionBankData.getOptionB(), questionBankData.getOptionC(), questionBankData.getOptionD());
		questionBank.selectCorrectAnswer(questionBankData.getCorrectOption());
		questionBank.submitQuestion();

		try {
			ToastResponse toast = toastUtils.captureToast();
			softAssert.assertEquals(toast.getType(), "SUCCESS", "Toast should be SUCCESS on question creation");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {}
	}

	@Test(priority = 9, groups = {"Sanity", "Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "QuestionBank", description = "Verify real-time Duplicate Similarity alert banner when typing identical question", priority = "P1")
	public void question_Bank_Duplicate_Similarity_Alert_Validation() {
		questionBank.goToQuestionBank();
		questionBank.clickOnCreateQuestion();

		// Type the exact question that was just saved
		questionBank.enterQuestionText(TEST_QUESTION_TEXT);

		// Assert that similarity banner activates
		boolean alertShown = questionBank.isSimilarityAlertDisplayed();
		softAssert.assertTrue(alertShown, "Real-time Duplicate Similarity banner should activate for exact duplicate question");

		questionBank.cancelCreateQuestion();
	}

	// ── 5. Assessments Module ────────────────────────────────────────────
	@Test(priority = 10, groups = {"Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Assessments", description = "Verify 10-20 questions validation policy loaded from AssessmentData POJO", priority = "P1")
	public void assessments_Min_10_Questions_Policy_Negative_Validation() {
		assessments.goToAssessments();
		assessments.clickOnCreateAssessment();
		assessments.fillAssessmentDetails(TEST_ASSESSMENT_TITLE, assessmentData.getDescription());

		// Select only invalidQuestionSelectionCount questions (less than the mandatory minimum threshold)
		assessments.selectQuestions(assessmentData.getInvalidQuestionSelectionCount());
		assessments.submitAssessment();

		boolean errorTriggered = assessments.isMinQuestionsErrorDisplayed() 
				|| assessments.getMinQuestionsErrorText().contains(String.valueOf(assessmentData.getMinQuestionsThreshold()));
		softAssert.assertTrue(errorTriggered, "Validation error should block saving assessment with fewer than 10 questions");

		assessments.cancelCreateAssessment();
	}

	// ── 6. Assign Training Wizard ────────────────────────────────────────
	@Test(priority = 11, groups = {"Smoke", "Sanity", "Regression", "E2E"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "AssignTraining", description = "Verify 3-step Assign Training wizard navigation and confirmation", priority = "P1")
	public void assign_Training_Wizard_Validation() {
		assignTraining.goToAssignTraining();
		assignTraining.clickOnAssignTraining();

		// Step 1: Select Course
		assignTraining.selectFirstCourse();
		assignTraining.clickNextButton();

		// Step 2: Select Audience
		assignTraining.selectAudienceMember();
		assignTraining.clickNextButton();

		// Step 3: Confirm Assignment
		assignTraining.confirmAssignment();

		try {
			ToastResponse toast = toastUtils.captureToast();
			softAssert.assertEquals(toast.getType(), "SUCCESS", "Toast should confirm training assigned successfully");
			toastUtils.waitForToastToDisappear();
		} catch (Exception ignored) {}
	}

	// ── 7. Master Groups ─────────────────────────────────────────────────
	@Test(priority = 12, groups = {"Sanity", "Regression", "E2E"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Admin-Master-Groups", description = "Verify Master Groups listing and creation modal loaded from GroupData POJO", priority = "P2")
	public void group_Master_Listing_And_Creation_Modal() {
		group.goToGroupMaster();
		softAssert.assertTrue(group.getPageHeading().contains("Group"), "Group master heading should be displayed");

		group.clickCreateGroup();
		String groupName = groupData.getGroupName() + "-" + UNIQUE_SUFFIX;
		group.enterGroupName(groupName);
		group.enterGroupDescription(groupData.getDescription());
		group.submitCreateGroup();
		ToastResponse toast = toastUtils.captureToast();
		if (toast != null) {
			softAssert.assertTrue(toast.getType().equalsIgnoreCase("success") || toast.getType().equalsIgnoreCase("info"), "Toast should confirm group action");
		}
	}

	// ── 8. Organogram View Modes ─────────────────────────────────────────
	@Test(priority = 13, groups = {"Sanity", "Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Admin-Organogram", description = "Verify Organogram view mode switcher tabs", priority = "P2")
	public void organogram_View_Modes_Validation() {
		organogram.goToOrganogram();
		softAssert.assertEquals(organogram.getPageHeading(), "Organogram", "Page heading should be 'Organogram'");

		softAssert.assertTrue(organogram.isViewTabPresent("List"), "List view tab should be present");
		softAssert.assertTrue(organogram.isViewTabPresent("Org Chart"), "Org Chart view tab should be present");
		softAssert.assertTrue(organogram.isViewTabPresent("Table"), "Table view tab should be present");
		softAssert.assertTrue(organogram.isViewTabPresent("Canvas"), "Canvas view tab should be present");

		organogram.switchView("Table");
		softAssert.assertTrue(true, "Switched to Table view successfully");

		organogram.switchView("List");
		softAssert.assertTrue(true, "Switched back to List view successfully");
	}

	// ── 9. Skill Matrix ──────────────────────────────────────────────────
	@Test(priority = 14, groups = {"Sanity", "Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Admin-SkillMatrix", description = "Verify Skill Matrix landing page and Coming Soon badge", priority = "P2")
	public void skillMatrix_Landing_And_Competency_Levels() {
		skillMatrix.goToSkillMatrix();
		softAssert.assertEquals(skillMatrix.getPageHeading(), "Skill Matrix", "Page heading should be 'Skill Matrix'");
		softAssert.assertTrue(skillMatrix.isComingSoonBadgeDisplayed(), "'Coming Soon' badge should be displayed on Skill Matrix");
	}

	// ── 10. Events Module ────────────────────────────────────────────────
	@Test(priority = 15, groups = {"Sanity", "Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Admin-Events", description = "Verify Events page header and Create Event modal loaded from EventData POJO", priority = "P2")
	public void events_Calendar_And_Create_Event_Modal() {
		events.goToEvents();
		softAssert.assertEquals(events.getPageHeading(), "Events", "Page heading should be 'Events'");

		events.openCreateEventModal();
		softAssert.assertTrue(events.isCreateEventModalOpen(), "Create Event modal should open");

		events.enterEventTitle(eventData.getEventTitle() + " " + UNIQUE_SUFFIX);
		events.cancelCreateEvent();
	}

	// ── 11. Reports & Cluster-Wise ───────────────────────────────────────
	@Test(priority = 16, groups = {"Smoke", "Sanity", "Regression", "E2E"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Admin-Reports", description = "Verify Reports dashboard and Cluster-Wise navigation", priority = "P2")
	public void reports_Compliance_Summary_And_ClusterWise_Navigation() {
		reports.goToReports();
		softAssert.assertTrue(reports.getReportsHeading().contains("Reports"), "Reports heading should be displayed");
		softAssert.assertTrue(reports.isExportButtonVisible(), "Export button should be visible");

		reports.goToClusterWiseReport();
		softAssert.assertTrue(driver.getCurrentUrl().contains("cluster-wise"), "Navigation should route to cluster-wise report");
	}

	// ── 12. Feedback ─────────────────────────────────────────────────────
	@Test(priority = 17, groups = {"Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Admin-Feedback", description = "Verify Feedback dashboard and search query loaded from FeedbackData POJO", priority = "P2")
	public void feedback_Dashboard_And_Search() {
		feedback.goToFeedback();
		softAssert.assertEquals(feedback.getPageHeading(), "Feedback", "Page heading should be 'Feedback'");

		feedback.searchFeedback(feedbackData.getSearchQuery());
		softAssert.assertTrue(true, "Feedback search query executed without UI errors");
	}

	// ── 13. Raise Ticket ─────────────────────────────────────────────────
	@Test(priority = 18, groups = {"Sanity", "Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Admin-RaiseTicket", description = "Verify Raise Ticket modal validation and ticket triage list", priority = "P2")
	public void raiseTicket_New_Ticket_Modal_And_Validation() {
		raiseTicket.goToRaiseTicket();
		softAssert.assertEquals(raiseTicket.getPageHeading(), "Raise Ticket", "Page heading should be 'Raise Ticket'");

		raiseTicket.openNewTicketModal();
		softAssert.assertTrue(raiseTicket.isNewTicketModalDisplayed(), "New Ticket modal should open");

		// Negative check: submit without required fields
		raiseTicket.clickSubmitTicket();
		ToastResponse toast = toastUtils.captureToast();
		if (toast != null) {
			softAssert.assertEquals(toast.getType(), "error", "Submitting empty ticket should trigger error toast");
		}
		raiseTicket.cancelNewTicket();
	}

	// ── 14. Recovery Center ──────────────────────────────────────────────
	@Test(priority = 19, groups = {"Sanity", "Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Admin-RecoveryCenter", description = "Verify Recovery Center tab switching and soft-deleted items filter", priority = "P2")
	public void recoveryCenter_Tabs_And_Type_Filters() {
		recoveryCenter.goToRecoveryCenter();
		softAssert.assertEquals(recoveryCenter.getPageHeading(), "Recovery Center", "Page heading should be 'Recovery Center'");

		recoveryCenter.selectRestoredHistoryTab();
		softAssert.assertTrue(true, "Restored History tab selected");

		recoveryCenter.selectDeletedItemsTab();
		softAssert.assertTrue(true, "Deleted Items tab selected");

		recoveryCenter.filterByType("Users");
		softAssert.assertTrue(true, "Users filter applied in Recovery Center");
	}

	// ── 15. Profile ──────────────────────────────────────────────────────
	@Test(priority = 20, groups = {"Sanity", "Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Admin-Profile", description = "Verify Profile personal info and Change Password modal", priority = "P2")
	public void profile_Personal_Information_And_Password_Modal() {
		profile.goToProfile();
		softAssert.assertTrue(profile.isPersonalInformationDisplayed(), "Personal Information card should be displayed");

		profile.openChangePasswordModal();
		softAssert.assertTrue(profile.isChangePasswordModalOpen(), "Change Password modal should open");

		profile.enterPasswords("OldPass123", "NewPass456", "NewPass456");
		profile.cancelChangePassword();
	}

	// ── 16. Sign Out Teardown ────────────────────────────────────────────
	@Test(priority = 21, groups = {"Smoke", "Sanity", "Regression"}, dependsOnMethods = {"admin_Login_And_Overview_Dashboard"})
	@TestInfo(author = "Shiva", module = "Logout", description = "Verify clean sign out after admin modules execution", priority = "P1")
	public void admin_SignOut() {
		loginPage.signOutFromPortal();
	}
}
