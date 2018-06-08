package utils;



 /**
 * @author J.Tamura
 * 定数クラス
 */
public class PropertyUtils {

    /* Employees NamedQuery */
    public static final String QRY_GET_ALL_EMPLOYEES = "getAllEmployees";
    public static final String QRY_GET_EMPLOYEES_COUNT = "getEmployeesCount";
    public static final String QRY_CHECK_REGISTERED_CODE = "checkRegisteredCode";
    public static final String QRY_CHECK_LOGIN_CODE_AND_PASSWORD = "checkLoginCodeAndPassword";

    /* Employees NamedQuerys Args */
    public static final String ARGS_CODE = "code";
    public static final String ARGS_PASS = "pass";
    
    /* Employees Action Mapping */
    public static final String FORWARD_TOPPAGE = "/WEB-INF/views/topPage/index.jsp";
    public static final String FORWARD_EMPLOYEES_INDEX = "/WEB-INF/views/employees/index.jsp";
    public static final String FORWARD_EMPLOYEES_NEW = "/WEB-INF/views/employees/new.jsp";
    public static final String FORWARD_EMPLOYEES_CREATE = "/WEB-INF/views/employees/create.jsp";
    public static final String FORWARD_EMPLOYEES_SHOW = "/WEB-INF/views/employees/show.jsp";
    public static final String FORWARD_EMPLOYEES_EDIT = "/WEB-INF/views/employees/edit.jsp";
    public static final String FORWARD_EMPLOYEES_UPDATE = "/WEB-INF/views/employees/update.jsp";
    public static final String FORWARD_EMPLOYEES_DESTROY = "/WEB-INF/views/employees/destroy.jsp";
    public static final String FORWARD_SESSION_ERROR = "/WEB-INF/views/common/sessionErr.jsp";
    public static final String FORWARD_LOGIN_LOGIN = "/WEB-INF/views/login/login.jsp";
    

    /* Reports NamedQuery */
    public static final String QRY_GET_ALL_REPORTS = "getAllReports";
    public static final String QRY_GET_REPORTS_COUNT = "getReportsCount";
    
    /* Reports Action Mapping */
    public static final String FORWARD_REPORTS_INDEX = "/WEB-INF/views/reports/index.jsp";
    public static final String FORWARD_REPORTS_NEW = "/WEB-INF/views/reports/new.jsp";
    public static final String FORWARD_REPORTS_CREATE = "/WEB-INF/views/reports/create.jsp";
    public static final String FORWARD_REPORTS_SHOW = "/WEB-INF/views/reports/show.jsp";
    public static final String FORWARD_REPORTS_EDIT = "/WEB-INF/views/reports/edit.jsp";
    public static final String FORWARD_REPORTS_UPDATE = "/WEB-INF/views/reports/update.jsp";
    public static final String FORWARD_REPORTS_DESTROY = "/WEB-INF/views/reports/destroy.jsp";
    
}
