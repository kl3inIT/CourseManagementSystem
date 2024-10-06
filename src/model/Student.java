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

    @Override
    public String toString() {
        return scode + "\\" + name + "\\" + byear;
    }

    public void displayStudentInfo() {
        System.out.printf("%-15s %-25s %-10s\n",
                scode, name, byear);
    }
}
