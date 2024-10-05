package util;

import java.util.Scanner;

/**
 *
 * @author nhatnhatnhat
 */
public class Validation {

    private static final Scanner sc = new Scanner(System.in);

    public static String getString(String inputMsg, String errorMsg) {
        while (true) {
            System.out.print(inputMsg);
            String string = sc.nextLine().trim();
            if (string.isEmpty()) {
                System.err.println(errorMsg);
            } else {
                return string;
            }
        }
    }

    public static double getPositiveDouble(String msg, String err) {
        while (true) {
            try {
                System.out.print(msg);
                double num = Double.parseDouble(sc.nextLine());
                if (num >= 0) {
                    return num;
                }
                throw new Exception();
            } catch (Exception e) {
                System.err.println(err);
            }
        }
    }

    public static String getValidString(String inputMsg, String errorMsg, String format) {
        while (true) {
            System.out.print(inputMsg);
            String string = sc.nextLine().trim().toUpperCase();
            boolean match = string.matches(format);
            if (string.isEmpty() || !match) {
                System.err.println(errorMsg);
            } else {
                return string;
            }
        }
    }

    public static int getAnInteger(String inputMsg, String errorMsg, int lowerBound, int upperBound) {
        while (true) {
            try {
                System.out.print(inputMsg);
                int n = Integer.parseInt(sc.nextLine());
                if (n < lowerBound || n > upperBound) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.err.println(errorMsg);
            }
        }
    }

}
