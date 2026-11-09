package indus.testcomponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import indus.pageobjects.admin.Assessments;
import indus.pageobjects.admin.AssignTraining;
import indus.pageobjects.admin.Circle;
import indus.pageobjects.admin.Cluster;
import indus.pageobjects.admin.Courses;
import indus.pageobjects.admin.Department;
import indus.pageobjects.admin.Designation;
import indus.pageobjects.admin.District;
import indus.pageobjects.admin.LoginPage;
import indus.pageobjects.admin.National;
import indus.pageobjects.admin.QuestionBank;
import indus.pageobjects.admin.Site;
import indus.pageobjects.admin.TowerTypes;
import indus.pageobjects.admin.Users;
import indus.pageobjects.admin.Overview;
import indus.pageobjects.admin.Reports;
import indus.pageobjects.admin.RaiseTicket;
import indus.pageobjects.admin.RecoveryCenter;
import indus.pageobjects.admin.Profile;
import indus.pageobjects.admin.Events;
import indus.pageobjects.admin.Feedback;
import indus.pageobjects.admin.SkillMatrix;
import indus.pageobjects.admin.Group;
import indus.pageobjects.admin.Organogram;
import indus.pageobjects.learner.LearnerAccessPage;
import indus.pageobjects.learner.LearnerProfileModal;
import indus.pageobjects.manager.ManagerAssessmentViewPage;
import indus.pageobjects.manager.ManagerTeamPage;
import indus.pageobjects.manager.MemberDetailPage;
import indus.pageobjects.superadmin.AccessControlPage;
import indus.pageobjects.superadmin.ActivityLogPage;
import indus.pageobjects.superadmin.CascadeConfirmModal;
import indus.pageobjects.superadmin.NewRoleModal;
import indus.pageobjects.superadmin.RoleEditorPage;
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
	
	// Admin Page Objects
	public LoginPage loginPage;
	public Department department;
	public Designation designation;
	public TowerTypes towerTypes;
	public National national;
	public Circle circle;
	public Cluster cluster;
	public District district;
	public Site site;
	public Users users;
	public Courses courses;
	public QuestionBank questionBank;
	public Assessments assessments;
	public AssignTraining assignTraining;
	public Overview overview;
	public Reports reports;
	public RaiseTicket raiseTicket;
	public RecoveryCenter recoveryCenter;
	public Profile profile;
	public Events events;
	public Feedback feedback;
	public SkillMatrix skillMatrix;
	public Group group;
	public Organogram organogram;

	// Super Admin Page Objects
	public AccessControlPage accessControlPage;
	public ActivityLogPage activityLogPage;
	public NewRoleModal newRoleModal;
	public RoleEditorPage roleEditorPage;
	public CascadeConfirmModal cascadeConfirmModal;

	// Manager Page Objects
	public ManagerTeamPage managerTeamPage;
	public MemberDetailPage memberDetailPage;
	public ManagerAssessmentViewPage managerAssessmentViewPage;

	// Learner Page Objects
	public LearnerAccessPage learnerAccessPage;
	public LearnerProfileModal learnerProfileModal;

	WebDriverFactory factory;

	@BeforeClass(alwaysRun = true)
	public void setup() throws IOException {
		factory = new WebDriverFactory();
		extent = ExtentReportNG.getInstance();
		driver = factory.initializeDriver();
		driver.get(ConfigReader.getUrl());	
		initializePageObjects();
	}
	
	public void initializePageObjects() {
		// Admin
		loginPage = new LoginPage(driver);
		department = new Department(driver);
		designation = new Designation(driver);
		towerTypes = new TowerTypes(driver);
		national = new National(driver);
		circle = new Circle(driver);
		cluster = new Cluster(driver);
		district = new District(driver);
		site = new Site(driver);
		users = new Users(driver);
		courses = new Courses(driver);
		questionBank = new QuestionBank(driver);
		assessments = new Assessments(driver);
		assignTraining = new AssignTraining(driver);
		overview = new Overview(driver);
		reports = new Reports(driver);
		raiseTicket = new RaiseTicket(driver);
		recoveryCenter = new RecoveryCenter(driver);
		profile = new Profile(driver);
		events = new Events(driver);
		feedback = new Feedback(driver);
		skillMatrix = new SkillMatrix(driver);
		group = new Group(driver);
		organogram = new Organogram(driver);

		// Super Admin
		accessControlPage = new AccessControlPage(driver);
		activityLogPage = new ActivityLogPage(driver);
		newRoleModal = new NewRoleModal(driver);
		roleEditorPage = new RoleEditorPage(driver);
		cascadeConfirmModal = new CascadeConfirmModal(driver);

		// Manager
		managerTeamPage = new ManagerTeamPage(driver);
		memberDetailPage = new MemberDetailPage(driver);
		managerAssessmentViewPage = new ManagerAssessmentViewPage(driver);

		// Learner
		learnerAccessPage = new LearnerAccessPage(driver);
		learnerProfileModal = new LearnerProfileModal(driver);
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
