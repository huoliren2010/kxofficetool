package info;

public class DepartMent {
	int id;
	String partname;
	int leaderid;//as uid througth table user id
	int companyId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPartname() {
		return partname;
	}
	public void setPartname(String partname) {
		this.partname = partname;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getLeaderid() {
		return leaderid;
	}
	public void setLeaderid(int leaderid) {
		this.leaderid = leaderid;
	}
	
}
