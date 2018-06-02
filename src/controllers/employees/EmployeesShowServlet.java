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
 * Servlet implementation class EmployeesShowServlet
 */
@WebServlet("/employees/show")
public class EmployeesShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    EmployeeExecute.doShow(request, response);
	    
	    RequestDispatcher rd = request.getRequestDispatcher(PropertyUtils.FORWARD_EMPLOYEES_SHOW);
	    rd.forward(request, response);
	}
}
