//Favian Zhafif Rizqullah Permana (2306274996)
//DDP2-G (SHY)

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Employee> employeeList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Selamat Datang di PacilRekrutment");
        while (true) {
            printWelcomingMsg();
            System.out.print("Input: ");
            int actionCode = sc.nextInt();
            switch (actionCode) {
                case 1:
                    printEmployeeList();
                    break;
                case 2:
                    hireEmployee();
                    break;
                case 3:
                    askForRaise();
                    break;
                case 4:
                    extendContract();
                    break;
                case 5:
                    System.out.println("Terima kasih telah menggunakan layanan PacilRekrutment ~ !");
                    sc.close();
                    return;
                default:
                    unknownActionMsg();
                    break;
            }
        }
    }

    public static void printEmployeeList() {
        //ngeprint semua employee jika ada di ArrayListnya masing2
        if (!getPermanentEmployee().isEmpty()){
            displayPermanentEmployee();
        }
        if (!getContractEmployee().isEmpty()){
            displayContractEmployee();
        }
        if (!getInternEmployee().isEmpty()){
            displayInternEmployee();
        }
    }

    public static void hireEmployee() {     //menambahkan employee baru
        sc.nextLine();
        System.out.print("Nama: ");
        String namaEmployee = sc.nextLine();

        //mengecek apakah nama sudah ada di employeeList
        boolean ada = false;
        for (Employee e : employeeList) {
            if (namaEmployee.equalsIgnoreCase(e.getName())) {
                ada = true;
            }
        }
        if (ada) {
            System.out.println("Nama sudah terdaftar!!!\n");
        }
        else {
            System.out.print("Base Salary: ");
            int baseSalary = sc.nextInt();
            sc.nextLine();
            System.out.print("Status Employee (Permanent/Contract/Intern): ");
            String statusEmployee = sc.nextLine();
            if (statusEmployee.equalsIgnoreCase("Permanent")) {     //jika statusEmployee adalah Permanent
                PermanentEmployee newPermanentEmployee = new PermanentEmployee(namaEmployee, baseSalary);    //dicasting jadi PermanentEmployee
                employeeList.add(newPermanentEmployee);     //employee ditambah ke employeeList
                newPermanentEmployee.setEmployeeId(employeeList.size() - 1);      //set id sang employee
                newPermanentEmployee.setEmployeeCnt(newPermanentEmployee.getEmployeeCnt());     //increment employeeCnt
                System.out.println("PermanentEmployee dengan ID " + newPermanentEmployee.getEmployeeId()
                                    + " bernama " + newPermanentEmployee.getName() + " berhasil ditambahkan!\n");
            }
            else if (statusEmployee.equalsIgnoreCase("Contract")) {     //jika statusEmployee adalah Contract
                System.out.print("Lama Kontrak (Bulan): ");     //meminta lama kontrak
                int lamaKontrak = sc.nextInt();
                ContractEmployee newContractEmployee = new ContractEmployee(namaEmployee, baseSalary, lamaKontrak);  //dicasting menjadi ContractEmployee
                employeeList.add(newContractEmployee);
                newContractEmployee.setEmployeeId(employeeList.size() - 1);
                newContractEmployee.setEmployeeCnt(newContractEmployee.getEmployeeCnt());
                System.out.println("ContractEmployee dengan ID " + newContractEmployee.getEmployeeId()
                                    + " bernama " + newContractEmployee.getName() + " berhasil ditambahkan!\n");
            }
            else if (statusEmployee.equalsIgnoreCase("Intern")) {   //jika statusEmployee adalah Intern
                System.out.print("Lama Kontrak (Bulan): ");     //meminta lama kontrak
                int lamaKontrak = sc.nextInt();
                InternEmployee newInternEmployee = new InternEmployee(namaEmployee, baseSalary, lamaKontrak);   //dicasting menjadi InternEmployee
                employeeList.add(newInternEmployee);
                newInternEmployee.setEmployeeId(employeeList.size() - 1);
                newInternEmployee.setEmployeeCnt(newInternEmployee.getEmployeeCnt());
                System.out.println("InternEmployee dengan ID " + newInternEmployee.getEmployeeId()
                                    + " bernama " + newInternEmployee.getName() + " berhasil ditambahkan!\n");
            }
        }
    }

    public static void askForRaise() {      //menaikkan salary employee
        //jika tidak ada ContractEmployee dan PermanentEmployee maka tidak ada yang dinaikkan gajinya
        if (getPermanentEmployee().isEmpty() && getContractEmployee().isEmpty()) { 
            System.out.println("Tidak Ada Permanent atau Contract Employee yang Terdaftar!!!\n");
        }
        else {
            //menampilkan employee2 masing2 tipe jika ada di ArrayListnya masing2
            if (!getPermanentEmployee().isEmpty()){
                displayPermanentEmployee();
            }
            if (!getContractEmployee().isEmpty()){
                displayContractEmployee();
            }
            sc.nextLine();
            System.out.print("Masukkan Nama/ID Employee: ");
            String namaOrID = sc.nextLine();

            //cek apakah yang di input Nama atau ID
            boolean isInt = true;
            try {       //jika bisa di parseInt maka ID, jika error maka yang di input Nama
                Integer.parseInt(namaOrID); 
            } catch (NumberFormatException e) {
                isInt = false;
            }
            boolean valid = false;
            Employee wantEmployee = null;       //inisialisasi Employee yang akan dinaikkan gajinya menjadi null dulu
            if (isInt) {
                if (Integer.parseInt(namaOrID) < employeeList.size()) {     //ID harus ada di employeeList
                    valid = true;
                    for (Employee e : employeeList) {
                        if (Integer.parseInt(namaOrID) == e.getEmployeeId()) {
                            wantEmployee = e;       //employee yang tadi adalah yang mempunya ID yang di input
                        }
                    }
                }
            }
            else {      //di cek apakah Nama yang di input ada di employeeList
                for (Employee e : employeeList) {
                    if (!namaOrID.equals(e.getName())){
                        continue;
                    }
                    else {      
                        valid = true;
                        System.out.println(e);
                        wantEmployee = e;
                        break;
                    }
                }
            }
            if (!valid) {       //jika Nama/ID tidak ada, maka tidak ada yang dinaikkan gajinya
                System.out.println("Employee dengan Nama/ID " + namaOrID + " Tidak Ditemukan!!!\n");
            }
            else {
                if (wantEmployee instanceof InternEmployee) {       //InternEmployee tidak bisa naik gaji
                    System.out.println("Intern Employee Tidak Bisa Mendapatkan Raise!!!\n");
                }
                else {
                    System.out.print("Masukkan Jumlah Kenaikan: ");
                    int jumlahKenaikan = sc.nextInt();
                    if (jumlahKenaikan < 0) { 
                        System.out.println("Kenaikkan Gaji Tidak Boleh Negatif!!!\n");
                    }
                    else {
                        if (wantEmployee instanceof PermanentEmployee) {    //jika employee berstatus permanent
                            PermanentEmployee permEmployee = (PermanentEmployee) wantEmployee;  //dicasting
                            permEmployee.setRaise(jumlahKenaikan);      
                            permEmployee.askRaise(jumlahKenaikan);      //menaikkan salary                        
                            System.out.println("Employee dengan Nama/ID " + namaOrID + " Berhasil Dinaikkan Gajinya Sebesar " + (int) jumlahKenaikan + "\n");
                        }
                        else if (wantEmployee instanceof ContractEmployee) {    //jika employee berstatus contract
                            ContractEmployee contEmployee = (ContractEmployee) wantEmployee;    //dicasting
                            contEmployee.setRaise(jumlahKenaikan);
                            contEmployee.askRaise(jumlahKenaikan);      //menaikkan salary                         
                            System.out.println("Employee dengan Nama/ID " + namaOrID + " Berhasil Dinaikkan Gajinya Sebesar " + (int) jumlahKenaikan + "\n");
                        }
                    }
                }
            }
        }
    }

    public static void extendContract() {   //menambahkan lama kontrak
        if (getContractEmployee().isEmpty() && getInternEmployee().isEmpty()) {     //harus ada employee yang berstatus contract atau intern
            System.out.println("Tidak Ada Contract atau Intern Employee yang Terdaftar!!!\n");
        }
        else {
            //menampilkan semua employee  yang berstatus contract atau intern, sisanya hampir sama jalannya dengan askForRaise
            if (!getContractEmployee().isEmpty()){
                displayContractEmployee();
            }
            if (!getInternEmployee().isEmpty()){
                displayInternEmployee();
            }
            sc.nextLine();
            System.out.print("Masukkan Nama/Id Employee: ");
            String namaOrID = sc.nextLine();
            boolean isInt = true;
            try {
                Integer.parseInt(namaOrID);
            } catch (NumberFormatException e) {
                isInt = false;
            }
            boolean valid = false;
            Employee wantEmployee = null;
            if (isInt) {
                if (Integer.parseInt(namaOrID) <= employeeList.size()) {
                    valid = true;
                }
                for (Employee e : employeeList) {
                    if (Integer.parseInt(namaOrID) == e.getEmployeeId()) {
                        wantEmployee = e;
                    }
                }
            }
            else {
                for (Employee e : employeeList) {
                    if (!namaOrID.equals(e.getName())){
                        continue;
                    }
                    else {
                        valid = true;
                        wantEmployee = e;
                        break;
                    }
                }
            }
            if (!valid) {
                System.out.println("Employee dengan Nama/ID " + namaOrID + " Tidak Ditemukan!!!\n");
            }
            else {
                if (wantEmployee instanceof PermanentEmployee) {        //PermanentEmployee tidak bisa memperpanjang kontrak
                    System.out.println("PermanentEmployee Tidak Bisa Extend Kontrak!!!\n");
                }
                else {
                    System.out.print("Masukkan Lama Extend Kontrak (Bulan): ");
                    int lamaExtend = sc.nextInt();
                    if (wantEmployee instanceof InternEmployee) {   
                        InternEmployee inteEmployee = (InternEmployee) wantEmployee;
                        inteEmployee.extendContract(lamaExtend);
                        System.out.println("Employee dengan Nama/ID " + namaOrID + " Berhasil Diperpanjang Kontraknya Selama " + lamaExtend + " Bulan\n");
                    }
                    else if (wantEmployee instanceof ContractEmployee) {
                        ContractEmployee contEmployee = (ContractEmployee) wantEmployee;
                        contEmployee.extendContract(lamaExtend);
                        System.out.println("Employee dengan Nama/ID " + namaOrID + " Berhasil Diperpanjang Kontraknya Selama " + lamaExtend + " Bulan\n");
                    }
                }
            }
        }    
    }

    // Kumpulan Helper Method

    public static Employee getEmployeeByNameOrId(String nameOrId) {
        // Return employee if exists, otherwise null
        for (Employee employee : employeeList) {
            if (employee.name.equals(nameOrId) || Integer.toString(employee.employeeId).equals(nameOrId)) {
                return employee;
            }
        }
        return null;
    }

    public static void displayPermanentEmployee() {
        if (PermanentEmployee.employeeCnt == 0) {
            return;
        }
        System.out.println("===== Pegawai Tetap =====");
        ArrayList<PermanentEmployee> permanentEmployees = getPermanentEmployee();
        for (PermanentEmployee employee : permanentEmployees) {
            System.out.println(employee);
        }
        System.out.println();
    }

    public static void displayContractEmployee() {
        if (ContractEmployee.employeeCnt == 0) {
            return;
        }
        System.out.println("===== Pegawai Kontrak =====");
        ArrayList<ContractEmployee> contractEmployees = getContractEmployee();
        for (ContractEmployee employee : contractEmployees) {
            System.out.println(employee);
        }
        System.out.println();
    }

    public static void displayInternEmployee() {
        if (InternEmployee.employeeCnt == 0) {
            return;
        }
        System.out.println("===== Pegawai Intern =====");
        ArrayList<InternEmployee> internEmployees = getInternEmployee();
        for (InternEmployee employee : internEmployees) {
            System.out.println(employee);
        }
        System.out.println();
    }

    // Penggunaan Generics dapat digunakan untuk mengurangi pengulangan 3 method ini
    public static ArrayList<InternEmployee> getInternEmployee() {
        ArrayList<InternEmployee> internEmployees = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee instanceof InternEmployee) {
                internEmployees.add((InternEmployee) employee);
            }
        }
        return internEmployees;
    }

    public static ArrayList<ContractEmployee> getContractEmployee() {
        ArrayList<ContractEmployee> contractEmployees = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee instanceof ContractEmployee) {
                contractEmployees.add((ContractEmployee) employee);
            }
        }
        return contractEmployees;
    }

    public static ArrayList<PermanentEmployee> getPermanentEmployee() {
        ArrayList<PermanentEmployee> permanentEmployees = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee instanceof PermanentEmployee) {
                permanentEmployees.add((PermanentEmployee) employee);
            }
        }
        return permanentEmployees;
    }

    // Printing Function
    public static void printWelcomingMsg() {
        System.out.println("Silakan pilih salah satu opsi berikut:");
        System.out.println("[1] Employee List");
        System.out.println("[2] Hire Employee");
        System.out.println("[3] Raise Salary");
        System.out.println("[4] Extend Contract");
        System.out.println("[5] Exit");
        System.out.println("=".repeat(64));
    }

    public static void unknownActionMsg() {
        System.out.println("Mohon masukkan opsi yang valid!\n");
    }
}