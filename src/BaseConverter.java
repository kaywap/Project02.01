import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Base converter, converts numbers of one base to another base
 * @author Kayla Cao
 * @version 11.17.2025
 * Extra feature (lines 64-80): Detects and flags invalid numerals (e.g., a base-8 number containing the digit 8 or higher)
 * Flint session: https://app.flintk12.com/activities/algorithm-helpe-1ae606/sessions/e50a8fd9-13b8-4f40-8840-243cb6fb037e
 */
public class BaseConverter {
    private final String ALPHANUM = "0123456789ABCDEF";
    /**
     * Convert a String num in fromBase to base-10 int.
     * @param num the existing number
     * @param fromBase the existing base
     * @return the number in the fromBase to a base-10 int to be used for conversion
     */
    public int strToInt(String num, String fromBase) {
        // num = "CE" fromBase = 15
        // CE in base 15 = 195 in base 10
        int fb = Integer.parseInt(fromBase);
        int b10num = 0;
        for (int exp = 0; exp < num.length(); exp++) {
            b10num += (int) (ALPHANUM.indexOf(num.charAt(num.length() - 1 - exp)) * Math.pow(fb, exp));
        }
        return b10num;
    }

    /**
     * Convert a base-10 int to a String number of base toBase.
     * @param num the existing number in base 10
     * @param toBase the base to convert to
     * @return the num converted to the toBase base
     */
    public String intToStr(int num, int toBase) {
        //checking for zero to avoid issues
        if (num == 0) {
            return "0";
        }
        String convertedNum = "";
        while (num > 0) {
            convertedNum = ALPHANUM.charAt(num % toBase) + convertedNum;
            num /= toBase;
        }
        return convertedNum;
    }

    /**
     * Opens the file stream, inputs data one line at a time, converts, prints the result to the console window and writes data to the output stream.
     */
    public void inputConvertPrintWrite() {
        Scanner in = null;
        PrintWriter pw = null;
        try {
            in = new Scanner(new File("datafiles/values10.dat"));
            String[] temp;
            pw = new PrintWriter(new FileWriter("converted.dat"));
            while (in.hasNextLine()) {
                temp = in.nextLine().split("\t");

                //EXTRA: flagging invalid numerals (for example, a base-8 number contains a digit greater than 7)
                boolean baseIssue = false;
                boolean invalidDigit = false;

                for (int i = 0; i < temp[0].length(); i++) {
                    String digit = String.valueOf(temp[0].charAt(i)); // for every digit in original num
                    // if a digit in original num is greater than base
                    if (ALPHANUM.indexOf(digit) >= Integer.parseInt(temp[1])) {
                        baseIssue = true;
                        break;
                    }
                    //or if it contains a digit not in ALPHANUM
                    else if (!ALPHANUM.contains(digit)) {
                        invalidDigit = true;
                        break;
                    }
                }
                if (baseIssue) {
                    System.out.println("Invalid input, " + temp[0] + " contains digits greater than its base");
                }
                else if (invalidDigit) {
                    System.out.println("Invalid input, " + temp[0] + " contains an invalid digit");
                }

                else if (Integer.parseInt(temp[1]) < 2 || Integer.parseInt(temp[1]) > 16) {
                    System.out.println("Invalid input base " + temp[1]);
                }
                else if (Integer.parseInt(temp[2]) < 2 || Integer.parseInt(temp[2]) > 16) {
                    System.out.println("Invalid output base " + temp[2]);
                }
                else {
                    System.out.println(temp[0] + " base " + temp[1] + " = " + intToStr(strToInt(temp[0], temp[1]), Integer.parseInt(temp[2])) + " base " + temp[2]);
                    pw.println(temp[0] + "\t" +  temp[1] + "\t" + intToStr(strToInt(temp[0], temp[1]), Integer.parseInt(temp[2])) + "\t" + temp[2]);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Here was your error:");
            System.out.println(e.toString());
        }
        finally {
            // always runs
            if (in != null) in.close();
            if (pw != null) pw.close();
        }
    }

    /**
     * Main method for class BaseConverter. Creates an instance and runs the conversion process.
     * @param args command line arguments, if needed
     */
    public static void main(String[] args) {
        BaseConverter app = new BaseConverter();
        app.inputConvertPrintWrite();
    }
}
