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
		System.out.println("login sqlstr="+logSql);

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
		String sql = String.format(format, userInfo.getUsername(), userInfo.getAvatar(), userInfo.getGender(), userInfo.getDepartmentid(), userInfo.getSignmessage(), userInfo.getId());
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
					dbmanager.closeDB();
					// create default department
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
			while(rs.next()){
				int departmentId = rs.getInt(1);
				listDepartmentIds.add(departmentId);
			}
			mListUserInfos = new ArrayList<UserInfo>();
			for(Integer departmentId : listDepartmentIds){
				sql = String.format(format, departmentId);
				ResultSet executeQuery = dbmanager.executeQuery(sql);
				while(executeQuery.next()){
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
			while(rs.next()){
				int departmentId = rs.getInt(1);
				listDepartmentIds.add(departmentId);
			}
			mListUserInfos = new ArrayList<UserInfo>();
			for(Integer departmentId : listDepartmentIds){
				sql = String.format(format, departmentId);
				ResultSet executeQuery = dbmanager.executeQuery(sql);
				while(executeQuery.next()){
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
		String format = "select * from company where id=some(select companyid from department where departmentid=%d)";
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
				return companyInfo;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
	
	
}
