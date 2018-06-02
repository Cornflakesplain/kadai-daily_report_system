package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import utils.EncryptUtil;


@Table(name = "employees")
@NamedQueries({
    @NamedQuery(
            name = "getAllEmployees",
            query = "SELECT e FROM Employee AS e ORDER BY e.id DESC"
            ),
    @NamedQuery(
            name = "getEmployeesCount",
            query = "SELECT COUNT(e) FROM Employee AS e"
            ),
    @NamedQuery(
            name = "checkRegisteredCode",
            query = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :code"
            ),
    @NamedQuery(
            name = "checkLoginCodeAndPassword",
            query = "SELECT e FROM Employee AS e WHERE e.delete_flag = 0 AND e.code = :code AND e.password = :pass")
})
@Entity
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "password", length = 64, nullable = false)
    private String password;
    
    @Column(name = "admin_flag", nullable = false)
    private Integer admin_flag;
    
    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;
    
    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;
    
    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdmin_flag() {
        return admin_flag;
    }

    public void setAdmin_flag(Integer admin_flag) {
        this.admin_flag = admin_flag;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }
    
    /**
     * 編集情報の設定
     * @param request
     * @param servlet
     * @param isUpdate    新規追加である
     */
    public void setEditedItems(HttpServletRequest request, HttpServlet servlet, boolean isNewRecord) {

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        
       // 新規追加である場合
       if (isNewRecord) {
           setCode(request.getParameter("code"));
           this.setCreated_at(currentTime);
       } else {
           // データと入力値が異なる場合
           if (!this.getCode().equals(request.getParameter("code"))) {
               setCode(request.getParameter("code"));           
           }
       }
       
       // インスタンス変数に入力値を設定
       this.setName(request.getParameter("name"));
       
       // パスワードが未入力でない場合
       if (StringUtils.isNotEmpty(request.getParameter("password"))) {
           this.setPassword(
                   EncryptUtil.getPasswordEncrypt(
                           request.getParameter("password"),
                           (String)servlet.getServletContext().getAttribute("salt")
                   )
           );
       }

       this.setAdmin_flag(Integer.parseInt(request.getParameter("admin_flag")));
       
       this.setUpdated_at(currentTime);
   
       this.setDelete_flag(0);
    }
}
