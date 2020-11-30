package fracCalc;
import java.util.*;

public class FracCalc {

    public static void main(String[] args) 
    {
        // TODO: Read the input from the user and call produceAnswer with an equation
        String input = "";
        Scanner console = new Scanner(System.in);
        while(!(input.equals("quit"))){
          System.out.print("Input: "); input = console.nextLine();
          System.out.println(produceAnswer(input));
          System.out.println();
        }
        console.close();
    }
    
    // ** IMPORTANT ** DO NOT DELETE THIS FUNCTION.  This function will be used to test your code
    // This function takes a String 'input' and produces the result
    //
    // input is a fraction string that needs to be evaluated.  For your program, this will be the user input.
    //      e.g. input ==> "1/2 + 3/4"
    //        
    // The function should return the result of the fraction after it has been calculated
    //      e.g. return ==> "1_1/4"
    public static String produceAnswer(String input)
    { 
        // TODO: Implement this function to produce the solution to the input
        if(input.equals("quit")){
          return "You have exited the program!";
        }

        String[] input_array = input.split(" ");
        String Frac1 = input_array[0];
        String Operator = input_array[1];
        String Frac2 = input_array[2];
        return "Frac 1: " + Frac1 + " \nOperator: " + Operator + " \nFrac2 : " + Frac2;
    }

    // TODO: Fill in the space below with any helper methods that you think you will need
    
}
