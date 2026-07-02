package indus.pojo;

import java.util.List;

public class ClusterData {
	private String clusterName;
	private List<DistrictData> districts;
	
	


	public List<DistrictData> getDistricts() {
		return districts;
	}

	public void setDistricts(List<DistrictData> districts) {
		this.districts = districts;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
}
