package indus.pojo;

public class AssignTrainingData {
	private String courseName;
	private String audienceType;
	private String audienceMember;
	private String reminderNote;

	public AssignTrainingData() {}

	public String getCourseName() { return courseName; }
	public void setCourseName(String courseName) { this.courseName = courseName; }

	public String getAudienceType() { return audienceType; }
	public void setAudienceType(String audienceType) { this.audienceType = audienceType; }

	public String getAudienceMember() { return audienceMember; }
	public void setAudienceMember(String audienceMember) { this.audienceMember = audienceMember; }

	public String getReminderNote() { return reminderNote; }
	public void setReminderNote(String reminderNote) { this.reminderNote = reminderNote; }

	@Override
	public String toString() {
		return "AssignTrainingData [courseName=" + courseName + ", audienceMember=" + audienceMember + "]";
	}
}
