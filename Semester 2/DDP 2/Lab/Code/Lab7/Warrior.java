public abstract class Warrior implements Comparable<Warrior>{
    //Inisiasi class>
    protected String name;
    protected int attack;
    protected int defense;
    protected int health;
    protected int numRevived;

    public Warrior(String name, int attack, int defense, int health) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
    }
    
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public int getNumRevived() {
        return numRevived;
    }
    
    public void attack(Warrior target) {
        System.out.println(this.name + " attacks " + target.name + " for " + this.attack + " damage.");
        target.takeDamage(this.attack);
    }

    public void takeDamage(int damage) {
        int reducedDamage = damage - this.defense;
        if (reducedDamage < 0) {
            reducedDamage = 0;
        }
        this.health -= reducedDamage;
        if (health >= 0) {
            System.out.println(this.name + " takes " + reducedDamage + " damage, remaining health: " + this.health);
        }
        else {
            System.out.println(this.name + " takes " + reducedDamage + " damage, remaining health: 0");
        }
    }

    public abstract void displayStats();

    public boolean isAlive() {
        return this.health > 0;
    }

    public void revive() {
        numRevived++;
    }

    public int compareTo(Warrior other) {   // compareTo method for sorting
        return this.name.compareTo(other.name);
    }

}