package indus.utils;

import org.testng.Assert;

import indus.reports.ExtentTestManager;

public class AssertUtils {
	public static void assertEquals(Object actual, Object expected, String message) {

		try {
			Assert.assertEquals(actual, expected);
			ExtentTestManager.getTest().pass("✓ " + message);
		} catch (AssertionError e) {
			ExtentTestManager.getTest().fail("✗ " + message);
			ExtentTestManager.getTest().fail(e.getMessage());
			throw e;
		}

	}

}
