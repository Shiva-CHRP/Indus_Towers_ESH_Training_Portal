package indus.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import indus.pojo.AssessmentData;
import indus.pojo.AssignTrainingData;
import indus.pojo.CourseData;
import indus.pojo.DepartmentData;
import indus.pojo.DesignationData;
import indus.pojo.EventData;
import indus.pojo.FeedbackData;
import indus.pojo.GroupData;
import indus.pojo.LearnerData;
import indus.pojo.ManagerTeamData;
import indus.pojo.NationalData;
import indus.pojo.QuestionBankData;
import indus.pojo.RoleData;
import indus.pojo.SupportTicketData;
import indus.pojo.TowerTypeData;
import indus.pojo.UserData;

public class TestDataUtil {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final String BASE_TESTDATA_PATH = System.getProperty("user.dir") + "/src/main/resources/testdata/";
	private static JsonNode testData;

	static {
		try {
			String path = BASE_TESTDATA_PATH + "testdata.json";
			testData = mapper.readTree(new File(path));
		} catch (IOException e) {
			System.err.println("Warning: testdata.json could not be loaded: " + e.getMessage());
		}
	}

	public static String getData(String jsonPath) {
		String[] keys = jsonPath.split("\\.");
		JsonNode node = testData;

		for (String key : keys) {
			if (node == null) {
				throw new RuntimeException("Invalid test data path: " + jsonPath);
			}
			node = node.get(key);
		}

		if (node == null) {
			throw new RuntimeException("Invalid test data path: " + jsonPath);
		}
		return node.asText();
	}

	public static int getIntData(String jsonPath) {
		return Integer.parseInt(getData(jsonPath));
	}

	public static boolean getBooleanData(String jsonPath) {
		return Boolean.parseBoolean(getData(jsonPath));
	}

	// ── Generic File Loader ──────────────────────────────────────────────
	public static <T> List<T> loadList(String fileName, TypeReference<List<T>> typeRef) {
		try {
			File file = new File(BASE_TESTDATA_PATH + fileName);
			return mapper.readValue(file, typeRef);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read JSON file: " + fileName, e);
		}
	}

	public static <T> T loadObject(String fileName, Class<T> clazz) {
		try {
			File file = new File(BASE_TESTDATA_PATH + fileName);
			return mapper.readValue(file, clazz);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read JSON object from: " + fileName, e);
		}
	}

	// ── Master Taxonomies (Hierarchy) ───────────────────────────────────
	public static List<DepartmentData> getDepartmentHierarchy(String filePath) {
		try {
			return mapper.readValue(new File(filePath), new TypeReference<List<DepartmentData>>() {});
		} catch (IOException e) {
			throw new RuntimeException("Failed to read department JSON file", e);
		}
	}

	public static List<DesignationData> getDesignationHierarchy(String filePath) {
		try {
			return mapper.readValue(new File(filePath), new TypeReference<List<DesignationData>>() {});
		} catch (IOException e) {
			throw new RuntimeException("Failed to read designation JSON file", e);
		}
	}

	public static List<TowerTypeData> getTowerTypeHierarchy(String filePath) {
		try {
			return mapper.readValue(new File(filePath), new TypeReference<List<TowerTypeData>>() {});
		} catch (IOException e) {
			throw new RuntimeException("Failed to read tower type JSON file", e);
		}
	}

	public static List<NationalData> getGeographyHierarchy(String filePath) {
		try {
			return mapper.readValue(new File(filePath), new TypeReference<List<NationalData>>() {});
		} catch (IOException e) {
			throw new RuntimeException("Failed to read geography JSON file", e);
		}
	}

	// ── Operational & Role POJOs ────────────────────────────────────────
	public static List<UserData> getUserDataList() {
		return loadList("usersData.json", new TypeReference<List<UserData>>() {});
	}

	public static UserData getSingleUserData() {
		return getUserDataList().get(0);
	}

	public static List<CourseData> getCourseDataList() {
		return loadList("coursesData.json", new TypeReference<List<CourseData>>() {});
	}

	public static CourseData getSingleCourseData() {
		return getCourseDataList().get(0);
	}

	public static List<QuestionBankData> getQuestionBankDataList() {
		return loadList("questionBankData.json", new TypeReference<List<QuestionBankData>>() {});
	}

	public static QuestionBankData getSingleQuestionBankData() {
		return getQuestionBankDataList().get(0);
	}

	public static List<AssessmentData> getAssessmentDataList() {
		return loadList("assessmentsData.json", new TypeReference<List<AssessmentData>>() {});
	}

	public static AssessmentData getSingleAssessmentData() {
		return getAssessmentDataList().get(0);
	}

	public static List<AssignTrainingData> getAssignTrainingDataList() {
		return loadList("assignTrainingData.json", new TypeReference<List<AssignTrainingData>>() {});
	}

	public static AssignTrainingData getSingleAssignTrainingData() {
		return getAssignTrainingDataList().get(0);
	}

	public static List<GroupData> getGroupDataList() {
		return loadList("groupsData.json", new TypeReference<List<GroupData>>() {});
	}

	public static GroupData getSingleGroupData() {
		return getGroupDataList().get(0);
	}

	public static List<EventData> getEventDataList() {
		return loadList("eventsData.json", new TypeReference<List<EventData>>() {});
	}

	public static EventData getSingleEventData() {
		return getEventDataList().get(0);
	}

	public static List<SupportTicketData> getSupportTicketDataList() {
		return loadList("supportTicketsData.json", new TypeReference<List<SupportTicketData>>() {});
	}

	public static SupportTicketData getSingleSupportTicketData() {
		return getSupportTicketDataList().get(0);
	}

	public static List<FeedbackData> getFeedbackDataList() {
		return loadList("feedbackData.json", new TypeReference<List<FeedbackData>>() {});
	}

	public static FeedbackData getSingleFeedbackData() {
		return getFeedbackDataList().get(0);
	}

	public static List<RoleData> getRoleDataList() {
		return loadList("rolesData.json", new TypeReference<List<RoleData>>() {});
	}

	public static RoleData getSingleRoleData() {
		return getRoleDataList().get(0);
	}

	public static List<ManagerTeamData> getManagerTeamDataList() {
		return loadList("managerTeamData.json", new TypeReference<List<ManagerTeamData>>() {});
	}

	public static ManagerTeamData getSingleManagerTeamData() {
		return getManagerTeamDataList().get(0);
	}

	public static List<LearnerData> getLearnerDataList() {
		return loadList("learnerData.json", new TypeReference<List<LearnerData>>() {});
	}

	public static LearnerData getSingleLearnerData() {
		return getLearnerDataList().get(0);
	}
}
