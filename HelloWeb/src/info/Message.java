package info;

import org.json.JSONObject;

public class Message {
	int id, uid, fid;
	String message;
	String time;

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (message != null)
			json.put("message", message);
		json.put("uid", uid);
		json.put("fid", fid);
		json.put("time", time);
		return json.toString();
	}


	public Message(int id, int uid, int fid, String message, String time) {
		super();
		this.id = id;
		this.uid = uid;
		this.fid = fid;
		this.message = message;
		this.time = time;
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

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getMsg() {
		return message;
	}

	public void setMsg(String msg) {
		this.message = msg;
	}


	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (message != null)
			json.put("message", message);
		json.put("uid", uid);
		json.put("fid", fid);
		json.put("time", time);
		return json;
	}

}
