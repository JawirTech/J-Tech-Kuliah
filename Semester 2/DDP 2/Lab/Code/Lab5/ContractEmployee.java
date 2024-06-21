public class ContractEmployee extends Employee implements RaiseSalary, ExtendContractDuration {
    //atribut khusus dari ContractEmployee
    int contractDuration;
    double raise = 0.0;

    ContractEmployee(String name, double salary, int contractDuration) {
        super(name, salary);        //menggunakan atribut yang sudah di buat di superclass Employee
        this.contractDuration = contractDuration;
    }

    public int getContractDuration() {
        return contractDuration;
    }

    public void setContractDuration(int contractDuration) {
        this.contractDuration = contractDuration;
    }

    public double getRaise() {
        return raise;
    }

    public void setRaise(double raise) {
        this.raise += raise;            //menambah kenaikkan gaji setiap kali dipanggil
    }

    @Override
    public void askRaise(double raise) {
        setsalary(getSalary());          
    }       

    @Override
    public double calculateSalary() {
        return (getSalary() + getRaise()) * getSalaryMultiplier();      //perhitungan salary tergantung dengan jumlah raise dan lama kontrak
    }

    @Override
    public void extendContract(int duration) {
        setContractDuration(duration + getContractDuration());      //menambah durasi kontrak tiap kali dipanggil
    }

    @Override
    public String toString() {      //override untuk print bagi ContractEmployee
        return "[" + getEmployeeId() + "] " + getName() + " | Salary : " + (int) calculateSalary() + " | Kenaikan : " + (int) getRaise() + " | Kontrak : " + getContractDuration();
    }

    private double getSalaryMultiplier() {      //multiply salary tergantung dengan panjang kontrak
        if (getContractDuration() <= 6) {
            return 1;
        }
        else if (getContractDuration() > 6 && getContractDuration() <= 12) {
            return 1.5;
        }
        else {      //jika kontrak diatas 12 bulan multiply menjadi 2
            return 2;
        }
    }
}