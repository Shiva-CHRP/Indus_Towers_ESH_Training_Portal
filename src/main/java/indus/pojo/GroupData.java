package indus.pojo;

public class GroupData {
	private String groupName;
	private String description;

	public GroupData() {}

	public String getGroupName() { return groupName; }
	public void setGroupName(String groupName) { this.groupName = groupName; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	@Override
	public String toString() {
		return "GroupData [groupName=" + groupName + "]";
	}
}
