package indus.pojo;

public class EventData {
	private String eventTitle;
	private String description;
	private String eventType;
	private String location;

	public EventData() {}

	public String getEventTitle() { return eventTitle; }
	public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getEventType() { return eventType; }
	public void setEventType(String eventType) { this.eventType = eventType; }

	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }

	@Override
	public String toString() {
		return "EventData [eventTitle=" + eventTitle + ", location=" + location + "]";
	}
}
