public class Accountant extends Employee {
    private int totalHoursWorked;
    private double hourlyRate;

    public Accountant(String name, int yearsOfWork, double baseSalary, double hourlyRate) {
        super(name, yearsOfWork, baseSalary);
        this.hourlyRate = hourlyRate;
    }

     //bikin no-arg constructor, getter, setter
    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(int totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }

    double CalculateSalary() {
        double gajiAccountant = super.CalculateSalary() + (getHourlyRate() * getTotalHoursWorked());
        if (getYearsOfWork() <= 5) {
            return gajiAccountant *= 1;
        }
        else if (getYearsOfWork() > 5 && getYearsOfWork() <= 10) {
            return gajiAccountant *= 1.5;
        }
        else {
            return gajiAccountant *= 2;
        }
    }

    @Override
    public String toString() {
        return String.format("%s \nRole: Salesman\nTotal Jam Kerja: %d \nFinal Salary: %.1f IDR\n", super.toString(), getTotalHoursWorked(), getFinalSalary());
    }
}
