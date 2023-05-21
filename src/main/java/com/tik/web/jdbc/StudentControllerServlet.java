package com.tik.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentUtil;

	private DbCon dbcon;

	// This method is called by Tomcat when the server is first loaded or
	// initialized
	@Override
	public void init() throws ServletException {
		try {
			studentUtil = new StudentDbUtil(dbcon.getConnection());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// read command parameter
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}

			// route to the appropiate method

			switch (theCommand) {
			case "LIST":
				listStudents(request, response);
				break;

			case "ADD":
				addStudent(request, response);
				break;

			case "LOAD":
				loadStudent(request, response);
				break;

			case "UPDATE":
				updateStudent(request, response);
				break;

			case "DELETE":
				deleteStudent(request, response);
				break;

			default:
				listStudents(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		// create a new student object
		Student theStudent = new Student(id, firstName, lastName, email);

		// perform update on database
		StudentDbUtil.updateStudent(theStudent);

		// send them back to list-students.jsp page
		listStudents(request, response);
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student id from form data
		String theStudentId = request.getParameter("studentId");

		// delete student from database
		StudentDbUtil.deleteStudent(theStudentId);

		// send them back to "list-students.jsp" page
		listStudents(request, response);
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student id from data
		String theStudentID = request.getParameter("studentId");

		// get student from database (Database Util)
		Student s3 = StudentDbUtil.getStudent(theStudentID);

		// Place student in the request attribute
		request.setAttribute("THE_STUDENT", s3);

		// send to JSP page: update-student-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student from form data from add-student-form
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		// create a new student object
		Student s2 = new Student(firstName, lastName, email);
		// add the student to the database
		StudentDbUtil.addStudent(s2);

		// send back to main page (the student list)
		listStudents(request, response);

	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get student from db util
		List<Student> students = studentUtil.getStudents();

		// add students to the request object
		request.setAttribute("students_list", students);

		// send to jsp page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);

	}

}
