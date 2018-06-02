package execute;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import models.Employee;
import models.validators.EmployeeValidator;
import utils.DBUtil;
import utils.PropertyUtils;
import utils.ServletUtils;


/**
 * 日報管理システム_リクエスト情報設定処理
 * @author J.Tamura
 *
 */

public class EmployeeExecute {

    private static final String REQ__TOKEN = "_token";
    private static final String REQ_EMPLOYEE = "employee";
    private static final String REQ_EMPLOYEES = "employees";
    private static final String REQ_EMPLOYEE_ID = "employee_id";
    private static final String REQ_EMPLOYEES_COUNT = "employees_count";
    private static final String REQ_PAGE = "page";
    private static final String REQ_FLUSH = "flush";
    private static final String REQ_ERRORS = "errors";
    private static final String REQ_ID = "id";
    
    /**
     * 一覧画面
     * @param request
     * @param response
     */
    public static void doIndex(HttpServletRequest request, HttpServletResponse response) {
        
        EntityManager em = DBUtil.createEntityManager();

        // ページネーションの初期化
        int page = 1;
        try{
            // 現在のページを取得
            page = Integer.parseInt(request.getParameter(REQ_PAGE));
        } catch (NumberFormatException e) {}
        
        // 全データを取得
        List<Employee> employees = em.createNamedQuery(PropertyUtils.QRY_GET_ALL_EMPLOYEES,Employee.class)
                                     .setFirstResult(15 * (page - 1))
                                     .setMaxResults(15)
                                     .getResultList();
        // データ数を取得        
        long employees_count = (long)em.createNamedQuery(PropertyUtils.QRY_GET_EMPLOYEES_COUNT,Long.class)
                                           .getSingleResult();
        em.close();
        
        request.setAttribute(REQ_EMPLOYEES, employees);
        request.setAttribute(REQ_EMPLOYEES_COUNT, employees_count);
        request.setAttribute(REQ_PAGE, page);

        // Flush メッセージが存在する場合は設定
        ServletUtils.existsThenSetFlush(request);
        
    }

    /**
     * 新規従業員の登録リンク押下時処理
     * @param request
     * @param response
     */
    public static void doNew(HttpServletRequest request, HttpServletResponse response) {
        
        // セッションID を設定
        request.setAttribute(REQ__TOKEN, request.getSession().getId());
        
        // 空白のEnployeeクラスを設定
        request.setAttribute(REQ_EMPLOYEE, new Employee());
    
    }
    
    /**
     * 新規従業員登録画面_投稿ボタン押下時処理
     * @param request
     * @param response
     * @return エラーフラグ
     */
    public static Boolean doCreate(
              HttpServletRequest request
            , HttpServletResponse response
            , HttpServlet servlet
            , StringBuilder forwardPath
    ) throws ServletException, IOException {
        
        // セッションIDが正しい場合
        if(ServletUtils.isFairSession(request)) {
    
            // 社員情報クラス生成
            Employee e = new Employee();
            
            // 新規登録に必要な情報を取得
            e.setEditedItems(request, servlet, true);
            
            // 入力チェック
            List<String> errors = EmployeeValidator.validate(e, true, true);
            
            // 入力エラーが存在する場合
            if (errors.size() != 0) {
              
                // エラー情報を設定
                setInputError(request, e, errors);
            
                // 新規登録画面に遷移
                forwardPath.append(PropertyUtils.FORWARD_EMPLOYEES_NEW);
                return false;
            }
            
            // DB接続オブジェクトを生成
            EntityManager em = DBUtil.createEntityManager();
   
            // 取得した情報を基にデータベース更新
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction();
            em.close();
            
            // Flashメッセージの設定
            request.getSession().setAttribute(REQ_FLUSH, "登録が完了しました。");
            
            // 指定のパスに遷移
            response.sendRedirect(request.getContextPath() + "/employees/index");
        
            return true;
        }
        // セッションエラー画面に遷移
        forwardPath.append(PropertyUtils.FORWARD_SESSION_ERROR);
        return false;
    }
    
    /**
    * 詳細を表示リンク押下時処理
    * @param request
    * @param response
    */   
    public static void doShow(HttpServletRequest request, HttpServletResponse response) {
        
        EntityManager em  = DBUtil.createEntityManager();
        
        // 対象の従業員データを取得
        Employee e = getToReadEmployee(em, request);

        em.close();
        
        request.setAttribute(REQ_EMPLOYEE, e);
        request.setAttribute("_token", request.getSession().getId());
        
    }

