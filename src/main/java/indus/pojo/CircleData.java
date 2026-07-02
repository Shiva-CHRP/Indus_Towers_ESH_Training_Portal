package indus.pojo;

import java.util.List;

public class CircleData {
	private String circleName;
	private List<ClusterData> clusters;
	
	

	public List<ClusterData> getClusters() {
		return clusters;
	}

	public void setClusters(List<ClusterData> clusters) {
		this.clusters = clusters;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
		
}
