package info;

//ÉóÅúÇë¼Ù¡£¡£¡£
public class Approval {
	int id;
	int uid;
	String message;
	long starttime;
	long endtime;
	long duration;
	long departid;
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
	public long getStarttime() {
		return starttime;
	}
	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}
	public long getEndtime() {
		return endtime;
	}
	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public long getDepartid() {
		return departid;
	}
	public void setDepartid(long departid) {
		this.departid = departid;
	}
}
