package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import model.Course;
import model.Register;
import model.Student;
import util.Validation;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterList {

    private final MyLinkedList<Register> registerList = new MyLinkedList<>();

    public RegisterList() {
    }

    //3.1
    public void loadData() {
        try {
            FileReader fr = new FileReader("registerings.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line != null) {
                    String[] word = line.split("\\\\");
                    String ccode = word[0];
                    String scode = word[1];
                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(word[2]);
                    double mark = Double.parseDouble(word[3]);
                    if (!isExistRegister(ccode, scode)) {
                        Register register = new Register(ccode, scode, date, mark);
                        registerList.addLast(register);
                    }
                }
            }
            System.out.println("Load data successfully!");
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    private boolean isExistRegister(String ccode, String scode) {
        Node<Register> current = registerList.head;
        while (current != null) {
            if (current.data.getCcode().equalsIgnoreCase(ccode)
                    && current.data.getScode().equalsIgnoreCase(scode)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    //3.2
    public void registerCourse(CourseList courseList, StudentList studentList) {
        String ccode = Validation.getValidString("Enter Course Code: ",
                "The Format code is CCxxxx with x is number", "^CC\\d{4}$");
        String scode = Validation.getValidString("Input student ID(HAxxxxxx, HExxxxxx, HSxxxxxx): ",
                "The format of id is HAXXXXXX, HEXXXXXX, HSXXXXXX", "H[ASE]\\d{6}");
        if (searchByCcode(ccode, courseList) == null || searchByScode(scode, studentList) == null) {
            System.err.println("Course code or student code is not exist");
            return;
        }
        Date date = new Date();
        if (courseList.searchByCcode(ccode).data.getRegistered() < courseList.searchByCcode(ccode).data.getSeats()) {
            Register register = new Register(ccode, scode, date, 0);
            registerList.addFirst(register);
            System.out.println("Registering successfully!");
            courseList.searchByCcode(ccode).data.setRegistered();
        } else {
            System.err.println("Seats are full!");
        }
    }

    private Node<Course> searchByCcode(String ccode, CourseList courseList) {
        return courseList.searchByCcode(ccode);
    }

    private Node<Student> searchByScode(String scode, StudentList studentList) {
        return studentList.searchByScode(scode);
    }

    //3.3
    public void display() {
        if (registerList.isEmpty()) {
            System.err.println("Course list is empty");
        }
        System.out.printf("%-15s %-15s %-15s %-10s %-10s\n",
                "Course Code", "Student Code", "Register Date", "Mark", "Status");
        System.out.println("--------------------------------------------------------------------------");
        Node<Register> cur = registerList.head;
        while (cur != null) {
            cur.data.displayRegistrationInfo();
            cur = cur.next;
        }
    }

    //3.4
    public void saveToFile(boolean isLoadData) {
        if (registerList.isEmpty()) {
            // vi list empty ma save vao file thi la clear file =))
            System.err.println("List is empty, can not save file");
            return;
        }
        if (!isLoadData) {
            // chua load ma save thi no xoa cai data cu di va thay vao cai data moi dang co trong list
            System.err.println("Please load data before save to file");
            return;
        }
        try {
            FileWriter fw = new FileWriter("registerings.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            Node<Register> cur = registerList.head;
            while (cur != null) {
                String data = cur.data.toString();
                bw.write(data);
                bw.newLine();
                cur = cur.next;
            }
            System.out.println("Save Date successfully!");
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.err.println("ERROR");
        }
    }

    //3.5
    public void sort() {
        if (registerList.isEmpty()) {
            System.out.println("Register List is empty.");
            return;
        }
        System.out.println("\nHere is the Register List after sorting ascending by ccode: ");
        for (Node<Register> i = registerList.head; i != registerList.tail; i = i.next) {
            Node<Register> pos = i;
            for (Node<Register> j = i.next; j != registerList.tail.next; j = j.next) {
                if (pos.data.getCcode().compareTo(j.data.getCcode()) > 0) {
                    pos = j;
                }
            }
            registerList.swap(i, pos);
        }
        display();
        System.out.println("\nHere is the Register List after sorting ascending by scode: ");
        for (Node<Register> i = registerList.head; i != registerList.tail; i = i.next) {
            Node<Register> pos = i;
            for (Node<Register> j = i.next; j != registerList.tail.next; j = j.next) {
                if (pos.data.getScode().compareTo(j.data.getScode()) > 0) {
                    pos = j;
                }
            }
            registerList.swap(i, pos);
        }
        display();
    }

    //3.6
    public void updateMark() {
        String scode, ccode;
        while (true) {
            ccode = Validation.getValidString("Enter Course Code: ",
                    "The Format code is CCxxxx with x is number", "^CC\\d{4}$");
            scode = Validation.getValidString("Input student ID(HAxxxxxx, HExxxxxx, HSxxxxxx): ",
                    "The format of id is HAXXXXXX, HEXXXXXX, HSXXXXXX", "H[ASE]\\d{6}");
            Node<Register> register = searchByScodeAndCcode(scode, ccode);
            if (register != null) {
                int mark = Validation.getAnInteger("Enter mark: ", "Invalid!", 0, 10);
                register.data.setMark(mark);
                System.out.println("Update mark successfully!");
                break;
            } else {
                System.out.println("Not found!");
            }
        }
    }

    private Node<Register> searchByScodeAndCcode(String scode, String ccode) {
        Node<Register> current = registerList.head;
        while (current != null) {
            if (current.data.getScode().equalsIgnoreCase(scode)
                    && current.data.getCcode().equalsIgnoreCase(ccode)) {
                return current;
            }
            current = current.next;
        }
        return null; // Not found
    }

    public void deleteRegisterByCcode(String ccode) {
        Node<Register> current = registerList.head;
        while (current != null) {
            Node<Register> nextNode = current.next;  // Save reference to the next node before deletion
            if (current.data.getCcode().equalsIgnoreCase(ccode)) {
                registerList.delete(current);
            }
            current = nextNode; // Move to the next node
        }
    }

    public void deleteRegisterByScode(String scode) {
        Node<Register> current = registerList.head;
        while (current != null) {
            Node<Register> nextNode = current.next;
            if (current.data.getScode().equalsIgnoreCase(scode)) {
                registerList.delete(current);
            }
            current = nextNode;
        }
    }

    public void printOutCourseByScode(String scode, CourseList courseList) {
        Node<Register> cur = registerList.head;
        while (cur != null) {
            if (cur.data.getScode().equalsIgnoreCase(scode)) {
                Node<Course> result = courseList.searchByCcode(cur.data.getCcode());
                if (result != null) {
                    result.data.displayCourseInfo();
                }
            }
            cur = cur.next;
        }
    }

    public void printOutStudentByCcode(String ccode, StudentList studentList) {
        Node<Register> cur = registerList.head;
        while (cur != null) {
            if (cur.data.getCcode().equalsIgnoreCase(ccode)) {
                Node<Student> result = studentList.searchByScode(cur.data.getScode());
                if (result != null) {
                    result.data.displayStudentInfo();
                }
            }
            cur = cur.next;
        }
    }
}
