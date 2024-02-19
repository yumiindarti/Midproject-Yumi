package main;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Employee> employees = new ArrayList<>();

        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Input data karyawan");
            System.out.println("2. View data karyawan");
            System.out.println("3. Update data karyawan");
            System.out.println("4. Delete data karyawan");
            System.out.println("0. Keluar");
            System.out.print("Pilihan Anda: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    inputEmployeeData(scanner, employees);
                    break;
                case 2:
                    viewEmployeeData(employees);
                    break;
                case 3:
                    updateEmployeeData(scanner, employees);
                    break;
                case 4:
                    deleteEmployeeData(scanner, employees);
                    break;
                case 0:
                    System.out.println("Program berhenti.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        } while (choice != 0);
    }

    public static void inputEmployeeData(Scanner scanner, ArrayList<Employee> employees) {
        // Input and validate employee name
        String employeeName;
        do {
            System.out.print("Masukkan nama karyawan (minimal 3 huruf): ");
            employeeName = scanner.nextLine();
        } while (employeeName.length() < 3);

        // Input and validate gender
        String gender;
        do {
            System.out.print("Masukkan jenis kelamin (Laki-Laki / Perempuan): ");
            gender = scanner.nextLine();
        } while (!gender.equals("Laki-Laki") && !gender.equals("Perempuan"));

        // Input and validate job title
        String jobTitle;
        do {
            System.out.print("Masukkan jabatan (Manager / Supervisor / Admin): ");
            jobTitle = scanner.nextLine();
        } while (!jobTitle.equals("Manager") && !jobTitle.equals("Supervisor") && !jobTitle.equals("Admin"));

        // Generate employee ID
        String employeeID;
        do {
            employeeID = Employee.generateRandomString(6);
        } while (!isUniqueID(employeeID, employees)); // Validate uniqueness

        // Assign salary based on job title
        int salary = 0;
        switch (jobTitle) {
            case "Manager":
                salary = 8000000;
                break;
            case "Supervisor":
                salary = 6000000;
                break;
            case "Admin":
                salary = 4000000;
                break;
        }

        // Create and add new employee to the list
        employees.add(new Employee(employeeID, employeeName, gender, jobTitle, salary));
        System.out.println("Data karyawan berhasil ditambahkan.");
    }

    public static void viewEmployeeData(ArrayList<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("Tidak ada data karyawan.");
            return;
        }

        // Sort employees by name in ascending order
        Collections.sort(employees, Comparator.comparing(Employee::getName));

        System.out.println("\nData Karyawan:");
        for (int i = 0; i < employees.size(); i++) {
            System.out.println((i + 1) + ". " + employees.get(i));
        }
    }

    public static void updateEmployeeData(Scanner scanner, ArrayList<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("Tidak ada data karyawan untuk diupdate.");
            return;
        }

        viewEmployeeData(employees);

        System.out.print("\nPilih nomor karyawan yang ingin diupdate (0 untuk batal): ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (index < 0 || index > employees.size()) {
            System.out.println("Nomor karyawan tidak valid.");
            return;
        } else if (index == 0) {
            System.out.println("Update dibatalkan.");
            return;
        }

        Employee employeeToUpdate = employees.get(index - 1);

        // Input and validate employee name
        System.out.print("Masukkan nama karyawan baru (0 untuk mempertahankan yang lama): ");
        String employeeName = scanner.nextLine();
        if (!employeeName.equals("0")) {
            while (employeeName.length() < 3) {
                System.out.print("Nama karyawan minimal terdiri dari 3 huruf alfabet. Masukkan nama karyawan baru: ");
                employeeName = scanner.nextLine();
            }
            employeeToUpdate.setName(employeeName);
        }

        // Input and validate gender
        System.out.print("Masukkan jenis kelamin baru (0 untuk mempertahankan yang lama): ");
        String gender = scanner.nextLine();
        if (!gender.equals("0")) {
            while (!gender.equals("Laki-Laki") && !gender.equals("Perempuan")) {
                System.out.print("Jenis kelamin harus 'Laki-Laki' atau 'Perempuan'. Masukkan jenis kelamin baru: ");
                gender = scanner.nextLine();
            }
            employeeToUpdate.setGender(gender);
        }

        // Input and validate job title
        System.out.print("Masukkan jabatan baru (0 untuk mempertahankan yang lama): ");
        String jobTitle = scanner.nextLine();
        if (!jobTitle.equals("0")) {
            while (!jobTitle.equals("Manager") && !jobTitle.equals("Supervisor") && !jobTitle.equals("Admin")) {
                System.out.print("Jabatan harus 'Manager', 'Supervisor', atau 'Admin'. Masukkan jabatan baru: ");
                jobTitle = scanner.nextLine();
            }
            employeeToUpdate.setJobTitle(jobTitle);

            // Update salary based on job title
            switch (jobTitle) {
                case "Manager":
                    employeeToUpdate.setSalary(8000000);
                    break;
                case "Supervisor":
                    employeeToUpdate.setSalary(6000000);
                    break;
                case "Admin":
                    employeeToUpdate.setSalary(4000000);
                    break;
            }
        }

        System.out.println("Data karyawan berhasil diupdate.");
    }

    public static void deleteEmployeeData(Scanner scanner, ArrayList<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("Tidak ada data karyawan untuk dihapus.");
            return;
        }

        viewEmployeeData(employees);

        System.out.print("\nPilih nomor karyawan yang ingin dihapus (0 untuk batal): ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index < 0 || index > employees.size()) {
            System.out.println("Nomor karyawan tidak valid.");
            return;
        } else if (index == 0) {
            System.out.println("Penghapusan dibatalkan.");
            return;
        }

        Employee deletedEmployee = employees.remove(index - 1);
        System.out.println("Data karyawan dengan nama " + deletedEmployee.getName() + " berhasil dihapus.");
    }

    // Method to check uniqueness of employee ID
    public static boolean isUniqueID(String employeeID, ArrayList<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.getEmployeeID().equals(employeeID)) {
                return false;
            }
        }
        return true;
    }
}

class Employee {
    private String employeeID;
    private String name;
    private String gender;
    private String jobTitle;
    private int salary;

    public Employee(String employeeID, String name, String gender, String jobTitle, int salary) {
        this.employeeID = employeeID;
        this.name = name;
        this.gender = gender;
        this.jobTitle = jobTitle;
        this.salary = salary;
    }

    // Getters and Setters
    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    // Method to generate random alphanumeric string
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String randomString = "";

        Random random = new Random();
        randomString += characters.charAt(random.nextInt(characters.length()));
        randomString += characters.charAt(random.nextInt(characters.length()));

        for (int i = 0; i < length - 2; i++) {
            randomString += numbers.charAt(random.nextInt(numbers.length()));
        }

        return randomString;
    }

    // Override toString method to display employee information
    @Override
    public String toString() {
        return "Kode Karyawan: " + employeeID +
               ", Nama: " + name +
               ", Jenis Kelamin: " + gender +
               ", Jabatan: " + jobTitle +
               ", Gaji: Rp. " + salary;
    }
}
