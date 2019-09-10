import java.lang.Math;
import java.util.ArrayList;

public class InheritA2Tester_Nazari {
    public static void main(String[] termArgs) {
        ArrayList<Person> people = new ArrayList<Person>();

        people.add(new Senior("Macho", "Broman"));
        people.add(new Freshman("Mike", "Jahseh"));
        people.add(new SFTeacher("Mary", "Ham", 2));

        for (Person person : people) person.greet();
    }
}

abstract class Person {
    public final int SSN;
    private String firstName, lastName;
    private static int lastSSN = 333224444;
    public String greeting;

    public Person(String first, String last) {
        System.out.println("Person born");
        SSN = ++lastSSN;
        firstName = first;
        lastName = last;
        greeting = "";
    }

    public String[] getName() {
        return new String[]{firstName, lastName};
    }

    public abstract void greet();
}

class Student extends Person {
    private double gpa;
    private int grade;
    public Student(String first, String last, int gradeLevel) {
        super(first, last);
        System.out.println("Student born");
        gpa = 4.0;
        grade = gradeLevel;
        greeting = "Salutations!";
    }

    public void greet() {
        System.out.println(greeting + " I am " + getName()[0] + ", a student in grade " + grade);
    }
}

class BCPStudent extends Student {
    private long apTestTakens;

    public BCPStudent(String first, String last, int gradeLevel) {
        super(first, last, gradeLevel);
        System.out.println("BCP Student born");
        apTestTakens = 0;
        greeting = "Yo dawggie!";
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
    public Senior(String first, String last) {
        super(first, last, 12);
        System.out.println("Senior born");
        greeting = "Wassuh!";
    }
    
    public void greet() {
        System.out.println(greeting + " I'm a rightous senior named " + getName()[0]);
    }
}

class Freshman extends Student {
    public Freshman(String first, String last) {
        super(first, last, 9);
        System.out.println("Freshman born");
        greeting = "Oh, um.. er... hello!";
    }
    
    public void greet() {
        System.out.println(greeting + " I'm a freshman. You can call me " + getName()[0]  + " I guess.");
    }
}


class Teacher extends Person {
    private int classesTeaching;
    private int yearsTeaching;

    public Teacher(String first, String last, int classes) {
        super(first, last);
        System.out.println("Teacher born");
        classesTeaching = classes;
        yearsTeaching = 0;
        greeting = "Why, greetings!";
    }
    
    public void greet() {
        System.out.println(greeting + " I am a teacher and I mean business!");
    }
}

class SFTeacher extends Teacher {
    public SFTeacher(String first, String last, int classes) {
        super(first, last, classes);
        System.out.println("SF Teacher born");
        greeting = "Go Lancers!";
    }
    
    public void greet() {
        System.out.println(greeting + " I'm a teacher and I foolishly think Bell sucks.");
    }
}