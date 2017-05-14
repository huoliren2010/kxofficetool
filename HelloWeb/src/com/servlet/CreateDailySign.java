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

import info.DailySign;
import info.DepartMent;
import info.Manager;
import info.UserInfo;
import util.Response;

public class CreateDailySign extends HttpServlet {
	private static String TAG = CreateDailySign.class.getSimpleName();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 新建服务对象
		Service serv = new Service();

		// 接收信息
		int uid = Integer.valueOf(request.getParameter("uid"));
		String address = request.getParameter("address");
		
		int departid = Integer.valueOf(request.getParameter("departid"));
		String message;
		System.out.println("DailySign(" + uid + "," + address +", "+departid+") creating");
		// 验证处理
		long currentTimeMillis = System.currentTimeMillis();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = simpleDateFormat.format(new Date(currentTimeMillis));
		
		DailySign createDailySign = serv.createDailySign(uid, address, time, departid);
		int status = Response.ERROR_CODE;
		if (createDailySign != null) {
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
		String resp = new Response(status, createDailySign.toJSONObject(), message).toString();
		System.out.println(TAG+" resp=" + resp);
		out.print(resp);
		// out.print("用户名：" + username);
		// out.print("密码：" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}
}
