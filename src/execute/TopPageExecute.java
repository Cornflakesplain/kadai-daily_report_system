package execute;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;
import utils.PropertyUtils;
import utils.ServletUtils;

public class TopPageExecute {

    private static final String REQ_LOGIN_EMPLOYEE = "login_employee";
    private static final String REQ_EMPLOYEE = "employee";    
    private static final String REQ_REPORTS = "reports";
    private static final String REQ_REPORTS_COUNT = "reports_count";
    private static final String REQ_PAGE = "page";
    
    /**
     * 一覧画面
     * @param request
     * @param response
     */
    public static void doIndex(HttpServletRequest request, HttpServletResponse response) {

        // DB接続オブジェクトを生成
        EntityManager em = DBUtil.createEntityManager();
        
        // ログイン者情報を取得
        Employee login_employee = (Employee)request.getSession().getAttribute(REQ_LOGIN_EMPLOYEE);

        // ページの初期化
        int page = 1;
        try{
            // 現在のページを取得
            page = Integer.parseInt(request.getParameter(REQ_PAGE));
        } catch(Exception e) {}
        
        // 自身の日報を全て取得
        List<Report> reports = em.createNamedQuery(PropertyUtils.QRY_GET_MY_ALL_REPORTS, Report.class)
                                  .setParameter(REQ_EMPLOYEE, login_employee)
                                  .setFirstResult(15 * (page - 1))
                                  .setMaxResults(15)
                                  .getResultList();

        // 自身の日報の総数を取得
        long reports_count = (long)em.createNamedQuery(PropertyUtils.QRY_GET_MY_REPORTS_COUNT, Long.class)
                                     .setParameter(REQ_EMPLOYEE, login_employee)
                                     .getSingleResult();

        // DB接続をクローズ
        em.close();

        request.setAttribute(REQ_REPORTS, reports);
        request.setAttribute(REQ_REPORTS_COUNT, reports_count);
        request.setAttribute(REQ_PAGE, page);

        ServletUtils.existsThenSetFlush(request);
    }
}
