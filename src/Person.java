import java.util.ArrayList;

public class Person {
    private String name;
    private ArrayList<Person> children = new ArrayList<Person>();
    private ArrayList<Person> parents = new ArrayList<Person>();

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, Person parent1, Person parent2) {
        this.name = name;
        this.parents.add(parent1);
        this.parents.add(parent2);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Person> getParents() {
        return parents;
    }

    public ArrayList<Person> getChildren() {
        return children;
    }

    public void giveChild(Person child) {
        this.children.add(child);
    }
}
