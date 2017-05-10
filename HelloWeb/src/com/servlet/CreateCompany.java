package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

import info.Company;
import util.Response;

public class CreateCompany extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 新建服务对象
				Service serv = new Service();

				// 接收信息
				String companyname = request.getParameter("companyname");
				companyname = new String(companyname.getBytes("ISO-8859-1"), "UTF-8");
				int uid = Integer.valueOf(request.getParameter("uid"));
				String confirm;
				// String password = request.getParameter("r_password");
				System.out.println("company("+companyname+","+uid+") creating");
				// 验证处理
				Company company = serv.createCompany(companyname, uid);
				System.out.println("test" + company);
				int status = Response.ERROR_CODE;
				if (company != null) {
					System.out.print("Succss CreateCompany");
					confirm = "创建公司成功";
					status = Response.SUCCESS_CODE;
					
				} else {
					System.out.print("Failed CreateCompany");
					confirm = "创建公司失败！";
				}

				// 返回信息
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				String resp = new Response(status, company.toJSONObject(), confirm).toString();
				System.out.println("CreateCompany resp="+resp);
				out.print(resp);
				// out.print("用户名：" + username);
				// out.print("密码：" + password);
				// out.print(confirm);
				out.flush();
				out.close();
	}

}
