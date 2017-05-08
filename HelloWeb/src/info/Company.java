package info;

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

}
