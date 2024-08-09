
/**
 * This class represents a Monomial e.g. 3x^2
 *
 * @author Ryan Metz
 * @version (a version number or a date)
 */
public class Mono
{
    /** the coefficiant for the monomial **/ 
    double coef; 
    /** the exponent for the monomial **/ 
    int exp; 

    /**
     * contructor for the Mono class 
     * 
     * @param coef the coefficiant for the monomial
     * @param exp the exponent for the monomial
     */
    public Mono(double coef, int exp) {
        this.coef = coef; 
        this.exp = exp; 
    }

    /**
     * Returns a textual representation of mono that this is called on in the 
     * form of 3x^2. Coefficients of 1 and 0 are not shown as well as "^1" is also omitted
     * 
     * 
     * @return a textual representation of a Mono
     */
    public String toString() {
        String res =  "";

        if (exp == 1 && coef == 1) {
            res += "x"; 
        }            
        else if (exp == 1) {
            res += Math.abs(coef) + "x"; 
        }
        else if(exp == 0) {
            res += Math.abs(coef); 
        }
        else if (coef == 1) {
            res += "x^" + exp;
        }
        else {
            res += Math.abs(coef) + "x^" + exp;
        }

        
        return res; 
    }
    
}
