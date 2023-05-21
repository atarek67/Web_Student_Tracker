package com.tik.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDbUtil {

	Connection con = null;
	PreparedStatement pst = null;
	static ResultSet rs = null;

	public StudentDbUtil(Connection con) {
		this.con = con;
	}

	public List<Student> getStudents() throws Exception {

		List<Student> students = new ArrayList<>();

		try {
			// get a connection
			con = DbCon.getConnection();

			// create sql statement
			String sql = "select * from student ORDER BY email DESC";

			pst = con.prepareStatement(sql);
			// execute query
			rs = pst.executeQuery();

			// process result set
			while (rs.next()) {

				// retrieve data from result set row
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");

				// create new student object
				Student s1 = new Student(id, firstName, lastName, email);

				// add it to the list of students
				students.add(s1);
			}

			return students;
		} finally {
			// close JDBC objects
			close_Conn(con, pst, rs);
		}
	}

	public static void addStudent(Student s) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		String sql = "";

		try {

			// get db connection
			conn = DbCon.getConnection();

			// create SQL for insert
			sql = "insert into student " + "(first_name , last_name , email)" + " values(? , ? , ?)";
			pst = conn.prepareStatement(sql);

			// set the param values for the student
			pst.setString(1, s.getFirstName());
			pst.setString(2, s.getLastName());
			pst.setString(3, s.getEmail());
			// execute sql insert
			pst.execute();

		} finally {
			// clean up JDBC objects
			close_Conn(conn, pst, rs);

		}

	}

	public static void deleteStudent(String theStudentId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// convert student id to int
			int studentId = Integer.parseInt(theStudentId);

			// get connection to database
			myConn = DbCon.getConnection();

			// create sql to delete student
			String sql = "delete from student where id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, studentId);

			// execute sql statement
			myStmt.execute();
		} finally {
			// clean up JDBC code
			close_Conn(myConn, myStmt, null);
		}
	}

	public static void close_Conn(Connection myConn, PreparedStatement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close(); // doesn't really close it ... just puts back in connection pool
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public static Student getStudent(String theStudentID) throws Exception {

		Student theStudent = null;

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "";
		int studentId;

		try {
			// convert student id into int
			studentId = Integer.parseInt(theStudentID);

			// get connection to database
			conn = DbCon.getConnection();

			// create sql to get selected student
			sql = "select * from student where id = ?";

			// create prepared statement
			pst = conn.prepareStatement(sql);

			// set params
			pst.setInt(1, studentId);

			// execute params
			rs = pst.executeQuery();

			// reterive data from result set
			while (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");

				// use the studentId during construction
				theStudent = new Student(studentId, firstName, lastName, email);
			}
//			else {
//				throw new Exception("Couldn't find studnet id: " + studentId);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theStudent;

	}

	public static void updateStudent(Student theStudent) throws Exception {

		Connection con = null;
		PreparedStatement pst = null;
		String sql = "";

		try {
			// get db connection
			con = DbCon.getConnection();

			// create SQL update statement
			sql = "update student " + "set first_name=?, last_name=?, email=? " + "where id=?";

			// prepare statement
			pst = con.prepareStatement(sql);

			// set params
			pst.setString(1, theStudent.getFirstName());
			pst.setString(2, theStudent.getLastName());
			pst.setString(3, theStudent.getEmail());
			pst.setInt(4, theStudent.getId());

			// execute SQL statement
			pst.execute();
		} finally {
			// clean up JDBC objects
			// close_Conn(myConn, myStmt, null);
			System.out.println("Finally update in DAO");
		}

	}
}
