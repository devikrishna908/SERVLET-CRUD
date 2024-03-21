package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	Connection con=null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		String name = request.getParameter("name");
		float mark = Float.parseFloat(request.getParameter("mark"));
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/luminarservlet","root","mysql");
			ps = con.prepareStatement("INSERT INTO student (stu_name,stu_mark) VALUES (?,?)");
			ps.setString(1, name);
			ps.setFloat(2, mark);
			int no = ps.executeUpdate();
			
			if(no>0)
				pw.println("<br><br>Insertion Successful..!");
			else
				pw.println("<br><br>Insertion Failed..!");
				
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		finally {
			if(con!=null)
				try {
					con.close();
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	//	response.sendRedirect("index.html");
		
	}

}
