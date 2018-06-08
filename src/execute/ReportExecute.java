package execute;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;
import utils.PropertyUtils;
import utils.ServletUtils;

public class ReportExecute {

    private static final String REQ__TOKEN = "_token";
    private static final String REQ_REPORT = "report";
    private static final String REQ_REPORTS = "reports";
    private static final String REQ_REPORT_ID = "report_id";
    private static final String REQ_REPORTS_COUNT = "reports_count";
    private static final String REQ_PAGE = "page";
    private static final String REQ_FLUSH = "flush";
    private static final String REQ_ERRORS = "errors";
    private static final String REQ_ID = "id";

    
    /**
     * 日報一覧
     * @param request
     * @param response
     */
    public static void doIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 現在のページを取得
        int page;
        try {
            page = Integer.parseInt(request.getParameter(REQ_PAGE));
        } catch(Exception e) {
            page = 1;
        }
        
        // 全体のページ数を取得
        EntityManager em = DBUtil.createEntityManager();


        List<Report> reports = em.createNamedQuery(PropertyUtils.QRY_GET_ALL_REPORTS, Report.class)
                                    .setFirstResult(15 * (page - 1))
                                    .setMaxResults(15)
                                    .getResultList();
        // 日誌の総数を取得
        long reports_count = (long)em.createNamedQuery(PropertyUtils.QRY_GET_REPORTS_COUNT,Long.class)
                                    .getSingleResult();
        
        em.close();
        
        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        
        // Flushメッセージが存在する場合は取得
        ServletUtils.existsThenSetFlush(request);
        
    }
    
    /**
     * 日報新規
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void doNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setAttribute(REQ__TOKEN, request.getSession().getId());
        
        Report r = new Report();
        r.setReport_date(new Date(System.currentTimeMillis()));
        request.setAttribute(REQ_REPORT, r);
    
    }
}
