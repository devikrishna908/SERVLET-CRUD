package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con = null;
	PreparedStatement ps = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/luminarservlet", "root", "mysql");
			
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<script>");
				pw.println("function redirect() {");
				pw.println("	window.location.href = 'index.html';");
				pw.println("}");
			pw.println("</script>");
			pw.println("</head>");
			pw.println("<body><form action ='updateServlet' method='get'>");
			pw.println("Enter the RollNo of the Student You Want to Update?");
			pw.println("<input type='text' name='roll'/><br><br>");
			pw.println("New Name: <input type='text' name='newName'/><br><br>");
			pw.println("New Mark: <input type='text' name='newMark'/><br><br>");
			pw.println("<input type='submit' value='UPDATE DETAILS'/>&nbsp;&nbsp;");
			pw.println("<input type='button' value='CANCEL' onClick='redirect()'/>");
			pw.println("</form></body></html>");
			
			String srollNo = request.getParameter("roll");
			String name = request.getParameter("newName");
			String smark = request.getParameter("newMark");

			if(srollNo==null||smark==null||name==null){
				pw.println("<br><br>All fields are mandatory");
				return;
			}

			if(srollNo.isEmpty()||smark.isEmpty()||name.isEmpty()){
				pw.println("<br><br>All fields are mandatory");
				return;
			}
			
			
			int rollNo = Integer.parseInt(srollNo);
			float mark = Float.parseFloat(smark);
			ps = con.prepareStatement("UPDATE student SET stu_name=?,stu_mark=? where stu_roll=?");
			ps.setString(1, name);
			ps.setFloat(2, mark);
			ps.setInt(3, rollNo);
			
			int rows = ps.executeUpdate();
			
			if(rows>0)
				pw.println("<br><br>Update Sucessful");
			else
				pw.println("<br><br>Updation Failed");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException ex) {
			ex.printStackTrace();
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
