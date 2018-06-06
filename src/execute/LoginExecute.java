package execute;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import models.Employee;
import utils.DBUtil;
import utils.EncryptUtil;
import utils.PropertyUtils;
import utils.ServletUtils;

public class LoginExecute {

    private static final String REQ__TOKEN = "_token";
    private static final String REQ_HAS_ERROR = "hasError";
    private static final String REQ_LOGIN_EMPLOYEE = "login_employee";
    private static final String REQ_FLUSH = "flush"; 
    
    private static final String REQ_CODE = "code";
    private static final String REQ_PASSWORD = "password";
    


    
    /**
     * ログイン画面
     * @param request
     * @param response
     */
    public static void doLogin(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        
        request.setAttribute(REQ__TOKEN, request.getSession().getId());
        request.setAttribute(REQ_HAS_ERROR, false);

        // フラッシュメッセージが存在する場合に設定
        ServletUtils.existsThenSetFlush(request);
    }
    
    public static boolean doLogin(HttpServletRequest request, HttpServletResponse response, HttpServlet servlet)  throws ServletException, IOException {
        
        String code = request.getParameter(REQ_CODE);
        String plain_pass = request.getParameter(REQ_PASSWORD);

        Employee e = null;

        // コード／パスワードどちらも入力値が空白でない場合
        if(StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(plain_pass)) {

            // パスワードを復号化した値を取得
            String password = EncryptUtil.getPasswordEncrypt(plain_pass, servlet);

            EntityManager em = DBUtil.createEntityManager();

            try {
                // 社員番号とパスワードの整合性チェック
                e = em.createNamedQuery(PropertyUtils.QRY_CHECK_LOGIN_CODE_AND_PASSWORD, Employee.class)
                      .setParameter(PropertyUtils.ARGS_CODE, code)
                      .setParameter(PropertyUtils.ARGS_PASS, password)
                      .getSingleResult();
            } catch(NoResultException ex) {}

            em.close();

            // 指定の社員番号とパスワードに紐付くデータが存在する場合、チェックOK
            Boolean check_result = (e != null);

            
            if(!check_result) {
                request.setAttribute(REQ__TOKEN, request.getSession().getId());
                request.getSession().setAttribute(REQ_FLUSH, "社員番号かパスワードが間違っています。");
                request.setAttribute(REQ_HAS_ERROR, true);
                return false;
                
    
            } else {
                request.getSession().setAttribute(REQ_LOGIN_EMPLOYEE, e);
    
                request.getSession().setAttribute(REQ_FLUSH, "ログインしました。");
                response.sendRedirect(request.getContextPath() + "/");
            }
            
            return true;
        }
        request.getSession().setAttribute(REQ_FLUSH, "社員番号とパスワードを入力してください。");
        request.setAttribute(REQ_HAS_ERROR, true);
        return false;
    }
    
    public static void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.getSession().removeAttribute(REQ_LOGIN_EMPLOYEE);
        request.getSession().setAttribute(REQ_FLUSH, "ログアウトしました。");
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
