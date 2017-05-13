package com.kx.officetool.infos;

import org.json.JSONException;
import org.json.JSONObject;

public class Manager {
	int id;
	int uid;
	int companyid;

	public Manager(int did, int uid2, int cid) {
		this.id = did;
		this.uid = uid2;
		this.companyid = cid;
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

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}
	
	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
		json.put("uid", uid);
			json.put("compnayid", companyid);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}
