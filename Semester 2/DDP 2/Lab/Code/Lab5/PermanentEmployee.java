public class PermanentEmployee extends Employee implements RaiseSalary {
    double raise = 0.0;

    PermanentEmployee(String name, double salary) {
        super(name, salary);        //menggunakan atribut yang sudah di buat di superclass Employee
    }

    public double getRaise() {
        return raise;
    }

    public void setRaise(double raise) {
        this.raise += raise;            //menambah kenaikkan gaji setiap kali dipanggil
    }

    @Override
    public double calculateSalary() {
        return getSalary();             //return salary yang sudah jadi
    }

    @Override
    public void askRaise(double raise) {
        setsalary(raise + getSalary());    //salary akan ditambah dengan kenaikkan
    }

    @Override
    public String toString() {      //override untuk print bagi PermanentEmployee
        return "[" + getEmployeeId() + "] " + getName() + " | Salary : " + (int) calculateSalary() + " | Kenaikan : " + (int) getRaise();
    }
}