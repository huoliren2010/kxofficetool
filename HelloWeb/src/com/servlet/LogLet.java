package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.service.Service;

import util.Response;

public class LogLet extends HttpServlet {

	private static final long serialVersionUID = 369840050351775312L;

	/**
	 * The doGet method of the Server let.
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// ������Ϣ
		String username = request.getParameter("username");
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String password = request.getParameter("password");
		String confirm;
		System.out.println(username + "--" + password);

		// �½��������
		Service serv = new Service();

		// ��֤����
		String strUserInfo = serv.login(username, password);
		int status = Response.ERROR_CODE;
		if (strUserInfo != null) {
			System.out.print("Succss");
			confirm = "��½�ɹ�";
			request.getSession().setAttribute("username", username);
			status = Response.SUCCESS_CODE;
		} else {
			System.out.print("Failed");
			confirm = "�˺Ż����벻��ȷ";
		}
		// ������Ϣ
		response.setCharacterEncoding("UsTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String resp = new Response(status, strUserInfo, confirm).toString();
		System.out.println("login resp="+resp);
		out.print(resp);
		out.flush();
		out.close();

	}

	/**
	 * The doPost method of the Server let.
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// ����Ϊ���ֻ����������룬��������
		// System.out.println("u1--"+username);
		// System.out.println("u2--"+username);

	}

}
