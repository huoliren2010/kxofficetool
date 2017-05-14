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
import info.Message;
import info.UserInfo;
import util.Response;
import util.Utils;

public class QueryMessage extends HttpServlet {
	private static String TAG = QueryMessage.class.getSimpleName();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 新建服务对象
		Service serv = new Service();

		// 接收信息

		int uid = -1;
		try {
			uid = Integer.valueOf(request.getParameter("uid"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int fid = -1;
		try {
			fid = Integer.valueOf(request.getParameter("fid"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String message;
		System.out.println("QueryMessage(" + uid + ", " + fid + ") creating");
		// 验证处理
		List<Message> list = null;
		if (uid != -1 && fid != -1) {
			list = serv.queryMessage(uid, fid);
		} else if (fid != -1) {// get who send message to me
			list = serv.queryReceiveMessge(fid);
		} else if (uid != -1) {// get message i send
			list = serv.querySendMessage(uid);
		}
		int status = Response.ERROR_CODE;
		if (list != null && !list.isEmpty()) {
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
		String resp = new Response(status, Utils.transFromListMessage(list), message).toString();
		System.out.println(TAG + " resp=" + resp);
		out.print(resp);
		// out.print("用户名：" + username);
		// out.print("密码：" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}
}
