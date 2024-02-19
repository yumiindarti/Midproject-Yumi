import java.util.*;

class Employee {
    String employeeCode;
    String employeeName;
    String gender;
    String position;
    double salary;

    Employee(String code, String name, String gender, String position, double salary) {
        this.employeeCode = code;
        this.employeeName = name;
        this.gender = gender;
        this.position = position;
        this.salary = salary;
    }

    void display() {
        System.out.println("Employee Code: " + employeeCode);
        System.out.println("Employee Name: " + employeeName);
        System.out.println("Gender: " + gender);
        System.out.println("Position: " + position);
        System.out.println("Salary: Rp. " + salary + " Juta");
        System.out.println();
    }
}

public class Main {
    static List<Employee> employees = new ArrayList<>();
    static Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Input Data Karyawan");
            System.out.println("2. View Data Karyawan");
            System.out.println("3. Update Data Karyawan");
            System.out.println("4. Delete Data Karyawan");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    inputData(scanner);
                    break;
                case 2:
                    viewData();
                    break;
                case 3:
                    updateData(scanner);
                    break;
                case 4:
                    deleteData(scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (choice != 0);
    }

    static void inputData(Scanner scanner) {
        System.out.println("Input Data Karyawan:");

        // Generate employee code
        String code;
        do {
            code = generateEmployeeCode();
        } while (isEmployeeCodeExists(code));

        System.out.print("Nama Karyawan (min. 3 huruf): ");
        String name = scanner.next();
        while (name.length() < 3) {
            System.out.print("Nama Karyawan (min. 3 huruf): ");
            name = scanner.next();
        }
        System.out.print("Jenis Kelamin (Laki-Laki / Perempuan): ");
        String gender = scanner.next();
        while (!gender.equals("Laki-Laki") && !gender.equals("Perempuan")) {
            System.out.print("Jenis Kelamin (Laki-Laki / Perempuan): ");
            gender = scanner.next();
        }
        System.out.print("Jabatan (Manager / Supervisor / Admin): ");
        String position = scanner.next();
        while (!position.equals("Manager") && !position.equals("Supervisor") && !position.equals("Admin")) {
            System.out.print("Jabatan (Manager / Supervisor / Admin): ");
            position = scanner.next();
        }

        double salary = 0;
        switch (position) {
            case "Manager":
                salary = 8.0;
                break;
            case "Supervisor":
                salary = 6.0;
                break;
            case "Admin":
                salary = 4.0;
                break;
        }

        Employee employee = new Employee(code, name, gender, position, salary);
        employees.add(employee);
        System.out.println("Data karyawan berhasil ditambahkan.\n");
    }

    static String generateEmployeeCode() {
        StringBuilder code = new StringBuilder();
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int i = 0; i < 2; i++) {
            code.append(alphabet[random.nextInt(26)]);
        }
        code.append("-");
        for (int i = 0; i < 4; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    static boolean isEmployeeCodeExists(String code) {
        for (Employee emp : employees) {
            if (emp.employeeCode.equals(code)) {
                return true;
            }
        }
        return false;
    }

    static void viewData() {
        if (employees.isEmpty()) {
            System.out.println("Tidak ada data karyawan.");
            return;
        }
        employees.sort(Comparator.comparing(emp -> emp.employeeName));

        System.out.println("Data Karyawan (berdasarkan nama):");
        for (Employee employee : employees) {
            employee.display();
        }
    }

    static void updateData(Scanner scanner) {
        if (employees.isEmpty()) {
            System.out.println("Tidak ada data karyawan untuk diupdate.");
            return;
        }

        viewData();

        System.out.print("Pilih nomor data yang ingin diupdate: ");
        int index = scanner.nextInt();

        if (index < 1 || index > employees.size()) {
            System.out.println("Nomor data tidak valid.");
            return;
        }

        Employee empToUpdate = employees.get(index - 1);

        System.out.println("Update Data Karyawan:");
        System.out.print("Nama Karyawan (" + empToUpdate.employeeName + "): ");
        String name = scanner.next();
        if (!name.equals("0")) {
            while (name.length() < 3) {
                System.out.print("Nama Karyawan (min. 3 huruf): ");
                name = scanner.next();
            }
            empToUpdate.employeeName = name;
        }
        System.out.print("Jenis Kelamin (" + empToUpdate.gender + "): ");
        String gender = scanner.next();
        if (!gender.equals("0")) {
            while (!gender.equals("Laki-Laki") && !gender.equals("Perempuan")) {
                System.out.print("Jenis Kelamin (Laki-Laki / Perempuan): ");
                gender = scanner.next();
            }
            empToUpdate.gender = gender;
        }
        System.out.print("Jabatan (" + empToUpdate.position + "): ");
        String position = scanner.next();
        if (!position.equals("0")) {
            while (!position.equals("Manager") && !position.equals("Supervisor") && !position.equals("Admin")) {
                System.out.print("Jabatan (Manager / Supervisor / Admin): ");
                position = scanner.next();
            }
            empToUpdate.position = position;
            double bonusPercentage = 0;
            switch (position) {
                case "Manager":
                    bonusPercentage = 0.10;
                    break;
                case "Supervisor":
                    bonusPercentage = 0.075;
                    break;
                case "Admin":
                    bonusPercentage = 0.05;
                    break;
            }
            empToUpdate.salary = empToUpdate.salary * (1 + bonusPercentage);
        }

        System.out.println("Data karyawan berhasil diupdate.\n");
    }

    static void deleteData(Scanner scanner) {
        if (employees.isEmpty()) {
            System.out.println("Tidak ada data karyawan untuk dihapus.");
            return;
        }

        viewData();

        System.out.print("Pilih nomor data yang ingin dihapus: ");
        int index = scanner.nextInt();

        if (index < 1 || index > employees.size()) {
            System.out.println("Nomor data tidak valid.");
            return;
        }

        employees.remove(index - 1);
        System.out.println("Data karyawan berhasil dihapus.\n");
    }
}
