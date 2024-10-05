package model;

public class Course {

    private String ccode;
    private String scode;
    private String sname;
    private String semester;
    private String year;
    private int seats;
    private int registered;
    private double price;

    public Course() {
    }

    public Course(String ccode, String scode, String sname, String semester, String year, int seats, double price) {
        this.ccode = ccode;
        this.scode = scode;
        this.sname = sname;
        this.semester = semester;
        this.year = year;
        this.price = price;
        this.seats = seats;
    }

    public Course(String ccode, String scode, String sname, String semester, String year, int seats, int registered, double price) {
        this.ccode = ccode;
        this.scode = scode;
        this.sname = sname;
        this.semester = semester;
        this.year = year;
        this.seats = seats;
        this.registered = registered;
        this.price = price;
    }

    public String getCcode() {
        return ccode;
    }

    public String getSname() {
        return sname;
    }

    public int getSeats() {
        return seats;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered() {
        this.registered += 1;
    }

    @Override
    public String toString() {
        return ccode + "\\" + scode + "\\" + sname + "\\"
                + semester + "\\" + year + "\\" + seats + "\\"
                + registered + "\\" + price;
    }

    public void displayCourseInfo() {
        System.out.println("Course code: " + ccode);
        System.out.println("Subject code: " + scode);
        System.out.println("Student name: " + sname);
        System.out.println("Semester: " + semester);
        System.out.println("Year: " + year);
        System.out.println("Seats: " + seats);
        System.out.println("Registered: " + registered);
        System.out.println("Price: " + price);

    }

}
