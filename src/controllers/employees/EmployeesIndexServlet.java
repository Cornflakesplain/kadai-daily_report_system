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
 * Servlet implementation class EmployeesIndexServlet
 */
@WebServlet("/employees/index")
public class EmployeesIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()s
     */
    public EmployeesIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    EmployeeExecute.doIndex(request,response);
	    
	    RequestDispatcher rd = request.getRequestDispatcher(PropertyUtils.FORWARD_EMPLOYEES_INDEX);
	    rd.forward(request, response);
	}

}
