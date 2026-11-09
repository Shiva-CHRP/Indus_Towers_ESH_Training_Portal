package indus.pojo;

public class CourseData {
	private String courseTitle;
	private String courseCode;
	private String description;
	private String category;
	private String duration;

	public CourseData() {}

	public String getCourseTitle() { return courseTitle; }
	public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

	public String getCourseCode() { return courseCode; }
	public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }

	public String getDuration() { return duration; }
	public void setDuration(String duration) { this.duration = duration; }

	@Override
	public String toString() {
		return "CourseData [courseTitle=" + courseTitle + ", courseCode=" + courseCode + "]";
	}
}
