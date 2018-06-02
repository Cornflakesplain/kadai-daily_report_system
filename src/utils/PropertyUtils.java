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
    
//    /* Employees Request Session */
//    public static final String REQUEST_EMPLOYEES = "employees";
//    public static final String REQUEST_EMPLOYEES_COUNT = "employees_count";
//    public static final String REQUEST_FLUSH = "flush";
//    public static final String REQUEST_PAGE = "page";
//    public static final String REQUEST_TOKEN = "_token";



}
