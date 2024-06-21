package assignments.assignment3.systemCLI;

import java.util.Scanner;

public abstract class UserSystemCLI {          //class ini adalah class abstract yang akan diturunkan ke class CustomerSystemCLI dan AdminSystemCLI
    protected Scanner input = new Scanner(System.in);
    public void run() {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int choice = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(choice);
        }
    }
    abstract void displayMenu();                //2 method abstract method yang perlu di implementasi di class2 turunan
    abstract boolean handleMenu(int choice);
}