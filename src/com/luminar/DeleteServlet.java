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

public class DeleteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/luminarservlet","root","mysql");
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<script>");
			pw.println("function redirect() {");
			pw.println("	window.location.href = 'index.html';");
			pw.println("}");
			pw.println("</script></head>");
			pw.println("<body><form action ='deleteServlet' method='get'>");
			pw.println("Enter the RollNo of the Student You Want to Delete?");
					pw.println("<input type='text' name='roll'/><br><br>");
			pw.println("<input type='submit' value='DELETE'/>&nbsp;&nbsp;");
			pw.println("<input type='button' value='CANCEL' onClick='redirect()'/>");
			pw.println("</form></body></html>");

			String sroll = request.getParameter("roll");
			if(sroll==null){
				pw.println("<br><br>Enter RollNo");
				return;
			}
			if(sroll.isEmpty()){
				pw.println("<br><br>Enter RollNo");
				return;	
			}

			int rollNo = Integer.parseInt(sroll);
			ps = con.prepareStatement("DELETE FROM student where stu_roll=?");
			ps.setInt(1, rollNo);
			int rs = ps.executeUpdate();
			if(rs>0)
				pw.println("<br><br>Record Deleted Successfully");
			else
				pw.println("<br><br>Record Not Found");


			
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
