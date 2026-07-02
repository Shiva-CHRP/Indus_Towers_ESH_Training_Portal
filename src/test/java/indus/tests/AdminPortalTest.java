package indus.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import indus.annotations.TestInfo;
import indus.listener.Listener;
import indus.pojo.CircleData;
import indus.pojo.ClusterData;
import indus.pojo.DepartmentData;
import indus.pojo.DesignationData;
import indus.pojo.DistrictData;
import indus.pojo.NationalData;
import indus.pojo.SiteData;
import indus.pojo.TowerTypeData;
import indus.testcomponents.BaseTest;
import indus.utils.AssertUtils;
import indus.utils.TestDataUtil;
import indus.utils.ToastResponse;

@Listeners(Listener.class)

public class AdminPortalTest extends BaseTest {
	String DepartmentName;
	String DesignationName;
	String TowerTypeName;
	String NationalName;
	String CircleName;
	String ClusterName;
	String DistrictName;
	String SiteName;

	@Test(priority = 1, groups = {"Smoke", "Regression"})
	@TestInfo(module = "Login", description = "Login using valid Email and Password", priority = "Critical")
	public void login() {
		loginPage.loginByEmail();
		loginPage.enterUserEmail(TestDataUtil.getData("loginValidation.validLogin.useremail"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.validLogin.password"));
		loginPage.clickLogin();
		ToastResponse toast = toastUtils.captureToast();
		String toastType = toast.getType();
		String toastMessage = toast.getMessage();
		if ("success".equalsIgnoreCase(toastType)) {

			softAssert.assertEquals(toastMessage,
					"Login successful! Redirecting...\nYou will be redirected to your dashboard.",
					"Success toast mismatch");

		} else if ("error".equalsIgnoreCase(toastType)) {

			softAssert.fail("Toast not displayed or locator not matching UI");
		}
		toastUtils.waitForToastToDisappear();
		String actualText = loginPage.adminOverallCompliance();
		softAssert.assertEquals(actualText, "OVERALL COMPLIANCE", "Overview Loaded Successfully");
	}

	@Test(priority = 2,groups = {"Regression"})
	@TestInfo(module = "Department", description = "Verify Department menu is displayed.", priority = "Critical")
	public void department_Menu_Validations() {
		String departmentMenu = department.departmentMenu();
		softAssert.assertEquals(departmentMenu, "Department", "Department Loaded Successfully in Sidebar");
	}

	@Test(priority = 3,groups = {"Regression"})
	@TestInfo(module = "Department", description = "Verify navigation to the Department Screen.", priority = "Critical")
	public void department_Navigation_Validations() {
		department.goToDepartment();
		String screenName = department.departmentScreenName();
		softAssert.assertEquals(screenName, "Departments", "Department Screen Loaded Successfully.");
	}

	@Test(priority = 4,groups = {"Regression"})
	@TestInfo(module = "Department", description = "Verify Add Department popup opens.", priority = "Critical")
	public void department_Add_Popup() {

		AssertUtils.assertEquals(department.departmentAddButtonEnable(), true, "Add Department button is enabled.");
		department.clickAddDepartment();
		String addDepartmentModel = department.addDepartmentModel();
		softAssert.assertEquals(addDepartmentModel, "Add Department", "Add Department Model is Opened");
		softAssert.assertEquals(department.departmentTitle(), true, "Add Department button is enabled.");
		department.cancelAddDepartment();
	}

	@Test(priority = 5, dataProvider = "departmentHierarchy", groups = {"Smoke", "Regression"})
	@TestInfo(module = "Department", description = "Verify a department can be created with valid data", priority = "Critical")
	public void department_Creation(DepartmentData data) {
		department.goToDepartment();
		DepartmentName = data.getDepartmentName();
		boolean exists = department.isDepartmentPresent("Title", DepartmentName);
		if (!exists) {
			department.clickAddDepartment();
			department.inputDepartmentTitle(DepartmentName);
			department.createDeaprtment();
			ToastResponse toast = toastUtils.captureToast();
			String toastType = toast.getType();
			String toastMessage = toast.getMessage();

			toastUtils.verifyToast(softAssert, "Department added",
					"A department titled '" + DepartmentName + "' already exists.");
	
		}
		else {softAssert.assertTrue(exists,  "Department Already exists in the Table");}
	}

	@Test(priority = 6,groups = {"Regression"})
	@TestInfo(module = "Department", description = "Verify department appears in the grid after creation", priority = "Critical")
	public void department_Appears_In_Table() {
		boolean exists = department.isDepartmentPresent("Title", DepartmentName);
		softAssert.assertFalse(exists, "Department Already exists in the Table");
		softAssert.assertAll();
	}

	@Test(priority = 7,groups = {"Regression"})
	@TestInfo(module = "Designation", description = "Verify Designation menu is displayed.", priority = "Critical")
	public void designation_Menu_Validations() {
		String designationMenu = designation.designationMenu();
		softAssert.assertEquals(designationMenu, "Designation", "Designation Loaded Successfully in Sidebar");
	}

	@Test(priority = 8,groups = {"Regression"})
	@TestInfo(module = "Designation", description = "Verify navigation to the Designation Screen.", priority = "Critical")
	public void designation_Navigation_Validations() {
		designation.goToDesignation();
		String screenName = designation.designationScreenName();
		softAssert.assertEquals(screenName, "Designations", "Designation Screen Loaded Successfully.");
	}

	@Test(priority = 9,groups = {"Regression"})
	@TestInfo(module = "Designation", description = "Verify Add Designation popup opens.", priority = "Critical")
	public void designation_Add_Popup() {

		AssertUtils.assertEquals(designation.designationAddButtonEnable(), true, "Add Designation button is enabled.");
		designation.clickAddDesignation();
		String addDesignationModel = designation.addDesignationModel();
		softAssert.assertEquals(addDesignationModel, "Add Designation", "Add Designation Model is Opened");
		softAssert.assertEquals(designation.designationTitle(), true, "Add Designation button is enabled.");
		designation.cancelAddDesignation();
	}

	@Test(priority = 10, dataProvider = "designationHierarchy", groups = {"Smoke", "Regression"})
	@TestInfo(module = "Designation", description = "Verify a Designation can be created with valid data", priority = "Critical")
	public void designation_Creation(DesignationData data) {
		designation.goToDesignation();
		DesignationName = data.getDesignationName();
		boolean exists = designation.isDesignationPresent("Title", DesignationName);
		if (!exists) {
			designation.clickAddDesignation();
			designation.inputDesignationTitle(DesignationName);
			designation.createDesignation();
			ToastResponse toast = toastUtils.captureToast();
			String toastType = toast.getType();
			String toastMessage = toast.getMessage();
			toastUtils.verifyToast(softAssert, "Designation added",
					"A designation titled '" + DesignationName + "' already exists.");

		} else {
			softAssert.assertTrue(exists, "Designation Already exists in the Table");
		}
	}

	@Test(priority = 11,groups = {"Regression"})
	@TestInfo(module = "Designation", description = "Verify Designation appears in the grid after creation", priority = "Critical")
	public void designation_Appears_In_Table() {
		boolean exists = designation.isDesignationPresent("Title", DesignationName);
		softAssert.assertTrue(exists, "Designation Already exists in the Table");
		softAssert.assertAll();
	}

	@Test(priority = 12,groups = {"Regression"})
	@TestInfo(module = "Tower Type", description = "Verify Tower Type menu is displayed.", priority = "Critical")
	public void tower_Type_Menu_Validations() {
		String towerTypeMenu = towerTypes.towerTypeMenu();
		softAssert.assertEquals(towerTypeMenu, "Tower Type", "Designation Loaded Successfully in Sidebar");
	}

	@Test(priority = 13,groups = {"Regression"})
	@TestInfo(module = "Tower Type", description = "Verify navigation to the Tower Type Screen.", priority = "Critical")
	public void tower_Type_Navigation_Validations() {
		towerTypes.goToTowerType();
		String screenName = towerTypes.towerTypeScreenName();
		softAssert.assertEquals(screenName, "Tower Types", "Tower Types Screen Loaded Successfully.");
	}

	@Test(priority = 14,groups = {"Regression"})
	@TestInfo(module = "Tower Type", description = "Verify Add Tower Type popup opens.", priority = "Critical")
	public void tower_Type_Add_Popup() {

		AssertUtils.assertEquals(towerTypes.towerTypeAddButtonEnable(), true, "Add Tower Type button is enabled.");
		towerTypes.clickAddTowerType();
		String addTowerTypesModel = towerTypes.addTowerTypeModel();
		softAssert.assertEquals(addTowerTypesModel, "Add Tower Type", "Add Tower Type Model is Opened");
		softAssert.assertEquals(towerTypes.towerTypeTitle(), true, "Add Tower Type button is enabled.");
		towerTypes.cancelAddTowerType();
	}

	@Test(priority = 15, dataProvider = "towerTypeHierarchy", groups = {"Smoke", "Regression"})
	@TestInfo(module = "Tower Type", description = "Verify a Tower Type can be created with valid data", priority = "Critical")
	public void tower_Type_Creation(TowerTypeData data) {
		towerTypes.goToTowerType();
		TowerTypeName = data.getTowerTypeName();
		boolean exists = towerTypes.isTowerTypePresent("Title", TowerTypeName);
		if (!exists) {
			towerTypes.clickAddTowerType();
			towerTypes.inputTowerTypeTitle(TowerTypeName);
			towerTypes.createTowerType();
			ToastResponse toast = toastUtils.captureToast();
			String toastType = toast.getType();
			String toastMessage = toast.getMessage();
			toastUtils.verifyToast(softAssert, "Tower Type added",
					"A tower type titled '" + TowerTypeName + "' already exists.");
			
			
		} else {
			softAssert.assertTrue(exists, "Tower Type Already exists in the Table");
		}
	}

	@Test(priority = 16,groups = {"Regression"})
	@TestInfo(module = "Tower Type", description = "Verify Tower Type appears in the grid after creation", priority = "Critical")
	public void tower_Type_Appears_In_Table() {
		boolean exists = towerTypes.isTowerTypePresent("Title", TowerTypeName);
		softAssert.assertTrue(exists, "Tower Type Already exists in the Table");
		softAssert.assertAll();
	}

	@Test(priority = 17,groups = {"Regression"})
	@TestInfo(module = "National", description = "Verify National menu is displayed.", priority = "Critical")
	public void national_Menu_Validations() {
		String nationalMenu = national.nationalMenu();
		softAssert.assertEquals(nationalMenu, "National", "National Loaded Successfully in Sidebar");
	}

	@Test(priority = 18,groups = {"Regression"})
	@TestInfo(module = "National", description = "Verify navigation to the National Screen.", priority = "Critical")
	public void national_Navigation_Validations() {
		national.goToNational();
		String screenName = national.nationalScreenName();
		softAssert.assertEquals(screenName, "Nationals", "National Screen Loaded Successfully.");
	}

	@Test(priority = 19,groups = {"Regression"})
	@TestInfo(module = "National", description = "Verify Add National popup opens.", priority = "Critical")
	public void national_Add_Popup() {

		AssertUtils.assertEquals(national.nationalAddButtonEnable(), true, "Add National button is enabled.");
		national.clickAddNational();
		String addNationalModel = national.addNationalModel();
		softAssert.assertEquals(addNationalModel, "Add National", "Add National Model is Opened");
		softAssert.assertEquals(national.nationalTitle(), true, "Add National button is enabled.");
		national.cancelAddNational();
	}

	@Test(priority = 20, dataProvider = "nationalDataProvider", groups = {"Smoke", "Regression"})
	@TestInfo(module = "National", description = "Verify a National can be created with valid data", priority = "Critical")
	public void national_Creation(NationalData data) {
		national.goToNational();
		NationalName = data.getNationalName();
		boolean exists = national.isNationalPresent("Title", NationalName);
		if (!exists) {
			national.clickAddNational();
			national.inputNationalTitle(NationalName);
			national.createNational();
			ToastResponse toast = toastUtils.captureToast();
			String toastType = toast.getType();
			String toastMessage = toast.getMessage();
			toastUtils.verifyToast(softAssert, "National added",
					"A national titled '" + NationalName + "' already exists.");
		} else {
			softAssert.assertTrue(exists, "National Already exists in the Table");
		}
	}

	@Test(priority = 21,groups = {"Regression"})
	@TestInfo(module = "National", description = "Verify National appears in the grid after creation", priority = "Critical")
	public void national_Appears_In_Table() {
		boolean exists = national.isNationalPresent("Title", NationalName);
		softAssert.assertTrue(exists, "National Already exists in the Table");
		softAssert.assertAll();
	}

	@Test(priority = 22,groups = {"Regression"})
	@TestInfo(module = "Circle", description = "Verify Circle menu is displayed.", priority = "Critical")
	public void circle_Menu_Validations() {
		String circleMenu = circle.circleMenu();
		softAssert.assertEquals(circleMenu, "Circle", "Circle Loaded Successfully in Sidebar");
	}

	@Test(priority = 23,groups = {"Regression"})
	@TestInfo(module = "Circle", description = "Verify navigation to the Circle Screen.", priority = "Critical")
	public void circle_Navigation_Validations() {
		circle.goToCircle();
		String screenName = circle.circleScreenName();
		softAssert.assertEquals(screenName, "Circles", "Circle Screen Loaded Successfully.");
	}

	@Test(priority = 24,groups = {"Regression"})
	@TestInfo(module = "Circle", description = "Verify Add Circle popup opens.", priority = "Critical")
	public void circle_Add_Popup() {

		AssertUtils.assertEquals(circle.circleAddButtonEnable(), true, "Add Circle button is enabled.");
		circle.clickAddCircle();
		String addCircleModel = circle.addCircleModel();
		softAssert.assertEquals(addCircleModel, "Add Circle", "Add Circle Model is Opened");
		softAssert.assertEquals(circle.circleTitle(), true, "Add Circle button is enabled.");
		circle.cancelAddCircle();
	}

	@Test(priority = 25, dataProvider = "circleDataProvider", groups = {"Smoke", "Regression"})
	@TestInfo(module = "Circle", description = "Verify a Circle can be created with valid data", priority = "Critical")
	public void circle_Creation(CircleData data) {

		national.goToNational();
		ensureNationalExists(NationalName);
		circle.goToCircle();
		CircleName = data.getCircleName();

		boolean exists = circle.isCirclePresent("Title", CircleName);
		if (!exists) {
			circle.clickAddCircle();
			circle.inputCircleTitle(CircleName);
			circle.selectNational(NationalName);
			circle.createCircle();
			ToastResponse toast = toastUtils.captureToast();
			String toastType = toast.getType();
			String toastMessage = toast.getMessage();
			toastUtils.verifyToast(softAssert, "Circle added",
					"A circle titled '" + CircleName + "' already exists.");

		} else {
			softAssert.assertTrue(exists, "Circle Already exists in the Table");
		}

	}

	@Test(priority = 26,groups = {"Regression"})
	@TestInfo(module = "Circle", description = "Verify Circle appears in the grid after creation", priority = "Critical")
	public void circle_Appears_In_Table() {
		boolean exists = circle.isCirclePresent("Title", CircleName);
		softAssert.assertTrue(exists, "Circle Already exists in the Table");
		softAssert.assertAll();
	}

	@Test(priority = 27,groups = {"Regression"})
	@TestInfo(module = "Cluster", description = "Verify Cluster menu is displayed.", priority = "Critical")
	public void cluster_Menu_Validations() {
		String clusterMenu = cluster.clusterMenu();
		softAssert.assertEquals(clusterMenu, "Cluster", "Cluster Loaded Successfully in Sidebar");
	}

	@Test(priority = 28,groups = {"Regression"})
	@TestInfo(module = "Cluster", description = "Verify navigation to the Cluster Screen.", priority = "Critical")
	public void cluster_Navigation_Validations() {
		cluster.goToCluster();
		String screenName = cluster.clusterScreenName();
		softAssert.assertEquals(screenName, "Clusters", "Cluster Screen Loaded Successfully.");
	}

	@Test(priority = 29,groups = {"Regression"})
	@TestInfo(module = "Cluster", description = "Verify Add Cluster popup opens.", priority = "Critical")
	public void cluster_Add_Popup() {

		AssertUtils.assertEquals(cluster.clusterAddButtonEnable(), true, "Add Cluster button is enabled.");
		cluster.clickAddCluster();
		String addClusterModel = cluster.addClusterModel();
		softAssert.assertEquals(addClusterModel, "Add Cluster", "Add Cluster Model is Opened");
		softAssert.assertEquals(cluster.clusterTitle(), true, "Add Cluster button is enabled.");
		cluster.cancelAddCluster();
	}

	@Test(priority = 30, dataProvider = "clusterDataProvider",groups = {"Smoke", "Regression"})
	@TestInfo(module = "Cluster", description = "Verify a Cluster can be created with valid data", priority = "Critical")
	public void cluster_Creation(CircleData data) {
		circle.goToCircle();
		CircleName = data.getCircleName();
		ensureCircleExists(CircleName, NationalName);

		for (ClusterData clusterData : data.getClusters()) {
			cluster.goToCluster();
			ClusterName = clusterData.getClusterName();
			boolean exists = cluster.isClusterPresent("Title", ClusterName);
			if (!exists) {
				cluster.clickAddCluster();
				cluster.inputClusterTitle(ClusterName);
				cluster.selectCircle(CircleName);
				cluster.createCluster();
				ToastResponse toast = toastUtils.captureToast();
				String toastType = toast.getType();
				String toastMessage = toast.getMessage();
				toastUtils.verifyToast(softAssert, "Cluster added",
						"A cluster titled '" + ClusterName + "' already exists.");
			} else {
				softAssert.assertTrue(exists, "Cluster Already exists in the Table");
			}
		}

	}

	@Test(priority = 31,groups = {"Regression"})
	@TestInfo(module = "Cluster", description = "Verify Cluster appears in the grid after creation", priority = "Critical")
	public void cluster_Appears_In_Table() {
		boolean exists = cluster.isClusterPresent("Title", ClusterName);
		softAssert.assertTrue(exists, "Cluster Already exists in the Table");
		softAssert.assertAll();
	}

	@Test(priority = 32,groups = {"Regression"})
	@TestInfo(module = "District", description = "Verify District menu is displayed.", priority = "Critical")
	public void district_Menu_Validations() {
		String districtMenu = district.districtMenu();
		softAssert.assertEquals(districtMenu, "District", "District Loaded Successfully in Sidebar");
	}

	@Test(priority = 33,groups = {"Regression"})
	@TestInfo(module = "District", description = "Verify navigation to the District Screen.", priority = "Critical")
	public void district_Navigation_Validations() {
		district.goToDistrict();
		String screenName = district.districtScreenName();
		softAssert.assertEquals(screenName, "Districts", "District Screen Loaded Successfully.");
	}

	@Test(priority = 34,groups = {"Regression"})
	@TestInfo(module = "District", description = "Verify Add District popup opens.", priority = "Critical")
	public void district_Add_Popup() {

		AssertUtils.assertEquals(district.districtAddButtonEnable(), true, "Add District button is enabled.");
		district.clickAddDistrict();
		String addDistrictModel = district.addDistrictModel();
		softAssert.assertEquals(addDistrictModel, "Add District", "Add District Model is Opened");
		softAssert.assertEquals(district.districtTitle(), true, "Add District button is enabled.");
		district.cancelAddDistrict();
	}

	@Test(priority = 35, dataProvider = "districtDataProvider",groups = {"Smoke", "Regression"})
	@TestInfo(module = "District", description = "Verify a District can be created with valid data", priority = "Critical")
	public void district_Creation(ClusterData data) {
		cluster.goToCluster();
		ClusterName = data.getClusterName();
		ensureClusterExists(ClusterName, CircleName, NationalName);
		for (DistrictData districtData : data.getDistricts()) {
			district.goToDistrict();
			DistrictName = districtData.getDistrictName();
			boolean exists = district.isDistrictPresent("Title", DistrictName);
			if (!exists) {
				district.clickAddDistrict();
				district.inputDistrictTitle(DistrictName);
				district.selectCluster(ClusterName);
				district.createDistrict();
				ToastResponse toast = toastUtils.captureToast();
				String toastType = toast.getType();
				String toastMessage = toast.getMessage();
				toastUtils.verifyToast(softAssert, "District added",
						"A district titled '" + DistrictName + "' already exists.");
			} else {
				softAssert.assertTrue(exists, "District Already exists in the Table");
			}
		}

	}

	@Test(priority = 36,groups = {"Regression"})
	@TestInfo(module = "District", description = "Verify District appears in the grid after creation", priority = "Critical")
	public void district_Appears_In_Table() {
		boolean exists = district.isDistrictPresent("Title", DistrictName);
		softAssert.assertTrue(exists, "District Already exists in the Table");
		softAssert.assertAll();
	}

	@Test(priority = 37,groups = {"Regression"})
	@TestInfo(module = "Site", description = "Verify Site menu is displayed.", priority = "Critical")
	public void site_Menu_Validations() {
		String siteMenu = site.siteMenu();
		softAssert.assertEquals(siteMenu, "Site", "Site Loaded Successfully in Sidebar");
	}

	@Test(priority = 38,groups = {"Regression"})
	@TestInfo(module = "Site", description = "Verify navigation to the Site Screen.", priority = "Critical")
	public void site_Navigation_Validations() {
		site.goToSite();
		String screenName = site.siteScreenName();
		softAssert.assertEquals(screenName, "Sites", "Site Screen Loaded Successfully.");
	}

	@Test(priority = 39,groups = {"Regression"})
	@TestInfo(module = "Site", description = "Verify Add Site popup opens.", priority = "Critical")
	public void site_Add_Popup() {

		AssertUtils.assertEquals(site.siteAddButtonEnable(), true, "Add Site button is enabled.");
		site.clickAddSite();
		String addSiteModel = site.addSiteModel();
		softAssert.assertEquals(addSiteModel, "Add Site", "Add Site Model is Opened");
		softAssert.assertEquals(site.siteTitle(), true, "Add Site button is enabled.");
		site.cancelAddSite();
	}

	@Test(priority = 40, dataProvider = "siteDataProvider",groups = {"Smoke", "Regression"})
	@TestInfo(module = "Site", description = "Verify a Site can be created with valid data", priority = "Critical")
	public void site_Creation(DistrictData data) {
		district.goToDistrict();
		DistrictName = data.getDistrictName();
		ensureDistrictExists(DistrictName, ClusterName, CircleName, NationalName);
		for (SiteData siteName : data.getSites()) {
			site.goToSite();
			SiteName = siteName.getSiteName();
			boolean exists = site.isSitePresent("Title", SiteName);
			if (!exists) {
				site.clickAddSite();
				site.inputSiteTitle(SiteName);
				site.selectCluster(ClusterName);
				site.selectDistrict(DistrictName);
				site.createSite();
				ToastResponse toast = toastUtils.captureToast();
				String toastType = toast.getType();
				String toastMessage = toast.getMessage();
				toastUtils.verifyToast(softAssert, "Site added",
						"A site titled '" + SiteName + "' already exists.");
			} else {
				softAssert.assertTrue(exists, "Site Already exists in the Table");
			}
		}

	}

	@Test(priority = 41,groups = {"Regression"})
	@TestInfo(module = "Site", description = "Verify Site appears in the grid after creation", priority = "Critical")
	public void site_Appears_In_Table() {
		boolean exists = site.isSitePresent("Title", SiteName);
		softAssert.assertTrue(exists, "Site Already exists in the Table");
		softAssert.assertAll();
	}

	@Test(priority = 42, groups = {"Smoke", "Regression"})
	@TestInfo(module = "Login", description = "Verify Sign Out from Portal", priority = "Critical")
	public void logout_from_Portal() {

		loginPage.clickProfile();
		loginPage.clickSignOut();
		loginPage.clickConfirmSignOut();
	}

	public void ensureNationalExists(String nationalName) {

		national.goToNational();

		boolean exists = national.isNationalPresent("Title", nationalName);

		if (!exists) {

			national.clickAddNational();
			national.inputNationalTitle(nationalName);
			national.createNational();

			ToastResponse toast = toastUtils.captureToast();

			Assert.assertEquals(toast.getMessage(), "National added");

			toastUtils.waitForToastToDisappear();
		}
	}

	public void ensureCircleExists(String circleName, String nationalName) {

		ensureNationalExists(nationalName);
		circle.goToCircle();

		boolean exists = circle.isCirclePresent("Title", circleName);

		if (!exists) {

			circle.clickAddCircle();
			circle.inputCircleTitle(circleName);
			circle.selectNational(NationalName);
			circle.createCircle();

			ToastResponse toast = toastUtils.captureToast();

			Assert.assertEquals(toast.getMessage(), "Circle added");

			toastUtils.waitForToastToDisappear();
		}
	}

	public void ensureClusterExists(String clusterName, String circleName, String nationalName) {

		ensureCircleExists(circleName, nationalName);
		cluster.goToCluster();

		boolean exists = cluster.isClusterPresent("Title", clusterName);

		if (!exists) {

			cluster.clickAddCluster();
			cluster.inputClusterTitle(clusterName);
			cluster.selectCircle(circleName);
			cluster.createCluster();

			ToastResponse toast = toastUtils.captureToast();

			Assert.assertEquals(toast.getMessage(), "Cluster added");

			toastUtils.waitForToastToDisappear();
		}
	}

	public void ensureDistrictExists(String districtName, String clusterName, String circleName, String nationalName) {

		ensureClusterExists(clusterName, circleName, nationalName);
		district.goToDistrict();

		boolean exists = district.isDistrictPresent("Title", districtName);

		if (!exists) {

			district.clickAddDistrict();
			district.inputDistrictTitle(districtName);
			district.selectCluster(clusterName);
			district.createDistrict();

			ToastResponse toast = toastUtils.captureToast();

			Assert.assertEquals(toast.getMessage(), "District added");

			toastUtils.waitForToastToDisappear();
		}
	}

	@DataProvider(name = "departmentHierarchy")
	public Object[][] departmentHierarchy() {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/departmentHierarchy.json";

		List<DepartmentData> dataList = TestDataUtil.getDepartmentHierarchy(path);

		Object[][] data = new Object[dataList.size()][1];

		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}

		return data;
	}

	@DataProvider(name = "designationHierarchy")
	public Object[][] designationHierarchy() {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/designationHierarchy.json";

		List<DesignationData> dataList = TestDataUtil.getDesignationHierarchy(path);

		Object[][] data = new Object[dataList.size()][1];

		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}

		return data;
	}

