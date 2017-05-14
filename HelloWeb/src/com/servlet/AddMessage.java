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
import info.Message;
import info.UserInfo;
import util.Response;

public class AddMessage extends HttpServlet {
	private static String TAG = AddMessage.class.getSimpleName();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// �½��������
		Service serv = new Service();

		// ������Ϣ
		int uid = Integer.valueOf(request.getParameter("uid"));
		int fid = Integer.valueOf(request.getParameter("fid"));
		String msg = request.getParameter("message");
		String time = request.getParameter("time");
		String message;
		System.out.println("AddMessage(" + uid + "," + fid +", "+msg+","+time+") creating");
		// ��֤����
		
		Message mesage = serv.addMessage(uid, fid, msg, time);
		int status = Response.ERROR_CODE;
		if (mesage != null) {
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
		String resp = new Response(status, mesage.toJSONObject(), message).toString();
		System.out.println(TAG+" resp=" + resp);
		out.print(resp);
		// out.print("�û�����" + username);
		// out.print("���룺" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}
}
