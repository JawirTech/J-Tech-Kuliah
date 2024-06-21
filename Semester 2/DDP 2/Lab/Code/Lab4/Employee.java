public class Employee {
    private int employeeID;
    private String name;
    private int yearsOfWork;
    private double baseSalary;
    private double finalSalary;

    //bikin no-arg constructor, getter, setter
    public Employee(String name, int yearsOfWork, double baseSalary) {
        this.name = name;
        this.yearsOfWork = yearsOfWork;
        this.baseSalary = baseSalary;   
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearsOfWork() {
        return yearsOfWork;
    }

    public void setYearsOfWork(int yearsOfWork) {
        this.yearsOfWork = yearsOfWork;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getFinalSalary() {
        return finalSalary;
    }

    public void setFinalSalary(double finalSalary) {
        this.finalSalary = finalSalary;
    }

    double CalculateSalary() {
        return getBaseSalary();
    }
    
    @Override
    public String toString() {
        String res = "Nama: " + getName() + "\nPengalaman Kerja: " + getYearsOfWork() + "\nJabatan: ";
        if (getYearsOfWork() <= 5) {
            res += "Junior";
        }
        else if (getYearsOfWork() > 5 && getYearsOfWork() <= 10) {
            res += "Senior";
        }
        else {
            res += "Expert";
        }
        return res;
    }
}
