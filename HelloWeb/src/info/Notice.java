package info;

import java.util.Collection;

import org.json.JSONObject;

/**
 * 
 * ¹«¸æ
 *
 */
public class Notice {
	public static String TYPE_UPDATE = "update";
	public static String TYPE_DELETE = "delete";
	int id;
	int uid;
	int departid;
	String title;
	String message;
	String time;

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("uid", uid);
		if (title != null)
			json.put("title", title);
		if (message != null)
			json.put("message", message);
		json.put("departid", departid);
		json.put("time", time);
		return json.toString();
	}

	public Notice(int id2, int uid, int departid, String title, String message2, String time) {
		this.id = id2;
		this.uid = uid;
		this.departid = departid;
		this.title = title;
		this.message = message2;
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

	public int getDepartid() {
		return departid;
	}

	public void setDepartid(int departid) {
		this.departid = departid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("uid", uid);
		if (title != null)
			json.put("title", title);
		if (message != null)
			json.put("message", message);
		json.put("departid", departid);
		json.put("time", time);
		return json;
	}

}
