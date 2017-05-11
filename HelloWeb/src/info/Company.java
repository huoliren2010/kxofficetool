package info;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Company {
	int id;
	String companyName;
	int ownerid;

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (companyName != null)
			json.put("ownerid", ownerid);
		if (companyName != null)
			json.put("companyName", companyName);
		return json.toString();
	}

	public Company(int cid, String cname, int cuid) {
		this.id = cid;
		this.companyName = cname;
		this.ownerid = cuid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getOwner() {
		return ownerid;
	}

	public void setOwner(int builder) {
		this.ownerid = builder;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		if (companyName != null)
			json.put("ownerid", ownerid);
		if (companyName != null)
			json.put("companyName", companyName);
		if (mListDepartMents.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (DepartMent departMent : mListDepartMents) {
				JSONObject jsonDepart = departMent.toJSONObject();
				jsonArray.put(jsonDepart);
			}
			json.put("department", jsonArray);
		}
		return json;
	}

	List<DepartMent> mListDepartMents = new ArrayList<DepartMent>();

	public void addDepartMent(DepartMent departMent) {
		if (!mListDepartMents.contains(departMent))
			mListDepartMents.add(departMent);
	}
}
