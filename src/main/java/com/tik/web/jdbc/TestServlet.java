package com.tik.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mysql.jdbc.Statement;


@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Define dataSource/connection pool for Resource injection


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// Step 1:  Set up the Print writer
				PrintWriter out = response.getWriter();
				response.setContentType("text/plain");
				
				// Step 2:  Get a connection to the database
				Connection myConn = null;
				PreparedStatement myStmt = null;
				ResultSet myRs = null;
				
				try {
					myConn = DbCon.getConnection();
					
					// Step 3:  Create a SQL statements
					String sql = "select * from student";
					myStmt = myConn.prepareStatement(sql);
					
					// Step 4:  Execute SQL query
					myRs = myStmt.executeQuery(sql);
					
					// Step 5:  Process the result set
					while (myRs.next()) {
						String email = myRs.getString("email");
						out.println(email);
					}
				}
				catch (Exception exc) {
					exc.printStackTrace();
				}
			}
	
	}




