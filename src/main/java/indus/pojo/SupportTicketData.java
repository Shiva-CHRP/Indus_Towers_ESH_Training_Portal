package indus.pojo;

public class SupportTicketData {
	private String ticketSubject;
	private String category;
	private String priority;
	private String description;
	private String expectedError;

	public SupportTicketData() {}

	public String getTicketSubject() { return ticketSubject; }
	public void setTicketSubject(String ticketSubject) { this.ticketSubject = ticketSubject; }

	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }

	public String getPriority() { return priority; }
	public void setPriority(String priority) { this.priority = priority; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getExpectedError() { return expectedError; }
	public void setExpectedError(String expectedError) { this.expectedError = expectedError; }

	@Override
	public String toString() {
		return "SupportTicketData [ticketSubject=" + ticketSubject + ", priority=" + priority + "]";
	}
}
