package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Register {

    private String ccode;
    private String scode;
    private Date bdate;
    private double mark;
    private int state;

    public Register() {
    }

    public Register(String ccode, String scode, Date bdate, double mark) {
        this.ccode = ccode;
        this.scode = scode;
        this.bdate = bdate;
        this.mark = mark;
        this.state = (mark >= 5) ? 1 : 0;
    }

    public String getCcode() {
        return ccode;
    }

    public String getScode() {
        return scode;
    }

    public String getBdate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(bdate);
    }

    public void setMark(double mark) {
        this.mark = mark;
        this.state = (mark >= 5) ? 1 : 0;
    }

    @Override
    public String toString() {
        return ccode + "\\" + scode + "\\" + getBdate() + "\\" + mark + "\\" + state;
    }

    public void displayRegistrationInfo() {
        System.out.println("Course Code: " + ccode);
        System.out.println("Student Code: " + scode);
        System.out.println("Registration date " + getBdate());
        System.out.println("Mark: " + mark);
        System.out.println("State: " + state);
    }
}
