package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.DBManager;

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

	public boolean createCompany(String companyName, int uid) {
		String strSql = "insert into company(name, ownerid) values('" + companyName + "'," + uid + ")";
		// 获取DB对象
		DBManager dbmanager = DBManager.createInstance();
		dbmanager.connectDB();

		int ret = dbmanager.executeUpdate(strSql);
		return false;
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
