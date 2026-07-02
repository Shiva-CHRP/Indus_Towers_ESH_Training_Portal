package indus.reports;

import java.util.HashMap;
import java.util.Map;

public class DashboardBuilder {
	
	private static Map<String, Integer> passedMap = new HashMap<>();
    private static Map<String, Integer> failedMap = new HashMap<>();
    private static Map<String, Integer> tagCount = new HashMap<>();
    
    static {
        passedMap.put("Smoke", 0);
        passedMap.put("Regression", 0);
        passedMap.put("Sanity", 0);
        passedMap.put("Critical", 0);

        failedMap.put("Smoke", 0);
        failedMap.put("Regression", 0);
        failedMap.put("Sanity", 0);
        failedMap.put("Critical", 0);
    }
    public static void markPassed(String[] suites) {
    	for (String suite : suites) {
    		passedMap.put(suite,
                    passedMap.getOrDefault(suite, 0) + 1);
    	}
    	
        
    }
    public static void markFailed(String[] suites) {
    	for (String suite : suites) { failedMap.put(suite,
                failedMap.getOrDefault(suite, 0) + 1);}
       
    }
    public static String generateDashboard() {

    	StringBuilder html = new StringBuilder();

        html.append("<h2>Execution Dashboard</h2>");
        html.append("<table border='1' style='border-collapse:collapse;width:50%'>");
        html.append("<tr><th>Suite</th><th>Result</th></tr>");

        html.append(row("Smoke"));
        html.append(row("Regression"));
        html.append(row("Sanity"));
        html.append(row("Critical"));

        html.append("</table>");

        return html.toString();
    }
    
    private static String row(String suite) {

        int pass = passedMap.getOrDefault(suite, 0);
        int fail = failedMap.getOrDefault(suite, 0);
        int total = pass + fail;

        return "<tr><td>" + suite + "</td><td>"
                + pass + "/" + total + "</td></tr>";
    }
    public static int getPassed(String suite) {
        return passedMap.getOrDefault(suite, 0);
    }
    public static int getFailed(String suite) {
        return failedMap.getOrDefault(suite, 0);
    }
    public static void addTags(String[] groups) {

        for(String group : groups) {

            tagCount.put(
                group,
                tagCount.getOrDefault(group, 0) + 1
            );
        }
    }
}
