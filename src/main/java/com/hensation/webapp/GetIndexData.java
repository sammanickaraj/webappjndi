package com.hensation.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class GetIndexData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String firstname;
	String lastname;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<html><body>");
		out.println("Hi "+request.getParameter("fname")+" "+request.getParameter("lname")+", how are you?");
		out.println("</body></html>");
		firstname=request.getParameter("fname");
		lastname=request.getParameter("lname");
		System.out.println(firstname);
		System.out.println(lastname);
		Context ctx = null;
		Connection con = null;
		Statement stmt = null;
		try{
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyLocalDB");
			System.out.println("Trying to get schema information "+ds.getConnection().getMetaData().getUserName());
			con = ds.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO student_tbl (first_name,last_name) "+"VALUES( '"+firstname+"', '"+lastname+"');");
			out.println("<br/>Added "+firstname+" to database");
		}catch(NamingException e){
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				con.close();
				ctx.close();
			} catch (SQLException e) {
				System.out.println("Exception in closing DB resources");
			} catch (NamingException e) {
				System.out.println("Exception in closing Context");
			}
	}
	}
}
