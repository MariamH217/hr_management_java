package management;

import entities.ManagementLevel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Manager;
import entities.Department;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ManagerManagement {

    public static void addManager(Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            Manager newManager = new Manager();

            System.out.println("Enter the manager name:");
            String name = scanner.nextLine().trim();
            newManager.setName(name);


            System.out.println("Enter the managed department ID (or press Enter to skip):");
            String departmentIdInput = scanner.nextLine().trim();
            if (!departmentIdInput.isEmpty()) {
                int departmentId = Integer.parseInt(departmentIdInput);
                Department department = session.get(Department.class, departmentId);
                if (department != null) {
                    newManager.setManagedDepartment(department);
                } else {
                    System.out.println("Department with ID " + departmentId + " not found.");
                }
            } else {
                System.out.println("Skipped specifying the managed department.");
            }

            System.out.println("Enter the email:");
            String email = scanner.nextLine().trim();
            newManager.setEmail(email);

            System.out.println("Enter the phone number:");
            String phoneNumber = scanner.nextLine().trim();
            newManager.setPhoneNumber(phoneNumber);

            System.out.println("Enter the hire date (yyyy-MM-dd):");
            String hireDateInput = scanner.nextLine().trim();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date hireDate = dateFormat.parse(hireDateInput);
            newManager.setHireDate(hireDate);

            System.out.println("Enter the job title:");
            String jobTitle = scanner.nextLine().trim();
            newManager.setJobTitle(jobTitle);


            System.out.println("Enter the management level (TOP_LEVEL, MID_LEVEL, FIRST_LINE):");
            String managementLevelStr = scanner.nextLine().trim().toUpperCase();
            try {
                ManagementLevel managementLevel = ManagementLevel.valueOf(managementLevelStr);
                newManager.setManagementLevel(managementLevel);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid management level. Please enter TOP_LEVEL, MID_LEVEL, or FIRST_LINE.");
            }

            session.save(newManager);

            transaction.commit();
            System.out.println("Manager added successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error adding manager.");
            e.printStackTrace();
        }
    }
}
