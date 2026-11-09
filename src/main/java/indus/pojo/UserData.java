package indus.pojo;

public class UserData {
	private String userName;
	private String employeeId;
	private String email;
	private String mobile;
	private String password;
	private String designation;
	private String department;
	private String duplicateName;
	private String duplicateEmployeeId;
	private String duplicateMobile;

	public UserData() {}

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	public String getEmployeeId() { return employeeId; }
	public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getMobile() { return mobile; }
	public void setMobile(String mobile) { this.mobile = mobile; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public String getDesignation() { return designation; }
	public void setDesignation(String designation) { this.designation = designation; }

	public String getDepartment() { return department; }
	public void setDepartment(String department) { this.department = department; }

	public String getDuplicateName() { return duplicateName; }
	public void setDuplicateName(String duplicateName) { this.duplicateName = duplicateName; }

	public String getDuplicateEmployeeId() { return duplicateEmployeeId; }
	public void setDuplicateEmployeeId(String duplicateEmployeeId) { this.duplicateEmployeeId = duplicateEmployeeId; }

	public String getDuplicateMobile() { return duplicateMobile; }
	public void setDuplicateMobile(String duplicateMobile) { this.duplicateMobile = duplicateMobile; }

	@Override
	public String toString() {
		return "UserData [userName=" + userName + ", email=" + email + ", employeeId=" + employeeId + "]";
	}
}
