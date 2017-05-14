package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

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

public class CreateNotice extends HttpServlet {
	private static String TAG = CreateNotice.class.getSimpleName();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// �½��������
		Service serv = new Service();

		// ������Ϣ
		int uid = Integer.valueOf(request.getParameter("uid"));
		String title = request.getParameter("title");
		String msg = request.getParameter("message");
		int departid = Integer.valueOf(request.getParameter("departid"));
		String message;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = simpleDateFormat.format(new java.util.Date(System.currentTimeMillis()));
		System.out
				.println("CreateNotice(" + uid + "," + title + "," + msg + ", " + departid + "," + time + ") creating");
		// ��֤����

		Notice notice = serv.createNotice(uid, departid, title, msg, time);
		int status = Response.ERROR_CODE;
		if (notice != null) {
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
		String resp = new Response(status, notice.toJSONObject(), message).toString();
		System.out.println(TAG + " resp=" + resp);
		out.print(resp);
		// out.print("�û�����" + username);
		// out.print("���룺" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}
}
