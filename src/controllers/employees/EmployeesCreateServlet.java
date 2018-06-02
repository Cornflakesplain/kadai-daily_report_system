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

	    final String REQ_FLUSH = "flush";
	    
	    if(ServletUtils.isFairSession(request)) {
	
	        Employee e = new Employee();
	        
	        // 追加に必要な情報を取得
	        e.setEditedItems(request, this, false);
	        
	        // DB接続オブジェクトを生成
	        EntityManager em = DBUtil.createEntityManager();
	        
	        // 入力チェック
	        List<String> errors = EmployeeValidator.validate(e, true, true);
	        
	        // 入力エラーが存在する場合
	        if(errors.size() > 0) {
	            
	            // コネクションクローズ
	            em.close();
	            
	            // エラー情報を設定
	            ServletUtils.setInputError(request,response,e,errors);
	            
	            // 遷移先を設定してフォワード
	            RequestDispatcher rd = request.getRequestDispatcher(PropertyUtils.FORWARD_EMPLOYEES_NEW);
	            rd.forward(request, response);

	        } else
	            
	            // 取得した情報を基にデータベース更新
	            em.getTransaction().begin();
	            em.persist(e);
	            em.getTransaction();
	            em.close();
	            
	            // Flashメッセージの設定
	            request.getSession().setAttribute(REQ_FLUSH, "登録が完了しました。");
	            
	            // 指定のパスに遷移
	            response.sendRedirect(request.getContextPath() + "/employees/index");
	            
	    }
	}
}
