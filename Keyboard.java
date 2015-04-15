/*
    Keyboard.java
    CIS 260
    Dave Klick
    2015-01-12
    
    This class can be used to create an object that is
    used for user input validation from the keyboard.
    It currently handles ints, doubles and Strings, but
    can easily be extended to include other data types.
    
    To use it:
    - make sure this class is in the same directory as the
      class that will be using it
    - create a Keyboard object
        Keyboard kbd = new Keyboard();
    - call the appropriate method
        int n = kbd.getInt("Enter an integer between 1 and 5: ", 1, 5);
*/
import java.util.Scanner;

public class Keyboard {
    private Scanner kbd;

    // default constructor
    public Keyboard() {
        kbd = new Scanner(System.in);
    }
    
    // used to get a String from the user with no prompt
    public String getString() {
        return kbd.nextLine();
    }
    
    // used to get a String from the user with a prompt
    public String getString(String prompt) {
        System.out.print(prompt);
        return kbd.nextLine();
    }
    
    // used to get an integer from the user with no prompt
    public int getInt() {
        return getInt("", Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    // used to get an integer from the user with a prompt
    public int getInt(String prompt) {
        return getInt(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    // used to get an integer from the user with a prompt and
    //     minimum and maximum values
    public int getInt(String prompt, long min, long max) {
        if (min > max) {
            long temp = min;
            min = max;
            max = temp;
        }
        long n = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            String s = kbd.nextLine();
            try {
                n = Long.parseLong(s);
                if (n < min) {
                    System.out.println("Error: Below minimum value of " + min);
                } else if (n > max) {
                    System.out.println("Error: Above maximum value of " + max);
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer");
            }
        }
        return (int) n;
    }
    
    // used to get a double from the user with no prompt
    public double getDouble() {
        return getDouble("");
    }

    // used to get a double from the user with a prompt
    public double getDouble(String prompt) {
        return getDouble(prompt, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    // used to get a double from the user with a prompt and
    //     minimum and maximum values
    public double getDouble(String prompt, double min, double max) {
        double num = 0.0;
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print(prompt);
                String strIn = kbd.nextLine();
                num = Double.parseDouble(strIn);
                if (num < min) {
                    System.out.println("Number below minimum of " + min);
                } else if (num > max) {
                    System.out.println("Number above maximum of " + max);
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number");
            }
        }
        return num;
    }
    
    // used to get a char from the user with no prompt
    public char getChar() {
        String s = null;
        boolean valid = false;
        while (!valid) {
            s = kbd.nextLine();
            if (s.length() != 1) {
                System.out.print("Invalid entry, please re-enter: ");
            } else {
                valid = true;
            }
        }
        return s.charAt(0);
    }

    // used to get a char from the user with a prompt
    public char getChar(String prompt) {
        System.out.print(prompt);
        return getChar();
    }

    // used to get a char from the user with a prompt and a set of allowed values
    public char getChar(String prompt, String allowed) {
        char c = ' ';
        do {
            System.out.print(prompt);
            c = getChar();
            if (allowed.indexOf(c) == -1) {
                System.out.println("Error: Character entered is not a valid response.");
            }
        } while (allowed.indexOf(c) == -1);
        return c;
    }

}
