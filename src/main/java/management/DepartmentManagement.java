package management;

import entities.Department;
import entities.Manager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class DepartmentManagement {

    public static void addDepartment(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            Department newDepartment = new Department();

            System.out.println("Enter the department name:");
            String name = scanner.nextLine().trim();
            newDepartment.setDepartmentName(name);

            System.out.println("Enter the location:");
            String location = scanner.nextLine().trim();
            newDepartment.setLocation(location);

            System.out.println("Enter the ID of the department manager:");
            int managerId = scanner.nextInt();
            scanner.nextLine();
            Manager departmentManager = session.get(Manager.class, managerId);
            if (departmentManager != null) {
                newDepartment.setDepartmentHead(departmentManager);
            } else {
                System.out.println("Manager with ID " + managerId + " not found.");
                transaction.rollback();
                return;
            }

            session.persist(newDepartment);
            session.flush();

            transaction.commit();
            System.out.println("Department added successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error adding department.");
            e.printStackTrace();
        }
    }

    public static void updateDepartment(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the department ID you want to update:");
            int departmentId = scanner.nextInt();
            scanner.nextLine();

            Department departmentToUpdate = session.get(Department.class, departmentId);

            if (departmentToUpdate != null) {
                System.out.println("Enter the new department name (or press Enter to keep existing):");
                String newName = scanner.nextLine().trim();
                if (!newName.isEmpty()) {
                    departmentToUpdate.setDepartmentName(newName);
                }

                System.out.println("Enter the new location (or press Enter to keep existing):");
                String newLocation = scanner.nextLine().trim();
                if (!newLocation.isEmpty()) {
                    departmentToUpdate.setLocation(newLocation);
                }


                transaction.commit();
                System.out.println("Department details updated successfully.");
            } else {
                System.out.println("Department with ID " + departmentId + " not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error updating department details.");
            e.printStackTrace();
        }
    }

    public static void deleteDepartment(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the department ID you want to delete:");
            int departmentId = scanner.nextInt();
            scanner.nextLine();

            Department departmentToDelete = session.get(Department.class, departmentId);

            if (departmentToDelete != null) {
                session.delete(departmentToDelete);

                transaction.commit();
                System.out.println("Department deleted successfully.");
            } else {
                System.out.println("Department with ID " + departmentId + " not found.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error deleting department.");
            e.printStackTrace();
        }
    }


    public static void listDepartments(Session session) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the department name to list departments (leave blank to list all):");
            String departmentName = scanner.nextLine().trim();

            String hql = "FROM Department";
            if (!departmentName.isEmpty()) {
                hql += " WHERE departmentName = :departmentName";
            }

            Transaction transaction = session.beginTransaction();
            List<Department> departments;
            if (!departmentName.isEmpty()) {
                departments = session.createQuery(hql, Department.class)
                        .setParameter("departmentName", departmentName)
                        .list();
            } else {
                departments = session.createQuery(hql, Department.class).list();
            }
            transaction.commit();

            if (departments.isEmpty()) {
                System.out.println("No departments found.");
            } else {
                System.out.println("Departments:");
                for (Department department : departments) {
                    System.out.println("Department ID: " + department.getDepartmentId());
                    System.out.println("Name: " + department.getDepartmentName());
                    System.out.println("Location: " + department.getLocation());
                    System.out.println("Department Head: " + (department.getDepartmentHead() != null ? department.getDepartmentHead().getName() : "null"));
                    System.out.println("---------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error listing departments.");
            e.printStackTrace();
        }
    }

}
