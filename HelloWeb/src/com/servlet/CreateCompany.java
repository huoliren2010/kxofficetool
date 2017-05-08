package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

import util.Response;

public class CreateCompany extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �½��������
				Service serv = new Service();

				// ������Ϣ
				String companyname = request.getParameter("companyname");
				companyname = new String(companyname.getBytes("ISO-8859-1"), "UTF-8");
				String password = request.getParameter("password");
				String phonenumber = request.getParameter("phonenumber");
				String confirm;
				// String password = request.getParameter("r_password");
				System.out.println("company("+companyname+","+password+","+phonenumber+") registing");
				// ��֤����
				String strUserInfo = serv.register(username, password, phonenumber);
				System.out.println("test" + strUserInfo);
				int status = Response.ERROR_CODE;
				if (strUserInfo != null) {
					System.out.print("Succss regist");
					confirm = "ע��ɹ�";
					request.getSession().setAttribute("username", username);
					status = Response.SUCCESS_CODE;
					
				} else {
					System.out.print("Failed");
					confirm = "ע��ʧ�ܣ��˴�ע����ë��";
				}

				// ������Ϣ
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				new Response(status, strUserInfo, confirm).toString();
				String resp = new Response(status, strUserInfo, confirm).toString();
				System.out.println("regist resp="+resp);
				out.print(resp);
				// out.print("�û�����" + username);
				// out.print("���룺" + password);
				// out.print(confirm);
				out.flush();
				out.close();
	}

}
