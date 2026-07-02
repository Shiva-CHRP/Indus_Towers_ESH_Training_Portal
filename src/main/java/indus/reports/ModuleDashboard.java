package indus.reports;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ModuleDashboard {
	private static final Map<String, Integer> passedMap = new HashMap<>();
    private static final Map<String, Integer> failedMap = new HashMap<>();

    public static void markPassed(String module) {
        passedMap.put(module, passedMap.getOrDefault(module, 0) + 1);
    }

    public static void markFailed(String module) {   	
        failedMap.put(module, failedMap.getOrDefault(module, 0) + 1);
    }

    public static String generateDashboard() {

        StringBuilder html = new StringBuilder();

        html.append("<h2>Module Execution Dashboard</h2>");
        html.append("<table border='1' style='border-collapse:collapse;width:60%'>");
        html.append("<tr>")
            .append("<th>Module</th>")
            .append("<th>Passed</th>")
            .append("<th>Failed</th>")
            .append("<th>Total</th>")
            .append("<th>Pass %</th>")
            .append("</tr>");

        // Get all unique modules
        Set<String> modules = new TreeSet<>();
        modules.addAll(passedMap.keySet());
        modules.addAll(failedMap.keySet());

        for (String module : modules) {
            html.append(row(module));
        }

        html.append("</table>");

        return html.toString();
    }

    private static String row(String module) {

        int pass = passedMap.getOrDefault(module, 0);
        int fail = failedMap.getOrDefault(module, 0);
        int total = pass + fail;

        double percentage = total == 0 ? 0 : (pass * 100.0) / total;

        return "<tr>"
                + "<td>" + module + "</td>"
                + "<td style='color:green'>" + pass + "</td>"
                + "<td style='color:red'>" + fail + "</td>"
                + "<td>" + total + "</td>"
                + "<td>" + String.format("%.2f", percentage) + "%</td>"
                + "</tr>";
    }

    public static int getPassed(String module) {
        return passedMap.getOrDefault(module, 0);
    }

    public static int getFailed(String module) {
        return failedMap.getOrDefault(module, 0);
    }
}
