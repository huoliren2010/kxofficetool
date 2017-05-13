package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

import info.DepartMent;
import util.Response;

public class CreateDepartMent extends HttpServlet {
	private static String TAG = CreateDepartMent.class.getSimpleName();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 新建服务对象
		Service serv = new Service();

		// 接收信息
		String departName = request.getParameter("departName");
		int companyid = Integer.valueOf(request.getParameter("companyid"));
		int leaderid = Integer.valueOf(request.getParameter("leaderid"));
		String message;
		// String password = request.getParameter("r_password");
		System.out.println("department(" + departName + "," + companyid + ", " + leaderid + ") creating");
		// 验证处理
		DepartMent department = serv.createDepartMent(departName, companyid, leaderid);
		int status = Response.ERROR_CODE;
		if (department != null) {
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
		String resp = new Response(status, department.toJSONObject(), message).toString();
		System.out.println(TAG+" resp=" + resp);
		out.print(resp);
		// out.print("用户名：" + username);
		// out.print("密码：" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}
}
