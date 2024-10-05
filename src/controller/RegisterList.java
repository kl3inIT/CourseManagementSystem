package controller;

import model.Course;
import model.Register;
import model.Student;
import util.Validation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RegisterList {

    private final MyLinkedList<Register> registerList = new MyLinkedList<>();

    public RegisterList() {
    }

    public MyLinkedList<Register> getRegisterList() {
        return registerList;
    }

    //3.1
    public void loadData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("registerings.txt"));
            String line;
            while ((line = br.readLine()) != null) {
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
            br.close();
        } catch (Exception e) {
            System.err.println("Error while loading data: " + e.getMessage());
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
        String scode, ccode;
        while (true) {
            ccode = Validation.getValidString("Enter Course Code: ",
                    "The Format code is CCxxxx with x is number", "^CC\\d{4}$");
            scode = Validation.getValidString("Input student ID(HAxxxxxx, HExxxxxx, HSxxxxxx): ",
                    "The format of id is HAXXXXXX, HEXXXXXX, HSXXXXXX", "H[ASE]\\d{6}");
            if (searchByCcode(ccode, courseList) != null && searchByScode(scode, studentList) != null) {
                break;
            }
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
        Node<Register> cur = registerList.head;
        while (cur != null) {
            System.out.println("========================");
            cur.data.displayRegistrationInfo();
            System.out.println("========================");
            cur = cur.next;
        }
    }

    //3.4
    public void saveToFile() {
        // Step 1: Collect all existing (course code, student code) pairs to avoid duplicates
        Set<String> existingRegisters = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("registerings.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] word = line.split("\\\\");
                    if (word.length >= 2) {
                        String combinedKey = (word[0].trim().toUpperCase() + "-" + word[1].trim().toUpperCase());
                        existingRegisters.add(combinedKey); // Store CourseCode-StudentCode as a key
                    }
                }
            }
        } catch (IOException e) {
            // Handle case when the file doesn't exist
            System.out.println("registerings.txt not found. A new file will be created.");
        }

        // Step 2: Write new records that don't already exist in the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("registerings.txt", true))) {
            Node<Register> cur = registerList.head;
            while (cur != null) {
                String combinedKey = cur.data.getCcode().toUpperCase() + "-" + cur.data.getScode().toUpperCase();
                if (!existingRegisters.contains(combinedKey)) {
                    bw.write(cur.data.toString());
                    bw.newLine();
                    existingRegisters.add(combinedKey); // Add new key to avoid future duplicates
                }
                cur = cur.next;
            }
            System.out.println("Register entries saved successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to registerings.txt: " + e.getMessage());
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
    public void updateMark(CourseList courseList, StudentList studentList) {
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
