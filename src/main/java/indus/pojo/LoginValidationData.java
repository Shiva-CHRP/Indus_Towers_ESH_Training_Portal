package indus.pojo;

public class LoginValidationData {
	private String useremail;
	private String password;
	private String expectedError;
	private String expectedUsernameError;
	private String expectedPasswordError;
	private String expectedFormatError;
	private String expectedInvalidError;
	private String expectedPasswordLengthError;
	private String otp;
	private String invalidOtp;

	public LoginValidationData() {}

	public String getUseremail() { return useremail; }
	public void setUseremail(String useremail) { this.useremail = useremail; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public String getExpectedError() { return expectedError; }
	public void setExpectedError(String expectedError) { this.expectedError = expectedError; }

	public String getExpectedUsernameError() { return expectedUsernameError; }
	public void setExpectedUsernameError(String expectedUsernameError) { this.expectedUsernameError = expectedUsernameError; }

	public String getExpectedPasswordError() { return expectedPasswordError; }
	public void setExpectedPasswordError(String expectedPasswordError) { this.expectedPasswordError = expectedPasswordError; }

	public String getExpectedFormatError() { return expectedFormatError; }
	public void setExpectedFormatError(String expectedFormatError) { this.expectedFormatError = expectedFormatError; }

	public String getExpectedInvalidError() { return expectedInvalidError; }
	public void setExpectedInvalidError(String expectedInvalidError) { this.expectedInvalidError = expectedInvalidError; }

	public String getExpectedPasswordLengthError() { return expectedPasswordLengthError; }
	public void setExpectedPasswordLengthError(String expectedPasswordLengthError) { this.expectedPasswordLengthError = expectedPasswordLengthError; }

	public String getOtp() { return otp; }
	public void setOtp(String otp) { this.otp = otp; }

	public String getInvalidOtp() { return invalidOtp; }
	public void setInvalidOtp(String invalidOtp) { this.invalidOtp = invalidOtp; }

	@Override
	public String toString() {
		return "LoginValidationData [useremail=" + useremail + "]";
	}
}
