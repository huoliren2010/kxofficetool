package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

import info.DepartMent;
import info.Manager;
import info.UserInfo;
import util.Response;

public class CreateManager extends HttpServlet {
	private static String TAG = CreateManager.class.getSimpleName();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 新建服务对象
		Service serv = new Service();

		// 接收信息
		int uid = Integer.valueOf(request.getParameter("uid"));
		int companyid = Integer.valueOf(request.getParameter("companyid"));
		String message;
		System.out.println("manager(" + uid + "," + companyid +") creating");
		// 验证处理
		UserInfo manager = serv.createManager(uid, companyid);
		int status = Response.ERROR_CODE;
		if (manager != null) {
			message = "Succss "+TAG;
			status = Response.SUCCESS_CODE;
		} else {
			message = "Failed "+TAG;
		}
		System.out.println(message);
		// 返回信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String resp = new Response(status, manager.toJSONObject(), message).toString();
		System.out.println(TAG+" resp=" + resp);
		out.print(resp);
		// out.print("用户名：" + username);
		// out.print("密码：" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}
}
