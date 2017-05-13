package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

import info.CompanyInfo;
import info.MeetingRoom;
import util.Response;

public class CreateRoom extends HttpServlet {
	private static String TAG = CreateRoom.class.getSimpleName();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 新建服务对象
		Service serv = new Service();

		// 接收信息
		String roomname = request.getParameter("roomname");
		int companyid = Integer.valueOf(request.getParameter("companyid"));
		String confirm;
		// String password = request.getParameter("r_password");
		System.out.println("roomname(" + roomname + "," + companyid + ") creating");
		// 验证处理
		MeetingRoom mt = serv.createMeetingRoom(roomname, companyid);
		int status = Response.ERROR_CODE;
		String message;
		if (mt != null) {
			message = "Succss "+TAG;
			status = Response.SUCCESS_CODE;
		} else {
			message = "Failed "+TAG;
		}

		// 返回信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String resp = new Response(status, mt.toJSONObject(), message).toString();
		System.out.println("CreateCompany resp=" + resp);
		out.print(resp);
		// out.print("用户名：" + username);
		// out.print("密码：" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}

}
