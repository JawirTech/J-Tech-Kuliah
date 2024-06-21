abstract class Employee {
    //atribut2 yang dimiliki oleh Employee
    int employeeId;
    static int employeeCnt = 0;
    String name;
    double salary;

    protected Employee(String name, double salary){
        this.name = name;
        this.salary = salary;
    }
    //method2 abstract yang harus di override para subclass yang extend class Employee
    abstract double calculateSalary();
    abstract public String toString();

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getEmployeeCnt() {
        return employeeCnt;
    }

    public void setEmployeeCnt(int employeeCnt) {
        Employee.employeeCnt++;     //employeeCnt di increment tiap kali dipanggil
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setsalary(double salary) {
        this.salary = salary;
    }
}