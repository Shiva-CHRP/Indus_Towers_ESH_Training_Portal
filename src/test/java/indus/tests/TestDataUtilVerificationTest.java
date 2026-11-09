package indus.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import indus.pojo.AssessmentData;
import indus.pojo.AssignTrainingData;
import indus.pojo.CourseData;
import indus.pojo.EventData;
import indus.pojo.FeedbackData;
import indus.pojo.GroupData;
import indus.pojo.LearnerData;
import indus.pojo.ManagerTeamData;
import indus.pojo.QuestionBankData;
import indus.pojo.RoleData;
import indus.pojo.SupportTicketData;
import indus.pojo.UserData;
import indus.utils.TestDataUtil;

public class TestDataUtilVerificationTest {

	@Test(description = "Verify all POJOs and TestData files load without null pointers or schema errors")
	public void verifyAllPojosAndTestDataFiles() {
		// 1. UserData
		UserData user = TestDataUtil.getSingleUserData();
		Assert.assertNotNull(user, "UserData should not be null");
		Assert.assertNotNull(user.getUserName(), "UserName should not be null");
		Assert.assertNotNull(user.getEmail(), "Email should not be null");

		// 2. CourseData
		CourseData course = TestDataUtil.getSingleCourseData();
		Assert.assertNotNull(course, "CourseData should not be null");
		Assert.assertNotNull(course.getCourseTitle(), "CourseTitle should not be null");

		// 3. QuestionBankData
		QuestionBankData qb = TestDataUtil.getSingleQuestionBankData();
		Assert.assertNotNull(qb, "QuestionBankData should not be null");
		Assert.assertNotNull(qb.getQuestionText(), "QuestionText should not be null");
		Assert.assertNotNull(qb.getOptionA(), "OptionA should not be null");

		// 4. AssessmentData
		AssessmentData assessment = TestDataUtil.getSingleAssessmentData();
		Assert.assertNotNull(assessment, "AssessmentData should not be null");
		Assert.assertEquals(assessment.getMinQuestionsThreshold(), 10, "MinQuestionsThreshold should be 10");

		// 5. AssignTrainingData
		AssignTrainingData assign = TestDataUtil.getSingleAssignTrainingData();
		Assert.assertNotNull(assign, "AssignTrainingData should not be null");
		Assert.assertNotNull(assign.getCourseName(), "CourseName should not be null");

		// 6. GroupData
		GroupData group = TestDataUtil.getSingleGroupData();
		Assert.assertNotNull(group, "GroupData should not be null");
		Assert.assertNotNull(group.getGroupName(), "GroupName should not be null");

		// 7. EventData
		EventData event = TestDataUtil.getSingleEventData();
		Assert.assertNotNull(event, "EventData should not be null");
		Assert.assertNotNull(event.getEventTitle(), "EventTitle should not be null");

		// 8. SupportTicketData
		SupportTicketData ticket = TestDataUtil.getSingleSupportTicketData();
		Assert.assertNotNull(ticket, "SupportTicketData should not be null");
		Assert.assertNotNull(ticket.getTicketSubject(), "TicketSubject should not be null");

		// 9. FeedbackData
		FeedbackData feedback = TestDataUtil.getSingleFeedbackData();
		Assert.assertNotNull(feedback, "FeedbackData should not be null");
		Assert.assertNotNull(feedback.getSearchQuery(), "SearchQuery should not be null");

		// 10. RoleData
		RoleData role = TestDataUtil.getSingleRoleData();
		Assert.assertNotNull(role, "RoleData should not be null");
		Assert.assertNotNull(role.getRoleName(), "RoleName should not be null");
		Assert.assertTrue(role.getPermissions().size() > 0, "Permissions list should have entries");

		// 11. ManagerTeamData
		ManagerTeamData mgr = TestDataUtil.getSingleManagerTeamData();
		Assert.assertNotNull(mgr, "ManagerTeamData should not be null");
		Assert.assertTrue(mgr.getStatusFilters().contains("Overdue"), "Status filters should contain Overdue");

		// 12. LearnerData
		LearnerData learner = TestDataUtil.getSingleLearnerData();
		Assert.assertNotNull(learner, "LearnerData should not be null");
		Assert.assertEquals(learner.getApkFileName(), "Indus_final.apk", "APK filename should match Indus_final.apk");

		// 13. testdata.json key path retrieval
		Assert.assertNotNull(TestDataUtil.getData("loginValidation.validLogin.useremail"));
		Assert.assertNotNull(TestDataUtil.getData("userManagement.userName"));
		Assert.assertNotNull(TestDataUtil.getData("courseManagement.courseTitle"));

		System.out.println(">>> ALL 12 POJOs and TestData files loaded and verified successfully! <<<");
	}
}
