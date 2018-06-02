package controllers.employees;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;
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
	    
	    final String REQ_EMPLOYEES = "employees";
	    final String REQ_EMPLOYEES_COUNT = "employees_count";
	    final String REQ_PAGE = "page";
	    final String REQ_FLASH = "flush";
	    
	    
	    EntityManager em = DBUtil.createEntityManager();
	    
	    int page = 1;
	    try{
	        page = Integer.parseInt(request.getParameter(REQ_PAGE));
	    } catch (NumberFormatException e) {}
	    List<Employee> employees = em.createNamedQuery(PropertyUtils.QRY_GET_ALL_EMPLOYEES,Employee.class)
	                                 .setFirstResult(15 * (page - 1))
	                                 .setMaxResults(15)
	                                 .getResultList();
	    
	    long employees_count = (long)em.createNamedQuery(PropertyUtils.QRY_GET_EMPLOYEES_COUNT,Long.class)
	                                       .getSingleResult();
	    em.close();
	    
	    request.setAttribute(REQ_EMPLOYEES, employees);
	    request.setAttribute(REQ_EMPLOYEES_COUNT, employees_count);
	    request.setAttribute(REQ_PAGE, page);

	    if(request.getSession().getAttribute(REQ_FLASH) != null) {
	        request.setAttribute(REQ_FLASH, request.getSession().getAttribute(REQ_FLASH));
	        request.getSession().removeAttribute(REQ_FLASH);
	    }
	    
	    RequestDispatcher rd = request.getRequestDispatcher(PropertyUtils.FORWARD_EMPLOYEES_INDEX);
	    rd.forward(request, response);
	}

}
