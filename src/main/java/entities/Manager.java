package entities;
import jakarta.persistence.*;
@Entity
@Table(name = "manager")
@Cacheable
public class Manager extends Employee {

    @OneToOne
    @JoinColumn(name = "managed_department_id")
    private Department managedDepartment;

    @Column(name = "management_level")
    private ManagementLevel managementLevel;

    public Department getManagedDepartment() {
        return managedDepartment;
    }

    public void setManagedDepartment(Department managedDepartment) {
        this.managedDepartment = managedDepartment;
    }

    public ManagementLevel getManagementLevel() {
        return managementLevel;
    }

    public void setManagementLevel(ManagementLevel managementLevel) {
        this.managementLevel = managementLevel;
    }
}
