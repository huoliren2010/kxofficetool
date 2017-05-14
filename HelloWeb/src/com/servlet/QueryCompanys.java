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
import info.UserInfo;
import util.Response;
import util.Utils;

public class QueryCompanys extends HttpServlet {
	private static String TAG = QueryCompanys.class.getSimpleName();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// �½��������
		Service serv = new Service();

		// ������Ϣ
	
		String companyname = request.getParameter("name");
		
		List<CompanyInfo> companyInfo = serv.queryCompanyInfoFromCompanyName(companyname);
		String message;
		System.out.println("QueryCompanys(" + companyname +") creating");
		// ��֤����
		int status = Response.ERROR_CODE;
		if (companyInfo != null) {
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
		
		String resp = new Response(status, Utils.transFromCompanyInfos(companyInfo), message).toString();
		System.out.println(TAG+" resp=" + resp);
		out.print(resp);
		// out.print("�û�����" + username);
		// out.print("���룺" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}

}
