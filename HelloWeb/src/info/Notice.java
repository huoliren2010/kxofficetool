package info;

import org.json.JSONObject;

/**
 * 
 * ¹«¸æ
 *
 */
public class Notice {
	int id;
	int departmentid;
	String message;
	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (message != null)
			json.put("message", message);
		json.put("departmentid", departmentid);
		return json.toString();
	}

	public Notice(int did, String rname, int cid) {
		this.id = did;
		this.departmentid = cid;
		this.message = rname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
