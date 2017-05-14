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
		// �½��������
		Service serv = new Service();

		// ������Ϣ

		int uid = Integer.valueOf(request.getParameter("uid"));
		int departid = Integer.valueOf(request.getParameter("departid"));

		List<DailySign> queryDailySign = serv.queryDailySign(uid, departid);
		String message;
		System.out.println("QueryDailySign(" + uid + "," + departid + ") creating");
		// ��֤����
		int status = Response.ERROR_CODE;
		if (queryDailySign != null && queryDailySign.size() > 0) {
			message = "Succss " + TAG;
			status = Response.SUCCESS_CODE;
		} else {
			message = "Failed " + TAG;
		}
		System.out.println(message);
		// ������Ϣ
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String resp = new Response(status, Utils.transFromDailySigns(queryDailySign), message).toString();
		System.out.println(TAG + " resp=" + resp);
		out.print(resp);
		// out.print("�û�����" + username);
		// out.print("���룺" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}

}
