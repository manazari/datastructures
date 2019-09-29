// specificCount 
// clone
// test it all!!!

import java.lang.Math;
import java.util.ArrayList;

public class InheritA3Tester_Nazari {
    public static void main(String[] termArgs) {
        // ArrayList<Person> people = new ArrayList<Person>();

        // people.add(new Senior("Macho", "Broman"));
        // people.add(new Freshman("Mike", "Jahseh"));
        // people.add(new SFTeacher("Mary", "Ham", 2));

        // for (Person person : people) person.greet();

        BCPStudent a = new BCPStudent("Matt", "Eckstrom", 12);
        BCPStudent b = new BCPStudent("Matthew", "Nazari", 12);
        System.out.println(a.equals(b));
    }
}



abstract class Person {
    public final int SSN;
    private String firstName, lastName;
    private static int lastSSN = 333224444;
    public String greeting;

    static int familyCount = 0;

    public Person(String first, String last) {
        System.out.println("Person born");
        SSN = lastSSN;
        firstName = first;
        lastName = last;
        greeting = "";
        
        familyCount++;
    }

    public String fullName() { return firstName + " " + lastName; }
    public String toString() { return "SSN: " + SSN + " of size " + familyCount + ": " + fullName(); }
    
    public int compareTo(Person anotherPerson) {
        if (otherPerson instanceof Teacher && this instanceof Student) return 1;
        if (otherPerson instanceof Student && this instanceof Teacher) return -1;
        return this.fullName().compareTo(anotherPerson.fullName());
    }

    public int SSN() { return SSN; }

    public String[] getName() { return new String[]{firstName, lastName}; }

    public String getFQN() { return this.getClass().getName() + "." + this.hashCode() + "." + SSN; }

    public void clone(Person p) { this = p; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        return SSN == ((Person) o).SSN();
    }

    public abstract void greet();
}



class Student extends Person {
    private double gpa;
    private int grade;

    static int familyCount = 0;

    public Student(String first, String last, int gradeLevel) {
        super(first, last);
        System.out.println("Student born");
        gpa = 4.0;
        grade = gradeLevel;
        greeting = "Salutations!";

        familyCount++;
    }

    public void greet() {
        System.out.println(greeting + " I am " + getName()[0] + ", a student in grade " + grade);
    }
}



class BCPStudent extends Student {
    private long apTestTakens;

    static int familyCount = 0;

    public BCPStudent(String first, String last, int gradeLevel) {
        super(first, last, gradeLevel);
        System.out.println("BCP Student born");
        apTestTakens = 0;
        greeting = "Yo dawggie!";

        familyCount++;
    }

    public void returnTest(float percent) {
        if (percent < 92.5) {
            System.out.println(getName()[0] + ": What!?? No... this can't be!!!");
        }
    }

    public void takeApTest() {
        apTestTakens ++;
        if (Math.random() >= 0.3) System.out.println(getName()[0] + " recieved a 5 and he is satisfied.");
        else System.out.println(getName()[0] + " recieved a 4 and is crying in his bathroom.");
    }

    public void greet() {
        System.out.println(greeting + " My names-like-" + getName()[0] + "... GO BELLS!");
    }
}



class Senior extends Student {
    static int familyCount = 0;

    public Senior(String first, String last) {
        super(first, last, 12);
        System.out.println("Senior born");
        greeting = "Wassuh!";

        familyCount++;
    }
    
    public void greet() {
        System.out.println(greeting + " I'm a rightous senior named " + getName()[0]);
    }
}



class Freshman extends Student {
    static int familyCount = 0;

    public Freshman(String first, String last) {
        super(first, last, 9);
        System.out.println("Freshman born");
        greeting = "Oh, um.. er... hello!";
        
        familyCount++;
    }
    
    public void greet() {
        System.out.println(greeting + " I'm a freshman. You can call me " + getName()[0]  + " I guess.");
    }
}



class Teacher extends Person {
    private int classesTeaching;
    private int yearsTeaching;

    static int familyCount = 0;

    public Teacher(String first, String last, int classes) {
        super(first, last);
        System.out.println("Teacher born");
        classesTeaching = classes;
        yearsTeaching = 0;
        greeting = "Why, greetings!";

        familyCount++;
    }
    
    public void greet() {
        System.out.println(greeting + " I am a teacher and I mean business!");
    }
}



class SFTeacher extends Teacher {
    static int familyCount = 0;

    public SFTeacher(String first, String last, int classes) {
        super(first, last, classes);
        System.out.println("SF Teacher born");
        greeting = "Go Lancers!";

        familyCount++;
    }
    
    public void greet() {
        System.out.println(greeting + " I'm a teacher and I foolishly think Bell sucks.");
    }
}