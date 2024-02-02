import management.DepartmentManagement;
import management.EmployeeManagement;
import management.ManagerManagement;
import management.ProjectManagement;
import entities.*;
import org.hibernate.Session;

import java.util.Scanner;

public class Console {
    static void menu(Session session) {
        int answer;

        do {
            manuOptions();
            Scanner sc = new Scanner(System.in);
            answer = sc.nextInt();

            switch (answer) {
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                case 1:
                    DepartmentManagement.addDepartment(session);
                    break;
                case 2:
                    DepartmentManagement.updateDepartment(session);
                    break;
                case 3:
                    DepartmentManagement.deleteDepartment(session);
                    break;
                case 4:
                    DepartmentManagement.listDepartments(session);
                    break;
                case 5:
                    ProjectManagement.addProject(session);
                    break;
                case 6:
                    ProjectManagement.updateProject(session);
                    break;
                case 7:
                    ProjectManagement.deleteProject(session);
                    break;
                case 8:
                    ProjectManagement.listProjects(session);
                    break;
                case 9:
                    EmployeeManagement.addEmployee(session);
                    break;
                case 10:
                    EmployeeManagement.updateEmployee(session);
                    break;
                case 11:
                    EmployeeManagement.deleteEmployee(session);
                    break;
                case 12:
                    EmployeeManagement.listEmployees(session);
                    break;
                case 13:
                    ManagerManagement.addManager(session);
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        } while (answer != 0);
    }

    private static void manuOptions() {

        System.out.println("0. Exit the program");
        System.out.println("1. Add department");
        System.out.println("2. Update department ");
        System.out.println("3. Delete department");
        System.out.println("4. List departments");
        System.out.println("5. Add project");
        System.out.println("6. Update project");
        System.out.println("7. Delete project");
        System.out.println("8. List projects");
        System.out.println("9. Add employee");
        System.out.println("10. Update employee");
        System.out.println("11. Delete employee");
        System.out.println("12. List employees");
        System.out.println("13. Add manager");
    }
}
