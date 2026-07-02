package indus.utils;

import java.io.IOException;
import java.util.List;
import java.io.File;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import indus.pojo.DepartmentData;
import indus.pojo.DesignationData;
import indus.pojo.NationalData;
import indus.pojo.TowerTypeData;

public class TestDataUtil {
	
	private static JsonNode testData;
	static {
		try {
			ObjectMapper mapper = new ObjectMapper();

			String path = System.getProperty("user.dir") + "/src/main/resources/testdata/testdata.json";

			testData = mapper.readTree(new File(path));

		} catch (IOException e) {
			throw new RuntimeException("Failed to load test data file", e);
		}
	}

	public static String getData(String jsonPath) {

		String[] keys = jsonPath.split("\\.");

		JsonNode node = testData;

		for (String key : keys) {
			node = node.get(key);

			if (node == null) {
				throw new RuntimeException("Invalid test data path: " + jsonPath);
			}
		}

		return node.asText();
	}

	public static int getIntData(String jsonPath) {
		return Integer.parseInt(getData(jsonPath));
	}

	public static boolean getBooleanData(String jsonPath) {
		return Boolean.parseBoolean(getData(jsonPath));
	}

	public static List<DepartmentData> getDepartmentHierarchy(String filePath) {

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(new File(filePath), new TypeReference<List<DepartmentData>>() {
			});
		} catch (IOException e) {
			throw new RuntimeException("Failed to read JSON file", e);
		}
	}
	
	public static List<DesignationData> getDesignationHierarchy(String filePath) {

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(new File(filePath), new TypeReference<List<DesignationData>>() {
			});
		} catch (IOException e) {
			throw new RuntimeException("Failed to read JSON file", e);
		}
	}

	public static List<TowerTypeData> getTowerTypeHierarchy(String filePath) {

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(new File(filePath), new TypeReference<List<TowerTypeData>>() {
			});
		} catch (IOException e) {
			throw new RuntimeException("Failed to read JSON file", e);
		}
	}
	
	public static List<NationalData> getGeographyHierarchy(String filePath) {

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(new File(filePath), new TypeReference<List<NationalData>>() {
			});
		} catch (IOException e) {
			throw new RuntimeException("Failed to read JSON file", e);
		}
	}
}
