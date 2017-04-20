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

public class LogLet extends HttpServlet {

	private static final long serialVersionUID = 369840050351775312L;

	/**
	 * The doGet method of the Server let.
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ������Ϣ
		String username = request.getParameter("username");
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String password = request.getParameter("password");
		String confirm;
		System.out.println(username + "--" + password);

		// �½��������
		Service serv = new Service();

		// ��֤����
		boolean loged = serv.login(username, password);
		if (loged) {
			System.out.print("Succss");
			confirm = "��½�ɹ�";
			request.getSession().setAttribute("username", username);
			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("Failed");
			confirm = "�˺Ż����벻��ȷ";
		}
		 // ������Ϣ
		 response.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 int status = loged ? 200 : 400;
			String resp = String.format("{\"status\":%d,\"message\":%s}", status, confirm);
		 out.print(resp);
//		 out.print("�û�����" + username);
//		 out.print("���룺" + password);
//		 out.print(confirm);
		 out.flush();
		 out.close();

	}

	/**
	 * The doPost method of the Server let.
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ����Ϊ���ֻ����������룬��������
		// System.out.println("u1--"+username);
		// System.out.println("u2--"+username);

	}

}
