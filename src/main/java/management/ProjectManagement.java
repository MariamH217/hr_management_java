package management;

import entities.Employee;
import entities.Project;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectManagement {

    public static void addProject(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            Project newProject = new Project();

            System.out.println("Enter the project name:");
            String name = scanner.nextLine().trim();
            newProject.setProjectName(name);

            System.out.println("Enter the start date (yyyy-MM-dd):");
            LocalDate startDate = LocalDate.parse(scanner.nextLine().trim());
            newProject.setStartDate(java.sql.Date.valueOf(startDate));


            System.out.println("Enter the end date (yyyy-MM-dd):");
            LocalDate endDate = LocalDate.parse(scanner.nextLine().trim());
            newProject.setEndDate(java.sql.Date.valueOf(endDate));

            System.out.println("Enter the budget:");
            double budget = scanner.nextDouble();
            newProject.setBudget(budget);


            System.out.println("Enter the number of employees to assign to the project:");
            int numEmployees = scanner.nextInt();

            List<Employee> employees = new ArrayList<>();
            for (int i = 0; i < numEmployees; i++) {
                System.out.println("Enter the employee ID for employee " + (i + 1) + ":");
                int employeeID = scanner.nextInt();

                Employee employee = session.get(Employee.class, employeeID);
                if (employee != null) {
                    employees.add(employee);
                } else {
                    System.out.println("Employee with ID " + employeeID + " not found. Skipping assignment.");
                }
            }

            newProject.setProjectEmployees(employees);

            session.save(newProject);

            transaction.commit();
            System.out.println("Project created successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error creating project.");
            e.printStackTrace();
        }
    }

    public static void updateProject(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the ID of the project to update:");
            int projectId = scanner.nextInt();
            Project projectToUpdate = session.get(Project.class, projectId);

            if (projectToUpdate == null) {
                System.out.println("Project with ID " + projectId + " not found.");
                return;
            }
            scanner.nextLine();
            System.out.println("Enter the updated project name (or press Enter to keep existing):");
            String newName = scanner.nextLine().trim();
            if (!newName.isEmpty()) {
                projectToUpdate.setProjectName(newName);
            }

            System.out.println("Enter the updated start date (yyyy-MM-dd)");

            LocalDate newStartDate = LocalDate.parse(scanner.nextLine().trim());
            projectToUpdate.setStartDate(java.sql.Date.valueOf(newStartDate));

            System.out.println("Enter the updated end date (yyyy-MM-dd)");
            LocalDate newEndDate = LocalDate.parse(scanner.nextLine().trim());
            projectToUpdate.setEndDate(java.sql.Date.valueOf(newEndDate));

            System.out.println("Enter the updated budget (or press Enter to keep existing):");
            String newBudgetStr = scanner.nextLine().trim();
            if (!newBudgetStr.isEmpty()) {
                double newBudget = Double.parseDouble(newBudgetStr);
                projectToUpdate.setBudget(newBudget);
            }

            System.out.println("Do you want to update project employees? (yes/no)");
            String updateEmployeesChoice = scanner.nextLine().trim().toLowerCase();
            if (updateEmployeesChoice.equals("yes")) {
                System.out.println("Enter the number of employees to assign to the project:");
                int numEmployees = scanner.nextInt();

                List<Employee> employees = new ArrayList<>();
                for (int i = 0; i < numEmployees; i++) {
                    System.out.println("Enter the employee ID for employee " + (i + 1) + ":");
                    int employeeID = scanner.nextInt();

                    Employee employee = session.get(Employee.class, employeeID);
                    if (employee != null) {
                        employees.add(employee);
                    } else {
                        System.out.println("Employee with ID " + employeeID + " not found. Skipping assignment.");
                    }
                }

                projectToUpdate.setProjectEmployees(employees);
            }

            session.update(projectToUpdate);

            transaction.commit();
            System.out.println("Project updated successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error updating project.");
            e.printStackTrace();
        }
    }

    public static void deleteProject(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the project ID you want to delete:");
            int projectId = scanner.nextInt();
            scanner.nextLine();

            Project projectToDelete = session.get(Project.class, projectId);

            if (projectToDelete != null) {
                session.delete(projectToDelete);

                transaction.commit();
                System.out.println("Project deleted successfully.");
            } else {
                System.out.println("Project with ID " + projectId + " not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error deleting project.");
            e.printStackTrace();
        }
    }

    public static void listProjects(Session session) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the project name to list projects (leave blank to list all):");
            String projectName = scanner.nextLine().trim();

            String hql = "FROM Project";
            if (!projectName.isEmpty()) {
                hql += " WHERE projectName = :projectName";
            }

            Transaction transaction = session.beginTransaction();
            List<Project> projects;
            if (!projectName.isEmpty()) {
                projects = session.createQuery(hql, Project.class)
                        .setParameter("projectName", projectName)
                        .list();
            } else {
                projects = session.createQuery(hql, Project.class).list();
            }
            transaction.commit();

            if (projects.isEmpty()) {
                System.out.println("No projects found.");
            } else {
                System.out.println("Projects:");
                for (Project project : projects) {
                    System.out.println("Project ID: " + project.getProjectId());
                    System.out.println("Name: " + project.getProjectName());
                    // Print other project details as needed...
                    System.out.println("---------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error listing projects.");
            e.printStackTrace();
        }
    }

}
