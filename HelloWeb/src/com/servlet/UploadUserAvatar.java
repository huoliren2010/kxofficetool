package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.service.Service;

import util.ManipulateImage;
import util.Response;

public class UploadUserAvatar extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 接收信息
		int userId = Integer.valueOf(request.getParameter("uid"));
		String imgEncodedStr = request.getParameter("image");
		String fileName = request.getParameter("filename");

		boolean result = false;
		if (imgEncodedStr != null) {
			result = ManipulateImage.convertStringtoImage(imgEncodedStr, fileName);
			System.out.println("Inside if");
			System.out.print("Image upload complete, Please check your directory");
		} else {
			System.out.println("Inside else");
			System.out.print("Image is empty");
		}

		// 新建服务对象
		Service serv = new Service();
		serv.updateUserAvatar(userId, fileName);

		// result
		JSONObject jsonResult = new JSONObject();
		if (result) {
			jsonResult.put(Response.STATUS, Response.SUCCESS_CODE);
			jsonResult.put(Response.MESSAGE, "saved in " + "E:/xx/officeTool/UploadedImages/" + fileName);
			System.out.print("Succss");
		} else {
			System.out.print("Failed");
		}
		// 返回信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// int status = loged ? 200 : 400;
		// String resp = String.format("{\"status\":%d,\"message\":%s}", status,
		// confirm);
		String resp = jsonResult.toString();
		out.print(resp);
		out.flush();
		out.close();
	}

}
