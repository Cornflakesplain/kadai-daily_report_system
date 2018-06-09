package execute;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import models.validators.ReportValidator;
import utils.DBUtil;
import utils.PropertyUtils;
import utils.ServletUtils;

public class ReportExecute {

    private static final String REQ__TOKEN = "_token";
    private static final String REQ_REPORT = "report";
    private static final String REQ_REPORT_DATE = "report_date";
    private static final String REQ_REPORTS = "reports";
    private static final String REQ_REPORTS_COUNT = "reports_count";
    private static final String REQ_LOGIN_EMPLOYEE = "login_employee";
    private static final String REQ_TITLE = "title";
    private static final String REQ_CONTENT = "content";
    
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
        
        request.setAttribute(REQ_REPORTS, reports);
        request.setAttribute(REQ_REPORTS_COUNT, reports_count);
        request.setAttribute(REQ_PAGE, page);
        
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
    
    public static boolean doCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(ServletUtils.isFairSession(request)) {
            
            EntityManager em = DBUtil.createEntityManager();
            System.out.println(request.getParameter(REQ_REPORT_DATE));

            Report r = new Report();

            r.setEditedItems(request,true);

            List<String> errors = ReportValidator.validate(r);
            if(errors.size() > 0) {
                em.close();

                setInputError(request, r, errors);
                return false;

            } else {
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute(REQ_FLUSH, "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/reports/index");
                return true;
            }
        }
        return false;

    }
    
    public static void doShow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        request.setAttribute(REQ_REPORT, r);
        request.setAttribute(REQ__TOKEN, request.getSession().getId());

    }
    
    /**
    * 入力エラー情報設定
    * @param request
    * @param response
    * @param forwordUrl
    */    
    public static void setInputError(
              HttpServletRequest request
            , Report r
            , List<String> errors)
    {
        request.setAttribute(REQ__TOKEN, request.getSession().getId());
        request.setAttribute(REQ_REPORT, r);
        request.setAttribute(REQ_ERRORS, errors);
    }
    
    
}
