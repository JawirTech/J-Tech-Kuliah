import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WarriorList<E> {
    private List<Warrior> warriors = new LinkedList<>(); // TODO: Instansiasi dengan concrete class yang merupakan List
    private Queue<Warrior> fallenWarriors = new LinkedList<>(); // TODO: Instansiasi dengan concrete class yang merupakan Queue

    public void addWarrior(Warrior warrior) {
        warriors.add(warrior);
    }

    public void removeWarrior(Warrior warrior) {        //remover warrior apa yang ingin di buang
        for (Warrior w : warriors) {
            if (w.equals(warrior)) {
                warriors.remove(w);
                break;
            }
        }
    }

    public List<Warrior> getWarriors() {
        return warriors;
    }

    public void addFallenWarrior(Warrior warrior) {
        fallenWarriors.add(warrior);
    }

    public Queue<Warrior> getFallenWarriors() {
        return fallenWarriors;
    }

    public void removeFallenWarrior(Warrior warrior) {  //remove tapi untuk dallen warrior 
        for (Warrior w : fallenWarriors) {
            if (w.equals(warrior)) {
                fallenWarriors.remove(w);
                break;
            }
        }
    }
}