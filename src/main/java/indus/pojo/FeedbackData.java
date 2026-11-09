package indus.pojo;

public class FeedbackData {
	private String searchQuery;
	private String category;
	private String comment;

	public FeedbackData() {}

	public String getSearchQuery() { return searchQuery; }
	public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }

	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }

	public String getComment() { return comment; }
	public void setComment(String comment) { this.comment = comment; }

	@Override
	public String toString() {
		return "FeedbackData [searchQuery=" + searchQuery + "]";
	}
}
