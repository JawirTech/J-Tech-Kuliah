import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Battlefield {
    //  TODO
    // inisiasi generics yang digunakan
    private Scanner scanner = new Scanner(System.in);
    private WarriorList<Warrior> warriors = new WarriorList<>();
    private WarriorList<Warrior> fallenWarriors = new WarriorList<>();

    // TODO

    public void runMenu() {

        while (true) {
            // Menu utama
            System.out.println("\nWelcome to the Battlefield Simulator!");
            System.out.println("1. Add Warrior");
            System.out.println("2. Display Warriors");
            System.out.println("3. Simulate Battle");
            System.out.println("4. Revive Warrior");
            System.out.println("5. Exit");
            System.out.println();
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();
            //implement semua fungsi yang dibuat
            switch (option) {
                case 1:
                    addWarrior();
                    break;
                case 2:
                    displayWarriors();
                    break;
                case 3:
                    simulateBattle();
                    break;
                case 4:
                    reviveWarrior();
                    break;
                case 5:
                    System.out.println("--------Game Over--------");
                    System.out.println("░░░░░░░█▐▓▓░████▄▄▄█▀▄▓▓▓▌█");
                    System.out.println("░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█");
                    System.out.println("░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█");
                    System.out.println("░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█");
                    System.out.println("░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█");
                    System.out.println("▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌");
                    System.out.println("█▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█");
                    System.out.println("█▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█");
                    System.out.println("▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌");
                    System.out.println("▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌");
                    System.out.println("█▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█");
                    return;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, 3, 4, or 5.");
            }
        }
    }

    // Method untuk tambah warrior ke Arraylist
    private void addWarrior() {
        // Minta tipe warrior
        System.out.println();
        System.out.println("Select type of warrior:");
        System.out.println("1. Tank");
        System.out.println("2. Archer");
        System.out.println("3. Mage");
        int type = getValidInt("Choose an option: ", 1, 3);     //mengecek inputan user apakah masuk dalam range 1-3

        System.out.print("Enter Warrior name: ");
        String name = scanner.nextLine().trim();

        int health = getValidInt("Enter Warrior health (500 to 5000): ", 500, 5000);
        int attack = getValidInt("Enter Warrior attack (30 to 1000): ", 30, 1000);
        int defense = getValidInt("Enter Warrior defense (0 to 250): ", 0, 250);

        Warrior warrior = null;

        // Tambah validasi sesuai tipe warrior
        if (type == 1) {    //ketika di pilih user, maka akan disuruh memasukkan angka 0-500 untuk shield khusus untuk Tank
            int shield = getValidInt("Enter shield strength (0 to 500): ", 0, 500);
            warrior = new Tank(name, attack, defense, health, shield);      //warrior dibentuk dengan tipe Tank

        } else if (type == 2) {     //ketika di pilih user, maka akan disuruh memasukkan angka 0.0-1.0 untuk crit chance dan 1.0-5.0 untuk crit dmg untuk karakter khusus Archer
            double criticalRate = getValidDouble("Enter critical rate (0.0 to 1.0): ", 0.0, 1.0);
            double criticalDamage = getValidDouble("Enter critical damage multiplier (1.0 to 5.0): ", 1.0, 5.0);
            warrior = new Archer(name, attack, defense, health, criticalRate, criticalDamage);  //warrior dibentuk dengan tipe Archer

        } else if (type == 3) {     //ketika di pilih user untuk karakter khusus Mage
            warrior = new Mage(name, attack, defense, health);  //warrior dibentuk dengan tipe Mage
        }
        warriors.addWarrior(warrior);       //memasukkan warrior tadi ke list
        System.out.println(String.format("\n%s has been added to the battle.", name));
    }

    // Method untuk validasi int
    private int getValidInt(String prompt, int min, int max) {
        int input;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a valid number!");
                System.out.print(prompt);
                scanner.next();
            }
            input = scanner.nextInt();
            scanner.nextLine();
            if (input < min || input > max) {
                System.out.println("Please enter a value between " + min + " and " + max + ".");
            }
        } while (input < min || input > max);
        return input;
    }

    // Method untuk validasi double
    private double getValidDouble(String prompt, double min, double max) {
        double input;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextDouble()) {
                System.out.println("That's not a valid number!");
                System.out.print(prompt);
                scanner.next();
            }
            input = scanner.nextDouble();
            scanner.nextLine();
            if (input < min || input > max) {
                System.out.println("Please enter a value between " + min + " and " + max + ".");
            }
        } while (input < min || input > max);
        return input;
    }

    // Method untuk display semua warrior
    public void displayWarriors() {
        // TODO 
        // Sort menggunakan collections berdasarkan nama warrior
        Collections.sort(warriors.getWarriors(), new Comparator<Warrior>() {        //sort semua anggota dari list warrior berdasarkan nama
            public int compare(Warrior w1, Warrior w2) {
                return w1.getName().compareTo(w2.getName());
            }
        });
        System.out.println("\nCurrent warriors in the battlefield:");
        System.out.println(
                "+------------+-----------------+---------+---------+---------+---------+------------+------------+");
        System.out.printf("| %-10s | %-15s | %-7s | %-7s | %-7s | %-7s | %-10s | %-10s |\n", "Type", "Name", "Attack",
                "Defense", "Health", "Shield", "Crit Rate", "Crit Dmg");
        System.out.println(
                "+------------+-----------------+---------+---------+---------+---------+------------+------------+");
        // TODO
        // Print semua warrior di dalam List
        for (Warrior w : warriors.getWarriors()) {  //display status semua kaarakter yang sudah di sorting
            w.displayStats();
            System.out.println("+------------+-----------------+---------+---------+---------+---------+------------+------------+");
        }
    }

    // Method untuk simulasi attack
    public void simulateBattle() {      
        List<Warrior> warriorsBattle = warriors.getWarriors();
        if (warriorsBattle.size() >= 2) {       //fitur ini akan jalan hanya jika warrior minimal ada 2
            System.out.println("Select the attacking warrior:");
            // TODO
            // Print setiap warrior di List dan lakukan validasi index attacker
            for (Warrior w : warriorsBattle) {  //list semua warrior
                System.out.println(warriorsBattle.indexOf(w) + 1 + ". " + w.getName());
            }
            int attackDisplay = getValidInt("\nChoose a warrior: ", 1, warriorsBattle.size());
            int attackerIndex = attackDisplay - 1;

            System.out.println("Select the defending warrior:");
            // TODO
            // Print setiap defender di List dan lakukan validasi index defender
            // Pastikan index defender dan attacker berbeda
            for (Warrior w : warriorsBattle) {      //list semua warrior kecuali yang sudah attack
                if (warriorsBattle.indexOf(w) != attackerIndex) {
                    System.out.println(warriorsBattle.indexOf(w) + 1 + ". " + w.getName());
                }
                else {
                    continue;
                }
            }
            int defenderDisplay;
            while (true) {      //hanya bisa defend dengan yang tidak attack
                defenderDisplay = getValidInt("\nChoose a warrior: ", 1, warriorsBattle.size());
                if (defenderDisplay == attackDisplay) {
                    System.out.println("You cannot attack yourself!");
                }
                else {
                    break;
                }
            }
            int defenderIndex = defenderDisplay - 1;
            // TODO
            // Simulasi attacking dan defending beserta outputnya  
            warriorsBattle.get(attackerIndex).attack(warriorsBattle.get(defenderIndex));        //menjalankan attack
            if (warriorsBattle.get(defenderIndex).isAlive()) {      //ketika karakter yang diserang mempunyai darah lebih, maka dia masih hidup dan masih ada di list
                System.out.println(warriorsBattle.get(defenderIndex).getName() + " survived the attack with " + warriorsBattle.get(defenderIndex).getHealth() + " health remaining.");
            }
            else {      ////ketika karakter yang diserang kehabisan darah, maka dia mati dan keluar dari list warrior dan pindah ke fallen
                System.out.println(warriorsBattle.get(defenderIndex).getName() + " has fallen in battle.\n" + warriorsBattle.get(defenderIndex).getName() + " has been removed from the battle.");
                fallenWarriors.addFallenWarrior(warriorsBattle.get(defenderIndex));
                warriors.removeWarrior(warriorsBattle.get(defenderIndex));
            }
        }
        else {  //kekuarangan warrior
            System.out.println("Not enough warriors for a battle. Please add more warriors.");
        }
    }

    // Method untuk membangkitkan warrior
    public void reviveWarrior() {
        // TODO: Implementasi cara membangkitkan warrior
        if (fallenWarriors.getFallenWarriors().size() > 0) {    //perlu ada karakter yang mati dan masuk ke list fallen dahulu supaya fitur jalan
            for (Warrior fw : fallenWarriors.getFallenWarriors()) {
                if (fw.getNumRevived() < 4) {   //revive satu karakter maksimum 3 kali
                    fw.revive();
                    System.out.println("Reviving " + fw.getName() + "...\nSucessfully revived " + fw.getName() + "!");
                    warriors.addWarrior(fw);    //balik lagi ke list warrior
                    fallenWarriors.removeFallenWarrior(fw); // keluar dari list fallen
                    break;
                }
                else {
                    System.out.println(fw.getName() + " cannot be revived anymore.");
                    continue;
                }
            }
        }
        else {
            System.out.println("There are currently no warriors to revive.");
        }
    }

    public static void main(String[] args) {
        Battlefield battlefield = new Battlefield();
        battlefield.runMenu();
    }
}