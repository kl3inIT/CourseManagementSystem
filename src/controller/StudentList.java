package controller;

import model.Student;
import util.Validation;
import java.io.*;
import java.time.LocalDate;

public class StudentList {

    private final MyLinkedList<Student> studentList = new MyLinkedList<>();

    public StudentList() {
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
            System.out.println("Load data successfully!");
        } catch (IOException e) {
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
            byear = Validation.getAnInteger("Enter birth year: ",
                    "Please input from 1990 to 2006!", 1990, LocalDate.now().getYear() - 18);
        System.out.println("Input student successfully!");
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
            return;
        }
        Node<Student> cur = studentList.head;
        System.out.printf("%-15s %-25s %-10s\n",
                "Student Code", "Student Name", "Birth Year");
        System.out.println("----------------------------------------------------");
        while (cur != null) {
            cur.data.displayStudentInfo();
            cur = cur.next;
        }
    }

    //2.4
    public void saveToFile(boolean isLoadData) {
        if (studentList.isEmpty()) {
            // vi list trong ma save vao file thi la clear file =))
            System.err.println("List is empty, can not save file");
            return;
        }
        if (!isLoadData) {
            // chua load ma save thi no xoa cai data cu di va thay vao cai data moi dang co trong list
            System.err.println("Please load data before save to file");
            return;
        }
        try {
            FileWriter fw = new FileWriter("students.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            Node<Student> cur = studentList.head;
            while (cur != null) {
                String data = cur.data.toString();
                bw.write(data);
                bw.newLine();
                cur = cur.next;
            }
            System.out.println("Save data successfully!");
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.err.println("ERROR");
        }
    }

    public Node<Student> searchByScode(String scode) {
        if (studentList.isEmpty()) {
            return null;
        }
        Node<Student> current = studentList.head;
        while (current != null) {
            if (current.data.getScode().equalsIgnoreCase(scode)) {
                return current;
            }
            current = current.next;
        }
        return null; // Not found
    }
    

    //2.5
    public void searchStudentByCode() {
        String scode = Validation.getValidString("Input student ID(HAxxxxxx, HExxxxxx, HSxxxxxx): ",
                "The format of id is HAXXXXXX, HEXXXXXX, HSXXXXXX", "H[ASE]\\d{6}");
        Node<Student> student = searchByScode(scode);
        if (student != null) {
            System.out.println("\nHere is student you want to search: ");
            student.data.displayStudentInfo();
        } else {
            System.err.println("Course with code " + scode + " NOT FOUND!");
        }
    }

    //2.6
    public void deleteByScode(RegisterList registerList) {
        String scodeDelete = Validation.getValidString("Input student ID(HAxxxxxx, HExxxxxx, HSxxxxxx): ",
                "The format of id is HAXXXXXX, HEXXXXXX, HSXXXXXX", "H[ASE]\\d{6}");
        // delete into register list first
        registerList.deleteRegisterByScode(scodeDelete);
        // delete into studentList
        Node<Student> result = searchByScode(scodeDelete);
        if (result != null) {
            studentList.delete(result);
            System.out.println("Deteletion successfully!");
        } else {
            System.err.println("Ccode is not Exist");
        }
    }

    //2.7
    public void searchByName() {
        String nameSearch = Validation.getString("Enter name searching: ", "Wrong input!");
        Node<Student> temp = studentList.head;
        boolean foundStudent = false;  // Track if a student is found

        while (temp != null) {
            if (temp.data.getName().toUpperCase().contains(nameSearch.toUpperCase())) {
                if (!foundStudent) {
                    // Print the message once when the first matching student is found
                    System.out.println("Here is the student that you want to search:");
                    foundStudent = true;
                }
                temp.data.displayStudentInfo();  // Display the student info
            }
            temp = temp.next;
        }

        if (!foundStudent) {
            System.err.println("Not found!");
        }
    }

    //2.8
    public void searchCourseByCcode(RegisterList registerList, CourseList courseList) {
        String scode = Validation.getValidString("Input student ID(HAxxxxxx, HExxxxxx, HSxxxxxx): ",
                "The format of id is HAXXXXXX, HEXXXXXX, HSXXXXXX", "H[ASE]\\d{6}");
        Node<Student> student = searchByScode(scode);
        if (student != null) {
            System.out.println("\nHere is student you want to seach: ");
            student.data.displayStudentInfo();
            System.out.println("\nHere are the courses this student has register in: ");
            registerList.printOutCourseByScode(scode, courseList); 
        } else {
            System.err.println("Student with code " + scode + " NOT FOUND!");
        }

    }
}
