package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

import info.CompanyInfo;
import info.DailySign;
import info.UserInfo;
import util.Response;
import util.Utils;

public class QueryDailySign extends HttpServlet {
	private static String TAG = QueryDailySign.class.getSimpleName();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 新建服务对象
		Service serv = new Service();

		// 接收信息

		int uid = Integer.valueOf(request.getParameter("uid"));
		int departid = Integer.valueOf(request.getParameter("departid"));

		List<DailySign> queryDailySign = serv.queryDailySign(uid, departid);
		String message;
		System.out.println("QueryDailySign(" + uid + "," + departid + ") creating");
		// 验证处理
		int status = Response.ERROR_CODE;
		if (queryDailySign != null && queryDailySign.size() > 0) {
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

		String resp = new Response(status, Utils.transFromDailySigns(queryDailySign), message).toString();
		System.out.println(TAG + " resp=" + resp);
		out.print(resp);
		// out.print("用户名：" + username);
		// out.print("密码：" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}

}
