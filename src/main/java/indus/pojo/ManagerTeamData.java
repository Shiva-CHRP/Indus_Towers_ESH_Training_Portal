package indus.pojo;

import java.util.List;

public class ManagerTeamData {
	private String searchMemberName;
	private String remindNote;
	private List<String> statusFilters;

	public ManagerTeamData() {}

	public String getSearchMemberName() { return searchMemberName; }
	public void setSearchMemberName(String searchMemberName) { this.searchMemberName = searchMemberName; }

	public String getRemindNote() { return remindNote; }
	public void setRemindNote(String remindNote) { this.remindNote = remindNote; }

	public List<String> getStatusFilters() { return statusFilters; }
	public void setStatusFilters(List<String> statusFilters) { this.statusFilters = statusFilters; }

	@Override
	public String toString() {
		return "ManagerTeamData [searchMemberName=" + searchMemberName + "]";
	}
}
