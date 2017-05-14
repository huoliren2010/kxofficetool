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
import info.UserInfo;
import util.Response;

public class CreateApproval extends HttpServlet {
	private static String TAG = CreateApproval.class.getSimpleName();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// �½��������
		Service serv = new Service();

		// ������Ϣ
		int uid = Integer.valueOf(request.getParameter("uid"));
		String uname = request.getParameter("uname");
		String msg = request.getParameter("message");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String type = request.getParameter("type");
		int departid = Integer.valueOf(request.getParameter("departid"));
		String message;
		System.out.println("Approval(" + uid + "," + uname +", "+msg+","+starttime+","+endtime+","+type+","+departid+") creating");
		// ��֤����
		
		Approval approval = serv.createApproval(uid, uname, msg, starttime, endtime, type, departid);
		int status = Response.ERROR_CODE;
		if (approval != null) {
			message = "Succss "+TAG;
			status = Response.SUCCESS_CODE;
		} else {
			message = "Failed "+TAG;
		}
		System.out.println(message);
		// ������Ϣ
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String resp = new Response(status, approval.toJSONObject(), message).toString();
		System.out.println(TAG+" resp=" + resp);
		out.print(resp);
		// out.print("�û�����" + username);
		// out.print("���룺" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}
}
