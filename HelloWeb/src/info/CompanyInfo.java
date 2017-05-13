package info;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class CompanyInfo {
	int id;
	String companyName;
	int ownerid;
	List<UserInfo> companyMembers;
	List<UserInfo> companyManagers;
	List<MeetingRoom> companyMeetingRooms;
	List<DepartMent> department;

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (companyName != null)
			json.put("ownerid", ownerid);
		if (companyName != null)
			json.put("companyName", companyName);
		return json.toString();
	}

	public CompanyInfo(int cid, String cname, int cuid) {
		this.id = cid;
		this.companyName = cname;
		this.ownerid = cuid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getOwner() {
		return ownerid;
	}

	public void setOwner(int builder) {
		this.ownerid = builder;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (companyName != null)
			json.put("ownerid", ownerid);
		if (companyName != null)
			json.put("companyName", companyName);
		if (department != null && department.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (DepartMent departMent : department) {
				JSONObject jsonDepart = departMent.toJSONObject();
				jsonArray.put(jsonDepart);
			}
			json.put("department", jsonArray);
		}
		if (companyMembers != null && companyMembers.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (UserInfo departMent : companyMembers) {
				JSONObject jsonDepart = departMent.toJSONObject();
				jsonArray.put(jsonDepart);
			}
			json.put("companyMembers", jsonArray);
		}
		if (companyManagers != null && companyManagers.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (UserInfo departMent : companyManagers) {
				JSONObject jsonDepart = departMent.toJSONObject();
				jsonArray.put(jsonDepart);
			}
			json.put("companyManagers", jsonArray);
		}
		if (companyMeetingRooms != null && companyMeetingRooms.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (MeetingRoom departMent : companyMeetingRooms) {
				JSONObject jsonDepart = departMent.toJSONObject();
				jsonArray.put(jsonDepart);
			}
			json.put("companyMeetingRooms", jsonArray);
		}
		return json;
	}

	public List<UserInfo> getCompanyMembers() {
		return companyMembers;
	}

	public void setCompanyMembers(List<UserInfo> companyMembers) {
		this.companyMembers = companyMembers;
	}

	public List<UserInfo> getCompanyManagers() {
		return companyManagers;
	}

	public void setCompanyManagers(List<UserInfo> companyManagers) {
		this.companyManagers = companyManagers;
	}

	public List<MeetingRoom> getCompanyMeetingRooms() {
		return companyMeetingRooms;
	}

	public void setCompanyMeetingRooms(List<MeetingRoom> companyMeetingRooms) {
		this.companyMeetingRooms = companyMeetingRooms;
	}

	public List<DepartMent> getDepartment() {
		return department;
	}

	public void setDepartment(List<DepartMent> department) {
		this.department = department;
	}
	
	
}
