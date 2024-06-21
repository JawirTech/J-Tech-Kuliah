public class InternEmployee extends Employee implements ExtendContractDuration {
    //atribut khusus dari InternEmployee
    int contractDuration;

    InternEmployee(String name, double salary, int contractDuration) {
        super(name, salary);        //menggunakan atribut yang sudah di buat di superclass Employee
        this.contractDuration = contractDuration;
    }

    public int getContractDuration() {
        return contractDuration;
    }

    public void setContractDuration(int contractDuration) {
        this.contractDuration = contractDuration;
    }

    @Override
    double calculateSalary() {
        return getSalary() * getSalaryMultiplier();     //gaji dikali dengan multiplier berdasarjan panjang kontrak
    }

    @Override
    public void extendContract(int duration) {
        setContractDuration(duration + getContractDuration());          //menambah durasi kontrak tiap kali dipanggil
    }

    @Override
    public String toString() {      //override untuk print bagi InternEmployee
        return "[" + getEmployeeId() + "] " + getName() + " | Salary : " + (int) calculateSalary() + " | Kontrak : " + getContractDuration() + " Bulan";
    }

    private double getSalaryMultiplier() {          //multiply salary tergantung dengan panjang kontrak
        if (getContractDuration() <= 6) {
            return 1;
        }
        else if (getContractDuration() > 6 && getContractDuration() <= 12) {
            return 1.25;
        }
        else {          //jika kontrak diatas 12 bulan multiply menjadi 1.5
            return 1.5;
        }
    }
}