public class Salesman extends Employee {
    private double totalSales;
    private double commissionFee;

    public Salesman(String name, int yearsOfWork, double baseSalary, int totalSales, double commissionFee) {
        super(name, yearsOfWork, baseSalary);
        this.totalSales = totalSales;
        this.commissionFee = commissionFee;
    }

     //bikin no-arg constructor, getter, setter
    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public double getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(int commissionFee) {
        this.commissionFee = commissionFee;
    }

    double CalculateSalary() {
        double gajiSalesman = super.CalculateSalary() + (getTotalSales() / getCommissionFee());
        if (getYearsOfWork() <= 5) {
            return gajiSalesman *= 1;
        }
        else if (getYearsOfWork() > 5 && getYearsOfWork() <= 10) {
            return gajiSalesman *= 1.5;
        }
        else {
            return gajiSalesman *= 2;
        }
    }

    @Override
    public String toString() {
        return String.format("%s \nRole: Salesman\nBanyak Sales: %.1f \nFinal Salary: %.1f IDR\n", super.toString(), getTotalSales(), getFinalSalary());
    }
}
