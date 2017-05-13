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

public class QueryCompany extends HttpServlet {
	private static String TAG = QueryCompany.class.getSimpleName();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 新建服务对象
		Service serv = new Service();

		// 接收信息
		int departmentid = -1;
		try {
			departmentid = Integer.valueOf(request.getParameter("departmentid"));
		} catch (NumberFormatException e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int companyid = -1;
		try {
			companyid = Integer.valueOf(request.getParameter("companyid"));
		} catch (NumberFormatException e) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CompanyInfo companyInfo = null;
		if(companyid != -1){
			companyInfo = serv.queryCompanyInfoFromCompanyid(companyid);
		}
		String message;
		System.out.println("QueryCompany(" + departmentid +") creating");
		// 验证处理
		if(companyInfo == null && departmentid != -1)
			companyInfo = serv.queryCompanyInfoFromDepartmentid(departmentid);
		int status = Response.ERROR_CODE;
		if (companyInfo != null) {
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
		
		String resp = new Response(status, companyInfo.toJSONObject(), message).toString();
		System.out.println(TAG+" resp=" + resp);
		out.print(resp);
		// out.print("用户名：" + username);
		// out.print("密码：" + password);
		// out.print(confirm);
		out.flush();
		out.close();
	}

}
