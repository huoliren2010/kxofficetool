package info;

import org.json.JSONObject;

//…Û≈˙«ÎºŸ°£°£°£
public class Approval {
	int id;
	int uid;
	String message;
	String starttime;
	String endtime;
	String type;
	int leaderid;

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("uid", uid);
		json.put("leaderid", leaderid);
		if (message != null)
			json.put("message", message);
		if (starttime != null)
			json.put("starttime", starttime);
		if (endtime != null)
			json.put("endtime", endtime);
		if (type != null)
			json.put("type", type);
		return json.toString();
	}

	public Approval(int id2, int uid2, String message2, String starttime2, String endtime2, String type, int leaderid) {
		this.id = id2;
		this.uid = uid2;
		this.message = message2;
		this.starttime = starttime2;
		this.endtime = endtime2;
		this.type = type;
		this.leaderid = leaderid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getLeaderid() {
		return leaderid;
	}

	public void setDepartid(int leaderid) {
		this.leaderid = leaderid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLeaderid(int leaderid) {
		this.leaderid = leaderid;
	}
	
}
