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
import models.validators.EmployeeValidator;
import utils.DBUtil;
import utils.PropertyUtils;
import utils.ServletUtils;

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

	    if(ServletUtils.isTrueSession(request)) {
	
	        Employee e = new Employee();
	        // 追加に必要な情報を取得
	        e.setEditedItems(request, this, false);
	        
	        // DB接続オブジェクトを生成
	        EntityManager em = DBUtil.createEntityManager();
	        
	        // 入力チェック
	        List<String> errors = EmployeeValidator.validate(e, true, true);
	        
	        // 入力エラーが存在する場合
	        if(errors.size() > 0) {
	            em.close();
	            ServletUtils.setInputError(request,response,e,errors);
	            
	            RequestDispatcher rd = request.getRequestDispatcher(PropertyUtils.FORWARD_EMPLOYEES_NEW);
	            rd.forward(request, response);

	        } else
	            em.getTransaction().begin();
	            em.persist(e);
	            em.getTransaction();
	            em.close();
	            request.getSession().setAttribute("flush", "登録が完了しました。");
	            
	            response.sendRedirect(request.getContextPath() + "/employees/index");
	            
	    }
	}
}
