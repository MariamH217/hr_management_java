package management;

import entities.Department;
import entities.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagement {

    public static void addEmployee(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            Employee newEmployee = new Employee();

            System.out.println("Enter the employee name:");
            String name = scanner.nextLine().trim();
            newEmployee.setName(name);

            System.out.println("Enter the email:");
            String email = scanner.nextLine().trim();
            newEmployee.setEmail(email);

            System.out.println("Enter the phone number:");
            String phoneNumber = scanner.nextLine().trim();
            newEmployee.setPhoneNumber(phoneNumber);

            System.out.println("Enter the department ID:");
            int departmentId = scanner.nextInt();
            Department department = session.get(Department.class, departmentId);
            if (department != null) {
                newEmployee.setDepartment(department);
            } else {
                System.out.println("Department with ID " + departmentId + " not found.");
            }

            System.out.println("Enter the hire date (yyyy-MM-dd):");
            String hireDateInput = scanner.nextLine().trim();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date hireDate = dateFormat.parse(hireDateInput);
            newEmployee.setHireDate(hireDate);

            System.out.println("Enter the job title:");
            String jobTitle = scanner.nextLine().trim();
            newEmployee.setJobTitle(jobTitle);

            session.save(newEmployee);

            transaction.commit();
            System.out.println("Employee added successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error adding employee.");
            e.printStackTrace();
        }
    }

    public static void updateEmployee(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the ID of the employee to update:");
            int employeeId = scanner.nextInt();
            Employee employeeToUpdate = session.get(Employee.class, employeeId);

            if (employeeToUpdate == null) {
                System.out.println("Employee with ID " + employeeId + " not found.");
                return;
            }
            scanner.nextLine();

            System.out.println("Enter the new employee name (or press Enter to keep existing):");
            String newName = scanner.nextLine().trim();
            if (!newName.isEmpty()) {
                employeeToUpdate.setName(newName);
            }


            System.out.println("Enter the new employee email (or press Enter to keep existing):");
            String newEmail = scanner.nextLine().trim();
            if (!newEmail.isEmpty()) {
                employeeToUpdate.setEmail(newEmail);
            }


            System.out.println("Enter the new employee phone number (or press Enter to keep existing):");
            String newPhoneNumber = scanner.nextLine().trim();
            if (!newPhoneNumber.isEmpty()) {
                employeeToUpdate.setPhoneNumber(newPhoneNumber);
            }


            System.out.println("Enter the new department ID (or press Enter to keep existing):");
            String departmentIdInput = scanner.nextLine().trim();
            if (!departmentIdInput.isEmpty()) {
                int newDepartmentId = Integer.parseInt(departmentIdInput);
                Department newDepartment = session.get(Department.class, newDepartmentId);
                if (newDepartment != null) {
                    employeeToUpdate.setDepartment(newDepartment);
                } else {
                    System.out.println("Department with ID " + newDepartmentId + " not found. Department not updated.");
                }
            }


            System.out.println("Enter the new hire date (yyyy-MM-dd) (or press Enter to keep existing):");
            String newHireDateInput = scanner.nextLine().trim();
            if (!newHireDateInput.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date newHireDate = dateFormat.parse(newHireDateInput);
                employeeToUpdate.setHireDate(newHireDate);
            }


            System.out.println("Enter the new job title (or press Enter to keep existing):");
            String newJobTitle = scanner.nextLine().trim();
            if (!newJobTitle.isEmpty()) {
                employeeToUpdate.setJobTitle(newJobTitle);
            }

            session.update(employeeToUpdate);

            transaction.commit();
            System.out.println("Employee updated successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error updating employee.");
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the employee ID you want to delete:");
            int employeeId = scanner.nextInt();
            scanner.nextLine();

            Employee employeeToDelete = session.get(Employee.class, employeeId);

            if (employeeToDelete != null) {
                session.delete(employeeToDelete);

                transaction.commit();
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee with ID " + employeeId + " not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error deleting employee.");
            e.printStackTrace();
        }
    }

    public static void listEmployees(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            List<Employee> employees = session.createQuery("FROM Employee", Employee.class).list();

            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                System.out.println("Employees:");
                for (Employee employee : employees) {
                    System.out.println("Employee ID: " + employee.getEmployeeID());
                    System.out.println("Name: " + employee.getName());
                    System.out.println("Email: " + employee.getEmail());
                    System.out.println("Phone Number: " + employee.getPhoneNumber());
                    System.out.println("Hire Date: " + employee.getHireDate());
                    System.out.println("Job Title: " + employee.getJobTitle());
                    System.out.println("---------------------------");
                }
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error listing employees.");
            e.printStackTrace();
        }
    }
}
