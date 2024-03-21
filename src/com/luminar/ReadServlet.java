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

public class ReadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	Connection con =null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/luminarservlet","root","mysql");
			ps = con.prepareStatement("SELECT * FROM student");
			rs = ps.executeQuery();
				
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<script>");
			pw.println("function redirect() {");
			pw.println("	window.location.href = 'index.html';");
			pw.println("}");
			pw.println("</script></head>");
			pw.println("<body><h1>STUDENT DETAILS<h1><br><form name= 'read' action='readServlet' method='get'>");
			pw.println("<table border='1px'><tr><th>ROLL NO</th><th>NAME</th><th>MARK</th></tr>");
				
			while(rs.next()){
				pw.println("<tr><td>"+rs.getInt(1)+"</td>");
				pw.println("<td>"+rs.getString(2)+"</td>");
				pw.println("<td>"+rs.getInt(3)+"</td></tr>");
			}
				
			pw.println("</table><br><br><input type='button' value='BACK' onClick='redirect()'/>");
			pw.println("</form></body></html>");
				
		} catch (ClassNotFoundException e) {
				e.printStackTrace();
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

			
	}
		
}

