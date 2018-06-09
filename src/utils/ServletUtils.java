package utils;

import javax.servlet.http.HttpServletRequest;

/**
* @author J.Tamura
* Servlet共通処理
*/
public class ServletUtils 
{
    static final String REQ__TOKEN = "_token";
    private static final String REQ_FLUSH = "flush";
    static final String REQ_EMPLOYEE = "employee";
    static final String REQ_ERRORS = "errors";
    
    /**
    * セッション判定 
    * @param request
    * @return true/正しいセッションである : false/不正なセッションである
    */
    public static boolean isFairSession(HttpServletRequest request) 
    {
        
        // セッションIDを取得
        String _token = (String)request.getParameter(REQ__TOKEN);
        if(_token !=null && _token.equals(request.getSession().getId())) 
        {
            return true;
        }
        return false;
    }

    
    /**
     * Flush メッセージが存在する場合は設定する
     * @param request
     */
    public static void existsThenSetFlush(HttpServletRequest request) {
        if(request.getSession().getAttribute(REQ_FLUSH) != null) {
            request.setAttribute(REQ_FLUSH, request.getSession().getAttribute(REQ_FLUSH));
            request.getSession().removeAttribute(REQ_FLUSH);
        }
    }
}