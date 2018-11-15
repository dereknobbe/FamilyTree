import java.util.ArrayList;

public class Person {
    private String name;
    private Person mother;
    private Person father;
    private ArrayList<Person> children;

    public String getName() {
        return name;
    }

    public Person getMother() {
        return mother;
    }

    public Person getFather() {
        return father;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public ArrayList<Person> getChildren() {
        return children;
    }
}
