package indus.pojo;

import java.util.List;

public class LearnerData {
	private String apkFileName;
	private String expectedRejectionMessage;
	private List<String> restrictedRoutes;

	public LearnerData() {}

	public String getApkFileName() { return apkFileName; }
	public void setApkFileName(String apkFileName) { this.apkFileName = apkFileName; }

	public String getExpectedRejectionMessage() { return expectedRejectionMessage; }
	public void setExpectedRejectionMessage(String expectedRejectionMessage) { this.expectedRejectionMessage = expectedRejectionMessage; }

	public List<String> getRestrictedRoutes() { return restrictedRoutes; }
	public void setRestrictedRoutes(List<String> restrictedRoutes) { this.restrictedRoutes = restrictedRoutes; }

	@Override
	public String toString() {
		return "LearnerData [apkFileName=" + apkFileName + "]";
	}
}
