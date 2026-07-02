package indus.pojo;

import java.util.List;

public class DistrictData {
	private String districtName;
	private List<SiteData> sites;

	public List<SiteData> getSites() {
		return sites;
	}

	public void setSites(List<SiteData> sites) {
		this.sites = sites;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
}
