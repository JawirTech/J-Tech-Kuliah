public class Engineer extends Employee{
    private int totalProject;
    private double projectFee;

    public Engineer(String name, int yearsOfWork, double baseSalary, int totalProject, double projectFee) {
        super(name, yearsOfWork, baseSalary);
        this.totalProject = totalProject;
        this.projectFee = projectFee;
    }


    //bikin no-arg constructor, getter, setter
    public int getTotalProject() {
        return totalProject;
    }

    public void setTotalProject(int totalProject) {
        this.totalProject = totalProject;
    }

    public double getProjectFee() {
        return projectFee;
    }

    public void setProjectfee(double projectFee) {
        this.projectFee = projectFee;
    }

    double CalculateSalary() {
        double gajiEngineer = super.CalculateSalary() + (getTotalProject() * getProjectFee());
        if (getYearsOfWork() <= 5) {
            return gajiEngineer *= 1;
        }
        else if (getYearsOfWork() > 5 && getYearsOfWork() <= 10) {
            return gajiEngineer *= 1.5;
        }
        else {
            return gajiEngineer *= 2;
        }
    }

    @Override
    public String toString() {
        return String.format("%s \nRole: Engineer\nBanyak Project: %d \nFinal Salary: %.1f IDR\n", super.toString(), getTotalProject(), getFinalSalary());
    }
}
