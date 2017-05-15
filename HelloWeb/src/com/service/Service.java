package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.db.DBManager;

import info.Approval;
import info.CommonInfo;
import info.CompanyInfo;
import info.DailySign;
import info.DepartMent;
import info.Manager;
import info.MeetingRoom;
import info.Message;
import info.Notice;
import info.UserInfo;

public class Service {

	public UserInfo login(String username, String password) {

		// 获取Sql查询语句
		String formatSql = "select * from user where username='%s' and password='%s'";
		String logSql = String.format(formatSql, username, password);
		System.out.println("login sqlstr=" + logSql);

		// 获取DB对象
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();

		// 操作DB对象
		try {
			ResultSet rs = dbmanager.executeQuery(logSql);
			if (rs.next()) {
				int uidColoumn = rs.findColumn("id");
				int uid = rs.getInt(uidColoumn);
				int unameColoumn = rs.findColumn("username");
				String uname = rs.getString(unameColoumn);
				int upswColoumn = rs.findColumn("password");
				String upsw = rs.getString(upswColoumn);
				int uphoneColoumn = rs.findColumn("phonenumber");
				String uphone = rs.getString(uphoneColoumn);
				int uavatarColoumn = rs.findColumn("avatar");
				String uavatar = rs.getString(uavatarColoumn);
				int ugenderColoumn = rs.findColumn("gender");
				String ugender = rs.getString(ugenderColoumn);
				int udepartmentidColoumn = rs.findColumn("departmentid");
				int udepartmentid = rs.getInt(udepartmentidColoumn);
				if (udepartmentid == 0)
					udepartmentid = -1;
				int usignmessageColoumn = rs.findColumn("signmessage");
				String usignmessage = rs.getString(usignmessageColoumn);
				UserInfo userInfo = new UserInfo(uid, uname, upsw, uphone);
				userInfo.setAvatar(uavatar);
				userInfo.setGender(ugender);
				userInfo.setDepartmentid(udepartmentid);
				userInfo.setSignmessage(usignmessage);
				dbmanager.closeDB();
				return userInfo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;
	}

	public UserInfo register(String username, String password, String phonenumber) {

		// 获取Sql查询语句
		String format = "insert into user (username,password, phonenumber) values('%s', '%s','%S')";
		String regSql = String.format(format, username, password, phonenumber);

		// 获取DB对象
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();

		int ret = dbmanager.executeUpdate(regSql);
		// System.out.println("test"+ ret);
		try {
			if (ret != 0) {
				String querySql = "select * from user where username='" + username + "' and password='" + password
						+ "' and phonenumber='" + phonenumber + "'";
				ResultSet rs = dbmanager.executeQuery(querySql);
				if (rs.next()) {
					int uidColoumn = rs.findColumn("id");
					int uid = rs.getInt(uidColoumn);
					int unameColoumn = rs.findColumn("username");
					String uname = rs.getString(unameColoumn);
					int upswColoumn = rs.findColumn("password");
					String upsw = rs.getString(upswColoumn);
					int uphoneColoumn = rs.findColumn("phonenumber");
					String uphone = rs.getString(uphoneColoumn);
					UserInfo userInfo = new UserInfo(uid, uname, upsw, uphone);
					dbmanager.closeDB();
					return userInfo;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();

		return null;
	}

	public UserInfo updateUser(UserInfo userInfo) {
		String format = "update user set username='%s', avatar='%s', gender='%s', departmentid=%d, signmessage='%s' where id=%d";
		String sql = String.format(format, userInfo.getUsername(), userInfo.getAvatar(), userInfo.getGender(),
				userInfo.getDepartmentid(), userInfo.getSignmessage(), userInfo.getId());
		// 获取DB对象
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		int ret = dbmanager.executeUpdate(sql);
		try {
			if (ret != 0) {
				String querySql = "select * from user where id=" + userInfo.getId();
				// 获取DB对象
				ResultSet rs = dbmanager.executeQuery(querySql);
				if (rs.next()) {
					int uidColoumn = rs.findColumn("id");
					int uid = rs.getInt(uidColoumn);
					int unameColoumn = rs.findColumn("username");
					String uname = rs.getString(unameColoumn);
					int upswColoumn = rs.findColumn("password");
					String upsw = rs.getString(upswColoumn);
					int uphoneColoumn = rs.findColumn("phonenumber");
					String uphone = rs.getString(uphoneColoumn);
					UserInfo userInfor = new UserInfo(uid, uname, upsw, uphone);
					dbmanager.closeDB();
					return userInfor;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();

		return null;
	}

	public CompanyInfo createCompany(String companyName, int uid) {
		String format = "insert into company(name, ownerid) values('%s', %d)";
		String strSql = String.format(format, companyName, uid);
		// 获取DB对象
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();

		int ret = dbmanager.executeUpdate(strSql);
		try {
			if (ret != 0) {
				strSql = "select * from company where name='" + companyName + "' and ownerid=" + uid;
				ResultSet rs = dbmanager.executeQuery(strSql);
				if (rs.next()) {
					int cidColoumn = rs.findColumn("id");
					int cid = rs.getInt(cidColoumn);
					int cnameColoumn = rs.findColumn("name");
					String cname = rs.getString(cnameColoumn);
					int uidColoumn = rs.findColumn("ownerid");
					int cuid = rs.getInt(uidColoumn);
					CompanyInfo company = new CompanyInfo(cid, cname, cuid);
					// create default department
					dbmanager.closeDB();
					DepartMent departMent = createDepartMent(CommonInfo.NOMRAL_DEPARTMENT, cid, cuid);
					if (departMent != null) {
						List<DepartMent> list = new ArrayList<DepartMent>();
						list.add(departMent);
						company.setDepartment(list);
					}
					return company;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;
	}

	public DepartMent createDepartMent(String departName, int companyid, int uid) {
		String format = "insert into department(partname, companyid, leaderid) values('%s', %d, %d)";
		String strSql = String.format(format, departName, companyid, uid);
		// 获取DB对象
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();

		int ret = dbmanager.executeUpdate(strSql);
		try {
			if (ret != 0) {
				strSql = "select * from department where partname='" + departName + "' and companyid=" + companyid
						+ " and leaderid=" + uid;
				ResultSet rs = dbmanager.executeQuery(strSql);
				if (rs.next()) {
					int didColoumn = rs.findColumn("id");
					int did = rs.getInt(didColoumn);
					int dnameColoumn = rs.findColumn("partname");
					String dname = rs.getString(dnameColoumn);
					int cidColoumn = rs.findColumn("companyid");
					int cid = rs.getInt(cidColoumn);
					int uidColoumn = rs.findColumn("leaderid");
					int duid = rs.getInt(uidColoumn);
					DepartMent departMent = new DepartMent(did, dname, cid, duid);
					dbmanager.closeDB();
					return departMent;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;
	}

	public UserInfo createManager(int uid, int companyid) {
		String format = "insert into manager(uid, companyid) values(%d, %d)";
		String sql = String.format(format, uid, companyid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();

		try {
			int ret = dbmanager.executeUpdate(sql);
			if (ret != 0) {
				String formatSql = "select * from user where id=%d";
				String logSql = String.format(formatSql, uid);

				ResultSet rs = dbmanager.executeQuery(logSql);
				if (rs.next()) {
					int unameColoumn = rs.findColumn("username");
					String uname = rs.getString(unameColoumn);
					int upswColoumn = rs.findColumn("password");
					String upsw = rs.getString(upswColoumn);
					int uphoneColoumn = rs.findColumn("phonenumber");
					String uphone = rs.getString(uphoneColoumn);
					int uavatarColoumn = rs.findColumn("avatar");
					String uavatar = rs.getString(uavatarColoumn);
					int ugenderColoumn = rs.findColumn("gender");
					String ugender = rs.getString(ugenderColoumn);
					int udepartmentidColoumn = rs.findColumn("departmentid");
					int udepartmentid = rs.getInt(udepartmentidColoumn);
					if (udepartmentid == 0)
						udepartmentid = -1;
					int usignmessageColoumn = rs.findColumn("signmessage");
					String usignmessage = rs.getString(usignmessageColoumn);
					UserInfo userInfo = new UserInfo(uid, uname, upsw, uphone);
					userInfo.setAvatar(uavatar);
					userInfo.setGender(ugender);
					userInfo.setDepartmentid(udepartmentid);
					userInfo.setSignmessage(usignmessage);
					dbmanager.closeDB();
					return userInfo;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;

	}

	public List<UserInfo> queryAllUserFromDepartmentid(int departmentid) {
		String format = "select id from department where companyid=some(select companyid from department where id=%d)";
		String sql = String.format(format, departmentid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		List<UserInfo> mListUserInfos = null;
		try {
			ResultSet rs = dbmanager.executeQuery(sql);
			format = "select * from user where departmentid=%d";
			List<Integer> listDepartmentIds = new ArrayList<Integer>();
			while (rs.next()) {
				int departmentId = rs.getInt(1);
				listDepartmentIds.add(departmentId);
			}
			mListUserInfos = new ArrayList<UserInfo>();
			for (Integer departmentId : listDepartmentIds) {
				sql = String.format(format, departmentId);
				ResultSet executeQuery = dbmanager.executeQuery(sql);
				while (executeQuery.next()) {
					int idColoumn = executeQuery.findColumn("id");
					int uid = executeQuery.getInt(idColoumn);
					int unameColoumn = executeQuery.findColumn("username");
					String uname = executeQuery.getString(unameColoumn);
					int upswColoumn = executeQuery.findColumn("password");
					String upsw = executeQuery.getString(upswColoumn);
					int uphoneColoumn = executeQuery.findColumn("phonenumber");
					String uphone = executeQuery.getString(uphoneColoumn);
					int uavatarColoumn = executeQuery.findColumn("avatar");
					String uavatar = executeQuery.getString(uavatarColoumn);
					int ugenderColoumn = executeQuery.findColumn("gender");
					String ugender = executeQuery.getString(ugenderColoumn);
					int udepartmentidColoumn = executeQuery.findColumn("departmentid");
					int udepartmentid = executeQuery.getInt(udepartmentidColoumn);
					if (udepartmentid == 0)
						udepartmentid = -1;
					int usignmessageColoumn = executeQuery.findColumn("signmessage");
					String usignmessage = executeQuery.getString(usignmessageColoumn);
					UserInfo userInfo = new UserInfo(uid, uname, upsw, uphone);
					userInfo.setAvatar(uavatar);
					userInfo.setGender(ugender);
					userInfo.setDepartmentid(udepartmentid);
					userInfo.setSignmessage(usignmessage);
					mListUserInfos.add(userInfo);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.connectDB();
		return mListUserInfos;
	}

	public List<UserInfo> queryAllUserFromCompanyid(int companyid) {
		String format = "select id from department where companyid=%d";
		String sql = String.format(format, companyid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		List<UserInfo> mListUserInfos = null;
		try {
			ResultSet rs = dbmanager.executeQuery(sql);
			format = "select * from user where departmentid=%d";
			List<Integer> listDepartmentIds = new ArrayList<Integer>();
			while (rs.next()) {
				int departmentId = rs.getInt(1);
				listDepartmentIds.add(departmentId);
			}
			mListUserInfos = new ArrayList<UserInfo>();
			for (Integer departmentId : listDepartmentIds) {
				sql = String.format(format, departmentId);
				ResultSet executeQuery = dbmanager.executeQuery(sql);
				while (executeQuery.next()) {
					int idColoumn = executeQuery.findColumn("id");
					int uid = executeQuery.getInt(idColoumn);
					int unameColoumn = executeQuery.findColumn("username");
					String uname = executeQuery.getString(unameColoumn);
					int upswColoumn = executeQuery.findColumn("password");
					String upsw = executeQuery.getString(upswColoumn);
					int uphoneColoumn = executeQuery.findColumn("phonenumber");
					String uphone = executeQuery.getString(uphoneColoumn);
					int uavatarColoumn = executeQuery.findColumn("avatar");
					String uavatar = executeQuery.getString(uavatarColoumn);
					int ugenderColoumn = executeQuery.findColumn("gender");
					String ugender = executeQuery.getString(ugenderColoumn);
					int udepartmentidColoumn = executeQuery.findColumn("departmentid");
					int udepartmentid = executeQuery.getInt(udepartmentidColoumn);
					if (udepartmentid == 0)
						udepartmentid = -1;
					int usignmessageColoumn = executeQuery.findColumn("signmessage");
					String usignmessage = executeQuery.getString(usignmessageColoumn);
					UserInfo userInfo = new UserInfo(uid, uname, upsw, uphone);
					userInfo.setAvatar(uavatar);
					userInfo.setGender(ugender);
					userInfo.setDepartmentid(udepartmentid);
					userInfo.setSignmessage(usignmessage);
					mListUserInfos.add(userInfo);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.connectDB();
		return mListUserInfos;
	}

	public List<UserInfo> queryManagersFromCompanyid(int companyid) {
		String format = "select * from user where id=some(select uid from manager where companyid=%d)";
		String sql = String.format(format, companyid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		List<UserInfo> mListUserInfos = null;
		try {
			mListUserInfos = new ArrayList<UserInfo>();
			ResultSet executeQuery = dbmanager.executeQuery(sql);
			while (executeQuery.next()) {
				int idColoumn = executeQuery.findColumn("id");
				int uid = executeQuery.getInt(idColoumn);
				int unameColoumn = executeQuery.findColumn("username");
				String uname = executeQuery.getString(unameColoumn);
				int upswColoumn = executeQuery.findColumn("password");
				String upsw = executeQuery.getString(upswColoumn);
				int uphoneColoumn = executeQuery.findColumn("phonenumber");
				String uphone = executeQuery.getString(uphoneColoumn);
				int uavatarColoumn = executeQuery.findColumn("avatar");
				String uavatar = executeQuery.getString(uavatarColoumn);
				int ugenderColoumn = executeQuery.findColumn("gender");
				String ugender = executeQuery.getString(ugenderColoumn);
				int udepartmentidColoumn = executeQuery.findColumn("departmentid");
				int udepartmentid = executeQuery.getInt(udepartmentidColoumn);
				if (udepartmentid == 0)
					udepartmentid = -1;
				int usignmessageColoumn = executeQuery.findColumn("signmessage");
				String usignmessage = executeQuery.getString(usignmessageColoumn);
				UserInfo userInfo = new UserInfo(uid, uname, upsw, uphone);
				userInfo.setAvatar(uavatar);
				userInfo.setGender(ugender);
				userInfo.setDepartmentid(udepartmentid);
				userInfo.setSignmessage(usignmessage);
				mListUserInfos.add(userInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.connectDB();
		return mListUserInfos;
	}

	public CompanyInfo queryCompanyInfoFromCompanyid(int compnayid) {
		String format = "select * from company where id=%d";
		String sql = String.format(format, compnayid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rs = dbmanager.executeQuery(sql);
		try {
			if (rs.next()) {
				int cidColoumn = rs.findColumn("id");
				int cid = rs.getInt(cidColoumn);
				int cnameColoumn = rs.findColumn("name");
				String cname = rs.getString(cnameColoumn);
				int uidColoumn = rs.findColumn("ownerid");
				int cuid = rs.getInt(uidColoumn);
				dbmanager.closeDB();
				CompanyInfo companyInfo = new CompanyInfo(cid, cname, cuid);
				companyInfo.setCompanyMembers(queryAllUserFromCompanyid(cid));
				companyInfo.setCompanyManagers(queryManagersFromCompanyid(cid));
				companyInfo.setCompanyMeetingRooms(queryMeetingRoomFormCompanyid(cid));
				return companyInfo;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public CompanyInfo queryCompanyInfoFromDepartmentid(int departmentid) {
		String format = "select * from company where id=some(select companyid from department where id=%d)";
		String sql = String.format(format, departmentid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rs = dbmanager.executeQuery(sql);
		try {
			if (rs.next()) {
				int cidColoumn = rs.findColumn("id");
				int cid = rs.getInt(cidColoumn);
				int cnameColoumn = rs.findColumn("name");
				String cname = rs.getString(cnameColoumn);
				int uidColoumn = rs.findColumn("ownerid");
				int cuid = rs.getInt(uidColoumn);
				dbmanager.closeDB();
				CompanyInfo companyInfo = new CompanyInfo(cid, cname, cuid);
				companyInfo.setCompanyMembers(queryAllUserFromCompanyid(cid));
				companyInfo.setCompanyManagers(queryManagersFromCompanyid(cid));
				companyInfo.setCompanyMeetingRooms(queryMeetingRoomFormCompanyid(cid));
				companyInfo.setDepartment(queryDepartFromCompanyid(cid));
				return companyInfo;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private List<DepartMent> queryDepartFromCompanyid(int cid) {
		String format = "select * from department where companyid=%d";
		String sql = String.format(format, cid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rs = dbmanager.executeQuery(sql);
		List<DepartMent> list = null;
		try {
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<DepartMent>();
				int idColoumn = rs.findColumn("id");
				int partnameCol = rs.findColumn("partname");
				int uidCol = rs.findColumn("leaderid");
				int id = rs.getInt(idColoumn);
				String partname = rs.getString(partnameCol);
				int uid = rs.getInt(uidCol);
				DepartMent dpart = new DepartMent(id, partname, cid, uid);
				list.add(dpart);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return list;
	}

	public List<MeetingRoom> queryMeetingRoomFormCompanyid(int cid) {
		String format = "select * from companyRoom where companyid=%d";
		String sql = String.format(format, cid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rs = dbmanager.executeQuery(sql);
		List<MeetingRoom> list = null;
		try {
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<MeetingRoom>();
				int idColoumn = rs.findColumn("id");
				int id = rs.getInt(idColoumn);
				int rnameColoumn = rs.findColumn("roomname");
				String rname = rs.getString(rnameColoumn);
				MeetingRoom met = new MeetingRoom(id, rname, cid);
				list.add(met);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return list;
	}

	public MeetingRoom createMeetingRoom(String roomname, int companyid) {
		String format = "insert into companyRoom(roomname, companyid) values('%s', %d)";
		String sql = String.format(format, roomname, companyid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		int exrt = dbmanager.executeUpdate(sql);
		MeetingRoom mt = null;
		if (exrt != 0) {
			format = "select id from companyRoom where roomname='%s' and companyid=%d";
			sql = String.format(format, roomname, companyid);
			ResultSet rt = dbmanager.executeQuery(sql);
			try {
				if (rt.next()) {
					int id = rt.getInt(1);
					mt = new MeetingRoom(id, roomname, companyid);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dbmanager.closeDB();
		return mt;
	}

	public DailySign createDailySign(int uid, String address, String time, int departid) {
		String format = "insert into dailysign(uid, address, time, departid) values(%d,'%s','%s',%d)";
		String sql = String.format(format, uid, address, time, departid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		int exrt = dbmanager.executeUpdate(sql);
		DailySign dailySign = null;
		if (exrt != 0) {
			format = "select id from dailysign where uid=%d and address='%s' and time='%s' and departid=%d";
			sql = String.format(format, uid, address, time, departid);
			ResultSet rt = dbmanager.executeQuery(sql);
			try {
				if (rt.next()) {
					int id = rt.getInt(1);
					dailySign = new DailySign(id, uid, address, time, departid);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dbmanager.closeDB();
		return dailySign;
	}

	public List<DailySign> queryDailySign(int uid, int departid) {
		String format = "select * from dailysign where uid=%d and departid=%d";
		String sql = String.format(format, uid, departid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rt = dbmanager.executeQuery(sql);
		List<DailySign> list = null;
		try {
			while (rt.next()) {
				if (list == null)
					list = new ArrayList<DailySign>();
				int idCol = rt.findColumn("id");
				int addressCol = rt.findColumn("address");
				int timeCol = rt.findColumn("time");

				int id = rt.getInt(idCol);
				String address = rt.getString(addressCol);
				String time = rt.getString(timeCol);
				DailySign dailySign = new DailySign(id, uid, address, time, departid);
				list.add(dailySign);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return list;
	}

	public Approval createApproval(int uid, String uname, String message, String starttime, String endtime, String type,
			int departid) {
		String format = "insert into approval(uid, uname, message, starttime, endtime,type, departid, status) values(%d, '%s', '%s', '%s', '%s', '%s', %d, '%s')";
		String sql = String.format(format, uid, uname, message, starttime, endtime, type, departid,
				Approval.STATUS_START);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		int exrt = dbmanager.executeUpdate(sql);
		Approval approval = null;
		if (exrt != 0) {
			format = "select id from approval where uid=%d and starttime='%s' and endtime='%s' and departid=%d";
			sql = String.format(format, uid, starttime, endtime, departid);
			ResultSet rt = dbmanager.executeQuery(sql);
			try {
				if (rt.next()) {
					int id = rt.getInt(1);
					approval = new Approval(id, uid, uname, message, starttime, endtime, type, departid,
							Approval.STATUS_START);
					approval.setAgree(false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dbmanager.closeDB();
		return approval;
	}

	public List<Approval> queryApproval(int departid) {
		String format = "select * from approval where departid=%d and status='%s'";
		String sql = String.format(format, departid, Approval.STATUS_START);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rt = dbmanager.executeQuery(sql);
		List<Approval> list = null;
		try {
			while (rt.next()) {
				if (list == null)
					list = new ArrayList<Approval>();
				int idCol = rt.findColumn("id");
				int uidCol = rt.findColumn("uid");
				int unameCol = rt.findColumn("uname");
				int messageCol = rt.findColumn("message");
				int starttCol = rt.findColumn("starttime");
				int endtCol = rt.findColumn("endtime");
				int tyCol = rt.findColumn("type");
				int statusCol = rt.findColumn("status");
				int id = rt.getInt(idCol);
				int uid = rt.getInt(uidCol);
				String uname = rt.getString(unameCol);
				String message = rt.getString(messageCol);
				String starttime = rt.getString(starttCol);
				String endtime = rt.getString(endtCol);
				String type = rt.getString(tyCol);
				String status = rt.getString(statusCol);
				Approval app = new Approval(id, uid, uname, message, starttime, endtime, type, departid, status);
				list.add(app);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return list;
	}

	public boolean updateApproval(int id, String agree, String result) {
		String format = "update approval set agree=%s, result='%s', status='%s' where id=%d";
		String sql = String.format(format, agree, result, Approval.STATUS_END, id);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		int exrt = dbmanager.executeUpdate(sql);
		dbmanager.closeDB();
		if (exrt != 0) {
			return true;
		}
		return false;
	}

	public Notice createNotice(int uid, int departid, String title, String message, String time) {
		String format = "insert into notice(uid, departid, title, message, time) values(%d, %d, '%s', '%s', '%s')";
		String sql = String.format(format, uid, departid, title, message, time);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		int exrt = dbmanager.executeUpdate(sql);
		Notice notice = null;
		if (exrt != 0) {
			format = "select id from notice where uid=%d and departid=%d and title=’%s' and message='%s' and time='%s'";
			sql = String.format(format, uid, departid, title, message, time);
			ResultSet rt = dbmanager.executeQuery(sql);
			try {
				if (rt.next()) {
					int id = rt.getInt(1);
					notice = new Notice(id, uid, departid, title, message, time);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dbmanager.closeDB();
		return notice;
	}

	public List<Notice> queryNotice(int departid) {
		String format = "select * from notice where departid=%d";
		String sql = String.format(format, departid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rt = dbmanager.executeQuery(sql);
		List<Notice> list = null;
		try {
			while (rt.next()) {
				if (list == null)
					list = new ArrayList<Notice>();
				int idCol = rt.findColumn("id");
				int uidCol = rt.findColumn("uid");
				int titleCol = rt.findColumn("title");
				int msgCol = rt.findColumn("message");
				int timeCol = rt.findColumn("time");
				int id = rt.getInt(idCol);
				int uid = rt.getInt(uidCol);
				String time = rt.getString(timeCol);
				String title = rt.getString(titleCol);
				String message = rt.getString(msgCol);
				Notice notice = new Notice(id, uid, departid, title, message, time);
				list.add(notice);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return list;
	}

	public boolean updateNotice(int id, String message) {
		String format = "update notice set message='%s' where id=%d";
		String sql = String.format(format, message, id);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		int rt = dbmanager.executeUpdate(sql);
		dbmanager.closeDB();
		if (rt != 0)
			return true;
		return false;
	}

	public boolean deleteNotice(int id) {
		String format = "delete from notice where id=%d";
		String sql = String.format(format, id);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		int rt = dbmanager.executeUpdate(sql);
		dbmanager.closeDB();
		if (rt != 0)
			return true;
		return false;
	}

	public Message addMessage(int uid, int fid, String message, String time) {
		String format = "insert into message(uid, fid, message, time) values(%d, %d, '%s','%s')";
		String sql = String.format(format, uid, fid, message, time);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		Message msg = null;
		int exrt = dbmanager.executeUpdate(sql);
		if (exrt != 0) {
			format = "select id from message where uid=%d and fid=%d and message='%s' and time='%s'";
			sql = String.format(format, uid, fid, message, time);
			ResultSet rt = dbmanager.executeQuery(sql);
			try {
				if (rt.next()) {
					int id = rt.getInt(1);
					msg = new Message(id, uid, fid, message, time);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dbmanager.closeDB();
		return msg;
	}

	public List<Message> queryReceiveMessge(int fid) {
		String format = "select * from message where fid=%d";
		String sql = String.format(format, fid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rt = dbmanager.executeQuery(sql);
		List<Message> list = null;
		try {
			while (rt.next()) {
				if (list == null)
					list = new ArrayList<Message>();
				int idCol = rt.findColumn("id");
				int uidCol = rt.findColumn("uid");
				int msgCol = rt.findColumn("message");
				int timeCol = rt.findColumn("time");

				int id = rt.getInt(idCol);
				int uid = rt.getInt(uidCol);
				String message = rt.getString(msgCol);
				String time = rt.getString(timeCol);
				Message msg = new Message(id, uid, fid, message, time);
				list.add(msg);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return list;
	}

	public List<Message> querySendMessage(int uid) {
		String format = "select * from message where uid=%d";
		String sql = String.format(format, uid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rt = dbmanager.executeQuery(sql);
		List<Message> list = null;
		try {
			while (rt.next()) {
				if (list == null)
					list = new ArrayList<Message>();
				int idCol = rt.findColumn("id");
				int fidCol = rt.findColumn("fid");
				int msgCol = rt.findColumn("message");
				int timeCol = rt.findColumn("time");

				int id = rt.getInt(idCol);
				int fid = rt.getInt(fidCol);
				String message = rt.getString(msgCol);
				String time = rt.getString(timeCol);
				Message msg = new Message(id, uid, fid, message, time);
				list.add(msg);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return list;
	}

	public List<Message> queryMessage(int uid, int fid) {
		String format = "select * from message where uid=%d and fid=%d";
		String sql = String.format(format, uid, fid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rt = dbmanager.executeQuery(sql);
		List<Message> list = null;
		try {
			while (rt.next()) {
				if (list == null)
					list = new ArrayList<Message>();
				int idCol = rt.findColumn("id");
				int msgCol = rt.findColumn("message");
				int timeCol = rt.findColumn("time");

				int id = rt.getInt(idCol);
				String message = rt.getString(msgCol);
				String time = rt.getString(timeCol);
				Message msg = new Message(id, uid, fid, message, time);
				list.add(msg);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return list;
	}

	public List<CompanyInfo> queryCompanyInfoFromCompanyName(String companyname) {
		String format = "select * from company where name='%s'";
		String sql = String.format(format, companyname);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rs = dbmanager.executeQuery(sql);
		List<CompanyInfo> list = null;
		try {
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<CompanyInfo>();
				int cidColoumn = rs.findColumn("id");
				int cid = rs.getInt(cidColoumn);
				int cnameColoumn = rs.findColumn("name");
				String cname = rs.getString(cnameColoumn);
				int uidColoumn = rs.findColumn("ownerid");
				int cuid = rs.getInt(uidColoumn);
				CompanyInfo companyInfo = new CompanyInfo(cid, cname, cuid);
				list.add(companyInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return list;
	}

	public int joinCompany(int uid, int companyid) {
		String format = "select id from department where companyid=%d";
		String sql = String.format(format, companyid);
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();
		ResultSet rs = dbmanager.executeQuery(sql);
		int departid = 0;
		try {
			if (rs.next()) {
				departid = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return departid;
	}
}
