package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

@Table(name = "reports")
@NamedQueries({
    @NamedQuery(
            name = "getAllReports",
            query = "SELECT r FROM Report AS r ORDER BY r.id DESC"
            ),
    @NamedQuery(
            name = "getReportsCount",
            query = "SELECT COUNT(r) FROM Report AS r"
            ),
})
@Entity
public class Report {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "report_date", nullable = false)
    private Date report_date;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    
    /**
     * 編集情報の設定
     * @param request
     * @param servlet
     * @param isUpdate    新規追加である
     */
    public void setEditedItems(HttpServletRequest request, boolean isNewRecord) {

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        
        this.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

        Date report_date = new Date(System.currentTimeMillis());
        String rd_str = request.getParameter("report_date");
        if(rd_str != null && !rd_str.equals("")) {
            report_date = Date.valueOf(request.getParameter("report_date"));
        }
        this.setReport_date(report_date);

        this.setTitle(request.getParameter("title"));
        this.setContent(request.getParameter("content"));

        this.setCreated_at(currentTime);
        this.setUpdated_at(currentTime);

    }
}