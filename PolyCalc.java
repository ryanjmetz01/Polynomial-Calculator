import java.util.Scanner; 

/**
 * This class gets input from the user to create Polynomials 
 * and apply an operation to them
 *
 * @author Ryan Metz
 */
public class PolyCalc
{
    /**
     * Prints out the text interface for the calculator 
     * Gets in put from user, creating Polynomials and 
     * applying the chosen operation to the Polynomials
     * 
     * @param args this is ignored
     */
    public static void main(String[] args) {                
        int comp = 1;  
        String op;
        Poly p1; 
        Poly p2; 
        Poly res;  
        String ans; 

        System.out.println("Hello and Welcome to Polynomial Calculator"); 
        System.out.println("             By Ryan Metz"); 
        System.out.println("Instructions:");
        System.out.println("    - Polynomials are created by inputting a coeffecient and an exponent seperated by a space"); 
        System.out.println("      ex: 2.5x^2 - 1 should be input as \"2.5 2 -1 0\"."); 
        System.out.println("    - \"quit\" can be input at anytime to exit the program"); 
        System.out.println(); 

        // keep going until user quits
        while(true) { 
            res = new Poly(); 
            ans = "";

            // get the input fom the user 
            System.out.println("--- Computation #" + comp + " ---");

            System.out.println("Polynomial a:" );
            p1 = process(); 
            if (p1 == null) {
                break; 
            }
            System.out.println("Polynomial a: " + p1); 

            op = op(); 
            if (op == null) {
                break; 
            }

            System.out.println("Polynomial b:"); 
            p2 = process(); 
            if (p2 == null)
                break; 

            System.out.println("Polynomial b: " + p2); 

            ans += "(" + p1; 
            switch (op) {
                case "+" :
                    res = p1.add(p2); 
                    break;
                case "-" :
                    res = p1.subtract(p2); 
                    break; 
                case "*" :
                    res = p1.multiply(p2); 
                    break;
                case "/" : 
                    res = p1.divide(p2); 
                    break; 
            }
            System.out.println(); 

            if (res == null) {
                ans += ") " + op + " (" + p2 + ") = indivible"; 
            }
            else {
                ans +=  ") " + op + " (" + p2 + ") = " + res; 
            }
            System.out.println(ans); 

            comp++; 
            System.out.println();
        }

        System.out.println(); 
        System.out.println("Thank you, and goodbye");  
    }

    /**
     * Processes a Polynomial input from the user and then 
     * returns that Polynomial 
     * 
     * @return a Poly that has been created from user input
     */
    static Poly process() {
        double coef; 
        int exp;
        Poly p1 = new Poly();
        Poly p = new Poly(); 
        Scanner keybScan = new Scanner(System.in); 
        String line = keybScan.nextLine(); 
        Scanner lineScan = new Scanner(line); 

        while (true){   

            // a valid token is available
            if (lineScan.hasNextDouble()) {
                coef = lineScan.nextDouble();
                if (lineScan.hasNextInt()) {
                    exp = lineScan.nextInt(); 
                }
                // had a double but not an int 
                // get new line and go back to top of while loop 
                else {
                    System.out.println("Invalid Polynomial. Consult instructions for how to input Polynomials"); 
                    keybScan = new Scanner(System.in); 
                    line = keybScan.nextLine();
                    lineScan = new Scanner(line);
                    p1 = new Poly(); 
                    continue; 
                }

                if (p1.listy.size() == 0) {
                    p1 = new Poly(coef, exp);
                }
                else {
                    p = new Poly(coef, exp); 
                    p1 = p1.add(p); 
                }
            }
            // there is string next -> check to see if it is quit
            // otherwise ask for new Poly
            else if(lineScan.hasNext()) {
                String s = lineScan.next().toUpperCase();
                if (s.equals("QUIT")) {
                    return null;
                }
                else {
                    System.out.println("Invalid Polynomial. Consult instructions for how to input Polynomials"); 
                    keybScan = new Scanner(System.in); 
                    line = keybScan.nextLine();
                    lineScan = new Scanner(line); 
                }
            }
            else if (p1.getTerms() == 0) {
                System.out.println("Invalid Polynomial. Consult instructions for how to input Polynomials"); 
                keybScan = new Scanner(System.in); 
                line = keybScan.nextLine();
                lineScan = new Scanner(line); 
            }
            // have reached the end of line and have a valid poly 
            else {
                break; 
            }

        }
        return p1; 
    }

    /**
     * returns a String for the operation that the user has picked 
     * 
     * @return a string of the operations that the user chose to apply to two Polys
     */
    public static String op() {
        String op; 

        while (true) {
            System.out.println("Choose the operation (+, -, *, /):"); 
            Scanner keybScan = new Scanner(System.in); 
            op = keybScan.next(); 

            if (op.compareTo("+") == 0 || op.compareTo("-") == 0 || op.compareTo("*") == 0 || op.compareTo("/") == 0) 
                break; 
            else if (op.toUpperCase().equals("QUIT")) {
                return null;  
            }
            else 
                System.out.println("Invalid Operation. Only listed ones are supported");   
        }

        return op; 
    }
}
