package indus.api;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import indus.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiHealthCheckTest {

    private String baseUrl;

    @BeforeClass(alwaysRun = true)
    public void setupApi() {
        String overrideUrl = System.getProperty("api.base.url");
        if (overrideUrl != null && !overrideUrl.trim().isEmpty()) {
            baseUrl = overrideUrl.trim();
        } else {
            baseUrl = ConfigReader.getUrl();
        }
        RestAssured.baseURI = baseUrl;
    }

    @Test(priority = 1, groups = { "Smoke", "Sanity", "Regression", "All", "HealthCheck" }, description = "Verify Base Application / API Endpoint Availability")
    public void testBaseHealthAndAvailability() {
        Response response = RestAssured.given()
                .relaxedHTTPSValidation()
                .when()
                .get()
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode >= 200 && statusCode < 400,
                "Expected 2xx/3xx response from base URL, got: " + statusCode);
    }

    @Test(priority = 2, groups = { "Sanity", "Regression", "All" }, description = "Verify Endpoint Response Headers & Security")
    public void testApiSecurityHeaders() {
        Response response = RestAssured.given()
                .relaxedHTTPSValidation()
                .when()
                .get()
                .then()
                .extract().response();

        String contentType = response.getContentType();
        Assert.assertNotNull(contentType, "Response Content-Type should not be null");
    }

    @Test(priority = 3, groups = { "Regression", "All" }, description = "Verify Endpoint Latency is within acceptable threshold")
    public void testApiLatencyThreshold() {
        long responseTime = RestAssured.given()
                .relaxedHTTPSValidation()
                .when()
                .get()
                .time();

        Assert.assertTrue(responseTime < 10000,
                "Response time exceeded threshold (10s): " + responseTime + " ms");
    }
}
