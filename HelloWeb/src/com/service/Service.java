package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.DBManager;

public class Service {

	public Boolean login(String username, String password) {

		// ��ȡSql��ѯ���
		String logSql = "select * from user where username ='" + username + "' and password ='" + password + "'";

		// ��ȡDB����
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		// ����DB����
		try {
			ResultSet rs = sql.executeQuery(logSql);
			if (rs.next()) {
				sql.closeDB();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}

	public Boolean register(String username, String password, String phonenumber) {

		// ��ȡSql��ѯ���
		String regSql = "insert into user (username,password, phonenumber) values('" + username + "','" + password
				+ "','" + phonenumber + "') ";

		// ��ȡDB����
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		int ret = sql.executeUpdate(regSql);
		// System.out.println("test"+ ret);
		if (ret != 0) {
			sql.closeDB();
			return true;
		}
		sql.closeDB();

		return false;
	}
}
