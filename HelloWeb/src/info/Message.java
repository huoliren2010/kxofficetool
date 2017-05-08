package info;

import org.json.JSONObject;

public class Message {
	int id, uid, fid;
	String msg;

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (msg != null)
			json.put("msg", msg);
		json.put("uid", uid);
		json.put("fid", fid);
		return json.toString();
	}

	public Message(int id2, int uid2, int fid2, String msg2) {
		this.id = id2;
		this.uid = uid2;
		this.fid = fid2;
		this.msg = msg2;
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
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
