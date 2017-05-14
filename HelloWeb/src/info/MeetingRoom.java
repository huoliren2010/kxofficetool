package info;

import org.json.JSONObject;

public class MeetingRoom {
	int id;
	String roomname;
	int companyid;

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (roomname != null)
			json.put("roomname", roomname);
		json.put("companyid", companyid);
		return json.toString();
	}

	public MeetingRoom(int did, String rname2, int cid) {
		this.id = did;
		this.roomname = rname2;
		this.companyid = cid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRname() {
		return roomname;
	}

	public void setRname(String rname) {
		this.roomname = rname;
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
		if (roomname != null)
			json.put("roomname", roomname);
		json.put("companyid", companyid);
		return json;
	}

}
