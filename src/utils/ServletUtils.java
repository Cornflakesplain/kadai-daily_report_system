package utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;

/**
* @author J.Tamura
* Servlet共通処理
*/
public class ServletUtils 
{
    /**
    * @author J.Tamura
    * @セッション判定 
    * @param request
    * @return true/正しいセッションである : false/不正なセッションである
    */
    public static boolean isTrueSession(HttpServletRequest request) 
    {
        
        String _token = (String)request.getParameter("_token");
        if(_token !=null && _token.equals(request.getSession().getId())) 
        {
            return true;
        }
        return false;
    }
    
    /**
    * @author J.Tamura
    * @入力エラー 
    * @param request
    * @param response
    * @param forwordUrl
    */    
    public static void setInputError(
              HttpServletRequest request
            , HttpServletResponse responce
            , Employee e
            , List<String> errors)
    {
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("employee", e);
        request.setAttribute("errors", errors);
    }
}
