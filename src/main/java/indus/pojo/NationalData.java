package indus.pojo;

import java.util.List;

public class NationalData {
	private String nationalName;
	private List<CircleData> circles;
	

	public List<CircleData> getCircles() {
		return circles;
	}

	public void setCircles(List<CircleData> circles) {
		this.circles = circles;
	}

	public String getNationalName() {
		return nationalName;
	}

	public void setNationalName(String nationalName) {
		this.nationalName = nationalName;
	}
}
