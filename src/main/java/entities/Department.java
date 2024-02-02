package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "department_name")
    private String departmentName;

    private String location;

    @OneToOne(mappedBy = "managedDepartment")
    private Manager departmentHead;

    @OneToMany(mappedBy = "department")
    private List<Employee> employeelist = new ArrayList<>();


    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Manager getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(Manager departmentHead) {
        this.departmentHead = departmentHead;
    }
}
