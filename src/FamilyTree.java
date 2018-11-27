import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FamilyTree {
    /**
     * @input directory or filename of input file. This file contains the information necessary to build the child
     * parent relation. Throws exception if file is not found
     * @param familyFile
     * @throws Exception
     */

    //File inputFile = new File("C:/Users/Derek/IdeaProjects/FamilyTree/src/names.txt");
    BufferedReader inputScan;
    PrintWriter output;
    ArrayList<Person> people = new ArrayList<Person>();



    /**
     * Declare necessary variables to describe your Tree
     * Each Node in the Tree represents a person
     * You can declare other classes if necessary
     */


    public FamilyTree() {

    }

    public void buildFamilyTree(String familyFile) throws Exception {
        File inputFile = new File(familyFile);
        {
            try {
                inputScan = new BufferedReader(new FileReader(inputFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        String line;
        while ((line = inputScan.readLine()) != null) {
            String lineFixed = line.trim().replaceAll(" +", " ");
            String[] names = lineFixed.split("\\s+");
            if (names.length == 2) {
                for (int i = 0; i < names.length; i++) {
                    people.add(new Person((names[i])));
                }
            } else if (names.length > 2) {
                for (int i = 0; i < names.length; i++) {
                    if ((i == 0) || (i == 1)) {
                        if (!inTree(names[i])) {
                            people.add(new Person(names[i]));
                        }
                    } else if (i > 1) {
                        people.add(new Person(names[i], findByName(names[0]), findByName(names[1])));
                        findByName(names[0]).giveChild(findByName(names[i]));
                        findByName(names[1]).giveChild(findByName(names[i]));
                    }
                }
            }
        }
    }

    /**
     * @param queryFile
     * @param outputFile
     * @throws Exception
     * @input directory or filename of Query and Output.
     * queryFile contains the queries about the tree.
     * The output of this query should be written in file outputfile.
     */

    public void evaluate(String queryFile, String outputFile) throws Exception {
        /*
         * Traverse the tree to answer the queries
         * For information on queries take a look at the handout
         */
        BufferedReader Scanner = null;
        File query1 = new File(queryFile);
        File output1 = new File(outputFile);
        {
            try {
                Scanner = new BufferedReader(new FileReader(query1));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        output = new PrintWriter(output1, "UTF-8");
        String line;
        while ((line = Scanner.readLine()) != null) {
            String lineFixed = line.trim().replaceAll(" +", " ");
          //  System.out.println("\n\n!!!!!!!");
           // System.out.println(lineFixed);
            //System.out.println("!!!!!!\n\n");
            String[] query = lineFixed.split("\\s+");
            String nameOne = query[0];
            String nameTwo = query[1];
            boolean decendancy = decendants(nameOne, nameTwo);
            if (!decendancy) {
                ArrayList<Person> checkRelated = relation(nameOne, nameTwo);
                String[] arrayCheck = new String[checkRelated.size()];
                for (int i = 0; i < checkRelated.size(); i++) {
                    arrayCheck[i] = checkRelated.get(i).getName();
                }
                Arrays.sort(arrayCheck);
                if (checkRelated.isEmpty()) {
              //      System.out.println("unrelated");
                    output.println("unrelated");
                } else {
                    for (int i = 0; i < arrayCheck.length; i++) {
                //        System.out.print(checkRelated.get(i).getName() + " ");
                        output.print(arrayCheck[i] + " ");
                    }
                }
            }
        }
        output.close();
    }

    public boolean inTree(String name) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Person findByName(String name) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getName().equals(name)) {
                return people.get(i);
            }
        }
        return null;
    }


    public ArrayList<Person> getAllAncestors(Person person) {
        ArrayList<Person> allAncestors = new ArrayList<Person>();
        findAncestors(person, allAncestors);
        return allAncestors;
    }


    public void findAncestors(Person person, ArrayList<Person> allAncestors) {
        if (person.getParents().isEmpty()) {
            return;
        }
        if (allAncestors.contains(person.getParents().get(0))) {
            return;
        }
        if (allAncestors.contains(person.getParents().get(1))) {
            return;
        }
        allAncestors.add(person.getParents().get(0));
        allAncestors.add(person.getParents().get(1));
        findAncestors(person.getParents().get(0), allAncestors);
        findAncestors(person.getParents().get(1), allAncestors);
    }

    public boolean decendants(String name1, String name2) {
        if (getAllAncestors(findByName(name1)).contains(findByName(name2))) {
            //System.out.print("\n" + name1 + " is a descendant of " + name2);
            //System.out.println();
            output.print("\n" + name1 + " is a descendant of " + name2);
            output.println();
            return true;
        } else if (getAllAncestors(findByName(name2)).contains(findByName(name1))) {
            //System.out.print("\n" + name2 + " is a descendant of " + name1);
            //System.out.println();
            output.print("\n" + name2 + " is a descendant of " + name1);
            output.println();
            return true;
        } else {
            return false;
        }
    }


    public ArrayList<Person> relation(String nameOne, String nameTwo) {
        Person personOne = findByName(nameOne);
        Person personTwo = findByName(nameTwo);
        ArrayList<Person> personOneList = getAllAncestors(personOne);
        ArrayList<Person> personTwoList = getAllAncestors(personTwo);
        ArrayList<Person> common = new ArrayList<Person>();
        int twoSpot = 0;
        for (int i = 0; i < personOneList.size(); i++) {
            if (personTwoList.contains(personOneList.get(i))) {
                common.add(personOneList.get(i));
            }
        }
        /*int oneSpot = 0;
        for (int i = 0; i < personTwoList.size(); i++) {
            if (personTwoList.get(i) == personOneList.get(oneSpot)) {
                if(!common.contains(personOneList.get(i))) {
                    common.add(personOneList.get(i));
                    oneSpot++;
                }
            }
        } */
        return common;
    }
}
