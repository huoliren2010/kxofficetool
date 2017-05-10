package info;

import org.json.JSONObject;

public class UserInfo {
	int id;
	String username;
	String password;
	String avatar;
	String phonenumber;
	String gender;
	int departmentid = -1;
	String signmessage;

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (username != null)
			json.put("username", username);
		if (password != null)
			json.put("password", password);
		if (avatar != null)
			json.put("avatar", avatar);
		else
			json.put("avatar", "");
		if (phonenumber != null)
			json.put("phonenumber", phonenumber);
		if (gender != null)
			json.put("gender", gender);
		else
			json.put("gender", "M");
		json.put("departmentid", departmentid);
		if (signmessage != null)
			json.put("signmessage", signmessage);
		else
			json.put("signmessage", "");

		return json.toString();
	}

	public UserInfo(int uid, String uname, String upsw, String uphone) {
		this.id = uid;
		this.username = uname;
		this.password = upsw;
		this.phonenumber = uphone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}

	public String getSignmessage() {
		return signmessage;
	}

	public void setSignmessage(String signmessage) {
		this.signmessage = signmessage;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (username != null)
			json.put("username", username);
		if (password != null)
			json.put("password", password);
		if (avatar != null)
			json.put("avatar", avatar);
		else
			json.put("avatar", "");
		if (phonenumber != null)
			json.put("phonenumber", phonenumber);
		if (gender != null)
			json.put("gender", gender);
		else
			json.put("gender", "M");
		json.put("departmentid", departmentid);
		if (signmessage != null)
			json.put("signmessage", signmessage);
		else
			json.put("signmessage", "");
		return json;
	}

}
