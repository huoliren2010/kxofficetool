package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

import info.Approval;
import info.DailySign;
import info.DepartMent;
import info.Manager;
import info.Notice;
import info.UserInfo;
import util.Response;
import util.Utils;

public class UpdateNotice extends HttpServlet {
	private static String TAG = UpdateNotice.class.getSimpleName();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 新建服务对象
		Service serv = new Service();

		// 接收信息

		int id = Integer.valueOf(request.getParameter("id"));
		String type = request.getParameter("type");
		String msg = request.getParameter("message");

		String message;
		System.out.println("UpdateNotice(" + id + "," + type + "," + msg + ") creating");
		// 验证处理
		boolean rs = false;
		if (type.equalsIgnoreCase(Notice.TYPE_UPDATE)) {
			rs = serv.updateNotice(id, msg);
		} else if (type.equalsIgnoreCase(Notice.TYPE_DELETE)) {
			rs = serv.deleteNotice(id);
		}
		int status = Response.ERROR_CODE;
		if (rs) {
			message = "Succss " + TAG;
			status = Response.SUCCESS_CODE;
		} else {
			message = "Failed " + TAG;
		}
		System.out.println(message);
		// 返回信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String resp = new Response(status, rs, message).toString();
		System.out.println(TAG + " resp=" + resp);
		out.print(resp);
		// out.print("用户名：" + username);
		// out.print("密码：" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}
}
