package info;

import org.json.JSONObject;

//…Û≈˙«ÎºŸ°£°£°£
public class Approval {
	public static final String TYPE_YEAR = "YEAR";
	public static final String TYPE_SICK = "SICK";
	public static final String TYPE_BIRTH = "BIRTH";
	public static final String TYPE_MARRY = "MARRY";
	public static final String TYPE_NOMRAL = "NOMRAL";
	
	public static final String STATUS_START = "START";
	public static final String STATUS_END = "END";
	
	int id;
	int uid;
	String uname;
	String message;
	String starttime;
	String endtime;
	String type;
	int departid;
	String status;
	boolean agree;
	String result;

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("uid", uid);
		if(uname != null)
			json.put("uname", uname);
		if (message != null)
			json.put("message", message);
		if (starttime != null)
			json.put("starttime", starttime);
		if (endtime != null)
			json.put("endtime", endtime);
		if (type != null)
			json.put("type", type);
		json.put("departid", departid);
		json.put("status", status);
		json.put("agree", agree);
		json.put("result", result);
		return json.toString();
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Approval(int id2, int uid2, String uname, String message2, String starttime2, String endtime2, String type, int departid, String status) {
		this.id = id2;
		this.uid = uid2;
		this.uname = uname;
		this.message = message2;
		this.starttime = starttime2;
		this.endtime = endtime2;
		this.type = type;
		this.departid = departid;
		this.status = status;
	}

	public Approval() {
		// TODO Auto-generated constructor stub
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

	public int getDepartid() {
		return departid;
	}

	public void setDepartid(int departid) {
		this.departid = departid;
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

	public boolean isAgree() {
		return agree;
	}

	public void setAgree(boolean agree) {
		this.agree = agree;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("uid", uid);
		if(uname != null)
			json.put("uname", uname);
		if (message != null)
			json.put("message", message);
		if (starttime != null)
			json.put("starttime", starttime);
		if (endtime != null)
			json.put("endtime", endtime);
		if (type != null)
			json.put("type", type);
		json.put("departid", departid);
		json.put("status", status);
		json.put("agree", agree);
		json.put("result", result);
		return json;
	}
	

}
