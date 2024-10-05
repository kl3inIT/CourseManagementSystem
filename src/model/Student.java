package model;

public class Student {

    private String scode;  // Student Code
    private String name;
    private int byear;  // Birth Year

    public Student() {
    }

    public Student(String scode, String name, int byear) {
        this.scode = scode;
        this.name = name;
        this.byear = byear;
    }

    public String getScode() {
        return scode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return scode + "\\" + name + "\\" + byear;
    }

    public void displayStudentInfo() {
        System.out.println("Student code: " + scode);
        System.out.println("Student name: " + name);
        System.out.println("Birth year of student: " + byear);
    }
}
