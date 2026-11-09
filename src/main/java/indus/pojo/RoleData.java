package indus.pojo;

import java.util.List;

public class RoleData {
	private String roleName;
	private String description;
	private String templateName;
	private String expectedDuplicateError;
	private List<String> permissions;

	public RoleData() {}

	public String getRoleName() { return roleName; }
	public void setRoleName(String roleName) { this.roleName = roleName; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getTemplateName() { return templateName; }
	public void setTemplateName(String templateName) { this.templateName = templateName; }

	public String getExpectedDuplicateError() { return expectedDuplicateError; }
	public void setExpectedDuplicateError(String expectedDuplicateError) { this.expectedDuplicateError = expectedDuplicateError; }

	public List<String> getPermissions() { return permissions; }
	public void setPermissions(List<String> permissions) { this.permissions = permissions; }

	@Override
	public String toString() {
		return "RoleData [roleName=" + roleName + ", templateName=" + templateName + "]";
	}
}