	@DataProvider(name = "towerTypeHierarchy")
	public Object[][] towerTypeHierarchy() {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/towerTypeHierarchy.json";

		List<TowerTypeData> dataList = TestDataUtil.getTowerTypeHierarchy(path);

		Object[][] data = new Object[dataList.size()][1];

		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}

		return data;
	}

	public List<NationalData> getHierarchy() {
		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/geographyHierarchy.json";

		return TestDataUtil.getGeographyHierarchy(path);
	}

	@DataProvider(name = "nationalDataProvider")
	public Object[][] nationalDataProvider() {

		List<NationalData> list = getHierarchy();

		return list.stream().map(n -> new Object[] { n }).toArray(Object[][]::new);
	}

	@DataProvider(name = "circleDataProvider")
	public Object[][] circleDataProvider() {

		List<NationalData> list = getHierarchy();

		List<CircleData> circles = list.stream().flatMap(n -> n.getCircles().stream()).toList();

		return circles.stream().map(c -> new Object[] { c }).toArray(Object[][]::new);
	}

	@DataProvider(name = "clusterDataProvider")
	public Object[][] clusterDataProvider() {

		List<NationalData> list = getHierarchy();

		List<CircleData> circles = list.stream().flatMap(n -> n.getCircles().stream()).toList();

		return circles.stream().map(c -> new Object[] { c }).toArray(Object[][]::new);
	}

	@DataProvider(name = "districtDataProvider")
	public Object[][] districtDataProvider() {

		List<NationalData> list = getHierarchy();

		List<ClusterData> clusters = list.stream().flatMap(n -> n.getCircles().stream())
				.flatMap(c -> c.getClusters().stream()).toList();

		return clusters.stream().map(c -> new Object[] { c }).toArray(Object[][]::new);
	}

	@DataProvider(name = "siteDataProvider")
	public Object[][] siteDataProvider() {

		List<NationalData> list = getHierarchy();

		List<DistrictData> districts = list.stream().flatMap(n -> n.getCircles().stream())
				.flatMap(c -> c.getClusters().stream()).flatMap(cl -> cl.getDistricts().stream()).toList();

		return districts.stream().map(d -> new Object[] { d }).toArray(Object[][]::new);
	}

	@DataProvider(name = "geographyHierarchy")
	public Object[][] geographyHierarchy() {

		String path = System.getProperty("user.dir") + "/src/main/resources/testdata/geographyHierarchy.json";

		List<NationalData> dataList = TestDataUtil.getGeographyHierarchy(path);

		Object[][] data = new Object[dataList.size()][1];

		for (int i = 0; i < dataList.size(); i++) {
			data[i][0] = dataList.get(i);
		}

		return data;
	}

}
