package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.service.Service;

import info.UserInfo;
import util.ManipulateImage;
import util.Response;

public class UpdateUserInfo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 新建服务对象
		Service serv = new Service();

		// 接收注册信息
		int uid = Integer.valueOf(request.getParameter("id"));
		String gender = request.getParameter("gender");
		String avatar = request.getParameter("avatar");
//		avatar = new String(avatar.getBytes("ISO-8859-1"), "UTF-8");

		String signmessage = request.getParameter("signmessage");
		int departmentid = Integer.valueOf(request.getParameter("departmentid"));
		String uname = request.getParameter("uname");
		String confirm;
		System.out.println("user(" + uid + "," + gender + "," + avatar + "," + signmessage + "," + departmentid + ","
				+ uname + ") update");
		// 验证处理
		UserInfo inUserInfo = new UserInfo();
		inUserInfo.setId(uid);
		if(gender != null){
			inUserInfo.setGender(gender);
		}
		if(avatar != null)inUserInfo.setAvatar(avatar);
		if(signmessage != null)inUserInfo.setSignmessage(signmessage);
		inUserInfo.setDepartmentid(departmentid);
		if(uname != null)inUserInfo.setUsername(uname);
		UserInfo userInfo = serv.updateUser(inUserInfo);
		System.out.println("update " + userInfo.toString());
		int status = Response.ERROR_CODE;
		if (userInfo != null) {
			System.out.print("Succss updateUser");
			confirm = "update success";
			request.getSession().setAttribute("username", uname);
			status = Response.SUCCESS_CODE;

		} else {
			System.out.print("Failed");
			confirm = "updateFailed";
		}

		// 返回信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String resp = new Response(status, userInfo == null ? null : userInfo.toJSONObject(), confirm).toString();
		System.out.println("update resp=" + resp);
		out.print(resp);
		// out.print("用户名：" + username);
		// out.print("密码：" + password);
		// out.print(confirm);
		out.flush();
		out.close();

	}

}
