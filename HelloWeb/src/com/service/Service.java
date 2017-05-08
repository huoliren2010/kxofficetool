package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.DBManager;

import info.Approval;
import info.Company;
import info.DailySign;
import info.DepartMent;
import info.MeetingRoom;
import info.Message;
import info.Notice;
import info.UserInfo;

public class Service {

	public String login(String username, String password) {

		// 获取Sql查询语句
		String logSql = "select * from user where username ='" + username + "' and password ='" + password + "'";

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
				return userInfo.toString();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;
	}

	public String register(String username, String password, String phonenumber) {

		// 获取Sql查询语句
		String regSql = "insert into user (username,password, phonenumber) values('" + username + "','" + password
				+ "','" + phonenumber + "') ";

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
					return userInfo.toString();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();

		return null;
	}

	public String createCompany(String companyName, int uid) {
		String strSql = "insert into company(name, ownerid) values('" + companyName + "'," + uid + ")";
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
					Company company = new Company(cid, cname, cuid);
					dbmanager.closeDB();
					return company.toString();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;
	}

	public String createDepartMent(String departName, int companyid, int uid) {
		String strSql = "insert into department(partname, companyid, leaderid) values('" + departName + "'," + companyid
				+ ", " + uid + ")";
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
					return departMent.toString();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;
	}

	public String createRoom(String roomName, int companyid) {
		String strSql = "insert into companyRoom(roomname, companyid) values('" + roomName + "'," + companyid + ")";
		// 获取DB对象
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();

		int ret = dbmanager.executeUpdate(strSql);
		try {
			if (ret != 0) {
				strSql = "select * from companyRoom where roomname='" + roomName + "' and companyid=" + companyid;
				ResultSet rs = dbmanager.executeQuery(strSql);
				if (rs.next()) {
					int didColoumn = rs.findColumn("id");
					int did = rs.getInt(didColoumn);
					int rnameColoumn = rs.findColumn("roomname");
					String rname = rs.getString(rnameColoumn);
					int cidColoumn = rs.findColumn("companyid");
					int cid = rs.getInt(cidColoumn);
					MeetingRoom meetingRoom = new MeetingRoom(did, rname, cid);
					dbmanager.closeDB();
					return meetingRoom.toString();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;
	}

	public String createNotice(int departid, String message) {
		String strSql = "insert into notice(message, departid) values('" + message + "'," + departid + ")";
		// 获取DB对象
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();

		int ret = dbmanager.executeUpdate(strSql);
		try {
			if (ret != 0) {
				strSql = "select * from notice where message='" + message + "' and departid=" + departid;
				ResultSet rs = dbmanager.executeQuery(strSql);
				if (rs.next()) {
					int didColoumn = rs.findColumn("id");
					int did = rs.getInt(didColoumn);
					int rnameColoumn = rs.findColumn("message");
					String rname = rs.getString(rnameColoumn);
					int cidColoumn = rs.findColumn("departid");
					int cid = rs.getInt(cidColoumn);
					Notice meetingRoom = new Notice(did, rname, cid);
					dbmanager.closeDB();
					return meetingRoom.toString();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;
	}

	public String createMessage(int uid, int fid, String msg) {
		String strSql = "insert into message(uid, fid, msg) values(" + uid + "," + fid + ",'" + msg + "')";
		// 获取DB对象
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();

		int ret = dbmanager.executeUpdate(strSql);
		try {
			if (ret != 0) {
				strSql = "select * from message where uid=" + uid + "and fid=" + fid + "and msg=" + "'" + msg + "'";
				ResultSet rs = dbmanager.executeQuery(strSql);
				if (rs.next()) {
					int didColoumn = rs.findColumn("id");
					int id = rs.getInt(didColoumn);
					Message message = new Message(id, uid, fid, msg);
					dbmanager.closeDB();
					return message.toString();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;
	}

	public String createApproval(int uid, String message, String starttime, String endtime, String type, int leaderid) {
		String strSql = "insert into approval(uid, message, starttime, endtime, type, leaderid) values(" + uid + ",'"
				+ message + "','" + starttime + "','" + endtime + "','" + type + "," + leaderid + ")";
		// 获取DB对象
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();

		int ret = dbmanager.executeUpdate(strSql);
		try {
			if (ret != 0) {
				strSql = "select * from approval where uid=" + uid + "and starttime='" + starttime + "' and type=" + "'"
						+ type + "' and leaderid=" + leaderid;
				ResultSet rs = dbmanager.executeQuery(strSql);
				if (rs.next()) {
					int didColoumn = rs.findColumn("id");
					int id = rs.getInt(didColoumn);
					Approval approval = new Approval(id, uid, message, starttime, endtime, type, leaderid);
					dbmanager.closeDB();
					return approval.toString();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;
	}

	public String createDailySign(int uid, String address, String time, int departid) {
		String strSql = "insert into dailysign(uid, address, time, departid) values(" + uid + ",'" + address + "','"
				+ time + "'," + departid + ")";
		// 获取DB对象
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();

		int ret = dbmanager.executeUpdate(strSql);
		try {
			if (ret != 0) {
				strSql = "select * from dailysign where uid=" + uid + "and address='" + address + "' and time=" + "'"
						+ time + " and departid=" + departid;
				ResultSet rs = dbmanager.executeQuery(strSql);
				if (rs.next()) {
					int didColoumn = rs.findColumn("id");
					int id = rs.getInt(didColoumn);
					DailySign dailySign = new DailySign(id, uid, address, time, departid);
					dbmanager.closeDB();
					return dailySign.toString();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbmanager.closeDB();
		return null;
	}

	public boolean updateUserAvatar(int uid, String fileavatar) {
		String updateSql = "update user set avatar='" + fileavatar + "' where id=" + uid;
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		int ret = sql.executeUpdate(updateSql);
		sql.closeDB();
		if (ret != 0) {
			return true;
		}
		return false;
	}
}
