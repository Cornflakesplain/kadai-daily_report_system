package controllers.reports;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import execute.ReportExecute;
import utils.PropertyUtils;

/**
 * Servlet implementation class ReportEditServlet
 */
@WebServlet("/reports/edit")
public class ReportsEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    ReportExecute.doEdit(request, response);
	    
	    RequestDispatcher rd = request.getRequestDispatcher(PropertyUtils.FORWARD_REPORTS_EDIT);
	    rd.forward(request, response);
	}

}
