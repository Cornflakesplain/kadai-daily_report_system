package controllers.employees;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import execute.EmployeeExecute;
import utils.PropertyUtils;

/**
 * Servlet implementation class EmployeesNewServlet
 */
@WebServlet("/employees/new")
public class EmployeesNewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    EmployeeExecute.doNew(request, response);
	    
	    RequestDispatcher rd = request.getRequestDispatcher(PropertyUtils.FORWARD_EMPLOYEES_NEW);
	    rd.forward(request, response);
	}

}
