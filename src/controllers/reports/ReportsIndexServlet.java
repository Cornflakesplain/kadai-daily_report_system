package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;
import utils.PropertyUtils;
import utils.ServletUtils;

/**
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet("/reports/index")
public class ReportsIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//	    ReportExecute.doIndex(request, response);
	    
        // 現在のページを取得
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }
        
        // 全体のページ数を取得
        EntityManager em = DBUtil.createEntityManager();
        List<Report> reports = em.createNamedQuery(PropertyUtils.QRY_GET_ALL_REPORTS, Report.class)
                .getResultList();
        
        response.getWriter().append(Integer.valueOf(reports.size()).toString());


//        List<Report> reports = em.createNamedQuery(PropertyUtils.QRY_GET_ALL_REPORTS, Report.class)
//                                    .setFirstResult(15 * (page - 1))
//                                    .setMaxResults(15)
//                                    .getResultList();
//        // 日誌の総数を取得
//        long reports_count = (long)em.createNamedQuery(PropertyUtils.QRY_GET_REPORTS_COUNT,Long.class)
//                                    .getSingleResult();
        
        em.close();
        
        request.setAttribute("reports", reports);
//        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        
        // Flushメッセージが存在する場合は取得
        ServletUtils.existsThenSetFlush(request);

	    RequestDispatcher rd = request.getRequestDispatcher(PropertyUtils.FORWARD_REPORTS_INDEX);
	    rd.forward(request, response);
	    
	}

}
