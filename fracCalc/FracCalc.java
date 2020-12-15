package fracCalc;

import java.util.*;

public class FracCalc {

  public static void main(String[] args) {
    // TODO: Read the input from the user and call produceAnswer with an equation
    String input = "";
    System.out.println("Enter a mathematical statement with fractions or 'quit' to exit the program.");
    Scanner console = new Scanner(System.in);
    while (!(input.equals("quit"))) {
      System.out.print("Input: ");
      input = console.nextLine(); // Takes input
      System.out.println(produceAnswer(input));
      System.out.println();
    }
    console.close();
  }

  // ** IMPORTANT ** DO NOT DELETE THIS FUNCTION. This function will be used to
  // test your code
  // This function takes a String 'input' and produces the result
  //
  // input is a fraction string that needs to be evaluated. For your program, this
  // will be the user input.
  // e.g. input ==> "1/2 + 3/4"
  //
  // The function should return the result of the fraction after it has been
  // calculated
  // e.g. return ==> "1_1/4"
  public static String produceAnswer(String input) {

    // TODO: Implement this function to produce the solution to the input
    if (input.equals("quit")) {
      return "You have exited the program!";
    }

    // processes input into operands, then runs operations
    String[] operands = input.split(" ");
    Frac result = new Frac(operands[0]); // Makes a new Frac object that will be returned as the result of the input

    for (int i = 2; i < operands.length; i += 2) {
      result.calcFrac(new Frac(operands[i]), operands[i - 1]);
    }

    return result.string;
  }

  // TODO: Fill in the space below with any helper methods that you think you will
  // need

  // Parses strings into their int values
  public static int parseInt(String string) {
    int num = 0;
    int sign = 0;
    boolean isValid = true;
    if (string.indexOf("-") == 0) {
      sign = -1;
      string = string.substring(1);
    } else {
      sign = 1;
    }

    for (int i = 0; i < string.length(); i++) {
      char character = string.charAt(i);
      int asciiValue = (int) character;
      isValid = (asciiValue >= 48 && asciiValue <= 57) && isValid; // Records whether the character is a valid digit

      if (isValid) {
        num += ((int) character - 48) * Math.pow(10, string.length() - i - 1);
      }
    }

    if (isValid) {
      num *= sign;
      return num;
    } else {
      // System.out.println("Invalid Statement");
      return 0;
    }

  }

  // Represents a fraction
  static class Frac {
    int whole;
    int numerator;
    int denominator;
    boolean isPositive; // stores the sign of the fraction
    String string; // String representation of the fraction
    String[] splitFrac; // Array of whole, numerator, denominator as Strings

    // Constructor
    public Frac(String frac) {
      splitFrac = splitFractionString(frac);
      whole = parseInt(splitFrac[0]);
      numerator = parseInt(splitFrac[1]);
      denominator = parseInt(splitFrac[2]);

      isPositive = whole + ((double) numerator / denominator) > 0;
      if (!isPositive) {
        whole = Math.abs(whole);
        numerator = Math.abs(numerator);
      }

      simplify();
    }

    // Operation Method
    public void calcFrac(Frac fracTwo, String operator) {
      makeImproper();
      fracTwo.makeImproper();

      if (operator.equals("+") || operator.equals("-")) {

        if (!isPositive) {
          numerator *= -1;
          isPositive = true;
        }
        if (!fracTwo.isPositive) {
          fracTwo.numerator *= -1;
          fracTwo.isPositive = true;
        }

        if (operator.equals("-")) {
          fracTwo.numerator *= -1;
        }
        numerator = (numerator * fracTwo.denominator) + (fracTwo.numerator * denominator);
        denominator *= fracTwo.denominator;

        if (numerator < 0) {
          numerator = Math.abs(numerator);
          isPositive = false;
        } else {
          isPositive = true;
        }
      } else if (operator.equals("*")) {
        numerator *= fracTwo.numerator;
        denominator *= fracTwo.denominator;
      } else if (operator.equals("/")) {
        numerator *= fracTwo.denominator;
        denominator *= fracTwo.numerator;
      }
      if (operator.equals("*") || operator.equals("/")) // Adjusts sign based on the signs of the operands
      {
        if ((isPositive && !fracTwo.isPositive) || (!isPositive && fracTwo.isPositive)) {
          isPositive = false;
        } else {
          isPositive = true;
        }
        if (numerator == 0) {
          isPositive = true;
        }
      }

      simplify();
    }

    public String[] splitFractionString(String frac) {
      String[] fracArray = new String[3]; // Stores whole, numerator, and denominator as Strings
      if (frac.contains("_")) {
        String[] wholeAndFrac = frac.split("_"); // Splits frac into whole and fractional part;
        fracArray[0] = wholeAndFrac[0]; // Sets first index of fracArray to the first index of wholeAndFrac
        String[] NumAndDen = wholeAndFrac[1].split("/"); // Numerator and denominator as Strings in an Array
        fracArray[1] = NumAndDen[0]; // Add numerator to fracArray
        fracArray[2] = NumAndDen[1]; // Add denominator to fracArray
      } else {
        if (frac.contains("/")) { // If no whole number is given, check for a fraction
          fracArray[0] = "0";
          String[] splitFrac = frac.split("/");
          fracArray[1] = splitFrac[0];
          fracArray[2] = splitFrac[1];
        } else { // if no fraction, the default fractional part is 0/1. Ex: 6 is set as 6_0/1
          fracArray[0] = frac;
          fracArray[1] = "0";
          fracArray[2] = "1";
        }
      }

      return fracArray;
    }

    public void simplify() {

      // Takes the sign off of the whole or the numerator and stores it in in
      // isPositive
      if ((!isPositive && (whole > 0) && (numerator > 0)) || isPositive && (whole < 0 || numerator < 0)) {
        whole = Math.abs(whole);
        numerator = Math.abs(numerator);
        isPositive = false;
      } else if (!isPositive && (whole < 0 || numerator < 0)) {
        whole = Math.abs(whole);
        numerator = Math.abs(numerator);
        isPositive = true;
      }

      while (numerator > denominator && denominator != 0) {
        numerator -= denominator;
        whole++;
      }

      for (int i = numerator; i > 0; i--) {
        if (numerator % i == 0 && denominator % i == 0) {
          numerator /= i;
          denominator /= i;
        }
      }
      if (numerator == denominator) {
        numerator = 0;
        whole++;
      }

      updateStrings();
    }

    // Rebuilds string variable with simplified values
    private void updateStrings() {
      string = "";
      splitFrac[0] = whole + "";
      splitFrac[1] = numerator + "";
      splitFrac[2] = denominator + "";

      if (numerator == 0 || denominator == 1) {
        if (denominator == 1) {
          whole += numerator;
        }
        string = whole + "";
      } else if (whole == 0) {
        if (numerator == 0) {
          string = "0";
        } else {
          string = numerator + "/" + denominator;
        }

      } else if (whole != 0 && numerator != 0) {

        string = whole + "_" + numerator + "/" + denominator;
      } else if (whole == 0 && numerator == 0) {
        string = "0";
      }

      if (!isPositive) {
        string = "-" + string;
      }

      if (denominator == 0) {
        string = "Invalid Input";
      }
    }

    // Turns frac into an improper fraction
    public void makeImproper() {
      simplify();
      numerator += whole * denominator;
      whole = 0;
      string = numerator + "/" + denominator;
    }

    public String toString() {
      return string;
    }
  }
}