    /**
    * この従業員情報を編集するリンク押下時処理
    * @param request
    * @param response
    */   
    public static void doEdit(HttpServletRequest request, HttpServletResponse response) {
        
        EntityManager em = DBUtil.createEntityManager();

        // 対象の従業員データを取得
        Employee e = getToReadEmployee(em, request);

        em.close();

        request.setAttribute(REQ_EMPLOYEE, e);
        request.setAttribute(REQ__TOKEN, request.getSession().getId());
        request.getSession().setAttribute(REQ_EMPLOYEE_ID, e.getId());
        
    }
    
    /**
    * 詳細従業員編集画面_投稿ボタン押下時処理
    * @param request
    * @param response
    * @return エラーフラグ
    */
    public static Boolean doUpdate(
             HttpServletRequest request
           , HttpServletResponse response
           , HttpServlet servlet
           , StringBuilder forwardPath
    ) throws ServletException, IOException {

        if (ServletUtils.isFairSession(request)) {

            EntityManager em = DBUtil.createEntityManager();

            // 編集対象の従業員データを取得
            Employee e = getToWriteEmployee(em, request);
            
            // コードの変更時のみ重複チェック
            Boolean code_duplicate_check = !e.getCode().equals(request.getParameter("code"));

            // パスワードの入力時のみ入力チェック
            Boolean password_check_flag = StringUtils.isNotEmpty(request.getParameter("password"));

            // 入力値から編集項目を取得
            e.setEditedItems(request, servlet, false);

            // 入力チェック
            List<String> errors = EmployeeValidator.validate(e, code_duplicate_check, password_check_flag);

            // 入力チェックにエラーが存在する場合
            if(errors.size() > 0) {

                // DB接続をクローズ
                em.close();

                // エラー内容をセッションに設定
                setInputError(request, e, errors);

                // 遷移先を取得_入力エラー
                forwardPath.append(PropertyUtils.FORWARD_EMPLOYEES_EDIT);

                return false;

            } else {

                // DB更新
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                request.getSession().setAttribute(REQ_FLUSH, "更新が完了しました。");

                request.getSession().removeAttribute(REQ_EMPLOYEE_ID);

                response.sendRedirect(request.getContextPath() + "/employees/index");

                return true;
            }

        }
        // 遷移先を取得_セッションエラー
        forwardPath.append(PropertyUtils.FORWARD_SESSION_ERROR);
        return false;
   }

    /**
    * 新規従業員編集画面_削除ボタン押下時処理
    * @param request
    * @param response
    * @return エラーフラグ
    */
    public static Boolean doDestroy(
             HttpServletRequest request
           , HttpServletResponse response
           , StringBuilder forwardPath
    ) throws ServletException, IOException {

        // セッションIDが正しい場合
        if (ServletUtils.isFairSession(request)) {
            
            EntityManager em = DBUtil.createEntityManager();

            // 対象の従業員データを取得
            Employee e = getToWriteEmployee(em, request);

            e.setDelete_flag(1);
            e.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute(REQ_FLUSH, "削除が完了しました。");

            response.sendRedirect(request.getContextPath() + "/employees/index");
        
            return true;
        }
        
        // 遷移先を取得_セッションエラー
        forwardPath.append(PropertyUtils.FORWARD_SESSION_ERROR);
        return false;
   }
    
    /**
    * 入力エラー情報設定
    * @param request
    * @param response
    * @param forwordUrl
    */    
    public static void setInputError(
              HttpServletRequest request
            , Employee e
            , List<String> errors)
    {
        request.setAttribute(REQ__TOKEN, request.getSession().getId());
        request.setAttribute(REQ_EMPLOYEE, e);
        request.setAttribute(REQ_ERRORS, errors);
    }

    /**
    * 対象従業員データ取得_読取処理
    * @param em
    * @param request
    */    
    public static Employee getToReadEmployee(EntityManager em, HttpServletRequest request) {
        
        // 画面の出力値から従業員データを取得
        return em.find(Employee.class,Integer.parseInt(request.getParameter(REQ_ID)));
        
    }
    
    /**
    * 対象従業員データ取得_更新処理
    * @param em
    * @param request
    */    
    public static Employee getToWriteEmployee(EntityManager em, HttpServletRequest request) {
        
        // 画面の入力力従業員データを取得
        return em.find(Employee.class,(Integer)(request.getSession().getAttribute(REQ_EMPLOYEE_ID)));
        
    }
}
