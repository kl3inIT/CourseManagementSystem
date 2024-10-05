package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import model.Student;
import util.Validation;

public class StudentList {

    private final MyLinkedList<Student> studentList = new MyLinkedList<>();

    public StudentList() {
    }

    public MyLinkedList<Student> getStudentList() {
        return studentList;
    }

    //2.1
    public void loadData() {
        try {
            FileReader fr = new FileReader("students.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line != null) {
                    String[] word = line.split("\\\\");
                    String scode = word[0];
                    String name = word[1];
                    int byear = Integer.parseInt(word[2]);
                    if (searchByScode(scode) == null) {
                        Student newStudent = new Student(scode, name, byear);
                        studentList.addLast(newStudent);
                    }

                }
            }
        } catch (Exception e) {
            System.err.println("File is empty");
        }
    }

    public Student inputStudent() {
        String scode;
        while (true) {
            scode = Validation.getValidString("Input student ID(HAxxxxxx, HExxxxxx, HSxxxxxx): ",
                    "The format of id is HAXXXXXX, HEXXXXXX, HSXXXXXX", "H[ASE]\\d{6}");
            if (searchByScode(scode) != null) {
                System.err.println("Student Code is already exist. Please input another one.");
            } else {
                break;
            }
        }
        String name = Validation.getString("Enter student name: ", "Wrong input!");
        int byear;
        while (true) {
            byear = Validation.getAnInteger("Enter birth year: ",
                    "Please input from 1900 to current year!", 2000, LocalDate.now().getYear());
            int age = LocalDate.now().getYear() - byear;
            if (age >= 18) {
                break;
            }
        }
        return new Student(scode, name, byear);
    }

    //2.2
    public void addLast() {
        studentList.addLast(inputStudent());
    }

    //2.3
    public void display() {
        if (studentList.isEmpty()) {
            System.err.println("Course list is empty");
        }
        Node<Student> cur = studentList.head;
        while (cur != null) {
            System.out.println("========================");
            cur.data.displayStudentInfo();
            System.out.println("========================");
            cur = cur.next;
        }
    }

    //2.4
    public void saveToFile() {
        try {
            FileReader fr = new FileReader("students.txt");
            BufferedReader br = new BufferedReader(fr);
            Node<Student> cur = studentList.head;
            while (cur != null) {
                String code = cur.data.getScode();
                boolean isExist = false;
                String line;
                while ((line = br.readLine()) != null) {
                    if (line != null) {
                        String[] word = line.split("\\\\");
                        if (word[0].equalsIgnoreCase(code)) {
                            isExist = true;
                            break;
                        }
                    }
                }
                if (!isExist) {
                    FileWriter fw = new FileWriter("students.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String data = cur.data.toString();
                    bw.write(data);
                    bw.newLine();
                    bw.close();
                    fw.close();
                }
                cur = cur.next;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //2.5
    public Node<Student> searchByScode(String scode) {
        Node<Student> current = studentList.head;
        while (current != null) {
            if (current.data.getScode().equalsIgnoreCase(scode)) {
                return current;
            }
            current = current.next;
        }
        return null; // Not found
    }

    //2.6
    public void deleteByScode(String scode) {
        Node<Student> result = searchByScode(scode);
        if (result != null) {
            studentList.delete(result);
        } else {
            System.err.println("Ccode is not Exist");
        }
    }

    //2.7
    public void searchByName() {
        String nameSearch = Validation.getString("Enter name searching: ", "Wrong input!");
        Node<Student> temp = studentList.head;
        boolean ok = true;
        while (temp != null) {
            if (temp.data.getName().toUpperCase().equalsIgnoreCase(nameSearch.toUpperCase())) {
                temp.data.displayStudentInfo();
                ok = false;
            }
            temp = temp.next;
        }

        if (ok) {
            System.err.println("Not found!");
        }
    }

    //2.8
}