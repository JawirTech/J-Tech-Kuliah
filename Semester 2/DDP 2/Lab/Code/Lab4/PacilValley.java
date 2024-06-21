import java.util.ArrayList;
import java.util.Scanner;

public class PacilValley {
    private static Scanner in = new Scanner(System.in);
    private static ArrayList<Employee> employees = new ArrayList<>();

    private static void printSeparator() {
        System.out.println("=".repeat(64));
    }

    public static void employeeList() {
        int totalEmployee = employees.size();

        if (totalEmployee == 0) {
            System.out.println("PacilValley belum memiliki karyawan :(\n");
            return;
        }

        printSeparator();
        System.out.println("PacilValley memiliki total " + totalEmployee + " karyawan:");
        for (Employee employee : employees) {       //setiap employee di ArrayList akan dipanggil toList()
            System.out.println(employee.toString());
        }

        printSeparator();
    }

    public static void hireEmployee() {
        Employee newEmployee;

        System.out.print("Nama: ");
        String nama = in.nextLine();

        System.out.print("Pengalaman Kerja (tahun): ");
        int pengalamanKerja = Integer.parseInt(in.nextLine());

        System.out.print("Base Salary (IDR): ");
        double baseSalary = Integer.parseInt(in.nextLine());

        String role;
        while (true) {
            System.out.print("Role Employee: ");
            role = in.nextLine();

            if (role.equalsIgnoreCase("Engineer")) {
                System.out.print("Project Fee (IDR): ");
                int projectFee = in.nextInt();
                in.nextLine();
                Engineer newEngineer = new Engineer(nama, pengalamanKerja, baseSalary, 0,  projectFee);
                employees.add(newEngineer);         //menambah employee ke ArrayList
                System.out.println("\nEngineer dengan ID " + (employees.indexOf(newEngineer) + 1) + " bernama " + nama + " berhasil dihire!");
                break;
            } else if (role.equalsIgnoreCase("Salesman")) {
                System.out.print("Commission Fee (%): ");
                int commissionFee = in.nextInt();
                in.nextLine();
                Salesman newSalesman = new Salesman(nama, pengalamanKerja, baseSalary, 0, commissionFee);
                employees.add(newSalesman);         //menambah employee ke ArrayList
                System.out.println("\nSalesman dengan ID " + (employees.indexOf(newSalesman) + 1) + " bernama " + nama + " berhasil dihire!");
                break;
            } else if (role.equalsIgnoreCase("Accountant")) {
                System.out.print("Hourly Rate (IDR): ");
                int hourlyRate = in.nextInt();
                in.nextLine();
                Accountant newaAccountant = new Accountant(nama, pengalamanKerja, baseSalary, hourlyRate);
                employees.add(newaAccountant);      //menambah employee ke ArrayList
                System.out.println("\nAccountant dengan ID " + (employees.indexOf(newaAccountant) + 1) + " bernama " + nama + " berhasil dihire!");
                break;
            } else {
                System.out.println("\nRole employee tidak valid, silahkan input kembali dengan nilai yang benar!\n");       //ketika role tidak ada
            }
        }
    }

    public static void logEmployeeSalary() {
        if (employees.isEmpty()) {
            System.out.println("PacilValley belum memiliki karyawan :(\n");
            return;
        }
        while (true) {
            System.out.print("Masukkan employee ID: ");
            int inputID = in.nextInt();
            if (inputID > employees.size() || inputID <= 0) {       //ketika id belum ada ada di ArrayList maka salah
                System.out.println("Employee dengan ID " + inputID + " tidak ditemukan! Silahkan masukkan ID yang sesuai.");
                continue;
            }
            else {
                Employee employee = employees.get(inputID - 1);     //memuat employee sesuai ID
                System.out.println("Employee bernama " + employee.getName() + " dengan role " + employee.getClass() + " berhasil dipilih!");
                if (employee instanceof Engineer) {     //ketika employee adalah seorang engineer
                    Engineer engineer = (Engineer) employee;        //merubah tipe menjadi Engineer
                    System.out.print("Jumlah assigned project: ");
                    int assignedProject = in.nextInt();
                    in.nextLine();
                    engineer.setTotalProject(assignedProject);      //me-set total project dari input assignedProject
                    System.out.println(String.format("Gaji %s bulan ini adalah %.1f IDR\n", engineer.getName(), engineer.CalculateSalary()));
                    engineer.setFinalSalary(engineer.CalculateSalary());        //meng-set finalSalary denag calculatesalary milik Engineer
                    break;
                }
                else if (employee instanceof Salesman) {        //ketika employee adalah seorang Salesman
                    Salesman salesman = (Salesman) employee;    //merubah tipe menjadi Salesman
                    System.out.print("Jumlah sales (IDR): ");
                    int jumlahSales = in.nextInt();
                    in.nextLine();
                    salesman.setTotalSales(jumlahSales);        //me-set total sales dari input jumlahSales
                    System.out.println(String.format("Gaji %s bulan ini adalah %.1f IDR\n", salesman.getName(), salesman.CalculateSalary()));
                    salesman.setFinalSalary(salesman.CalculateSalary());         //meng-set finalSalary denag calculatesalary milik Salesman
                    break;
                }  
                else {           //ketika employee adalah seorang Accountant
                    Accountant accountant = (Accountant) employee;  //merubah tipe menjadi Accountant
                    System.out.print("Jumlah jam kerja: ");
                    int jamKerja = in.nextInt();
                    in.nextLine();
                    accountant.setTotalHoursWorked(jamKerja);    //me-set Total Hour worked dari input jamKerja
                    System.out.println(String.format("Gaji %s bulan ini adalah %.1f IDR\n", accountant.getName(), accountant.CalculateSalary()));
                    accountant.setFinalSalary(accountant.CalculateSalary());    //meng-set finalSalary denag calculatesalary milik Accountant
                    break;  
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("Silakan pilih salah satu opsi berikut:");
        System.out.println("[1] Employee List");
        System.out.println("[2] Hire Employee");
        System.out.println("[3] Log Employee Salary");
        System.out.println("[4] Exit");
        System.out.println("=".repeat(64));
    }

    public static void main(String[] args) {
        System.out.println("Selamat datang di PacilValley!");
        while (true) {
            printMenu();
            System.out.print("Input: ");
            int pilihan = Integer.parseInt(in.nextLine());

            if (pilihan == 1) {
                employeeList();
            } else if (pilihan == 2) {
                hireEmployee();
            } else if (pilihan == 3) {
                logEmployeeSalary();
            } else {
                System.out.println("Terima kasih telah menggunakan layanan PacilValley ~ !");
                break;
            }
        }
    }
}