package controller;

import model.Course;
import model.Register;
import model.Student;
import util.Validation;
import java.io.*;
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
                    if (searchByCcode(ccode) == null && searchByScode(scode) == null) {
                        Register register = new Register(ccode, scode, date, mark);
                        registerList.addLast(register);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    private Node<Register> searchByScode(String scode) {
        Node<Register> current = registerList.head;
        while (current != null) {
            if (current.data.getScode().equalsIgnoreCase(scode)) {
                return current;
            }
            current = current.next;
        }
        return null; // Not found
    }

    private Node<Register> searchByCcode(String ccode) {
        Node<Register> current = registerList.head;
        while (current != null) {
            if (current.data.getCcode().equalsIgnoreCase(ccode)) {
                return current;
            }
            current = current.next;
        }
        return null; // Not found
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
                "Course Code", "Student Code", "Birth Date", "Mark", "Status");
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
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.err.println("ERROR");
        }
    }

    //3.5
    public void sort() {
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
            if (current.data.getCcode().equalsIgnoreCase(ccode)) {
                registerList.delete(current);
            }
            current = current.next;
        }
    }

    public void deleteRegisterByScode(String scode) {
        Node<Register> current = registerList.head;
        while (current != null) {
            if (current.data.getScode().equalsIgnoreCase(scode)) {
                registerList.delete(current);
            }
            current = current.next;
        }
    }

    public String findScodeByCcode(String ccode) {
        Node<Register> current = registerList.head;
        while (current != null) {
            if (current.data.getCcode().equalsIgnoreCase(ccode)) {
                return current.data.getScode();
            }
            current = current.next;
        }
        return null;
    }

    public String findCcodeByScode(String scode) {
        Node<Register> current = registerList.head;
        while (current != null) {
            if (current.data.getScode().equalsIgnoreCase(scode)) {
                return current.data.getCcode();
            }
            current = current.next;
        }
        return null;
    }
}
