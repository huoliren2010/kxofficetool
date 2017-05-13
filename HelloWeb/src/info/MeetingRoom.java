package info;

import org.json.JSONObject;

public class MeetingRoom {
	int id;
	String rname;
	int companyid;

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (rname != null)
			json.put("rname", rname);
		json.put("companyid", companyid);
		return json.toString();
	}

	public MeetingRoom(int did, String rname2, int cid) {
		this.id = did;
		this.rname = rname2;
		this.companyid = cid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (rname != null)
			json.put("rname", rname);
		json.put("companyid", companyid);
		return json;
	}

}
