package controllers.employees;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import execute.EmployeeExecute;

/**
 * Servlet implementation class EmployeesCreateServlet
 */
@WebServlet("/employees/create")
public class EmployeesCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    // 遷移先パス
	    StringBuilder forwardPath = new StringBuilder();
	    
	    // 新規登録処理でエラーが発生した場合
	    if (!EmployeeExecute.doCreate(request, response, this, forwardPath)) {
            
            // 遷移先を設定してフォワード
            RequestDispatcher rd = request.getRequestDispatcher(forwardPath.toString());
            rd.forward(request, response);
            
        }
	}
}
